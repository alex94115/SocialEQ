#!/bin/bash

echo "Starting the streamLogSetup.sh script"

if [ -d /home/ec2-user/rodlaver-twitter-stream ]; then
    echo "Stream deployment directory exists. Continuing."
fi

if [[ ! -d /home/ec2-user/rodlaver-twitter-stream ]]; then
    echo "Stream deployment directory not found. Creating it."
    mkdir /home/ec2-user/rodlaver-twitter-stream
    chown ec2-user:ec2-user /home/ec2-user/rodlaver-twitter-stream
fi


if [ -d  /var/log/rodlaver-twitter-stream ]; then
    echo "Stream logging directory exists. Continuing."
fi

if [[ ! -d /var/log/rodlaver-twitter-stream ]]; then
    echo "Stream logging directory not found. Creating it."
    mkdir /var/log/rodlaver-twitter-stream
    chmod 770 /var/log/rodlaver-twitter-stream
    chown ec2-user:ec2-user /var/log/rodlaver-twitter-stream/

    touch /var/log/rodlaver-twitter-stream/stream.out
    chmod 660 /var/log/rodlaver-twitter-stream/stream.out
    chown ec2-user:ec2-user /var/log/rodlaver-twitter-stream/stream.out
fi

if [ -f /etc/logrotate.conf.rodlaver-twitter-stream ]; then
    echo " log rotate configuration file exists. Continuing."
fi

if [[ ! -f /etc/logrotate.conf.rodlaver-twitter-stream ]]; then
    echo "Stream log rotate configuration file does not exists. Creating it."
    cp /tmp/deployment/application/ROOT/.ebextensions/logrotate.conf.rodlaver-twitter-stream /etc/
fi

if [ -f /etc/cron.hourly/logrotate-rodlaver-twitter-stream ]; then
    echo "Stream hourly rotate log cron job exists. Continuing."
fi

if [[ ! -f /etc/cron.hourly/logrotate-rodlaver-twitter-stream ]]; then
    echo "Stream hourly rotate log cron job does not exist. Creating it."
    cp /tmp/deployment/application/ROOT/.ebextensions/logrotate-rodlaver-twitter-stream /etc/cron.hourly/
    chmod ugo+x /etc/cron.hourly/logrotate-rodlaver-twitter-stream
fi

echo "Completed the streamLogSetup.sh script"
