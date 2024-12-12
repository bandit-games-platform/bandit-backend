#!/bin/bash

#------------------------------------------------------------------------------------------------
#- Name:	    deploy-rabbitmq-prod.sh
#- Author:	  Inês da Costa Bastos
#- Function:	Deploys a RabbitMQ containerapp for the production environment on Microsoft Azure
#- Usage:	    ./deploy-rabbitmq-prod.sh
#------------------------------------------------------------------------------------------------


CONTAINER_NAME="rabbitmq-prod-container"
RESOURCE_GROUP="rg_bandit_games_prod"
ENV_NAME="prod-containers-env"
IMAGE="$REGISTRY_USERNAME".azurecr.io/rabbitmq:3.13.7-management-alpine


echo "Deploying RabbitMQ containerapp..."
az containerapp create \
  --name $CONTAINER_NAME \
  --resource-group $RESOURCE_GROUP \
  --environment $ENV_NAME \
  --image $IMAGE \
  --target-port 5672 \
  --ingress external \
  --cpu 0.5 \
  --memory 1Gi \
  --min-replicas 0 \
  --max-replicas 1 \
  --env-vars RABBITMQ_DEFAULT_USER="$RABBITMQ_USER" RABBITMQ_DEFAULT_PASS="$RABBITMQ_PASSWORD" \
  --yaml ./rabbitmq-containerapp.yml

