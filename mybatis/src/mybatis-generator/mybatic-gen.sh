#!/usr/bin/env bash
rm -rf codegen
mkdir codegen
java -jar mybatis-generator-core-1.3.5.jar -configfile generatorConfig.xml -overwrite