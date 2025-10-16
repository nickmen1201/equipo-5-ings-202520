# CULTIVAAPVDOS — README

Este README explica la parte principal del proyecto, cómo instalar y ejecutar, los estándares aplicados, la versión de Java y la base de datos requerida, y una breve guía para resolver un error común (conflicto de clave primaria al inicializar datos).

## ¿Qué hace esta carpeta?
La raíz del repositorio contiene una aplicación Spring Boot. Es un backend CRUD para gestionar usuarios, especies y cultivos. La estructura principal relevante:

- `src/main/java/.../auth` — Autenticación y seguridad (controladores, servicio JWT, configuración de seguridad).
- `src/main/java/.../controllers` — Controladores REST para `usuarios`, `especies`, `cultivos`.
- `src/main/java/.../models` — Entidades JPA (`Usuario`, `Especie`, `Cultivo`) y enums.
- `src/main/java/.../repositories` — Repositorios Spring Data JPA.
- `src/main/java/.../services` — Lógica de negocio.
- `src/main/resources` — Configuración y datos iniciales (`application.properties`, `application-dev.properties`, `data.sql`).

## ¿Cómo se instala esta parte del proyecto?
Requisitos previos:
- Java 21 (el proyecto está configurado con `<java.version>21</java.version>` en `pom.xml`).
- Maven (opcional si usas los wrappers `mvnw`/`mvnw.cmd`).

Pasos:
1. Clona el repositorio o descarga el código.
2. Abre una terminal en la carpeta raíz donde está `pom.xml`.
3. Construye el proyecto (opcional, Spring Boot lo hará al ejecutar):

```powershell
# Si usas mvnw en Windows (desde PowerShell)
./mvnw.cmd clean package -DskipTests
```

## ¿Cómo se corre esta parte del proyecto?
Puedes ejecutar con Maven wrapper o desde tu IDE (Run as Spring Boot app). Ejemplos:

```powershell
# Ejecutar con mvnw (recomendado en desarrollo)
./mvnw.cmd spring-boot:run

# O ejecutar el JAR después de build
java -jar target\CULTIVAAPVDOS-0.0.1-SNAPSHOT.jar
```

La aplicación arranca por defecto en el puerto 8080.

Rutas útiles (según `request.http` incluido en el repo):
- POST `/api/auth/register` — Registrar usuario
- POST `/api/auth/login` — Login (genera JWT)
- CRUD `/api/especies` — Gestión de especies
- CRUD `/api/cultivos` — Gestión de cultivos
- GET `/api/cultivos/usuario/{id}` — cultivos por usuario

## Base de datos
- En desarrollo el proyecto usa H2 (dependencia `com.h2database:h2`), configurado en `application-dev.properties` en modo file: `jdbc:h2:file:./server/.data/cultivapp-dev`.
- También hay dependencia de PostgreSQL agregada, por si quieres conectar a Postgres en producción.

Si usas H2 puedes (opcional) habilitar la consola web editando `application-dev.properties`:

```
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

La URL de conexión (según `application-dev.properties`) es similar a:

```
jdbc:h2:file:./server/.data/cultivapp-dev;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;AUTO_SERVER=TRUE
user: sa
password: (vacío)
```

## Estándares y prácticas
- Lombok para reducir código boilerplate (`@Getter/@Setter/@Builder` etc.).
- JPA/Hibernate para persistencia.
- JWT para autenticación; el `AuthService` genera un token con el claim `role` (rol del usuario).
- Roles definidos en `models/enums/Roles.java` (ej.: `ADMIN`, `PRODUCTOR`).
- Validaciones de request con `spring-boot-starter-validation`.
- Los controladores usan rutas REST y devuelven respuestas JSON.
- Si necesitas seguridad por roles, editar `SecurityConfig` para proteger rutas con `.requestMatchers(...).hasRole("ADMIN")` o usar `@PreAuthorize` en métodos.


## Versiones y herramientas
- Java: 21 (ver `pom.xml` property `<java.version>`)
- Maven: usa `mvnw` / `mvnw.cmd` incluidos
- H2: dependencia runtime para desarrollo

