# CultivApp - Backend (Servidor)

## Descripción General
Backend de CultivApp construido con Spring Boot 3.x, proporciona una API REST para la gestión de cultivos agrícolas, incluyendo autenticación JWT, gestión de especies, cultivos, etapas de crecimiento y sistema de reglas para generación automática de tareas.

## Stack Tecnológico
- Java 21
- Spring Boot 3.x
- Spring Security con JWT
- Spring Data JPA / Hibernate
- PostgreSQL 16
- Lombok
- Maven 3.9.6

## Instalación

### Prerrequisitos
- Java 21 JDK
- Maven 3.9.6 o superior (o usar mvnw incluido)
- PostgreSQL 16

### Pasos de Instalación
```powershell
# Navegar al directorio del servidor
cd server/src/cultivapp/cultivapp

# Instalar dependencias y compilar
./mvnw.cmd clean package -DskipTests
```

## Ejecución de la Aplicación

### Modo Desarrollo
```powershell
./mvnw.cmd spring-boot:run
```
La aplicación se iniciará en `http://localhost:8080`

### Ejecutar JAR
```powershell
java -jar target/cultivapp-0.0.1-SNAPSHOT.jar
```

## Estructura del Proyecto
```
src/main/java/com/cultivapp/cultivapp/
├── auth/              # Autenticación y JWT
├── config/            # Configuración (Security, CORS)
├── controllers/       # Controladores REST
├── dto/               # Data Transfer Objects
├── models/            # Entidades JPA
│   └── enums/         # Enumeraciones (Roles, Estado, TipoEtapa, etc.)
├── repositories/      # Repositorios Spring Data JPA
└── services/          # Lógica de negocio y schedulers

src/main/resources/
├── application.properties           # Configuración general
├── application-dev.properties       # Configuración desarrollo
├── application-prod.properties      # Configuración producción
└── data.sql                         # Datos iniciales
```

## Características Principales
- Autenticación y autorización con JWT
- Gestión de usuarios con roles (ADMIN, PRODUCTOR)
- CRUD completo de especies de plantas
- CRUD completo de cultivos
- Sistema de etapas de crecimiento con progresión automática
- Motor de reglas para generación de tareas
- Scheduler automático para verificación de etapas y generación de tareas
- Base de datos PostgreSQL con datos iniciales precargados

## Variables de Entorno

