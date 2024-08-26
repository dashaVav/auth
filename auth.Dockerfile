FROM openjdk:21-slim
LABEL authors="dashavav"
COPY target/auth*.jar /auth.jar
ENTRYPOINT ["java", "-jar", "/auth.jar"]