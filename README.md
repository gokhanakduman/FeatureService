# Feature Service

Serves branch locations based on geo-spatial inputs

#  Build

To build the project:

```
$ ./mvnw clean install
```

Build the image with JIB:

```
./mvnw compile jib:dockerBuild
```

# Run locally

If the application is started for the first time:

```
 mvn compile jib:dockerBuild && docker-compose up
```
If jib command is already runned once, then only run:

```
docker-compose up
```

And the app is stopped with:

```
docker-compose down
```

###### Swagger
After running the application, swagger can be used to access the endpoints of the service and their definitions  
> http://localhost:8080/swagger-ui.html

###### Actuator
Actuator endpoints:  
> http://localhost:8080/actuator  
http://localhost:8080/actuator/metrics
http://localhost:8080/actuator/env

###### Postman
Postman collection including all endpoints can be found [here](./postman)

# Implementation

## Database
[Flyway](https://flywaydb.org/) is used for migrations.  
Schema is as follows:

```
CREATE TABLE IF NOT EXISTS features
(
    id UUID PRIMARY KEY,
    "timestamp" timestamp NOT NULL,
    begin_viewing_date timestamp NOT NULL,
    end_viewing_date timestamp NOT NULL,
    mission_name varchar(32) NOT NULL,
    quicklook TEXT
);
```
Migration script for creating table can be found [here](./src/main/resources/db/migration)  
Also Java Based Migration is used for migrating static data into Database while ensuring that operation is only made once.  
That migration can be found [here](./src/main/java/com/up42/featureService/repository/migration)  

## Repository
[FeatureRepository](./src/main/java/com/up42/featureService/repository/FeatureRepository.java) is the repository interface that extends [FeatureModel](./src/main/java/com/up42/featureService/repository/model/FeatureModel.java) entity.

## Controllers
[FeatureRestController](./src/main/java/com/up42/featureService/controller/FeatureRestController.java) is the only controller in this project.  
Responsible for serving to these 3 endpoints:

1. /v1/features  
Returns all features as json list
2. /v1/features/{id}  
Returns a single feature as json object
3. /v1/features/{id}/quicklook  
Returns quicklook image of the feature as image

ResponseEntityExceptionHandling is made by [FeatureRestControllerAdvisor](./src/main/java/com/up42/featureService/controller/FeatureRestControllerAdvisor.java)  
**Boundary conditions and error codes:**  
- If the id is not in UUID V4 format, *HTTP 400 Bad request* will be returned
- If a feature with requested id not exists, *HTTP 404 Not exitsts* will be returned
- If saved quicklook image string can not be decoded, *HTTP 422 Unprocessable Entity* will be returned

## Service
[FeatureService](./src/main/java/com/up42/featureService/service/FeatureService.java) is the service that serves to FeatureRestController.  
Pretty straightforward, nothing special.

