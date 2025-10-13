# 🚀 CultivApp Backend - Quick Reference

## ✅ Current Status: RUNNING & VERIFIED

---

## 🏃 Start Application
```powershell
cd server\src\cultivapp\cultivapp
.\mvnw.cmd spring-boot:run
```
**Wait for**: "Started CultivappApplication" message (~4 seconds)

---

## 🧪 Test Login API

### Option 1: PowerShell
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/auth/login" `
  -Method POST `
  -Headers @{"Content-Type"="application/json"} `
  -Body '{"email":"productor@cultivapp.com","password":"password"}'
```

### Option 2: cURL
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"productor@cultivapp.com","password":"password"}'
```

### Option 3: Postman/Insomnia
```
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "email": "productor@cultivapp.com",
  "password": "password"
}
```

---

## 👤 Test Credentials

| Email | Password | Role |
|-------|----------|------|
| `admin@cultivapp.com` | `password` | ADMIN |
| `productor@cultivapp.com` | `password` | PRODUCTOR |

---

## 🗄️ Access H2 Console

1. Open browser: `http://localhost:8080/h2-console`
2. Fill in:
   - **JDBC URL**: `jdbc:h2:file:./server/.data/cultivapp-dev`
   - **Username**: `sa`
   - **Password**: (leave empty)
3. Click "Connect"

---

## 📊 Database Tables (11)

```sql
SELECT * FROM paises;          -- Countries
SELECT * FROM ciudades;        -- Cities
SELECT * FROM usuarios;        -- Users
SELECT * FROM especies;        -- Plant species
SELECT * FROM cultivos;        -- Crops
SELECT * FROM tareas;          -- Tasks
SELECT * FROM alertas;         -- Alerts
SELECT * FROM reglas;          -- Rules
SELECT * FROM etapas;          -- Growth stages
SELECT * FROM datos_climaticos; -- Climate data
SELECT * FROM configuracion_sistema; -- Config
```

---

## 🔄 Reset Database

```powershell
# Stop application (Ctrl+C in terminal)
Remove-Item "server\src\cultivapp\cultivapp\server\.data\cultivapp-dev.mv.db"
.\mvnw.cmd spring-boot:run
# Database will be recreated from schema.sql and data.sql
```

---

## 📁 Important Files

| File | Purpose |
|------|---------|
| `application-dev.yml` | Database configuration |
| `schema.sql` | Table definitions (DDL) |
| `data.sql` | Sample data (DML) |
| `server/.data/cultivapp-dev.mv.db` | Database file |

---

## 🐛 Troubleshooting

### Application Won't Start
```powershell
# Check Java version (need 21)
java -version

# Check if port 8080 is in use
netstat -ano | findstr :8080

# Clean and rebuild
.\mvnw.cmd clean install
```

### Login Returns 401
- Verify credentials are exactly: `productor@cultivapp.com` / `password`
- Check H2 console that `usuarios` table has data
- Verify BCrypt hashes in database

### Database Not Created
- Check working directory is `server\src\cultivapp\cultivapp`
- Verify `schema.sql` and `data.sql` exist in `src/main/resources`
- Check application logs for SQL errors

---

## 📝 API Response Format

### Successful Login (200)
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "role": "PRODUCTOR"
}
```

### Failed Login (401)
```json
{
  "error": "Invalid credentials"
}
```

---

## 🎯 Next Development Steps

1. **Create Controllers** for CRUD operations
2. **Add Services** for business logic
3. **Implement DTOs** for request/response
4. **Add Validation** for input data
5. **Create Tests** for endpoints
6. **Document APIs** with Swagger

---

## 📚 Documentation

- **Full Verification**: `DB_IMPLEMENTATION_VERIFIED.md` (root)
- **Detailed Results**: `server/DB_VERIFICATION_COMPLETE.md`
- **Setup Guide**: `server/db-setup.md`
- **Backend Docs**: `server/readme.md`

---

## 🔗 Useful URLs

| Service | URL |
|---------|-----|
| Application | `http://localhost:8080` |
| H2 Console | `http://localhost:8080/h2-console` |
| Login API | `http://localhost:8080/api/auth/login` |

---

## ✅ Verification Commands

```powershell
# Check if running
curl http://localhost:8080/api/auth/login

# Check database file exists
Test-Path "server\src\cultivapp\cultivapp\server\.data\cultivapp-dev.mv.db"

# View application logs
Get-Content "server\src\cultivapp\cultivapp\target\spring-boot.log" -Tail 50
```

---

**Quick Start**: Run `.\mvnw.cmd spring-boot:run` and test login API ✅
