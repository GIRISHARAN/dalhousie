FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src /app/src
RUN mvn install -DskipTests -e

#create image
FROM openjdk:17

COPY --from=build  /app/target/amazon-vpc-jar.jar .

COPY src /app/src

#ENV DB_HOSTNAME "giricluster-instance-1.cjqqafqh2rlm.us-east-1.rds.amazonaws.com:3306"
#ENV DB_NAME "giridatabase"
#ENV DB_USERNAME "admin"
#ENV DB_PASSWORD "admin123"

EXPOSE 9001

# Specify the command to run your application
ENTRYPOINT ["java", "-jar", "amazon-vpc-jar.jar"]