#!/bin/sh
set -e
sample-spring-cache-app/mvn clean package
cp sample-spring-cache-app/target/*.jar build-output/
cp sample-spring-cache-app/manifest.yml build-output/
ls -al build-output
