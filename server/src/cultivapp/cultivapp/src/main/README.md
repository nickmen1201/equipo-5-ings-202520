# Spring Boot Sources

## What does this folder do?
Contains Java sources and resources for the backend.

## How to install
No separate install. Build from the module root with `mvnw.cmd`.

## How to run
Run `mvnw.cmd spring-boot:run` at module root.

## Standards
- Package by feature: `auth`, `config`, `controller`, `dto`, `model`, `repository`, `service`.
- Javadoc on public types recommended.

## Java version
Java 21

## Database
Configured via `resources/application.properties`.
