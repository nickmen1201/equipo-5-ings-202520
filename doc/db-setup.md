# Database Setup Guide

## Overview

CultivApp uses **H2 Database** in file-based mode with PostgreSQL compatibility. This means:
- Data persists across application restarts (unlike in-memory mode)
- No external database installation required
- Works identically on all machines (Windows, Mac, Linux)
- PostgreSQL-compatible syntax for easy migration later

## Database Location

The database file is stored in:
```
server/.data/cultivapp-dev.mv.db
```

This file is automatically created when you first run the application.

## Quick Start

### 1. Run the Backend

Navigate to the backend directory and start the application:

```bash
cd server/src/cultivapp/cultivapp
./mvnw spring-boot:run
```

On Windows, use:
```powershell
cd server\src\cultivapp\cultivapp
.\mvnw.cmd spring-boot:run
```

The application will:
1. Create the `.data` folder if it doesn't exist
2. Create the `cultivapp-dev.mv.db` database file
3. Run `schema.sql` to create all tables
4. Run `data.sql` to populate sample data
5. Start the REST API on http://localhost:8080

### 2. Run the Frontend

In a separate terminal, navigate to the frontend directory:

```bash
cd client
npm install  # Only needed first time
npm run dev
```

The frontend will start on http://localhost:5173

## Sample Data

The database comes pre-loaded with:

### Users
- **Admin User**
  - Email: `admin@cultivapp.com`
  - Password: `password`
  - Role: ADMIN
  - Status: Active

- **Producer User**
  - Email: `productor@cultivapp.com`
  - Password: `password`
  - Role: PRODUCTOR
  - Status: Active

### Plant Species Catalog
- Tomate (Tomato)
- Maíz (Corn)
- Papa (Potato)
- Café (Coffee)
- Frijol (Bean)
- Zanahoria (Carrot)
- Lechuga (Lettuce)
- Cebolla (Onion)

### Sample Crop
- Name: Tomates Lote A
- Owner: Juan Pérez (productor user)
- Species: Tomato
- Status: Active, in growth stage

## Database Configuration

### Development Profile (application-dev.yml)

```yaml
spring:
  datasource:
    # File-based H2 keeps data even after restart
    url: jdbc:h2:file:./server/.data/cultivapp-dev;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;AUTO_SERVER=TRUE
    driver-class-name: org.h2.Driver
    username: sa
    password:
  
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: none  # We use schema.sql for table creation
```

### Configuration Explained

- `MODE=PostgreSQL` - Use PostgreSQL-compatible SQL syntax
- `DATABASE_TO_LOWER=TRUE` - Convert identifiers to lowercase
- `AUTO_SERVER=TRUE` - Allow multiple connections to same database file
- `ddl-auto: none` - Don't auto-generate schema (we use schema.sql)

## Common Tasks

### Reset the Database

To start with a fresh database:

1. Stop the backend application (Ctrl+C)
2. Delete the database file:
   ```bash
   rm server/.data/cultivapp-dev.mv.db
   ```
   On Windows:
   ```powershell
   Remove-Item server\.data\cultivapp-dev.mv.db
   ```
3. Restart the application - it will recreate the database with sample data

### View Database Contents

Since H2 Console is disabled in file mode, you can:

**Option 1: Use a SQL Client**

Connect using any SQL client (DBeaver, IntelliJ Database Tools, etc.):
- JDBC URL: `jdbc:h2:file:./server/.data/cultivapp-dev`
- Driver: H2
- Username: `sa`
- Password: (empty)

**Option 2: Query via Application**

Use the REST API endpoints to view data:
- Login: POST http://localhost:8080/api/auth/login
- Future endpoints will support CRUD operations on all entities

### Backup the Database

Simply copy the database file:

```bash
cp server/.data/cultivapp-dev.mv.db server/.data/cultivapp-dev.backup.mv.db
```

On Windows:
```powershell
Copy-Item server\.data\cultivapp-dev.mv.db server\.data\cultivapp-dev.backup.mv.db
```

### Switch to PostgreSQL (Production)

When ready for production:

1. Create `application-prod.yml` with PostgreSQL configuration:
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/cultivapp
       username: your_username
       password: your_password
       driver-class-name: org.postgresql.Driver
     jpa:
       database-platform: org.hibernate.dialect.PostgreSQLDialect
   ```

2. Run with production profile:
   ```bash
   ./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
   ```

3. The same `schema.sql` and `data.sql` will work due to PostgreSQL compatibility

## Database Schema

See the complete schema in:
- `server/src/cultivapp/cultivapp/src/main/resources/schema.sql`

### Main Tables

1. **usuarios** - System users (admin and producers)
2. **especies** - Plant species catalog
3. **cultivos** - User's crops
4. **tareas** - Tasks for crops
5. **alertas** - Alerts for crops
6. **reglas** - Alert rules
7. **etapas** - Growth stages
8. **datos_climaticos** - Climate data
9. **configuracion_sistema** - System configuration
10. **ciudades** - Cities catalog
11. **paises** - Countries catalog

### Enums (Stored as Strings)

All enums are stored as VARCHAR strings for portability:
- **Rol**: ADMIN, PRODUCTOR
- **EtapaActual**: GERMINACION, CRECIMIENTO, FLORACION, MADURACION, COSECHADO
- **Estado**: ACTIVO, COSECHADO, PERDIDO
- **TipoTarea**: RIEGO, FERTILIZACION, PODA, COSECHA, OTRO
- **TipoConfiguracion**: NUMERO, TEXTO, BOOLEAN

## Troubleshooting

### Database file not found

If you see "Database not found" errors:
1. Ensure you're running from the correct directory
2. Check that the path in `application-dev.yml` is correct
3. Delete any partial database files and restart

### Schema changes not applied

If you modify `schema.sql`:
1. Stop the application
2. Delete the database file
3. Restart - the new schema will be created

### Data not persisting

If data disappears after restart:
1. Check that you're using `application-dev.yml` (not in-memory mode)
2. Verify the database file exists in `server/.data/`
3. Check file permissions on the `.data` folder

### Port already in use

If port 8080 is busy:
1. Change port in `application.yml`:
   ```yaml
   server:
     port: 8081
   ```
2. Update frontend API URL in `client/src/services/authService.js`

## File Structure

```
server/
├── .data/                          # Database files (created automatically)
│   └── cultivapp-dev.mv.db         # H2 database file
└── src/cultivapp/cultivapp/
    └── src/main/
        ├── resources/
        │   ├── application.yml      # Base configuration
        │   ├── application-dev.yml  # Development (H2 file-based)
        │   ├── schema.sql           # Table creation DDL
        │   └── data.sql             # Sample data inserts
        └── java/com/cultivapp/cultivapp/
            ├── model/               # JPA entities
            ├── repository/          # Data access layer
            └── auth/                # Authentication logic
```

## Next Steps

Now that your database is configured:

1. Test login with sample users
2. Verify all tables are created
3. Start implementing CRUD endpoints for Cultivos
4. Start implementing CRUD endpoints for Especies (admin catalog)

For any issues, check the application logs or contact the development team.
