FROM openjdk:17
EXPOSE 8001
WORKDIR /app
COPY . /app/code
ADD target/docker-container2-jar.jar docker-container2-jar.jar
ENTRYPOINT ["java", "-jar", "docker-container2-jar.jar"]