#!/bin/bash

# Echo the important environment variables
echo BATCH_ENVIRONMENT=$BATCH_ENVIRONMENT
echo DO_INIT_BATCH_DB=$DO_INIT_BATCH_DB
echo BATCH_DB_SCHEMA=$BATCH_DB_SCHEMA

# Read JDBC_URL
echo -n 'JDBC_URL: ' 
read -s JDBC_URL
export BATCH_JDBC_URL=$JDBC_URL
echo

# Read APP_ENCRYPTION_PASSWORD
echo -n 'APP_ENCRYPTION_PASSWORD: ' 
read -s APP_ENCRYPTION_PASSWORD
export APP_ENCRYPTION_PASSWORD=$APP_ENCRYPTION_PASSWORD
echo

# Initialize environment variables
export TIMEOUT=10
export ATTEMPTS=5

# Run the batch script in the background and redirect output
./bin/runforever.sh >> /var/log/rodlaver-jdbc-batch/batch.out 2>&1 &