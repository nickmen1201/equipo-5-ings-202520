# How to Verify CultivApp is Working ✅

## Step 1: Start the Backend Application

### Option A: Using Maven Wrapper (Recommended)
```powershell
cd server\src\cultivapp\cultivapp
.\mvnw.cmd spring-boot:run
```

### Option B: Using the full path
```powershell
cd c:\Users\LENOVO\OneDrive\Escritorio\equipo-5-ings-202520\server\src\cultivapp\cultivapp
& ".\mvnw.cmd" spring-boot:run
```

### What to Look For:
You should see output similar to this:

```
[INFO] --- spring-boot:3.5.6:run (default-cli) @ cultivapp ---
[INFO] Attaching agents: []

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.5.6)

2025-10-12T... INFO ... CultivappApplication : Starting CultivappApplication
2025-10-12T... INFO ... CultivappApplication : The following 1 profile is active: "dev"
2025-10-12T... INFO ... TomcatWebServer : Tomcat initialized with port 8080 (http)
2025-10-12T... INFO ... TomcatWebServer : Tomcat started on port 8080 (http)
2025-10-12T... INFO ... CultivappApplication : Started CultivappApplication in X.XXX seconds
```

### Success Indicators:
✅ No red ERROR messages  
✅ Message says: `Started CultivappApplication`  
✅ Message says: `Tomcat started on port 8080`  
✅ Application doesn't crash or stop

## Step 2: Verify Database File Was Created

### Check if the database file exists:
```powershell
Test-Path "server\.data\cultivapp-dev.mv.db"
```

**Expected Output:** `True`

### View the file:
```powershell
Get-ChildItem "server\.data\"
```

**Expected Output:**
```
cultivapp-dev.mv.db  (size should be > 100 KB)
```

### If the file exists:
✅ **SUCCESS!** The database was created and initialized with schema.sql and data.sql

## Step 3: Test the Login API Endpoint

### Option A: Using PowerShell (Invoke-RestMethod)

**Test with Producer user:**
```powershell
$body = @{
    email = "productor@cultivapp.com"
    password = "password"
} | ConvertTo-Json

$response = Invoke-RestMethod -Uri "http://localhost:8080/api/auth/login" -Method POST -Body $body -ContentType "application/json"

# Display the response
$response
```

**Expected Output:**
```
token                                                              role
-----                                                              ----
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwcm9kdWN0b3JAY3VsdGl2YXBwLmNvb... PRODUCTOR
```

**Test with Admin user:**
```powershell
$body = @{
    email = "admin@cultivapp.com"
    password = "password"
} | ConvertTo-Json

$response = Invoke-RestMethod -Uri "http://localhost:8080/api/auth/login" -Method POST -Body $body -ContentType "application/json"
$response
```

**Expected Output:**
```
token                                                              role
-----                                                              ----
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBjdWx0aXZhcHAuY29tIiwic... ADMIN
```

### Option B: Using curl (if installed)
```powershell
curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d "{\"email\":\"productor@cultivapp.com\",\"password\":\"password\"}"
```

### Option C: Using Postman or Insomnia
1. Create a new POST request
2. URL: `http://localhost:8080/api/auth/login`
3. Headers: `Content-Type: application/json`
4. Body (raw JSON):
```json
{
  "email": "productor@cultivapp.com",
  "password": "password"
}
```
5. Send the request
6. You should get a response with `token` and `role`

## Step 4: Test with the Frontend

### Start the frontend:
```powershell
cd client
npm run dev
```

### Open in browser:
Navigate to: `http://localhost:5173`

### Try to login:
- Email: `productor@cultivapp.com`
- Password: `password`

### What should happen:
✅ Login button submits the form  
✅ No errors appear  
✅ You should be redirected or see a success message  
✅ Token is stored in localStorage

## Step 5: Inspect the Database with a SQL Client

### Option A: DBeaver (Free SQL Client)
1. Download from: https://dbeaver.io/download/
2. Install and open DBeaver
3. Click "New Database Connection"
4. Select "H2" as the database type
5. Configure:
   - **JDBC URL:** `jdbc:h2:file:C:/Users/LENOVO/OneDrive/Escritorio/equipo-5-ings-202520/server/.data/cultivapp-dev`
   - **Username:** `sa`
   - **Password:** (leave empty)
6. Click "Test Connection" then "Finish"
7. Expand the connection and view tables

### Option B: IntelliJ IDEA (if you have it)
1. Open Database tool window (View → Tool Windows → Database)
2. Click "+" → Data Source → H2
3. Configure:
   - **URL:** `jdbc:h2:file:C:/Users/LENOVO/OneDrive/Escritorio/equipo-5-ings-202520/server/.data/cultivapp-dev`
   - **User:** `sa`
   - **Password:** (empty)
4. Test connection and apply

### Option C: VS Code with SQLite/H2 Extension
1. Install extension: "SQLite Viewer" or "Database Client"
2. Open the database file
3. Browse tables

## Step 6: Verify Database Contents

### Once connected to the database, run these queries:

**Check all tables exist:**
```sql
SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='PUBLIC';
```

**Expected Output (11 tables):**
```
PAISES
CIUDADES
USUARIOS
ESPECIES
CULTIVOS
TAREAS
ALERTAS
REGLAS
ETAPAS
DATOS_CLIMATICOS
CONFIGURACION_SISTEMA
```

**Check users:**
```sql
SELECT id, email, nombre, apellido, rol, activo FROM usuarios;
```

**Expected Output:**
```
id | email                      | nombre | apellido | rol        | activo
---+----------------------------+--------+----------+------------+-------
1  | admin@cultivapp.com        | Admin  | Sistema  | ADMIN      | true
2  | productor@cultivapp.com    | Juan   | Pérez    | PRODUCTOR  | true
```

