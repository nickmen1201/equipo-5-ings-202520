# 🎯 QUICK START - Just Follow These 3 Steps!

## ⚡ FASTEST METHOD - One Command!

### Option 1: Use the automated script

1. Open PowerShell in the project root folder
2. Run:
   ```powershell
   .\start-app.ps1
   ```
3. Two new windows will open (backend + frontend)
4. Wait for both to start
5. Open browser: `http://localhost:5173`

---

## 🐌 MANUAL METHOD - Step by Step

### Terminal 1️⃣ - Backend

```powershell
cd server\src\cultivapp\cultivapp
.\mvnw.cmd spring-boot:run
```

**Wait for**: `Started CultivappApplication in X seconds`

### Terminal 2️⃣ - Frontend  

```powershell
cd client\src
npm run dev
```

**Wait for**: `Local: http://localhost:5173/`

### Browser 🌐

1. Go to: `http://localhost:5173`
2. Login:
   - Email: `admin@cultivapp.com`
   - Password: `Admin123!`
3. Navigate to: `http://localhost:5173/admin/especies`

---

## ✅ You'll Know It's Working When...

- Backend terminal says: ✅ "Started CultivappApplication"
- Frontend terminal says: ✅ "Local: http://localhost:5173/"
- Browser shows: ✅ Login page with green CultivApp logo
- After login: ✅ Green navbar appears
- At /admin/especies: ✅ "Administrar Especies" page appears

---

## ❌ If It's NOT Working...

### Check #1: Ports in use?

```powershell
# Check if ports are free
netstat -ano | findstr :8080
netstat -ano | findstr :5173

# If you see results, kill those processes:
taskkill /PID <number> /F
```

### Check #2: Backend running?

Visit: `http://localhost:8080/api/especies`
- Should see `[]` or list of species
- If error page → backend is not running!

### Check #3: Dependencies installed?

```powershell
cd client\src
npm install
```

---

## 🆘 Still Stuck?

Read the full guide: **STARTUP-GUIDE.md**

Or check:
1. Backend terminal for error messages
2. Browser console (F12) for errors
3. Make sure Java 17+ and Node.js 16+ are installed

---

## 📌 Bookmark These URLs

- **Frontend**: http://localhost:5173
- **Admin Login**: http://localhost:5173/login
- **Especies Page**: http://localhost:5173/admin/especies
- **Backend API**: http://localhost:8080/api/especies

---

**Credentials**:
- Admin: `admin@cultivapp.com` / `Admin123!`
- Producer: `productor@cultivapp.com` / `Productor123!`
