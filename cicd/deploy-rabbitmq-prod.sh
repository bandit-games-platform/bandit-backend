#!/bin/bash

#------------------------------------------------------------------------------------------------
#- Name:	    deploy-rabbitmq-prod.sh
#- Author:	  Inês da Costa Bastos
#- Function:	Deploys a RabbitMQ containerapp for the production environment on Microsoft Azure
#- Usage:	    ./deploy-rabbitmq-prod.sh
#------------------------------------------------------------------------------------------------


VNET_NAME="banditProdVnet"
SUBNET_NAME="prodSubnet"

CONTAINER_NAME="rabbitmq-prod-container"
RESOURCE_GROUP="rg_bandit_games_prod"
ENV_NAME="env-prod-containers"


echo "Checking resource group list"
az group list


# Vnet and Subnets
VNET_EXISTS=$(az network vnet list --resource-group $RESOURCE_GROUP --query "[?name=='$VNET_NAME'].name" -o tsv)

if [ -z "$VNET_EXISTS" ]; then
    # vnet
    echo $VNET_NAME
    az network vnet create --name "$VNET_NAME" --resource-group $RESOURCE_GROUP --location northeurope --address-prefix 10.0.0.0/16

    # contexts subnet
    echo $SUBNET_NAME
    az network vnet subnet create --name $SUBNET_NAME --resource-group $RESOURCE_GROUP --vnet-name $VNET_NAME --address-prefix 10.0.1.0/24
    az network vnet subnet update \
      --resource-group $RESOURCE_GROUP \
      --vnet-name $VNET_NAME \
      --name $SUBNET_NAME \
      --delegations Microsoft.App/environments

    echo "Vnet $VNET_NAME created."
    echo "Subnet $SUBNET_NAME created."
else
    echo "VNet $VNET_NAME already exists."
fi


# Containerapp Environment
ENV_EXISTS=$(az containerapp env list --resource-group $RESOURCE_GROUP --query "[?name=='$ENV_NAME'].name" -o tsv)

if [ -z "$ENV_EXISTS" ]; then
    INFRASTRUCTURE_SUBNET=$(az network vnet subnet show --resource-group ${RESOURCE_GROUP} --vnet-name $VNET_NAME --name ${SUBNET_NAME} --query "id" -o tsv | tr -d '[:space:]')

    az containerapp env create --name $ENV_NAME --resource-group $RESOURCE_GROUP --location northeurope --infrastructure-subnet-resource-id "$INFRASTRUCTURE_SUBNET"

    echo "Container Apps environment $ENV_NAME created."
else
    echo "Container Apps environment $ENV_NAME already exists."
fi


# RabbitMQ Containerapp
echo "Deploying RabbitMQ containerapp..."
RABBITMQ_EXISTS=$(az containerapp list --resource-group $RESOURCE_GROUP --query "[?name=='$CONTAINER_NAME'].name" -o tsv)

az account set --subscription $DEV_ID_PROD
docker login acrbanditgamesdev.azurecr.io --username $PROD_AZURE_APP_ID --password $PROD_AZURE_PASSWORD
echo "Logging into acr"

docker pull acrbanditgamesdev.azurecr.io/rabbitmq:3.13.7-management-alpine
echo "Pulling the image"

az account set --subscription $SUBS_ID_PROD

echo "Creating the container"
if [ -z "$RABBITMQ_EXISTS" ]; then
  az containerapp create \
    --name $CONTAINER_NAME \
    --resource-group $RESOURCE_GROUP \
    --environment $ENV_NAME \
    --image acrbanditgamesdev.azurecr.io/rabbitmq:3.13.7-management-alpine \
    --target-port 15672 \
    --ingress external \
    --env-vars RABBITMQ_DEFAULT_USER=myuser RABBITMQ_DEFAULT_PASS=mypassword
#    --yaml ./cicd/rabbitmq-containerapp.yml

#  az containerapp update \
#    --name $CONTAINER_NAME \
#    --resource-group $RESOURCE_GROUP \
#    --set-env-vars RABBITMQ_DEFAULT_USER="$RABBITMQ_USER" RABBITMQ_DEFAULT_PASS="$RABBITMQ_PASSWORD"

  echo "Creating $CONTAINER_NAME containerapp."
else
  echo "Containerapp $CONTAINER_NAME already exists."
fi

