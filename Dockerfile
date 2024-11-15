FROM node:9.4.0-alpine
FROM openjdk:8-jdk-alpine

COPY target/project-0.0.1-SNAPSHOT.jar /app/user-registry.jar

ENTRYPOINT ["java","-jar","user-registry.jar"]