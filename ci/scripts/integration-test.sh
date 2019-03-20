#!/bin/sh
set -e
ls -al
cd sample-spring-cache-app
./mvnw verify -Dunit.tests.skip=true