### Desarrollo (`application-dev.properties`)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/cultivapp_dev
spring.datasource.username=postgres
spring.datasource.password=postgres
jwt.secret=your-secret-key-here
jwt.expiration=86400000
```

### Producción (`application-prod.properties`)
```properties
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
jwt.secret=${JWT_SECRET}
jwt.expiration=${JWT_EXPIRATION}
```

## Base de Datos

### Configuración de PostgreSQL
La aplicación utiliza PostgreSQL 16. Se debe crear una base de datos:

```sql
CREATE DATABASE cultivapp_dev;
```

### Esquema Inicial
El esquema se crea automáticamente mediante JPA (`spring.jpa.hibernate.ddl-auto=update`).

### Datos Iniciales
El archivo `data.sql` contiene:
- 2 usuarios de prueba (admin@cultivapp.com, productor@cultivapp.com)
- 8 especies de plantas base
- 13 reglas predefinidas para tareas
- Script `seed-etapas.sql` adicional con 54 etapas y 146 relaciones etapa-regla

### Ejecutar Script de Etapas
```powershell
# Conectar a PostgreSQL y ejecutar
Get-Content "server/src/cultivapp/cultivapp/src/main/resources/seed-etapas.sql" | docker exec -i cultivapp-db psql -U postgres -d cultivapp_dev
```

## Endpoints Principales

### Autenticación
- `POST /api/auth/register` - Registro de usuario
- `POST /api/auth/login` - Login (retorna JWT)

### Especies
- `GET /api/especies` - Listar todas las especies
- `GET /api/especies/{id}` - Obtener especie por ID
- `POST /api/especies` - Crear especie (ADMIN)
- `PUT /api/especies/{id}` - Actualizar especie (ADMIN)
- `DELETE /api/especies/{id}` - Eliminar especie (ADMIN)

### Cultivos
- `GET /api/cultivos` - Listar todos los cultivos
- `GET /api/cultivos/{id}` - Obtener cultivo por ID
- `GET /api/cultivos/usuario/{usuarioId}` - Cultivos por usuario
- `POST /api/cultivos` - Crear cultivo
- `PUT /api/cultivos/{id}` - Actualizar cultivo
- `DELETE /api/cultivos/{id}` - Eliminar cultivo
- `PATCH /api/cultivos/{id}/estado` - Cambiar estado del cultivo

### Etapas
- `GET /api/etapas/especie/{especieId}` - Etapas por especie
- `POST /api/etapas` - Crear etapa (ADMIN)
- `DELETE /api/etapas/{id}` - Eliminar etapa (ADMIN)

### Reglas
- `GET /api/reglas` - Listar todas las reglas
- `POST /api/reglas` - Crear regla (ADMIN)

## Estándares y Buenas Prácticas
- Arquitectura en capas: Controller → Service → Repository
- DTOs para transferencia de datos (evitar exponer entidades directamente)
- Validación de entrada con Bean Validation (`@Valid`, `@NotNull`, etc.)
- Manejo de excepciones centralizado
- Javadoc en clases y métodos públicos
- Lombok para reducir boilerplate
- Separación de configuración por perfiles (dev, prod)

## Seguridad
- Autenticación basada en JWT (JSON Web Tokens)
- Tokens incluyen claims: `userId`, `email`, `role`
- Expiración configurable (por defecto 24 horas)
- Endpoints protegidos por rol mediante Spring Security
- CORS configurado para `localhost:5173` (frontend) y `localhost:3000`

---

## Testing

### 5 Puntos Críticos del Smoke Test

Esta sección documenta los 5 puntos críticos del smoke test para el backend de CultivApp. Estos tests se ejecutan en cada despliegue para verificar que las funcionalidades principales del servidor están operativas.

---

### 1. Login (Endpoint de Autenticación)

**¿Qué flujo cubre y por qué debe hacer parte del smoke test?**

Este test cubre el endpoint de autenticación que valida las credenciales del usuario y genera un token JWT. Cuando se recibe una petición POST a `/api/auth/login` con email y contraseña, el sistema verifica las credenciales contra la base de datos, y si son correctas, genera y retorna un token JWT válido junto con la información básica del usuario. Este endpoint es la puerta de entrada al sistema y sin él funcionando, ningún usuario puede acceder a la aplicación.

**¿Cómo se ejecuta?**

1. Enviar una petición POST a `/api/auth/login` con el body:
```json
{
  "email": "productor@cultivapp.com",
  "password": "password"
}
```

2. Verificar que la respuesta tiene status `200 OK`

3. Verificar que la respuesta contiene:
   - Un token JWT válido
   - Información del usuario (id, email, rol)
   - No contiene la contraseña en la respuesta

4. Verificar que el token generado tiene la estructura correcta (header.payload.signature)

5. Opcionalmente, verificar que con credenciales inválidas retorna `401 Unauthorized`

**¿Qué tipo de test lo prueba?**

**Integration**: Prueba la integración entre el controller de autenticación, el servicio de usuarios, el repositorio, la base de datos y la generación de JWT.

Se puede complementar con tests **Unit** para las funciones individuales de validación de contraseñas y generación de tokens.

---

### 2. CRUD de Cultivos - Creación y Consulta

**¿Qué flujo cubre y por qué debe hacer parte del smoke test?**

Este test cubre los endpoints de creación y consulta de cultivos, que son las operaciones más críticas del sistema. La creación permite insertar un nuevo cultivo asociado a un usuario y una especie, mientras que la consulta permite obtener la lista de cultivos. Sin estas operaciones, la funcionalidad core de CultivApp no existe. Este test verifica que el backend puede recibir datos de cultivos, validarlos, persistirlos en la base de datos, y posteriormente recuperarlos.

**¿Cómo se ejecuta?**

**Creación de cultivo:**

1. Obtener un token JWT válido (autenticarse primero)

2. Enviar una petición POST a `/api/cultivos` con headers de autorización:
```http
Authorization: Bearer {token}
```

Body de la petición:
```json
{
  "nombre": "Tomates Cherry Test",
  "especieId": 1,
  "usuarioId": 2,
  "areaHectareas": 50.5,
  "etapaActual": 1,
  "estado": "ACTIVO"
}
```

3. Verificar que la respuesta tiene status `201 Created`

4. Verificar que retorna el objeto del cultivo creado con su ID asignado

**Consulta de cultivos:**

1. Enviar una petición GET a `/api/cultivos` con el token de autorización

2. Verificar que la respuesta tiene status `200 OK`

3. Verificar que retorna un array de cultivos

4. Verificar que cada cultivo contiene las propiedades esperadas

5. Verificar que los cultivos incluyen información de especie y usuario

**¿Qué tipo de test lo prueba?**

**Integration**: Prueba la integración completa desde el controller hasta la base de datos, incluyendo validaciones, seguridad (JWT) y persistencia.

Puede complementarse con tests **Unit** para las validaciones de negocio específicas (área mínima, máxima, etc.).

---

### 3. Validación de Tokens JWT

**¿Qué flujo cubre y por qué debe hacer parte del smoke test?**

Este test verifica que el sistema de validación de tokens JWT funciona correctamente. Incluye verificar que tokens válidos permiten acceso a recursos protegidos, que tokens expirados son rechazados, y que tokens manipulados o inválidos no otorgan acceso. La seguridad de toda la aplicación depende de este mecanismo, ya que protege todos los endpoints que requieren autenticación.

**¿Cómo se ejecuta?**

**Escenario 1 - Token válido:**
1. Acceder a un endpoint protegido (GET `/api/cultivos`) con un token válido
2. Verificar que la respuesta es `200 OK` y retorna los datos solicitados

**Escenario 2 - Token inválido/manipulado:**
1. Intentar acceder con un token falso o modificado
2. Verificar que la respuesta es `401 Unauthorized` o `403 Forbidden`
3. Verificar que el mensaje de error indica token inválido

**Escenario 3 - Sin token:**
1. Intentar acceder sin incluir el header `Authorization`
2. Verificar que la respuesta es `401 Unauthorized`

**Escenario 4 - Token expirado:**
1. Intentar acceder con un token que ha expirado
2. Verificar que la respuesta es `401 Unauthorized`
3. Verificar que el mensaje indica que el token ha expirado

**¿Qué tipo de test lo prueba?**

**Unit/Integration**: Tests unitarios para las funciones de validación de JWT (JwtService), y tests de integración para verificar que el filtro/middleware de seguridad (`SecurityConfig`) funciona correctamente en el flujo completo de peticiones.

---

### 4. Catálogo de Especies con Etapas

**¿Qué flujo cubre y por qué debe hacer parte del smoke test?**

Este test cubre el endpoint que retorna el catálogo completo de especies de plantas disponibles en el sistema, junto con sus etapas de crecimiento asociadas. Las especies son entidades maestras fundamentales porque definen las características y requerimientos de cada tipo de cultivo. Sin acceso a este catálogo y sin etapas definidas, no se pueden crear cultivos válidos ni generar tareas automáticamente. Este test verifica que el endpoint responde correctamente, que la base de datos contiene las especies necesarias, y que cada especie tiene sus etapas de crecimiento configuradas.

**¿Cómo se ejecuta?**

1. Autenticarse y obtener un token JWT válido

2. Enviar una petición GET a `/api/especies` con el token de autorización:
```http
Authorization: Bearer {token}
```

3. Verificar que la respuesta tiene status `200 OK`

4. Verificar que retorna un array de 8 especies (Tomate, Maíz, Frijol, Lechuga, Zanahoria, Papa, Cebolla, Pimentón)

5. Verificar que cada especie contiene las propiedades esperadas:
   - `id`, `nombre`, `nombreCientifico`
   - `descripcion`, `aguaSemanalMm`
   - `imagenUrl`, `activo`, `fechaCreacion`

6. Verificar que hay al menos una especie en el sistema (datos iniciales cargados)

7. **Verificación de etapas por especie:**
   - Enviar GET a `/api/etapas/especie/{especieId}` para cada especie
   - Verificar que cada especie tiene entre 5-8 etapas definidas
   - Verificar que las etapas tienen: `id`, `nombre`, `orden`, `duracionDias`
   - Verificar que los nombres de etapas son válidos (PREPARACION, SIEMBRA, GERMINACION, DESARROLLO_VEGETATIVO, FLORACION, FRUCTIFICACION, MADURACION, COSECHA)

8. **Verificación de totalEtapas:**
   - Obtener un cultivo existente (GET `/api/cultivos/{id}`)
   - Verificar que el campo `totalEtapas` de la especie coincide con el conteo de etapas
   - Ejemplo: Tomate debe tener `totalEtapas = 8`

**¿Qué tipo de test lo prueba?**

**Integration**: Prueba la integración entre el controller de especies, el servicio de especies, el servicio de etapas, los repositorios y la base de datos. Verifica que las relaciones entre especies y etapas están correctamente configuradas.

Puede incluir tests **Unit** para validaciones específicas de los datos de especies y etapas.

---

### 5. Conexión a Base de Datos (PostgreSQL)

**¿Qué flujo cubre y por qué debe hacer parte del smoke test?**

Este test verifica que la conexión a la base de datos PostgreSQL está establecida y funcionando correctamente. Sin conexión a base de datos, ninguna operación de persistencia funciona y la aplicación es completamente inútil. Este test debe ejecutarse al inicio del smoke test para fallar rápido si hay problemas de infraestructura básica. También verifica que las tablas principales existen y que los datos iniciales se cargaron correctamente.

**¿Cómo se ejecuta?**

**Opción 1 - Health Check endpoint (Recomendada):**

1. Crear un endpoint simple (`/api/health`) que verifique la conexión

2. Enviar una petición GET a `/api/health`

3. El endpoint debe ejecutar una query simple a la BD (ejemplo: `SELECT 1`)

4. Verificar que la respuesta es `200 OK`

5. Verificar que el body indica que la BD está conectada:
```json
{
  "status": "UP",
  "database": "CONNECTED",
  "timestamp": "2025-11-12T20:00:00Z"
}
```

**Opción 2 - Verificación mediante operación real:**

Ejecutar cualquier consulta simple a la base de datos usando el repositorio directamente en el test:

```java
@Test
void testDatabaseConnection() {
    long count = usuarioRepository.count(); 
    assertTrue(count >= 0, "Database connection failed");
}
```

Verificar que la operación se completa sin excepciones.

**Verificaciones adicionales:**

1. Verificar que las tablas principales existen:
   - `usuarios`, `especies`, `cultivos`, `etapas`, `reglas`, `etapas_reglas`, `tareas`

2. Verificar que los datos iniciales se cargaron:
   - Al menos 2 usuarios (admin y productor)
   - 8 especies base
   - 13 reglas predefinidas
   - 54 etapas (después de ejecutar `seed-etapas.sql`)
   - 146 relaciones etapa-regla

3. Verificar integridad referencial:
   - Todas las etapas tienen un `especie_id` válido
   - Todas las relaciones `etapas_reglas` apuntan a etapas y reglas existentes

**¿Qué tipo de test lo prueba?**

**Integration**: Prueba la integración entre Spring Boot, JPA/Hibernate y la base de datos PostgreSQL. Es fundamentalmente un test de infraestructura que debe ejecutarse primero.

Puede complementarse con tests **Unit** para las configuraciones de conexión y propiedades de DataSource.

---

## Notas de Implementación de Tests

### Estructura de Tests

- Los tests de integración deben usar `@SpringBootTest` o `@WebMvcTest` según el alcance
- Los tests unitarios deben usar mocks con Mockito
- Se recomienda usar `@Sql` para cargar datos de prueba específicos
- Usar `@TestConfiguration` para configuraciones específicas de tests

### Ejecución y Ambiente

- Todos los smoke tests deben ser independientes y no depender del orden de ejecución
- El tiempo total de ejecución de los smoke tests debe ser menor a 10 minutos
- Usar perfiles de Spring (`@ActiveProfiles("test")`) para aislar la configuración de pruebas
- La base de datos utilizada es PostgreSQL
- Considerar usar Testcontainers para tests de integración con base de datos real

### Comandos de Ejecución

```powershell
# Ejecutar todos los tests
./mvnw.cmd test

