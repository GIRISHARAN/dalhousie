FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src /app/src
RUN mvn install -DskipTests -e

#create image
FROM openjdk:17

COPY --from=build  /app/target/login-jar.jar .

COPY src /app/src

EXPOSE 9101

#https://medium.com/datamindedbe/application-default-credentials-477879e31cb5
ENV GOOGLE_APPLICATION_CREDENTIALS /app/src/avid-shape-390123-a4c2e12795ea.json

# Specify the command to run your application
ENTRYPOINT ["java", "-jar", "login-jar.jar"]