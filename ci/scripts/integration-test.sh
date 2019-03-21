#!/bin/sh
set -e
cd sample-spring-cache-app
./mvnw verify -Dunit.tests.skip=true -Dcache_app_url=$APP_URL
