
### Overview

This project contains examples on the following concepts

- Spring Cache for PCC
- Contract Testing
- JSON Testing
- Spring JPA Using H2
- Swagger API Documentation
- Logging Utilizing AOP
- Testing External API Callouts with Wiremock
- Integration Testing with Cucumber

### Testing Approach

Unit tests have been omitted from the project for brevity, but the actual testing strategy represented by this POC app would include a significant set of Unit Tests which would leverage `Mockito`.

Order smallest test suite size to largest, the following is the list of test types used to verify this application.

1. Integration (Test classes with the suffix `IT`)
1. Component (Test classes with the suffix `ApplicationServiceTests`)
1. JSON Tests (Test classes with the suffix `JsonTest`)
1. Contract (Tests defined within `/resources/contracts`)
1. Unit