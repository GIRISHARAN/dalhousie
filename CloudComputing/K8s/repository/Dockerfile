# FROM openjdk:17
# EXPOSE 6000
# WORKDIR /app
# COPY . /app/code
# ADD target/docker-container1-jar.jar docker-container1-jar.jar
# ENTRYPOINT ["java", "-jar", "docker-container1-jar.jar"]



FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the project's pom.xml file
COPY pom.xml .

# Download the project dependencies
RUN mvn dependency:go-offline -B

# Copy the project source code
COPY src /app/src

# Build the project
RUN mvn install -DskipTests

# Create the final image
FROM openjdk:17

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/docker-container1-jar.jar .

EXPOSE 5000

# Specify the command to run your application
ENTRYPOINT ["java", "-jar", "docker-container1-jar.jar"]