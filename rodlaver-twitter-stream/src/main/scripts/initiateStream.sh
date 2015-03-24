#!/bin/bash

# Echo the important environment variables
echo STREAM_JDBC_URL=obscured  
echo CONSUMER_KEY=$CONSUMER_KEY 
echo CONSUMER_SECRET=$CONSUMER_SECRET 
echo TOKEN=$TOKEN 
echo SECRET=$SECRET

# Read STREAM_JDBC_URL
echo -n 'STREAM_JDBC_URL: ' 
read -s STREAM_JDBC_URL
export STREAM_JDBC_URL=$STREAM_JDBC_URL
echo

# Initialize the restart / retry environment variables
export TIMEOUT=10
export ATTEMPTS=5

# Run the stream script in the background and redirect output
./bin/runStreamForever.sh >> /var/log/rodlaver-twitter-stream/stream.out 2>&1 &