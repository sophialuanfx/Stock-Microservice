FROM maven:3.8.8-eclipse-temurin-17 AS build

WORKDIR /user-registry
COPY pom.xml mvnw ./
COPY .mvn/ .mvn/
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

RUN addgroup -S spring && adduser -S spring -G spring
# Create data directory with proper permissions
RUN mkdir -p /tmp/userdata && \
    chown -R spring:spring /tmp/userdata

WORKDIR /user-registry

COPY --from=build --chown=spring:spring /user-registry/target/user-registry-0.0.1-SNAPSHOT.jar app.jar

USER spring:spring

ENV JAVA_OPTS="-Xms512m -Xmx512m"

# Expose the correct port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
