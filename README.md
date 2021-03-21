# tb-rest-micronaut-mongodb
This project uses mongodb to store its data. You can spin it up via Docker:
```bash
docker run -d -p 27017-27019:27017-27019 --name mongodb mongo
```
## graalvm native images
### docker
Creating the docker-native image is done like this:
```bash
mvn clean package -Dpackaging=docker-native
```
Now before you spin up the image, make sure to create a docker network so the micronaut container and mongo container 
can communicate with each other
```bash
docker network create mn-network
```
add the mongo container to this network
```bash
docker network connect mn-network mongodb
```
Spring up the docker-native image in the same network
```bash
docker run -d -p 8080:8080 -e MONGO_HOST=mongodb --network mn-network --name mn-mongodb mn-mongodb
```
### native-image
Creating the native image is done like this:
```bash
mvn clean package -Dpackaging=native-image
```
Then you can run it
./target/

## Micronaut 2.4.0 Documentation

- [User Guide](https://docs.micronaut.io/2.4.0/guide/index.html)
- [API Reference](https://docs.micronaut.io/2.4.0/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/2.4.0/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

## Feature openapi documentation

- [Micronaut OpenAPI Support documentation](https://micronaut-projects.github.io/micronaut-openapi/latest/guide/index.html)

- [https://www.openapis.org](https://www.openapis.org)

## Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)

## Feature mongo-reactive documentation

- [Micronaut Mongo Reactive Driver documentation](https://micronaut-projects.github.io/micronaut-mongodb/latest/guide/index.html)

- [https://docs.mongodb.com](https://docs.mongodb.com)

## Feature testcontainers documentation

- [https://www.testcontainers.org/](https://www.testcontainers.org/)

