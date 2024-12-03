FROM maven:3.8.8-eclipse-temurin-17 AS build

WORKDIR /stock-microservice
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /stock-microservice

# Copy the built artifact from build stage
COPY --from=build /stock-microservice/target/stock-microservice-0.0.1-SNAPSHOT.jar stock-microservice-0.0.1-SNAPSHOT.jar

# Expose the correct port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "stock-microservice-0.0.1-SNAPSHOT.jar"]
