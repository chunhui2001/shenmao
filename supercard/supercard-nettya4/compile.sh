#!/usr/bin/env bash
mvn clean install -Dmaven.test.skip=true
mkdir target/netty
unzip -q target/nettya4.jar -d target/nettya4