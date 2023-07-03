FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src /app/src
RUN mvn dependency:go-offline -B
RUN mvn install -DskipTests

FROM openjdk:17
COPY --from=build /app/target/registration-jar.jar .
COPY src /app/src
EXPOSE 9001
#https://medium.com/datamindedbe/application-default-credentials-477879e31cb5
ENV GOOGLE_APPLICATION_CREDENTIALS /app/src/avid-shape-390123-a4c2e12795ea.json

# Specify the command to run your application
ENTRYPOINT ["java", "-jar", "registration-jar.jar"]