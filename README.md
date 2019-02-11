
### Overview

This PoC demonstrates architecture, design, and coding strategies recommended by Pivotal. 
The following recommendations are implemented:

- Spring Cache for PCC
- Feature Toggles
- Spring JPA Using H2
- Logging Utilizing AOP
- Swagger API Documentation
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