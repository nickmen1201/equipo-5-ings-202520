# CultivApp Backend

Spring Boot REST API for agricultural crop management.

---

## Tech Stack

- Java 21
- Spring Boot 3.5.6
- Spring Security (JWT)
- Spring Data JPA
- H2 Database (file-based, PostgreSQL-compatible)
- Maven

---

## Quick Start

```powershell
cd server\src\cultivapp\cultivapp
.\mvnw.cmd spring-boot:run
```
→ API on http://localhost:8080

On first run:
1. Creates `server/.data/` folder
2. Creates H2 database file
3. Runs `schema.sql` (tables)
4. Runs `data.sql` (sample data)

---

## API Endpoints

### Authentication (REQ-001)

**POST /api/auth/login**

Request:
```json
{
  "email": "productor@cultivapp.com",
  "password": "password"
}
```

Response (200 OK):
```json
{
  "token": "eyJhbGci...",
  "role": "PRODUCTOR"
}
```

**Sample Users:**
- Admin: `admin@cultivapp.com` / `password` (ADMIN)
- Producer: `productor@cultivapp.com` / `password` (PRODUCTOR)

---

## Project Structure

```
src/main/
├── java/com/cultivapp/cultivapp/
│   ├── auth/              # Login, JWT, security
│   ├── model/             # JPA entities (Usuario, Cultivo, etc.)
│   └── repository/        # Data access (Spring Data JPA)
└── resources/
    ├── application.yml         # Base config
    ├── application-dev.yml     # Dev settings (H2)
    ├── schema.sql              # DDL
    └── data.sql                # Sample data
```

---

## Database

**H2 file-based** (PostgreSQL-compatible):
- Location: `server/.data/cultivapp-dev.mv.db`
- Persists across restarts
- No installation required

See [Database Setup Guide](../doc/db-setup.md) for details.

---

## Development

**Run tests:**
```powershell
.\mvnw.cmd test
```

**Build JAR:**
```powershell
.\mvnw.cmd clean package
```

**Run JAR:**
```powershell
java -jar target\cultivapp-0.0.1-SNAPSHOT.jar
```

---

## Configuration

**Database:** `application-dev.yml`  
**Server port:** `application.yml` (default: 8080)  
**JWT secret:** `application.yml` (change for production)
