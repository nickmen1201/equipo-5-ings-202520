@echo off
echo ====================================
echo Starting CultivApp
echo ====================================
echo.
echo Starting Backend in new window...
start "CultivApp Backend" cmd /k "cd server\src\cultivapp\cultivapp && mvnw.cmd spring-boot:run"
echo.
echo Waiting 15 seconds for backend to start...
timeout /t 15 /nobreak > nul
echo.
echo Starting Frontend in new window...
start "CultivApp Frontend" cmd /k "cd client\src && npm run dev"
echo.
echo ====================================
echo Both servers are starting!
echo Backend: http://localhost:8080
echo Frontend: http://localhost:5173
echo ====================================