**Check plant species:**
```sql
SELECT id, nombre, nombre_cientifico FROM especies;
```

**Expected Output (8 species):**
```
1  | Tomate    | Solanum lycopersicum
2  | Maíz      | Zea mays
3  | Papa      | Solanum tuberosum
4  | Café      | Coffea arabica
5  | Frijol    | Phaseolus vulgaris
6  | Zanahoria | Daucus carota
7  | Lechuga   | Lactuca sativa
8  | Cebolla   | Allium cepa
```

**Check sample crop:**
```sql
SELECT c.id, c.nombre, e.nombre as especie, u.nombre as usuario, c.estado, c.etapa_actual
FROM cultivos c
JOIN especies e ON c.especie_id = e.id
JOIN usuarios u ON c.usuario_id = u.id;
```

**Expected Output:**
```
1 | Tomates Lote A | Tomate | Juan | ACTIVO | CRECIMIENTO
```

**Count records in each table:**
```sql
SELECT 'paises' as tabla, COUNT(*) as registros FROM paises
UNION ALL SELECT 'ciudades', COUNT(*) FROM ciudades
UNION ALL SELECT 'usuarios', COUNT(*) FROM usuarios
UNION ALL SELECT 'especies', COUNT(*) FROM especies
UNION ALL SELECT 'cultivos', COUNT(*) FROM cultivos
UNION ALL SELECT 'tareas', COUNT(*) FROM tareas
UNION ALL SELECT 'alertas', COUNT(*) FROM alertas
UNION ALL SELECT 'reglas', COUNT(*) FROM reglas
UNION ALL SELECT 'etapas', COUNT(*) FROM etapas
UNION ALL SELECT 'datos_climaticos', COUNT(*) FROM datos_climaticos
UNION ALL SELECT 'configuracion_sistema', COUNT(*) FROM configuracion_sistema;
```

**Expected Output:**
```
tabla                    | registros
-------------------------+-----------
paises                   | 3
ciudades                 | 5
usuarios                 | 2
especies                 | 8
cultivos                 | 1
tareas                   | 2
alertas                  | 1
reglas                   | 3
etapas                   | 5
datos_climaticos         | 3
configuracion_sistema    | 4
```

## Step 7: Check Application Logs

### Look for these SUCCESS indicators in the console:

✅ **Database initialization:**
```
Executing SQL script from URL [file:...schema.sql]
Executing SQL script from URL [file:...data.sql]
```

✅ **Hibernate initialization:**
```
HHH000204: Processing PersistenceUnitInfo [name: default]
HHH000412: Hibernate ORM core version X.X.X
```

✅ **Entities loaded:**
```
Mapped "{[/api/auth/login],methods=[POST]}" onto ...AuthController.login
```

✅ **No constraint violations or SQL errors**

## Step 8: Common Success Indicators

### ✅ Backend is Working When:
1. Application starts without errors
2. Port 8080 is listening
3. Database file is created in `server/.data/`
4. Login API returns JWT token
5. All 11 tables exist in database
6. Sample data is loaded (2 users, 8 species, etc.)

### ✅ Database is Correct When:
1. All 11 tables exist
2. Foreign keys work (no constraint violations)
3. Enums are stored as strings (not numbers)
4. Indexes are created
5. Sample data matches expectations

### ✅ Integration is Working When:
1. Frontend can call backend API
2. Login successful (token received)
3. CORS allows requests from frontend
4. No 404 or 500 errors

## Troubleshooting

### ❌ Port 8080 already in use
**Solution:** Change port in `application.yml`:
```yaml
server:
  port: 8081
```

### ❌ Database file not created
**Solution:** Check you're running from correct directory:
```powershell
pwd  # Should show: ...equipo-5-ings-202520
```

### ❌ Login returns 401
**Possible causes:**
1. Wrong password (try: `password`)
2. User doesn't exist in database
3. Password hash doesn't match

### ❌ SQL errors on startup
**Solution:** Delete database and restart:
```powershell
Remove-Item server\.data\cultivapp-dev.mv.db
# Then restart the application
```

### ❌ Cannot connect to database with SQL client
**Make sure:**
1. Application is NOT running (H2 file can't be accessed by multiple processes)
2. Use absolute path to database file
3. Username is `sa`, password is empty

## Quick Verification Checklist

Use this checklist to verify everything:

- [ ] Backend starts without errors
- [ ] Database file exists: `server\.data\cultivapp-dev.mv.db`
- [ ] Can call `POST /api/auth/login` and get token
- [ ] All 11 tables exist in database
- [ ] 2 users exist (admin + productor)
- [ ] 8 plant species exist
- [ ] 1 sample crop exists
- [ ] Frontend can connect to backend
- [ ] Login works from frontend
- [ ] No CORS errors in browser console

## What's in Your Database Right Now

### Tables (11):
✅ paises (3 countries)  
✅ ciudades (5 cities)  
✅ usuarios (2 users: admin + productor)  
✅ especies (8 plant species)  
✅ cultivos (1 sample crop: Tomates Lote A)  
✅ tareas (2 sample tasks)  
✅ alertas (1 sample alert)  
✅ reglas (3 alert rules)  
✅ etapas (5 growth stages)  
✅ datos_climaticos (3 days of climate data)  
✅ configuracion_sistema (4 configuration entries)  

### Test Credentials:
- **Producer:** `productor@cultivapp.com` / `password`
- **Admin:** `admin@cultivapp.com` / `password`

---

## Summary

To verify everything works:

1. ✅ Start backend → Look for "Started CultivappApplication"
2. ✅ Check database file exists
3. ✅ Test login API → Should return token
4. ✅ Connect SQL client → View 11 tables with data
5. ✅ Start frontend → Test login

If all these work, your application is **100% operational**! 🎉
