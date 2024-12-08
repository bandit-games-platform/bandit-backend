#!/bin/bash

#---------------------------------------------------------------
#- Name:	    create-init-image.sh
#- Author:	  Roman Gordon
#- Function:	Creates an image with the init script for the database
#- Usage:	    ./create-init-image.sh
#---------------------------------------------------------------

IMAGE_NAME="acrbanditgamesdev.azurecr.io/init-script-image:latest"

mkdir -p temp
# Write the Dockerfile
cat <<EOF > "temp/Dockerfile"
FROM postgres:latest
RUN echo "\
CREATE SCHEMA IF NOT EXISTS chatbot; \
CREATE SCHEMA IF NOT EXISTS gameplay; \
CREATE SCHEMA IF NOT EXISTS game_registry; \
CREATE SCHEMA IF NOT EXISTS player; \
CREATE SCHEMA IF NOT EXISTS statistics; \
CREATE SCHEMA IF NOT EXISTS storefront;" > /docker-entrypoint-initdb.d/init.sql
EOF

# Build the Docker image
docker build -t $IMAGE_NAME temp
# Push the Docker image to the registry
docker push $IMAGE_NAME
# Clean up the temporary directory
rm -rf temp
echo "Docker image $IMAGE_NAME built and pushed successfully."
