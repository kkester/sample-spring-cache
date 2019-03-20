#!/bin/sh
set -e
cd sample-spring-cache-app
./mvnw clean package
cd ..
cp sample-spring-cache-app/target/*.jar build-output/
cp sample-spring-cache-app/manifest.yml build-output/
ls -al
ls -al .m2
ls -al sample-spring-cache-app
