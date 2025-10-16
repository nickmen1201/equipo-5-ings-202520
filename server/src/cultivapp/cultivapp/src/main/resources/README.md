# Resources

## What does this folder do?
Holds Spring configuration and seed data for the backend.

- `application.properties` — base config
- `application-dev.properties` — dev profile
- `data.sql` — initial data (users, species,...)

## How to install
No installation required.

## How to run
Run the app from the module root with `mvnw.cmd spring-boot:run`.

## Standards
- Use profiles for environment-specific config (e.g., `dev`).
- Keep secrets out of source control.

## Java version
Java 21

## Database
- H2 (file) in development, created automatically.
