@echo off
echo ========================================
echo  RESTARTING BACKEND WITH SPECIES DATA
echo ========================================
echo.
echo This will:
echo  1. Stop the current backend
echo  2. Restart with 8 base species
echo.
echo Press Ctrl+C to cancel, or
pause

cd /d "c:\Users\LENOVO\OneDrive\Escritorio\equipo-5-ings-202520\server\src\cultivapp\cultivapp"

echo.
echo Starting backend...
echo Wait for "Started CultivappApplication" message
echo.

call mvnw.cmd spring-boot:run

pause
