# Spring Boot App (cultivapp)

## What does this folder do?
This is the Spring Boot application module for CultivApp.

## How to install
- Java 21 required.
- Use Maven Wrapper: `mvnw.cmd clean package -DskipTests`.

## How to run
- `mvnw.cmd spring-boot:run`
- Runs on http://localhost:8080

## Standards
- Spring Boot 3 / REST / DTOs / Services.
- Validation (Jakarta) and exception handling.

## Java version
- 21

## Database
- H2 (file) in dev. See `src/main/resources/application.properties` and `data.sql`.
