#!/usr/bin/env bash

currentFolder=`pwd`
nettyServerFolder="/Users/cc/eclipse-workspace/shenmao-supercard/supercard/supercard-nettya4"
nettyServerTargetFolder=$nettyServerFolder/target/nettya4/assets


ng build --prod

mv ./dist/inline.*.bundle.js ./dist/inline.bundle.js
mv ./dist/main.*.bundle.js ./dist/main.bundle.js
mv ./dist/polyfills.*.bundle.js ./dist/polyfills.bundle.js
mv ./dist/styles.*.bundle.css ./dist/styles.bundle.css
mv ./dist/vendor.*.bundle.js ./dist/vendor.bundle.js

cp ./dist/*.bundle.* $nettyServerFolder/src/main/resources/assets/

if [ -d "$nettyServerTargetFolder" ]; then
  cp ./dist/*.bundle.* $nettyServerTargetFolder/
  echo "[`date "+%Y-%m-%d %H:%M:%S"`] Deployment [angular4 assets] to ["$nettyServerTargetFolder"/] completed"
fi


echo "[`date "+%Y-%m-%d %H:%M:%S"`] Deployment [angular4 assets] to ["$nettyServerFolder"/src/main/resources/assets/] completed"


cd $currentFolder

#pid=`lsof -i tcp:8844  | grep LISTEN | awk '{print $2}'`
#
#
#if [ $pid ]
#  then
#		kill -9 $pid
#		echo "[`date "+%Y-%m-%d %H:%M:%S"`] Netty HttpServer killed!"
#
#		echo "Restarting Netty HttpServer...."
#		cd $nettyServerFolder && ./run.sh
#		pid=`lsof -i tcp:8844  | grep LISTEN | awk '{print $2}'`
#
#		if [ $pid ]
#		  then
#		    echo "[`date "+%Y-%m-%d %H:%M:%S"`] Restart Netty HttpServer successfull!"
#      else
#        echo "[`date "+%Y-%m-%d %H:%M:%S"`] Restart Netty HttpServer failed!"
#    fi
#
#    cd $currentFolder
#
#fi
