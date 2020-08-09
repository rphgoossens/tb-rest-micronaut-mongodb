FROM openjdk:14-alpine
COPY target/tb-rest-micronaut-mongodb-*.jar tb-rest-micronaut-mongodb.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "tb-rest-micronaut-mongodb.jar"]