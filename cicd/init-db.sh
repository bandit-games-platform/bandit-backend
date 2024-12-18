#!/bin/sh

PGPASSWORD=$PG_ADMIN_PASSWORD psql -h "$DB_SERVER_NAME".postgres.database.azure.com -U "$PG_ADMIN_USER" -d bandit_db -c "CREATE USER $PG_NON_ADMIN_USER WITH PASSWORD '$PG_NON_ADMIN_PASSWORD';"
PGPASSWORD=$PG_ADMIN_PASSWORD psql -h "$DB_SERVER_NAME".postgres.database.azure.com -U "$PG_ADMIN_USER" -d bandit_db -c "GRANT ALL PRIVILEGES ON DATABASE bandit_db TO $DEV_PG_USER";
PGPASSWORD=$PG_NON_ADMIN_PASSWORD psql -h "$DB_SERVER_NAME".postgres.database.azure.com -U "$PG_NON_ADMIN_USER" -d bandit_db -c "CREATE SCHEMA IF NOT EXISTS chatbot; CREATE SCHEMA IF NOT EXISTS gameplay; CREATE SCHEMA IF NOT EXISTS game_registry; CREATE SCHEMA IF NOT EXISTS player; CREATE SCHEMA IF NOT EXISTS statistics; CREATE SCHEMA IF NOT EXISTS storefront;"
PGPASSWORD=$PG_ADMIN_PASSWORD psql -h "$DB_SERVER_NAME".postgres.database.azure.com -U "$PG_ADMIN_USER" -d bandit_db -c "SELECT * FROM information_schema.schemata;"