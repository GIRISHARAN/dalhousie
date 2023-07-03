FROM openjdk:17
EXPOSE 6000
WORKDIR /app
COPY . /app/code
ADD target/docker-container1-jar.jar docker-container1-jar.jar
ENTRYPOINT ["java", "-jar", "docker-container1-jar.jar"]