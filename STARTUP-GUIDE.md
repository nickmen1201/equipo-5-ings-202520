# CultivApp - Complete Startup Guide

## 📋 Prerequisites

Before starting the application, ensure you have:

1. **Java 21** or higher installed
   - Check: `java -version`
   - Download: https://adoptium.net/

2. **Node.js 18+** and npm installed
   - Check: `node -v` and `npm -v`
   - Download: https://nodejs.org/

3. **Maven** (included via Maven Wrapper, no separate installation needed)

## 🚀 Starting the Application

### Method 1: Automated Startup (Recommended)

#### Using Batch Script (.bat)
```cmd
start-all.bat
```

#### Using PowerShell Script (.ps1)
```powershell
.\start-all.ps1
```

**What it does:**
- Checks if ports are available
- Starts backend in a new window
- Waits for backend to initialize
- Starts frontend in a new window
- Shows you the URLs to access

### Method 2: Manual Startup

#### Step 1: Start the Backend

Open a terminal and run:

```powershell
cd server\src\cultivapp\cultivapp
.\mvnw.cmd spring-boot:run
```

**Wait for this message:**
```
Started CultivappApplication in X.XXX seconds
```

The backend will be available at: **http://localhost:8080**

#### Step 2: Start the Frontend

Open a **new terminal** and run:

```powershell
cd client\src
npm install  # Only needed the first time
npm run dev
```

**Look for this message:**
```
VITE ready in XXX ms
Local: http://localhost:5173/
```

The frontend will be available at: **http://localhost:5173** (or 5174 if 5173 is busy)

## 🌐 Accessing the Application

Once both servers are running:

1. **Open your browser** and go to: http://localhost:5173 (or the port shown in the frontend terminal)

2. **Login** with sample credentials:
   - Email: `productor@cultivapp.com`
   - Password: `password`

3. **Backend API** is accessible at: http://localhost:8080

4. **H2 Database Console** (for debugging): http://localhost:8080/h2-console
   - JDBC URL: `jdbc:h2:file:./server/.data/cultivapp-dev`
   - Username: `sa`
   - Password: (leave empty)

## 🛑 Stopping the Application

To stop the servers:

1. Go to each terminal window
2. Press **Ctrl + C**
3. Confirm with **Y** if prompted

## ⚠️ Troubleshooting

### Port 8080 Already in Use

**Error:** `Port 8080 was already in use`

**Solution:**
```powershell
# Find process using port 8080
Get-NetTCPConnection -LocalPort 8080 | Select-Object OwningProcess

# Kill the process (replace XXXX with the process ID)
Stop-Process -Id XXXX -Force
```

### Port 5173 Already in Use

**Error:** `Port 5173 is in use, trying another one...`

**Solution:** Vite will automatically use port 5174 or another available port. Check the terminal output for the actual URL.

### Backend Won't Start

**Issue:** Backend compiles but immediately shuts down

**Solution:**
1. Make sure no other instance is running
2. Check if port 8080 is free
3. Use the `start-all.bat` script which handles this automatically

### Frontend Dependencies Issues

**Error:** `npm install` fails or shows warnings

**Solution:**
```powershell
cd client\src
rm -r node_modules
rm package-lock.json
npm install
```

### Node Version Warning

**Warning:** `You are using Node.js 20.18.0. Vite requires Node.js version 20.19+`

**Impact:** This is just a warning. The application will work, but consider upgrading Node.js for optimal performance.

## 📁 Project Structure

```
cultivapp/
├── start-all.bat         # Windows batch startup script
├── start-all.ps1         # PowerShell startup script
├── start-backend.bat     # Backend only
├── start-frontend.bat    # Frontend only
├── client/               # React frontend
│   └── src/
│       ├── package.json
│       └── src/
│           ├── App.jsx
│           └── ...
└── server/               # Spring Boot backend
    └── src/cultivapp/cultivapp/
        ├── pom.xml
        ├── mvnw.cmd
        └── src/
            └── main/
                ├── java/
                └── resources/
```

## 🔄 First Time Setup

When running the application for the first time:

1. **Backend** will:
   - Download Maven dependencies (takes a few minutes)
   - Create H2 database file in `server/.data/`
   - Run schema.sql and data.sql to populate sample data
   - Start on port 8080

2. **Frontend** will:
   - Install npm packages (if not already installed)
   - Build development server
   - Start on port 5173

**Total first-time setup:** ~3-5 minutes

**Subsequent starts:** ~30 seconds

## 📝 Development Workflow

### Making Changes

**Frontend:**
- Changes auto-reload (Hot Module Replacement)
- No need to restart the server

**Backend:**
- Spring DevTools will auto-restart on file changes
- Or manually restart with Ctrl+C and re-run

### Database

- **Development:** H2 file-based database (persistent)
- **Location:** `server/.data/cultivapp-dev.mv.db`
- **Reset:** Delete the `.data` folder to start fresh

## ✅ Verification Checklist

- [ ] Java 21+ installed (`java -version`)
- [ ] Node.js 18+ installed (`node -v`)
- [ ] Port 8080 is free
- [ ] Port 5173 is free
- [ ] Ran `start-all.bat` or `start-all.ps1`
- [ ] Backend shows "Started CultivappApplication"
- [ ] Frontend shows "VITE ready"
- [ ] Can access http://localhost:5173
- [ ] Can login with `productor@cultivapp.com` / `password`

## 🆘 Need Help?

If you encounter issues:

1. Check this guide's Troubleshooting section
2. Verify all prerequisites are installed
3. Make sure ports 8080 and 5173 are available
4. Try the manual startup method to see detailed error messages
5. Check the terminal output for specific error messages

## 🎯 Quick Reference

**Start everything:**
```cmd
start-all.bat
```

**Backend URL:** http://localhost:8080  
**Frontend URL:** http://localhost:5173  
**Sample Login:** `productor@cultivapp.com` / `password`  
**Stop:** Ctrl+C in each terminal window
