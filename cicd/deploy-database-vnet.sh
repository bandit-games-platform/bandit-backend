#!/bin/bash

#---------------------------------------------------------------
#- Name:	    deploy-database-vnet.sh
#- Author:	  Roman Gordon
#- Function:	Deploys a vnet if not exists and postgres database if not exists for our development environment
#- Usage:	    ./deploy-database-vnet.sh
#---------------------------------------------------------------

# Variables
VNET_NAME="banditDevVnetCh"
SUBNET_NAME="devSubnetCh"
SUBNET_DB_NAME="devDbSubnetCh"
DB_SERVER_NAME="banditdevpostgresch"
ENV_NAME="env-dev-containers-ch"
RG_NAME="rg_bandit_games_dev"

PG_ADMIN_USER=$DEV_PG_ADMIN_USR
PG_ADMIN_PASSWORD=$DEV_PG_ADMIN_PWD
PG_NON_ADMIN_USER=$DEV_PG_USER
PG_NON_ADMIN_PASSWORD=$DEV_PG_PWD

# Check and create VNet if it doesn't exist
VNET_EXISTS=$(az network vnet list --resource-group $RG_NAME --query "[?name=='$VNET_NAME'].name" -o tsv)
if [ -z "$VNET_EXISTS" ]; then
    az network vnet create --name $VNET_NAME --resource-group $RG_NAME --location switzerlandnorth --address-prefix 10.0.0.0/16
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
    echo "VNet $VNET_NAME already exists."
fi


# Check and create Container Apps environment if it doesn't exist
ENV_EXISTS=$(az containerapp env list --resource-group $RG_NAME --query "[?name=='$ENV_NAME'].name" -o tsv)
if [ -z "$ENV_EXISTS" ]; then
    INFRASTRUCTURE_SUBNET=$(az network vnet subnet show --resource-group ${RG_NAME} --vnet-name $VNET_NAME --name ${SUBNET_NAME} --query "id" -o tsv | tr -d '[:space:]')

    az containerapp env create --name $ENV_NAME --resource-group $RG_NAME --location switzerlandnorth --infrastructure-subnet-resource-id "$INFRASTRUCTURE_SUBNET"
    echo "Container Apps environment $ENV_NAME created."
else
    echo "Container Apps environment $ENV_NAME already exists."
fi


# Check and create PostgreSQL server if it doesn't exist
DB_EXISTS=$(az postgres flexible-server list --resource-group $RG_NAME --query "[?name=='$DB_SERVER_NAME'].name" -o tsv)
if [ -z "$DB_EXISTS" ]; then
    az postgres flexible-server create --name $DB_SERVER_NAME --resource-group $RG_NAME \
       --location switzerlandnorth \
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

    az containerapp create \
        --name init-container \
        --resource-group $RG_NAME \
        --environment $ENV_NAME \
        --image postgres:latest \
        --command "sh -c 'PGPASSWORD=$PG_ADMIN_PASSWORD psql -h $DB_SERVER_NAME.postgres.database.azure.com -U $PG_ADMIN_USER -d bandit_db -c \"
        CREATE SCHEMA IF NOT EXISTS chatbot;
        CREATE SCHEMA IF NOT EXISTS gameplay;
        CREATE SCHEMA IF NOT EXISTS game_registry;
        CREATE SCHEMA IF NOT EXISTS player;
        CREATE SCHEMA IF NOT EXISTS statistics;
        CREATE SCHEMA IF NOT EXISTS storefront;
        \" && PGPASSWORD=$PG_ADMIN_PASSWORD psql -h $DB_SERVER_NAME.postgres.database.azure.com -U $PG_ADMIN_USER -d bandit_db -c \"CREATE USER $PG_NON_ADMIN_USER WITH PASSWORD '$PG_NON_ADMIN_PASSWORD';\" && PGPASSWORD=$PG_ADMIN_PASSWORD psql -h $DB_SERVER_NAME.postgres.database.azure.com -U $PG_ADMIN_USER -d bandit_db -c \"SELECT * FROM information_schema.schemata;\"'"

#    az containerapp delete --name init-container --resource-group $RG_NAME --yes

    echo "PostgreSQL server $DB_SERVER_NAME created."
else
    echo "PostgreSQL server $DB_SERVER_NAME already exists."
fi
