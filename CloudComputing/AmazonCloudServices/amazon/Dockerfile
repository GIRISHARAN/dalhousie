FROM openjdk:17
EXPOSE 50051
WORKDIR /app
COPY . /app/code
ADD target/awscloud-s3bucket-jar.jar awscloud-s3bucket-jar.jar
ENTRYPOINT ["java", "-jar", "awscloud-s3bucket-jar.jar"]