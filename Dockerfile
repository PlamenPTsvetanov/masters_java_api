FROM eclipse-temurin:21-jdk as jdk

WORKDIR /app

COPY target/java_api.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
