#!/bin/bash

#---------------------------------------------------------------
#- Name:	    deploy-database-vnet.sh
#- Author:	  Roman Gordon
#- Function:	Deploys a vnet if not exists and postgres database if not exists for our development environment
#- Usage:	    ./deploy-database-vnet.sh
#---------------------------------------------------------------

# Variables
VNET_NAME="banditDevVnet"
SUBNET_NAME="devSubnet"
DB_SERVER_NAME="banditdevpostgres"
ENV_NAME="dev-containers"
RG_NAME="rg_bandit_games_dev"

IMAGE_NAME="acrbanditgamesdev.azurecr.io/init-script-image:latest"

PG_ADMIN_USER=$DEV_PG_ADMIN_USR
PG_ADMIN_PASSWORD=$DEV_PG_ADMIN_PWD
PG_NON_ADMIN_USER=$DEV_PG_USER
PG_NON_ADMIN_PASSWORD=$DEV_PG_PWD

# Check and create VNet if it doesn't exist
VNET_EXISTS=$(az network vnet list --resource-group $RG_NAME --query "[?name=='$VNET_NAME'].name" -o tsv)
if [ -z "$VNET_EXISTS" ]; then
    az network vnet create --name $VNET_NAME --resource-group $RG_NAME --location northeurope --address-prefix 10.0.0.0/16
    az network vnet subnet create --name $SUBNET_NAME --resource-group $RG_NAME --vnet-name $VNET_NAME --address-prefix 10.0.1.0/24
    echo "VNet $VNET_NAME created."
else
    echo "VNet $VNET_NAME already exists."
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
       --subnet $SUBNET_NAME \
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

    az containerapp create \
        --name init-container \
        --resource-group $RG_NAME \
        --environment dev-containers \
        --image $IMAGE_NAME \
        --command "sh -c 'PGPASSWORD=$PG_ADMIN_PASSWORD psql -h $DB_SERVER_NAME.postgres.database.azure.com -U $PG_ADMIN_USER -d postgres -c \"CREATE USER $PG_NON_ADMIN_USER WITH PASSWORD '$PG_NON_ADMIN_PASSWORD';\"'"

    az containerapp delete --name init-container --resource-group $RG_NAME --yes

    echo "PostgreSQL server $DB_SERVER_NAME created."
else
    echo "PostgreSQL server $DB_SERVER_NAME already exists."
fi

# Check and create Container Apps environment if it doesn't exist
ENV_EXISTS=$(az containerapp env list --resource-group $RG_NAME --query "[?name=='$ENV_NAME'].name" -o tsv)
if [ -z "$ENV_EXISTS" ]; then
    az containerapp env create --name $ENV_NAME --resource-group $RG_NAME --location northeurope --infrastructure-subnet $SUBNET_NAME --vnet $VNET_NAME
    echo "Container Apps environment $ENV_NAME created."
else
    echo "Container Apps environment $ENV_NAME already exists."
fi
