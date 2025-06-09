# Etapa 1: build
FROM maven:3.9.0-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Etapa 2: runtime
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/treinoemcasa-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
