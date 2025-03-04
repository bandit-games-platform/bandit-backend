#!/bin/bash

#---------------------------------------------------------------
#- Name:	    deploy-database-vnet.sh
#- Author:	  Roman Gordon
#- Function:	Deploys a vnet if not exists, creates the container app environment and postgres database if not exists
#- Usage:	    ./deploy-database-vnet.sh
#---------------------------------------------------------------

# Variables
VNET_NAME="null"
SUBNET_NAME="null"
SUBNET_DB_NAME="null"
DB_SERVER_NAME="null"
ENV_NAME="null"
RG_NAME="null"

PG_ADMIN_USER="null"
PG_ADMIN_PASSWORD="null"
PG_NON_ADMIN_USER=$DEV_PG_USER
PG_NON_ADMIN_PASSWORD=$DEV_PG_PWD

declare -A provided_values
for pair in $1; do
    IFS='=' read -r key value <<< "$pair"
    provided_values["$key"]="$value"
done

for key in "${!provided_values[@]}"; do
    if declare -p "$key" &>/dev/null; then
        declare "$key=${provided_values[$key]}"
    else
        echo "Warning: Variable $key is not defined."
    fi
done

# Check and create VNet if it doesn't exist
VNET_EXISTS=$(az network vnet list --resource-group $RG_NAME --query "[?name=='$VNET_NAME'].name" -o tsv)
if [ -z "$VNET_EXISTS" ]; then
    az network vnet create --name $VNET_NAME --resource-group $RG_NAME --location northeurope --address-prefix 10.0.0.0/16
    az network vnet subnet create --name $SUBNET_NAME --resource-group $RG_NAME --vnet-name $VNET_NAME --address-prefix 10.0.1.0/24
    az network vnet subnet update \
      --resource-group $RG_NAME \
      --vnet-name $VNET_NAME \
      --name $SUBNET_NAME \
      --delegations Microsoft.App/environments

    az network vnet subnet create --name $SUBNET_DB_NAME --resource-group $RG_NAME --vnet-name $VNET_NAME --address-prefix 10.0.2.0/24
    az network vnet subnet update \
      --resource-group $RG_NAME \
      --vnet-name $VNET_NAME \
      --name $SUBNET_DB_NAME \
      --delegations Microsoft.DBforPostgreSQL/flexibleServers

    echo "VNet $VNET_NAME created."
else
    echo "VNet $VNET_NAME already exists, moving on."
fi


# Check and create Container Apps environment if it doesn't exist
ENV_EXISTS=$(az containerapp env list --resource-group $RG_NAME --query "[?name=='$ENV_NAME'].name" -o tsv)
if [ -z "$ENV_EXISTS" ]; then
    INFRASTRUCTURE_SUBNET=$(az network vnet subnet show --resource-group ${RG_NAME} --vnet-name $VNET_NAME --name ${SUBNET_NAME} --query "id" -o tsv | tr -d '[:space:]')

    az containerapp env create --name $ENV_NAME --resource-group $RG_NAME --location northeurope --infrastructure-subnet-resource-id "$INFRASTRUCTURE_SUBNET"
    echo "Container Apps environment $ENV_NAME created."
else
    echo "Container Apps environment $ENV_NAME already exists, moving on."
fi


# Check and create PostgreSQL server if it doesn't exist
DB_EXISTS=$(az postgres flexible-server list --resource-group $RG_NAME --query "[?name=='$DB_SERVER_NAME'].name" -o tsv)
if [ -z "$DB_EXISTS" ]; then
    az postgres flexible-server create --name $DB_SERVER_NAME --resource-group $RG_NAME \
       --location northeurope \
       --admin-user "$PG_ADMIN_USER" \
       --admin-password "$PG_ADMIN_PASSWORD" \
       --tier Burstable \
       --sku-name standard_b1ms \
       --storage-size 32 \
       --vnet $VNET_NAME \
       --subnet $SUBNET_DB_NAME \
       --database-name "bandit_db" \
       --yes

    echo "Database has been created, waiting for it to be ready before initialising and creating user"

    MAX_RETRIES=30
    COUNT=0
    # Wait for the database server to be ready
    while [ "$(az postgres flexible-server show --name $DB_SERVER_NAME --resource-group $RG_NAME --query \"state\" -o tsv)" != "Ready" ]
    do
      COUNT=$((COUNT + 1))
      if [ $COUNT -ge $MAX_RETRIES ]; then
        echo "Error: PostgreSQL server did not become ready in time."
        exit 1
      fi

      echo "Waiting for PostgreSQL server to be ready... ($COUNT/$MAX_RETRIES)"
      sleep 10
    done

    echo "Database is now ready"

# TODO: Since this does not work in the current version of azure, schema and permissions have to be granted manually

#    az containerapp create \
#        --name init-container \
#        --resource-group $RG_NAME \
#        --environment $ENV_NAME \
#        --image postgres:16-alpine \
#        --command "/bin/sh" \
#        --args "-c, while true; do echo hello; sleep 10; done"

        # --command "/bin/sh -c 'PGPASSWORD=$PG_ADMIN_PASSWORD psql -h $DB_SERVER_NAME.postgres.database.azure.com -U $PG_ADMIN_USER -d bandit_db -c \"CREATE USER $PG_NON_ADMIN_USER WITH PASSWORD '$PG_NON_ADMIN_PASSWORD';\" && PGPASSWORD=$PG_ADMIN_PASSWORD psql -h $DB_SERVER_NAME.postgres.database.azure.com -U $PG_ADMIN_USER -d bandit_db -c \"GRANT ALL PRIVILEGES ON DATABASE bandit_db TO $DEV_PG_USER;\" && PGPASSWORD=$PG_NON_ADMIN_PASSWORD psql -h $DB_SERVER_NAME.postgres.database.azure.com -U $PG_NON_ADMIN_USER -d bandit_db -c \"
        #    CREATE SCHEMA IF NOT EXISTS chatbot;
        #    CREATE SCHEMA IF NOT EXISTS gameplay;
        #    CREATE SCHEMA IF NOT EXISTS game_registry;
        #    CREATE SCHEMA IF NOT EXISTS player;
        #    CREATE SCHEMA IF NOT EXISTS statistics;
        #    CREATE SCHEMA IF NOT EXISTS storefront;
        #    \" && PGPASSWORD=$PG_ADMIN_PASSWORD psql -h $DB_SERVER_NAME.postgres.database.azure.com -U $PG_ADMIN_USER -d bandit_db -c \"SELECT * FROM information_schema.schemata;\"'" "docker-entrypoint.sh"

#    az containerapp delete --name init-container --resource-group $RG_NAME --yes

    echo "PostgreSQL server $DB_SERVER_NAME created."
    echo "WARNING: You will have to complete the initialization of the database manually!"
    echo "  See: https://docs.google.com/document/d/1IbtubPYPNUIkIFZi3LDxaL2bxK6UrxXJCm9nBcznmaU/edit?tab=t.0#heading=h.ssl3r5y3wqsx"
else
    echo "PostgreSQL server $DB_SERVER_NAME already exists, moving on."
fi
