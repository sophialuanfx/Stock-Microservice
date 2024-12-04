# Build stage
FROM maven:3.8.8-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /stock-perfomance

# Copy only the POM file first to cache dependencies
COPY pom.xml .
COPY .mvn/ .mvn/
COPY mvnw .
COPY src ./src

RUN ./mvnw clean package -DskipTests


FROM eclipse-temurin:17-jre-alpine

RUN addgroup -S spring && adduser -S spring -G spring

WORKDIR /stock-perfomance


COPY --from=build --chown=spring:spring /stock-perfomance/target/*.jar app.jar

USER spring:spring


HEALTHCHECK --interval=30s --timeout=3s \
  CMD wget -q --spider http://localhost:8080/actuator/health || exit 1

ENV JAVA_OPTS="-Xms512m -Xmx512m"

EXPOSE 8080

# Run application
ENTRYPOINT ["java", "-jar", "/stock-perfomance/app.jar"]
