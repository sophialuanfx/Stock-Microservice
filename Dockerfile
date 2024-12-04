FROM maven:3.8.8-eclipse-temurin-17 AS build

WORKDIR /user-subscription

COPY pom.xml .
COPY .mvn/ .mvn/
COPY mvnw .
COPY src ./src

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine

WORKDIR /user-subscription

RUN addgroup -S spring && adduser -S spring -G spring

COPY --from=build --chown=spring:spring /user-subscription/target/*.jar app.jar
COPY --from=build --chown=spring:spring /user-subscription/src/main/resources/templates/ /user-subscription/templates/

USER spring:spring

ENV JAVA_OPTS="-Xms512m -Xmx512m"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /user-subscription/app.jar"]
