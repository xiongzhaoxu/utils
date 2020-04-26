#!/usr/bin/env bash

version=1.0.8-RELEASE
mvn clean
mvn package
cd ./target/
mvn install:install-file -Dfile=utils-$version.jar -DgroupId=com.sinry -DartifactId=utils -Dversion=$version -Dpackaging=jar
cd ~/.m2/repository/com/sinry
git remote set-url origin https://github.com/isinry/maven.git
git add .
git commit -m "add version"
git push
cd ~/project/java/work/code/utils