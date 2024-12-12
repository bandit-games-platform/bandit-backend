#!/bin/bash

#---------------------------------------------------------------
#- Name:	    deploy-context-container.sh
#- Author:	  Roman Gordon
#- Function:	Deploys the statistics container for dev on azure
#- Usage:	    ./deploy-context-container.sh
#---------------------------------------------------------------

CONTAINER_NAME="null"
RESOURCE_GROUP="null"
PG_USER="null"
PG_PASSWORD="null"
PG_DB_NAME="null"
ENVIRONMENT="null"
IMAGE_NAME="null"
PORT=8080
EXTRA_ENV_VARS=""

declare -A provided_values
for pair in $1; do
    IFS='=' read -r key value <<< "$pair"
    provided_values["$key"]="$value"
done

for key in "${!provided_values[@]}"; do
    echo "Key: $key, Value: ${provided_values[$key]}"
    if declare -p "$key" &>/dev/null; then
        declare "$key=${provided_values[$key]}"
    else
        echo "Warning: Variable $key is not defined, adding to extra environment variables."
        EXTRA_ENV_VARS+="$key=${provided_values[$key]} "
    fi
done

echo "CONTAINER_NAME=$CONTAINER_NAME, RESOURCE_GROUP=$RESOURCE_GROUP, PG_USER=$PG_USER, PG_PASSWORD=$PG_PASSWORD, PG_DB_NAME=$PG_DB_NAME, ENVIRONMENT=$ENVIRONMENT, IMAGE_NAME=$IMAGE_NAME, PORT=$PORT"

# Database fully qualified name
DB_FQDN=$(az postgres flexible-server show --name "$PG_DB_NAME" --resource-group $RESOURCE_GROUP --query "fullyQualifiedDomainName" --output tsv)
JDBC_URL="jdbc:postgresql://$DB_FQDN:5432/bandit_db"

echo "Bringing up container app"
az containerapp up --name $CONTAINER_NAME --resource-group $RESOURCE_GROUP \
  --location northeurope --environment $ENVIRONMENT \
  --image "$REGISTRY_USERNAME".azurecr.io/$IMAGE_NAME:"${CI_COMMIT_SHORT_SHA::-1}" \
  --target-port $PORT --ingress external

az containerapp secret set --name $CONTAINER_NAME --resource-group $RESOURCE_GROUP \
      --secrets \
      database-usr-pwd="$PG_PASSWORD"

az containerapp update --name $CONTAINER_NAME --resource-group $RESOURCE_GROUP \
  --cpu 1 --memory 2Gi \
  --min-replicas 0 --max-replicas 1 \
  --set-env-vars DATASOURCE_URL="$JDBC_URL" DATASOURCE_USER="$PG_USER" DATASOURCE_PASS=secretref:database-usr-pwd "$EXTRA_ENV_VARS"
