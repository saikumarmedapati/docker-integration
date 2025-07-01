FROM openjdk:17-jdk
EXPOSE 9092
ADD target/asset-mgmt-api.jar asset-mgmt-api.jar
ENTRYPOINT [ "java", "-jar", "/asset-mgmt-api.jar" ]