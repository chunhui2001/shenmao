#!/usr/bin/env bash
./compile.sh
# java -cp `pwd`/target/netty.jar com.shenmao.netty.HttpServer
java -server -XX:+UseParallelGC -Xmn320m -Xms128m -Xmx512m -cp `pwd`/target/nettya4.jar com.supercard.netty.HttpServer


# pm2 start run.sh --name netty.app.serve
# pm2 save
# pm2 startup ubuntu -u keesh --hp /home/keesh
# [ubuntu | ubuntu14 | ubuntu12 | centos | centos6 | arch | oracle | amazon | macos | darwin | freesd | systemd | systemv | upstart | launchd | rcd | openrc]


# 解决 AWS EC2 中文乱码
# sudo locale-gen zh_CN.UTF-8 && sudo reboot
# export LC_ALL="zh_CN.UTF-8"