# CultivApp Server Workspace

## What does this folder do?
Holds the Maven workspace that contains the Spring Boot app under `cultivapp/`.

## How to install
No separate install. Use the Maven wrapper inside `cultivapp/`.

## How to run
`cd cultivapp && mvnw.cmd spring-boot:run` (Windows PowerShell).

## Standards
- Maven multi-folder layout, but single app in `cultivapp/`.

## Java version
Java 21

## Database
Configured by the app in `cultivapp/src/main/resources`.
