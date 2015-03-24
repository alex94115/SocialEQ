#!/bin/bash


LIB=lib/*
for f in $LIB
do
 CP=$CP:$f
done

echo "JAVA COMMAND: " java -DJDBC_URL={STREAM_JDBC_URL} -DconsumerKey={consumerKey} -DconsumerSecret={consumerSecret} -Dtoken={token} -Dsecret={secret} -cp $CP com.projectlaver.twitterstream.TwitterStream

java -DappJdbcUrl=$STREAM_JDBC_URL  -DconsumerKey=$CONSUMER_KEY -DconsumerSecret=$CONSUMER_SECRET -Dtoken=$TOKEN -Dsecret=$SECRET -cp $CP com.projectlaver.twitterstream.TwitterStream
