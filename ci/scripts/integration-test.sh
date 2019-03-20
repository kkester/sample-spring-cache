#!/bin/sh
set -e
ls -al
ls -al .m2
ls -al sample-spring-cache-app
cd sample-spring-cache-app
./mvnw verify -Dunit.tests.skip=true
