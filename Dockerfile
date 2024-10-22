FROM eclipse-temurin:17-jdk-focal
WORKDIR /app
COPY /target/techvisit-0.0.0.jar techvisit-0.0.0.jar
EXPOSE 8080
CMD ["java", "-jar", "techvisit-0.0.0.jar"]
