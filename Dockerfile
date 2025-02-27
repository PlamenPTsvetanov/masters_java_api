FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean install -DskipTests

FROM eclipse-temurin:21-jdk AS runtime
WORKDIR /app
COPY --from=build /app/target/java_api.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
