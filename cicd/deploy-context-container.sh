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
STRIPE_API_KEY=
KEYCLOAK_CLIENT_SECRET=
EXTRA_ENV_VARS=

declare -A provided_values
for pair in $1; do
    IFS='=' read -r key value <<< "$pair"
    provided_values["$key"]="$value"
done

for key in "${!provided_values[@]}"; do
    if declare -p "$key" &>/dev/null; then
        declare "$key=${provided_values[$key]}"
    else
        echo "Warning: Variable $key is not defined, adding to extra environment variables (make sure this isn't secret it won't be kept secret)."
        EXTRA_ENV_VARS+="$key=${provided_values[$key]} "
    fi
done

# Database fully qualified name
DB_FQDN=$(az postgres flexible-server show --name "$PG_DB_NAME" --resource-group $RESOURCE_GROUP --query "fullyQualifiedDomainName" --output tsv)
JDBC_URL="jdbc:postgresql://$DB_FQDN:5432/bandit_db"

echo "Bringing up container app"
az containerapp up --name $CONTAINER_NAME --resource-group $RESOURCE_GROUP \
  --location northeurope --environment $ENVIRONMENT \
  --image "$REGISTRY_USERNAME".azurecr.io/$IMAGE_NAME:"${CI_COMMIT_SHORT_SHA::-1}" \
  --target-port $PORT --ingress external

SECRET_ARGS=()
SECRET_ARGS+=("database-usr-pwd=$PG_PASSWORD")

if [ -n "$KEYCLOAK_CLIENT_SECRET" ]; then
  SECRET_ARGS+=("keycloak-client-secret=$KEYCLOAK_CLIENT_SECRET")
fi
if [ -n "$STRIPE_API_KEY" ]; then
  SECRET_ARGS+=("stripe-api-key=$STRIPE_API_KEY")
fi

az containerapp secret set --name $CONTAINER_NAME --resource-group $RESOURCE_GROUP \
      --secrets "${SECRET_ARGS[@]}"

unset SECRET_STRING

ENV_VAR_ARGS=()
ENV_VAR_ARGS+=("DATASOURCE_URL=$JDBC_URL")
ENV_VAR_ARGS+=("DATASOURCE_USER=$PG_USER")
ENV_VAR_ARGS+=("DATASOURCE_PASS=secretref:database-usr-pwd")

if [ -n "$KEYCLOAK_CLIENT_SECRET" ]; then
  ENV_VAR_ARGS+=("KEYCLOAK_CLIENT_SECRET=secretref:keycloak-client-secret")
fi
if [ -n "$STRIPE_API_KEY" ]; then
  ENV_VAR_ARGS+=("STRIPE_API_KEY=secretref:stripe-api-key")
fi
if [ -n "$EXTRA_ENV_VARS" ]; then
  for VAR in $EXTRA_ENV_VARS; do
      ENV_VAR_ARGS+=("${VAR}")
    done
fi

echo "$ENV_VAR_STRING"

az containerapp update --name $CONTAINER_NAME --resource-group $RESOURCE_GROUP \
  --cpu 1 --memory 2Gi \
  --min-replicas 0 --max-replicas 1 \
  --set-env-vars "${ENV_VAR_ARGS[@]}"

unset ENV_VAR_STRING
