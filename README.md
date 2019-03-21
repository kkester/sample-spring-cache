
### Overview

This PoC demonstrates architecture, design, and coding strategies recommended by Pivotal. 
The following recommendations are implemented:

- Spring Cache for PCC
- Feature Toggles
- Spring JPA Using H2
- Logging Utilizing AOP
- Circuit Breakers Utilizing Hysterix
- Swagger API Documentation
- JUnit Jupiter Unit Testing
- Contract Testing
- JSON Testing
- Testing External API Callouts with Wiremock
- Integration Testing with Cucumber

### Testing Approach

Unit tests have been omitted from the project for brevity, but the actual testing strategy represented by this POC app would include a significant set of Unit Tests which would leverage something such as `Mockito`.

Order smallest test suite size to largest, the following is the list of test types used to verify this application.

1. Integration (Test classes with the suffix `IT`)
1. Component (Test classes with the suffix `ApplicationServiceTests`)
1. JSON Tests (Test classes with the suffix `JsonTest`)
1. Contract (Tests defined within `/resources/contracts`)
1. Unit

- `mvn test` will execute all Unit, JSON, Component, and Contract Tests <br>
- `mvn verify -Dunit.tests.skip=true -Dcache_app_url={url}` will execute only the integration tests. eg:
    ```text
    mvn verify -Dunit.tests.skip=true -Dcache_app_url=http://spring-store-cache.apps.pcfone.io
    ```

### Gemfire Setup

1. Download and install the `GFSH` cli tool
1. Startup `GFSH`
1. Initiate a locator using `start locator`
1. Initiate a server using  `start server`
1. Establish a region using `create region --name=products --type=PARTITION`
1. Establish a region using `create region --name=offers --type=PARTITION`
1. Verify server structure
  - `list members`
  - `describe member --name={serverName}`
  - Ensure region is contained by server

### Wiremock

If using `Wiremock` to simulate the service callouts to retrieve offers, then the request below can be used to stub the integration. 

POST /__admin/mappings/new HTTP/1.1<br>
Content-Type: application/json<br>
```json
{ 
	"request": { 
		"url": "/offers?type=banners", 
		"method": "GET" 
		
	}, 
	"response": { 
		"status": 200, 
		"body": "[{\"name\":\"banner-1\"}]",
		"headers": {
            "Content-Type": "application/json"
        }
	}
}
```

### Concourse CI

The `ci` folder contains a pipeline configuration for concourse.

1. Startup Concourse
    ```text
    docker-compose up -d
    ```
1. Setup Pipeline
    ```text
    fly -t tutorial sp -p sample-spring-cache -c main-pipeline.yml -l {credentials.yml}
    ```
1. Unpause The Pipeline
    ```text
    fly -t tutorial up -p sample-spring-cache
    ```
1. Any changes made to application repository should automatically trigger a build
1. Shutdown Concourse
    ```text
    docker-compose down
    ```

### Jenkins CI

