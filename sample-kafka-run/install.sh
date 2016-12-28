#!/bin/bash

export KAFKA_VERSION="0.10.1.1"
export KAFKA_PACKAGE="kafka_2.11-$KAFKA_VERSION"
export KAFKA_RELEASE="http://apache.mirrors.tds.net/kafka/$KAFKA_VERSION/$KAFKA_PACKAGE.tgz"

curl -oL "$KAFKA_PACKAGE.tgz" "http://apache.mirrors.tds.net/kafka/0.10.1.1/kafka_2.11-0.10.1.1.tgz"
wget $KAFKA_RELEASE
tar -vxzf $KAFKA_PACKAGE.tgz
rm $KAFKA_PACKAGE.tgz
cd $KAFKA_PACKAGE

#bin/zookeeper-server-start.sh config/zookeeper.properties
#bin/kafka-server-start.sh config/server.properties




