# Stage 1: Build the application with Maven
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src /app/src
RUN mvn package -DskipTests

# Stage 2: Copy the JAR file
FROM openjdk:17-slim AS final
WORKDIR /app
COPY --from=build /app/target/docker-container2-jar.jar .

EXPOSE 8080

# Entry point
ENTRYPOINT ["java", "-jar", "/app/docker-container2-jar.jar"]