FROM openjdk:17-jdk
EXPOSE 8085
ADD target/docker-integration-api.jar docker-integration-api.jar
ENTRYPOINT [ "java", "-jar", "/docker-integration-api.jar" ]