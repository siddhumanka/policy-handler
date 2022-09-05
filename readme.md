# Policy-handler Service

Policy handler service helps to create a policy on a specific start date with for multiple people provided they
are investing some premium

## Technologies

- Spring boot
- Kotlin

Database used currently is in memory map. 

## API
POST /policy
PUT /policy
GET /policy?policyId=<>&requestDate=<request_date>
...

## Getting started

### Running the app
To start the application 
```shell
./gradlew bootRun
```

### Running the tests
To run the tests
```shell
./gradlew test
```

### Setting up IntelliJ
Import project straight with default gradle settings


## Assumptions considered
1. **requestDate** : is only the date when the request to view the policy details is made.
2. **effectiveDate** : is a date when the policy is matured
