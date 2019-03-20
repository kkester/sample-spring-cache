#!/bin/sh
set -e
cd sample-spring-cache-app
./mvnw verify -Dunit.tests.skip=true
cd ..
ls -al
