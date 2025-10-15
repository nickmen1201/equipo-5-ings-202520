# 🚀 CultivApp - Complete Startup Guide

## Step-by-Step Instructions (Always Works!)

Follow these steps **in order** every time you want to run the application:

---

## 📋 Prerequisites (One-Time Setup)

Before starting, make sure you have installed:

1. **Java 17 or higher** - Check version:
   ```powershell
   java -version
   ```

2. **Node.js 16 or higher** - Check version:
   ```powershell
   node -v
   npm -v
   ```

3. **Maven** (included in project) - No separate install needed

---

## 🔴 STEP 1: Start the Backend (Spring Boot)

### Open Terminal #1 - Backend

1. **Open VS Code**
2. **Open Terminal**: `Terminal` → `New Terminal` (or press `` Ctrl+` ``)
3. **Navigate to backend folder**:
   ```powershell
   cd server\src\cultivapp\cultivapp
   ```

4. **Start Spring Boot**:
   
   **On Windows (PowerShell/CMD):**
   ```powershell
   .\mvnw.cmd spring-boot:run
   ```
   
   **On Windows (Git Bash):**
   ```bash
   ./mvnw spring-boot:run
   ```

5. **Wait for startup message**:
   - Look for: `Started CultivappApplication in X.XXX seconds`
   - Backend will be available at: `http://localhost:8080`
   - **DO NOT CLOSE THIS TERMINAL!**

### ✅ Verify Backend is Running

Open your browser and go to:
```
http://localhost:8080/api/especies
```

- If you see `[]` (empty array) or a list of species → **Backend is working! ✅**
- If you see an error page → **Backend is NOT running ❌** (go back to Step 1)

---

## 🔵 STEP 2: Start the Frontend (React + Vite)

### Open Terminal #2 - Frontend

1. **In VS Code, open a NEW terminal**: `Terminal` → `New Terminal`
2. **Navigate to frontend folder**:
   ```powershell
   cd client\src
   ```

3. **Install dependencies** (only first time or after pulling new code):
   ```powershell
   npm install
   ```

4. **Start Vite dev server**:
   ```powershell
   npm run dev
   ```

5. **Wait for startup message**:
   - Look for: `Local: http://localhost:5173/`
   - Frontend will be available at: `http://localhost:5173`
   - **DO NOT CLOSE THIS TERMINAL!**

---

## 🌐 STEP 3: Access the Application

### 3.1 Open Browser

1. Open your browser (Chrome, Edge, Firefox)
2. Go to: **http://localhost:5173**

### 3.2 Login

You should see the **Login page**. Use these credentials:

**Admin User** (for accessing `/admin/especies`):
```
Email: admin@cultivapp.com
Password: Admin123!
```

**OR**

**Producer User** (for regular dashboard):
```
Email: productor@cultivapp.com
Password: Productor123!
```

### 3.3 Navigate to Especies Page

After login as **Admin**:
1. You'll be redirected to `/admin` (Admin Dashboard)
2. **Manually navigate** to: **http://localhost:5173/admin/especies**
3. You should see the "Administrar Especies" page!

---

## ❌ Common Problems & Solutions

### Problem 1: "Port 8080 is already in use"

**Solution**: Another process is using port 8080.

**Fix**:
```powershell
# Find process using port 8080
netstat -ano | findstr :8080

# Kill the process (replace PID with the number from previous command)
taskkill /PID <PID> /F

# Then restart backend (Step 1)
```

### Problem 2: "Port 5173 is already in use"

**Solution**: Another Vite server is running.

**Fix**:
```powershell
# Find process using port 5173
netstat -ano | findstr :5173

# Kill the process
taskkill /PID <PID> /F

# Then restart frontend (Step 2)
```

### Problem 3: Backend starts but crashes immediately

**Solution**: Database connection issue.

**Check**:
1. Look at the backend terminal for error messages
2. Check `src/main/resources/application.yml` for database config
3. Make sure H2 database is configured (default in project)

### Problem 4: Frontend shows blank page or "Network Error"

**Solution**: Backend is not running or CORS issue.

**Fix**:
1. Verify backend is running: `http://localhost:8080/api/especies`
2. Check browser console for errors (F12 → Console tab)
3. Make sure `@CrossOrigin(origins = "*")` is in `EspecieController.java`

### Problem 5: "Cannot find module" errors in frontend

**Solution**: Missing dependencies.

**Fix**:
```powershell
cd client\src
npm install
npm run dev
```

### Problem 6: Login fails with 401 error

**Solution**: User credentials don't exist in database.

**Check**:
1. Make sure `data.sql` has been executed by Spring Boot
2. Check backend logs for "Executing SQL script" message
3. Verify users exist in database

### Problem 7: Page shows but no data loads

**Solution**: Backend API not responding or wrong URL.

**Check**:
1. Open browser console (F12)
2. Go to "Network" tab
3. Look for failed requests to `http://localhost:8080/api/especies`
4. Check backend terminal for errors

---

## 🔧 Quick Restart Procedure

If something goes wrong, **restart everything**:

### 1. Stop Everything
- In backend terminal: Press `Ctrl+C`
- In frontend terminal: Press `Ctrl+C`
- Wait for both to fully stop

### 2. Restart Backend
```powershell
cd server\src\cultivapp\cultivapp
.\mvnw.cmd spring-boot:run
```

### 3. Restart Frontend
```powershell
cd client\src
npm run dev
```

### 4. Access Application
- Browser: `http://localhost:5173`
- Login as admin
- Go to: `http://localhost:5173/admin/especies`

---

## 📝 Checklist - Use This Every Time!

Before asking "Why isn't it working?", check:

- [ ] Backend terminal is open and shows "Started CultivappApplication"
- [ ] Frontend terminal is open and shows "Local: http://localhost:5173"
- [ ] `http://localhost:8080/api/especies` returns JSON (even if empty)
- [ ] `http://localhost:5173` shows login page
- [ ] You logged in with correct credentials (admin@cultivapp.com / Admin123!)
- [ ] You manually navigated to `http://localhost:5173/admin/especies`
- [ ] Browser console (F12) shows no red errors
- [ ] No other process is using ports 8080 or 5173

---

## 🎯 Success Indicators

**You'll know everything is working when**:

1. ✅ Backend terminal shows: `Started CultivappApplication in X.XXX seconds`
2. ✅ Frontend terminal shows: `Local: http://localhost:5173/`
3. ✅ Browser at `localhost:5173` shows login page
4. ✅ After login, you see green navbar with CultivApp logo
5. ✅ At `/admin/especies`, you see "Administrar Especies" header
6. ✅ "Nueva Especie" button is visible (green button)
7. ✅ Either species cards appear OR empty state message shows

---

## 🆘 Still Not Working?

If you followed all steps and it's still not working:

### 1. Check Terminal Outputs

**Backend Terminal** - Should show:
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.x.x)

