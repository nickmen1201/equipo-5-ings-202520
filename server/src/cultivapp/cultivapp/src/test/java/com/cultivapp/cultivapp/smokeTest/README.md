# Smoke Tests - CultivApp Backend

## Descripción
Este directorio contiene los smoke tests críticos que deben ejecutarse en cada despliegue para verificar que las funcionalidades principales del sistema están operativas.

## Tests Implementados

### ✅ 1. LoginSmokeTest
**Archivo:** `LoginSmokeTest.java`

**Qué flujo cubre:** Validación de credenciales y generación de token JWT

**Por qué es crítico:** Sin autenticación funcionando, ningún usuario puede acceder a la aplicación

**Tipo de test:** Integration

**Escenarios probados:**
- 1.1 - Login exitoso con credenciales válidas retorna token JWT
- 1.2 - Token JWT tiene estructura correcta (header.payload.signature)
- 1.3 - Login con credenciales inválidas retorna 401 Unauthorized
- 1.4 - Login con email inexistente retorna 404 Not Found
- 1.5 - Login con datos incompletos retorna 400 Bad Request

---

### ✅ 3. JwtValidationSmokeTest
**Archivo:** `JwtValidationSmokeTest.java`

**Qué flujo cubre:** Verificación del sistema de validación de tokens JWT

**Por qué es crítico:** La seguridad de toda la aplicación depende de este mecanismo

**Tipo de test:** Integration

**Escenarios probados:**
- 3.1 - Token válido permite acceso a recursos protegidos (200 OK)
- 3.2 - Token inválido/manipulado es rechazado (401/403)
- 3.3 - Petición sin token es rechazada (401 Unauthorized)
- 3.4 - Token con formato incorrecto es rechazado
- 3.5 - Token sin prefijo 'Bearer' es rechazado
- 3.6 - Token vacío es rechazado
- 3.7 - Token válido permite múltiples peticiones consecutivas
- 3.8 - Diferentes endpoints protegidos requieren token válido

---

### ✅ 5. DatabaseConnectionSmokeTest
**Archivo:** `DatabaseConnectionSmokeTest.java`

**Qué flujo cubre:** Verificación de conectividad con PostgreSQL y carga de datos iniciales

**Por qué es crítico:** Sin BD funcionando, ninguna operación de persistencia es posible

**Tipo de test:** Integration

**Escenarios probados:**
- 5.1 - Conexión básica a base de datos funciona
- 5.2 - Usuarios iniciales cargados correctamente (admin y productor)
- 5.3 - Especies base cargadas correctamente (8 especies)
- 5.4 - Reglas predefinidas cargadas correctamente (13 reglas)
- 5.5 - Etapas cargadas correctamente (54 etapas)
- 5.6 - Todas las especies tienen etapas asociadas
- 5.7 - Tablas principales existen y son accesibles
- 5.8 - Operaciones CRUD básicas funcionan

---

## Cómo Ejecutar los Smoke Tests

### Prerrequisitos
1. Base de datos PostgreSQL corriendo
2. Variables de entorno configuradas (ver `application-dev.properties`)
3. Datos iniciales cargados (ejecutar `data.sql` y `seed-etapas.sql`)

### Ejecutar todos los smoke tests
```powershell
cd server/src/cultivapp/cultivapp
./mvnw.cmd test -Dtest="*SmokeTest"
```

### Ejecutar un smoke test específico
```powershell
# Test de Login
./mvnw.cmd test -Dtest=LoginSmokeTest

# Test de JWT Validation
./mvnw.cmd test -Dtest=JwtValidationSmokeTest

# Test de Database Connection
./mvnw.cmd test -Dtest=DatabaseConnectionSmokeTest
```

### Ejecutar con perfil de test
```powershell
./mvnw.cmd test -Dtest="*SmokeTest" -Dspring.profiles.active=test
```

## Tiempo de Ejecución
- LoginSmokeTest: ~3 segundos
- JwtValidationSmokeTest: ~4 segundos
- DatabaseConnectionSmokeTest: ~2 segundos

**Total:** < 10 segundos (cumple con el requisito de ejecución)

## Criterios de Éxito
Todos los tests deben pasar (verde) para considerar el despliegue exitoso. Si algún test falla:

1. **LoginSmokeTest falla:** Problema con autenticación, servicio de usuarios o generación de JWT
2. **JwtValidationSmokeTest falla:** Problema con la configuración de seguridad o validación de tokens
3. **DatabaseConnectionSmokeTest falla:** Problema con conexión a BD o datos iniciales no cargados

## Configuración de Base de Datos para Tests

### Opción 1: Usar base de datos de desarrollo
Asegurarse de que `application-dev.properties` tiene la configuración correcta:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/cultivapp_dev
spring.datasource.username=postgres
spring.datasource.password=postgres
```

### Opción 2: Usar perfil de test
Crear `application-test.properties` con una BD de test separada:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/cultivapp_test
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=create-drop
```

## Integración Continua (CI/CD)
Estos smoke tests deben ejecutarse automáticamente en el pipeline de CI/CD:

```yaml
# Ejemplo para GitHub Actions
- name: Run Smoke Tests
  run: |
    cd server/src/cultivapp/cultivapp
    ./mvnw test -Dtest="*SmokeTest"
```

## Notas Adicionales
- Los tests son independientes entre sí (no dependen del orden de ejecución)
- Cada test limpia su estado después de ejecutarse
- Los tests usan `@SpringBootTest` para cargar el contexto completo de la aplicación
- Se recomienda ejecutar los smoke tests antes de cada despliegue a producción

## Tests Pendientes (No Implementados)
Los siguientes smoke tests están documentados pero no implementados aún:

- **2. CRUD de Cultivos - Creación y Consulta**
- **4. Catálogo de Especies con Etapas**

Se recomienda implementar estos tests en futuras iteraciones para tener cobertura completa de los 5 puntos críticos.

## Contacto
Para preguntas sobre los tests, contactar al equipo de desarrollo.
