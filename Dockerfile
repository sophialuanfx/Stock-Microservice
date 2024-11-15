FROM maven:3-jdk-11-openj9 AS builder

WORKDIR /Stock-Microservice
COPY . /Stock-Microservice

RUN mvn package

FROM adoptopenjdk:11-jre-openj9
WORKDIR /Stock-Microservice
COPY --from=builder /Stock-Microservice/target/Stock-Microservice-1.0-SNAPSHOT-fat.jar /Stock-Microservice/Stock-Microservice.jar

EXPOSE  8080
ENTRYPOINT ["java","-jar","/Stock-Microservice/Stock-Microservice.jar"]