FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app

COPY . /app

RUN mvn clean package

FROM openjdk:17

RUN mkdir /deploy

COPY --from=build /app/target/custom-inventory-0.0.1.jar /deploy/custom-inventory.jar

WORKDIR /deploy

EXPOSE 8080

CMD ["java", "-jar", "custom-inventory.jar"]

LABEL version="0.0.1"
LABEL maintainer="Badhri Nadh Arja <arja.badhrinadh@gmail.com>"
LABEL description="Custom Inventory Application"
