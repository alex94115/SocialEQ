#!/bin/bash

echo "Starting the batchLogSetup.sh script"

if [ -d /home/ec2-user/rodlaver-jdbc-batch ]; then
    echo "Batch deployment directory exists. Continuing."
fi

if [[ ! -d /home/ec2-user/rodlaver-jdbc-batch ]]; then
    echo "Batch deployment directory not found. Creating it."
    mkdir /home/ec2-user/rodlaver-jdbc-batch
    chown ec2-user:ec2-user /home/ec2-user/rodlaver-jdbc-batch
fi


if [ -d  /var/log/rodlaver-jdbc-batch ]; then
    echo "Batch logging directory exists. Continuing."
fi

if [[ ! -d /var/log/rodlaver-jdbc-batch ]]; then
    echo "Batch logging directory not found. Creating it."
    mkdir /var/log/rodlaver-jdbc-batch
    chmod 770 /var/log/rodlaver-jdbc-batch
    chown ec2-user:ec2-user /var/log/rodlaver-jdbc-batch/

    touch /var/log/rodlaver-jdbc-batch/batch.out
    chmod 660 /var/log/rodlaver-jdbc-batch/batch.out
    chown ec2-user:ec2-user /var/log/rodlaver-jdbc-batch/batch.out
fi

if [ -f /etc/logrotate.conf.rodlaver-jdbc-batch ]; then
    echo "Batch log rotate configuration file exists. Continuing."
fi

if [[ ! -f /etc/logrotate.conf.rodlaver-jdbc-batch ]]; then
    echo "Batch log rotate configuration file does not exists. Creating it."
    cp /tmp/deployment/application/ROOT/.ebextensions/logrotate.conf.rodlaver-jdbc-batch /etc/
fi

if [ -f /etc/cron.hourly/logrotate-rodlaver-jdbc-batch ]; then
    echo "Batch hourly rotate log cron job exists. Continuing."
fi

if [[ ! -f /etc/cron.hourly/logrotate-rodlaver-jdbc-batch ]]; then
    echo "Batch hourly rotate log cron job does not exist. Creating it."
    cp /tmp/deployment/application/ROOT/.ebextensions/logrotate-rodlaver-jdbc-batch /etc/cron.hourly/
    chmod ugo+x /etc/cron.hourly/logrotate-rodlaver-jdbc-batch
fi

echo "Completed the batchLogSetup.sh script"
