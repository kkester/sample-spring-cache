#!/bin/sh
set -e
cd sample-spring-cache-app
./mvnw integration-test -Dunit.tests.skip=true
