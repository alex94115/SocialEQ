#!/bin/bash

#if [ $# -eq 0 ]
#  then
#    echo "Run as ./bin/runRodLaver.sh <environment> <shouldInitializeDatabase> <schemaName>"
#    echo "E.g., ./bin/runRodLaver.sh localhost true hootit_batch"
#    exit 1
#fi

# used this on AWS - resources/:resources/jobs:mysql-connector-java-5.1.26-bin.jar
local CP=resources/:resources/jobs

LIB=lib/*
for f in $LIB
do
 CP=$CP:$f
done

echo "JAVA COMMAND: " java -Djava.io.tmpdir=/var/tmp/ -DENVIRONMENT=$BATCH_ENVIRONMENT -DSHOULD_INITIALIZE_DATABASE=$DO_INIT_BATCH_DB -DSCHEMA=$BATCH_DB_SCHEMA -DJDBC_URL={JDBC_URL} -DAPP_ENCRYPTION_PASSWORD={APP_ENCRYPTION_PASSWORD} -cp $CP org.springframework.batch.core.launch.support.CommandLineJobRunner launch-context.xml rodLaverJob -next

java -Djava.io.tmpdir=/var/tmp/ -DENVIRONMENT=$BATCH_ENVIRONMENT -DSHOULD_INITIALIZE_DATABASE=$DO_INIT_BATCH_DB -DSCHEMA=$BATCH_DB_SCHEMA -DJDBC_URL=$BATCH_JDBC_URL -DAPP_ENCRYPTION_PASSWORD=$APP_ENCRYPTION_PASSWORD -cp $CP org.springframework.batch.core.launch.support.CommandLineJobRunner launch-context.xml rodLaverJob -next
sleep 10
