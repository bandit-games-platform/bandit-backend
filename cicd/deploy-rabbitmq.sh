#!/bin/bash

#------------------------------------------------------------------------------------------------
#- Name:	    deploy-rabbitmq-prod.sh
#- Author:	  Inês da Costa Bastos
#- Function:	Deploys a RabbitMQ containerapp for the production environment on Microsoft Azure
#- Usage:	    ./deploy-rabbitmq-prod.sh
#------------------------------------------------------------------------------------------------



# Variables
ENV_NAME="null"
RESOURCE_GROUP="null"
ACR_NAME="null"
CONTAINER_NAME="null"


# Map
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


# RabbitMQ Containerapp
echo "Deploying RabbitMQ containerapp..."
RABBITMQ_EXISTS=$(az containerapp list --resource-group $RESOURCE_GROUP --query "[?name=='$CONTAINER_NAME'].name" -o tsv)

echo "Creating the container"
if [ -z "$RABBITMQ_EXISTS" ]; then

  az containerapp up \
    --name $CONTAINER_NAME \
    --resource-group $RESOURCE_GROUP \
    --environment $ENV_NAME \
    --image "$ACR_NAME".azurecr.io/rabbitmq:3.13.7-management-alpine \
    --target-port 15672 \
    --ingress external \
    --env-vars RABBITMQ_DEFAULT_USER="$RABBITMQ_USER" RABBITMQ_DEFAULT_PASS="$RABBITMQ_PASSWORD" RABBITMQ_DEFAULT_VHOST="$RABBITMQ_VHOST"

  az containerapp update \
    --name $CONTAINER_NAME \
    --resource-group $RESOURCE_GROUP \
    --min-replicas 0 \
    --max-replicas 1 \
    --cpu 0.5 \
    --memory 1Gi

  az containerapp update \
      --name $CONTAINER_NAME \
      --resource-group $RESOURCE_GROUP \
      --yaml ./cicd/rabbitmq-containerapp.yml

  echo "Creating $CONTAINER_NAME containerapp."
else
  echo "Containerapp $CONTAINER_NAME already exists."
fi

