#!/bin/bash

#---------------------------------------------------------------
#- Name:	    deploy-context-container.sh
#- Author:	  Roman Gordon
#- Function:	Deploys a context container on azure based on provided variables
#- Usage:	    ./deploy-context-container.sh
#---------------------------------------------------------------

SCALE_DOWN=true
CONTAINER_NAME="null"
RESOURCE_GROUP="null"
PG_USER="null"
PG_PASSWORD="null"
PG_DB_NAME="null"
ENVIRONMENT="null"
IMAGE_NAME="null"
REG_USERNAME="null"
PORT=8080
STRIPE_API_KEY=
KEYCLOAK_CLIENT_SECRET=
DDL_PROD=
DB_INIT_MODE=
RABBIT_URL=
PYTHON_URL=
GAME_REGISTRY_URL=
FRONTEND_URL=
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

CONTAINER_EXISTS=$(az containerapp show --name $CONTAINER_NAME --resource-group $RESOURCE_GROUP --query "name" -o tsv)
if [ -n "$CONTAINER_EXISTS" ]; then
  echo "The container app already exists, deleting it!"
  az containerapp delete --name $CONTAINER_NAME --resource-group $RESOURCE_GROUP --yes
fi

echo "Bringing up container app"
az containerapp up --name $CONTAINER_NAME --resource-group $RESOURCE_GROUP \
  --location northeurope --environment $ENVIRONMENT \
  --image "$REG_USERNAME".azurecr.io/$IMAGE_NAME:"${CI_COMMIT_SHORT_SHA::-1}" \
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
ENV_VAR_ARGS+=("RABBITMQ_USER=$RABBITMQ_USER")
ENV_VAR_ARGS+=("RABBITMQ_PASSWORD=$RABBITMQ_PASSWORD")
ENV_VAR_ARGS+=("RABBITMQ_VHOST=$RABBITMQ_VHOST")

if [ -n "$KEYCLOAK_CLIENT_SECRET" ]; then
  ENV_VAR_ARGS+=("KEYCLOAK_CLIENT_SECRET=secretref:keycloak-client-secret")
fi
if [ -n "$STRIPE_API_KEY" ]; then
  ENV_VAR_ARGS+=("STRIPE_API_KEY=secretref:stripe-api-key")
fi
if [ -n "$DDL_PROD" ]; then
  echo "Using DDL mode: $DDL_PROD"
  ENV_VAR_ARGS+=("DDL_PROD=$DDL_PROD")
fi
if [ -n "$DB_INIT_MODE" ]; then
  echo "Using DB init mode: $DB_INIT_MODE"
  ENV_VAR_ARGS+=("DB_INIT_MODE=$DB_INIT_MODE")
fi
if [ -n "$RABBIT_URL" ]; then
  ENV_VAR_ARGS+=("RABBIT_URL=$RABBIT_URL")
fi
if [ -n "$PYTHON_URL" ]; then
  ENV_VAR_ARGS+=("PYTHON_URL=$PYTHON_URL")
fi
if [ -n "$GAME_REGISTRY_URL" ]; then
  ENV_VAR_ARGS+=("GAME_REGISTRY_URL=$GAME_REGISTRY_URL")
fi
if [ -n "$FRONTEND_URL" ]; then
  ENV_VAR_ARGS+=("FRONTEND_URL=$FRONTEND_URL")
fi
if [ -n "$EXTRA_ENV_VARS" ]; then
  for VAR in $EXTRA_ENV_VARS; do
      ENV_VAR_ARGS+=("${VAR}")
    done
fi

echo "$ENV_VAR_STRING"

if [ "$SCALE_DOWN" = false ]; then
  az containerapp update --name $CONTAINER_NAME --resource-group $RESOURCE_GROUP \
    --cpu 1 --memory 2Gi \
    --min-replicas 1 --max-replicas 1 \
    --set-env-vars "${ENV_VAR_ARGS[@]}"

else
  az containerapp update --name $CONTAINER_NAME --resource-group $RESOURCE_GROUP \
      --cpu 1 --memory 2Gi \
      --min-replicas 0 --max-replicas 1 \
      --set-env-vars "${ENV_VAR_ARGS[@]}"

fi

unset ENV_VAR_STRING


# enabling CORS
az containerapp ingress cors enable \
  --name $CONTAINER_NAME \
  --resource-group $RESOURCE_GROUP \
  --allowed-origins $FRONTEND_URL $PYTHON_URL \
  --allowed-methods "*" \
  --allowed-headers Content-Type Authorization \
  --allow-credentials true
