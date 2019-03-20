#!/bin/sh
set -e
cd sample-spring-cache-app
./mvnw clean package
cd ..
cp sample-spring-cache-app/target/*.jar build-output/
cp sample-spring-cache-app/manifest.yml build-output/
ls -al build-output
