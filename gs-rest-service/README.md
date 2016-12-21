# Sample Springboot app

This sample app contains a restfull interface. This sample is based on the code found at: https://spring.io/guides/gs/rest-service/

## Build

Build this app from `initial` directory using:

```
./mvnw clean package
```

This will create a jar file  `initial/target/gs-rest-service-0.1.0.jar`

To build a docker image run:

```
docker build -t geerd/gs-rest-service .
```

Push the service to the registry:

```
docker push geerd/gs-rest-service
```

## Run the service

Run the docker image using:

```
docker run geerd/gs-rest-service
