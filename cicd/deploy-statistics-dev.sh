#!/bin/bash

#---------------------------------------------------------------
#- Name:	    deploy-statistics-dev.sh
#- Author:	  Roman Gordon
#- Function:	Deploys the statistics container for dev on azure
#- Usage:	    ./deploy-statistics-dev.sh
#---------------------------------------------------------------

CONTAINER_NAME="statistics-dev-container"
RESOURCE_GROUP="rg_bandit_games_dev"
PG_USER=$DEV_PG_USR
PG_PASSWORD=$DEV_PG_PWD
# Database fully qualified name
DB_FQDN=$(az postgres flexible-server show --name "banditdevpostgres" --resource-group rg_bandit_games_dev --query "fullyQualifiedDomainName" --output tsv)
JDBC_URL="jdbc:postgresql://$DB_FQDN:5432/bandit_db"

echo "Bringing up container app"
az containerapp up --name $CONTAINER_NAME --resource-group $RESOURCE_GROUP \
  --location northeurope --environment dev-containers \
  --image "$REGISTRY_USERNAME".azurecr.io/statistics-context:"${CI_COMMIT_SHORT_SHA::-1}" \
  --target-port 8095 --ingress external

az containerapp secret set --name $CONTAINER_NAME --resource-group $RESOURCE_GROUP \
      --secrets \
      database-usr-pwd="$PG_PASSWORD"

az containerapp update --name $CONTAINER_NAME --resource-group $RESOURCE_GROUP \
  --cpu 1 --memory 2Gi \
  --min-replicas 0 --max-replicas 1 \
  --set-env-vars DATASOURCE_URL="$JDBC_URL" DATASOURCE_USER="$PG_USER" DATASOURCE_PASS=secretref:database-usr-pwd
