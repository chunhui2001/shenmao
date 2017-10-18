#!/usr/bin/env bash
cd ../
mvn clean package -Dmaven.test.skip=true
cd ./supercard-nettya4
mkdir target/netty
unzip -q target/nettya4.jar -d target/nettya4

