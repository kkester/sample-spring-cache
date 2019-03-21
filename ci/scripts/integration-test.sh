#!/bin/sh
set -e
cd sample-spring-cache-app
echo "Executing ./mvnw verify -Dunit.tests.skip=true -Dcache_app_url=$APP_URL"
./mvnw verify -Dunit.tests.skip=true -Dcache_app_url=$APP_URL
