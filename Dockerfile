FROM eclipse-temurin:21-jdk-focal
WORKDIR /app
COPY target/techvisit-0.0.0.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