...
Started CultivappApplication in 5.123 seconds (JVM running for 5.789)
```

**Frontend Terminal** - Should show:
```
VITE v5.x.x  ready in 523 ms

  ➜  Local:   http://localhost:5173/
  ➜  Network: use --host to expose
  ➜  press h + enter to show help
```

### 2. Check Browser Console

1. Open page: `http://localhost:5173/admin/especies`
2. Press `F12` to open DevTools
3. Go to "Console" tab
4. Look for red error messages
5. Take a screenshot and share with team

### 3. Check Network Tab

1. In DevTools, go to "Network" tab
2. Refresh page (`Ctrl+R`)
3. Look for failed requests (shown in red)
4. Click on failed request to see details

---

## 💡 Pro Tips

### Tip 1: Use VS Code Split Terminal
- Click the "Split Terminal" button in VS Code
- Run backend in left terminal, frontend in right terminal
- See both logs at the same time!

### Tip 2: Create PowerShell Scripts

Create `start-backend.ps1`:
```powershell
cd server\src\cultivapp\cultivapp
.\mvnw.cmd spring-boot:run
```

Create `start-frontend.ps1`:
```powershell
cd client\src
npm run dev
```

Then just run these scripts!

### Tip 3: Bookmark the URLs
- Backend API: `http://localhost:8080/api/especies`
- Frontend: `http://localhost:5173`
- Admin Especies: `http://localhost:5173/admin/especies`

### Tip 4: Use Browser Auto-Refresh
- Vite has Hot Module Replacement (HMR)
- Code changes auto-refresh the page
- No need to manually refresh!

---

## 📞 Getting Help

If none of this works:

1. **Take screenshots** of:
   - Backend terminal output
   - Frontend terminal output
   - Browser error (if any)
   - Browser console (F12 → Console tab)

2. **Note down** what step failed

3. **Share** with your team:
   - "I got to Step X"
   - "I see this error: [error message]"
   - Include screenshots

---

**Last Updated**: October 14, 2025
**For**: REQ-005 Implementation
**Branch**: feature/REQ-005-catalogo-minimo-especies
