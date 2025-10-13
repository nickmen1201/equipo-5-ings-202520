# CultivApp Backend

Spring Boot REST API for CultivApp - Agricultural Crop Management System

## Technology Stack

- **Java 21**
- **Spring Boot 3.5.6**
- **Spring Security** (JWT Authentication)
- **Spring Data JPA** (Hibernate)
- **H2 Database** (File-based, PostgreSQL-compatible)
- **Maven** (Build tool)
- **Lombok** (Reduces boilerplate code)

## Database

The backend uses **H2 database in file-based mode** with PostgreSQL compatibility:

- **File location**: `server/.data/cultivapp-dev.mv.db`
- **Mode**: PostgreSQL-compatible SQL
- **Persistence**: Data survives application restarts
- **No installation required**: H2 is embedded in the application

See [Database Setup Guide](../doc/db-setup.md) for detailed information.

## Quick Start

### Prerequisites

- Java 21 or higher
- Maven (included via Maven Wrapper)

### Run the Application

```bash
cd server/src/cultivapp/cultivapp
./mvnw spring-boot:run
```

On Windows:
```powershell
cd server\src\cultivapp\cultivapp
.\mvnw.cmd spring-boot:run
```

The API will start on **http://localhost:8080**

On first run, the application will:
1. Create the `.data` folder
2. Create the database file
3. Run `schema.sql` to create tables
4. Run `data.sql` to populate sample data

## API Endpoints

### Authentication (REQ-001)

**POST /api/auth/login**
- Authenticate user and receive JWT token
- Request body:
  ```json
  {
    "email": "productor@cultivapp.com",
    "password": "Productor123"
  }
  ```
- Response (200 OK):
  ```json
  {
    "token": "eyJhbGci...",
    "role": "PRODUCTOR"
  }
  ```

### Sample Users

- **Admin**: `admin@cultivapp.com` / `password` (Role: ADMIN)
- **Producer**: `productor@cultivapp.com` / `password` (Role: PRODUCTOR)

## Project Structure

```
src/
├── main/
│   ├── java/com/cultivapp/cultivapp/
│   │   ├── auth/              # Authentication logic (login, JWT, security)
│   │   ├── model/             # JPA entities (Usuario, Cultivo, Especie, etc.)
│   │   └── repository/        # Data access layer (Spring Data JPA)
│   └── resources/
│       ├── application.yml         # Base configuration
│       ├── application-dev.yml     # Development settings (H2 file-based)
│       ├── schema.sql              # Database schema (DDL)
│       └── data.sql                # Sample data (DML)
└── test/                      # Unit and integration tests
```

## Database Schema

### Core Entities

- **usuarios** - System users (admin and producers)
- **especies** - Plant species catalog
- **cultivos** - User's crops
- **tareas** - Tasks for crops (irrigation, fertilization, etc.)
- **alertas** - Alerts for crops
- **reglas** - Alert rules by growth stage
- **etapas** - Growth stages
- **datos_climaticos** - Climate data
- **configuracion_sistema** - System configuration
- **ciudades** / **paises** - Location data

All enums are stored as strings for database portability.

See `schema.sql` for complete schema definition.

## Development

### Build the Project

```bash
./mvnw clean install
```

### Run Tests

```bash
./mvnw test
```

### Reset Database

To start with a fresh database:

```bash
# Stop the application
# Delete the database file
rm server/.data/cultivapp-dev.mv.db  # Unix/Mac
Remove-Item server\.data\cultivapp-dev.mv.db  # Windows
# Restart the application
```

### Change Port

Edit `application.yml`:
```yaml
server:
  port: 8081
```

## Configuration Files

### application.yml
Base configuration shared across all environments.

### application-dev.yml
Development profile with H2 file-based database.

### schema.sql
DDL statements to create all tables, foreign keys, and indexes.

### data.sql
Sample data for development and testing.

## Security

- Passwords are hashed using **BCrypt** (never stored in plain text)
- JWT tokens for stateless authentication
- CORS enabled for development (frontend on different port)
- Generic error messages to prevent user enumeration

## Future Enhancements

- CRUD endpoints for Cultivos (crops)
- Admin catalog for Especies (plant species)
- Task management endpoints
- Alert system endpoints
- Climate data integration
- Production PostgreSQL configuration

## Troubleshooting

### Database not found
Ensure you're running from the correct directory. The database path is relative to the working directory.

### Port already in use
Change the port in `application.yml` or stop the application using port 8080.

### Schema changes not applied
Delete the database file and restart to recreate with the new schema.

See the [Database Setup Guide](../doc/db-setup.md) for more troubleshooting tips.

## Contributing

1. Create a feature branch from `main`
2. Make your changes
3. Write/update tests
4. Submit a pull request

## License

Internal project for academic purposes.