# Ejecutar solo smoke tests (si están etiquetados)
./mvnw.cmd test -Dgroups=smoke

# Ejecutar con perfil de test
./mvnw.cmd test -Dspring.profiles.active=test

# Ver reporte de cobertura
./mvnw.cmd test jacoco:report
```

## Schedulers Automáticos

### TareaScheduler
- Ejecuta cada 24 horas
- Genera tareas automáticamente basadas en las reglas asociadas a la etapa actual de cada cultivo
- Solo genera tareas para cultivos en estado ACTIVO

### EtapaScheduler
- Ejecuta cada hora
- Verifica si los cultivos deben avanzar a la siguiente etapa basándose en la duración de días
- Actualiza automáticamente `etapaActual` cuando corresponde

## Despliegue

### Checklist de Despliegue
- [ ] Ejecutar todos los smoke tests exitosamente
- [ ] Verificar configuración de variables de entorno de producción
- [ ] Verificar conexión a base de datos PostgreSQL de producción
- [ ] Ejecutar migraciones de base de datos si es necesario
- [ ] Ejecutar script `seed-etapas.sql` en base de datos de producción
- [ ] Verificar que los schedulers están activos
- [ ] Verificar logs de aplicación (sin errores críticos)
- [ ] Verificar que CORS permite el dominio del frontend de producción

### Construcción de JAR para Producción
```powershell
./mvnw.cmd clean package -DskipTests
```

El JAR se generará en `target/cultivapp-0.0.1-SNAPSHOT.jar`

## Contacto
Para preguntas o problemas, contactar al equipo de desarrollo.
