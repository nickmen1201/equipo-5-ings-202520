# Database Setup Guide

## Overview

**H2 Database** in file-based mode with PostgreSQL compatibility:
- Data persists across restarts
- No external database install required
- PostgreSQL-compatible for easy migration
- Location: `server/.data/cultivapp-dev.mv.db` (auto-created)

---

## Quick Start

### 1. Start Backend
```powershell
cd server\src\cultivapp\cultivapp
.\mvnw.cmd spring-boot:run
```
→ API on http://localhost:8080  
→ Database auto-created with sample data

### 2. Start Frontend
```bash
cd client
npm install  # First time only
npm run dev
```
→ UI on http://localhost:5173

---

## Sample Data

**Users:**
- Admin: `admin@cultivapp.com` / `password` (ADMIN role)
- Producer: `productor@cultivapp.com` / `password` (PRODUCTOR role)

**Plant Species:** Tomate, Maíz, Papa, Café, Frijol, Zanahoria, Lechuga, Cebolla

**Sample Crop:** "Tomates Lote A" (producer user, active)

---

## Configuration

**Location:** `server/src/cultivapp/cultivapp/src/main/resources/application-dev.yml`

```yaml
spring:
  datasource:
    url: jdbc:h2:file:./server/.data/cultivapp-dev;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;AUTO_SERVER=TRUE
    driver-class-name: org.h2.Driver
    username: sa
    password:
  jpa:
    hibernate:
      ddl-auto: none  # Uses schema.sql instead
```

---

## Common Tasks

### Reset Database
```powershell
# Stop backend (Ctrl+C)
Remove-Item server\.data\cultivapp-dev.mv.db
# Restart backend - fresh database created
```

### Backup Database
```powershell
Copy-Item server\.data\cultivapp-dev.mv.db server\.data\backup.mv.db
```

### View Database
Use SQL client (DBeaver, IntelliJ):
- URL: `jdbc:h2:file:./server/.data/cultivapp-dev`
- Driver: H2
- User: `sa` / Password: (empty)

### Switch to PostgreSQL
Create `application-prod.yml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/cultivapp
    username: your_user
    password: your_pass
```
Run: `.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=prod`

---

## Database Schema

**11 Tables:**
1. `usuarios` - System users
2. `especies` - Plant species catalog
3. `cultivos` - User's crops
4. `tareas` - Tasks
5. `alertas` - Alerts
6. `reglas` - Alert rules
7. `etapas` - Growth stages
8. `datos_climaticos` - Climate data
9. `configuracion_sistema` - System config
10. `ciudades` - Cities
11. `paises` - Countries

**Enums (stored as VARCHAR):**
- `Rol`: ADMIN, PRODUCTOR
- `EtapaActual`: GERMINACION, CRECIMIENTO, FLORACION, MADURACION, COSECHADO
- `Estado`: ACTIVO, COSECHADO, PERDIDO
- `TipoTarea`: RIEGO, FERTILIZACION, PODA, COSECHA, OTRO

---

## Troubleshooting

| Issue | Solution |
|-------|----------|
| Database not found | Run from correct directory, check `application-dev.yml` path |
| Schema changes not applied | Delete database file, restart |
| Data not persisting | Verify using file-based mode (not in-memory), check file permissions |
| Port 8080 busy | Change `server.port` in `application.yml` |

---

## File Structure

```
server/
├── .data/
│   └── cultivapp-dev.mv.db         # H2 database (auto-created)
└── src/cultivapp/cultivapp/src/main/
    ├── resources/
    │   ├── application.yml          # Base config
    │   ├── application-dev.yml      # Dev config (H2)
    │   ├── schema.sql               # DDL
    │   └── data.sql                 # Sample data
    └── java/com/cultivapp/cultivapp/
        ├── model/                   # JPA entities
        ├── repository/              # Data access
        └── auth/                    # Authentication
```
