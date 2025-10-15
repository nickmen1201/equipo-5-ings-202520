# CultivApp Startup Script for Windows PowerShell
Write-Host "====================================" -ForegroundColor Cyan
Write-Host "     CultivApp Startup Script      " -ForegroundColor Cyan
Write-Host "====================================" -ForegroundColor Cyan
Write-Host ""

# Function to check if port is in use
function Test-Port {
    param([int]$Port)
    $connection = Get-NetTCPConnection -LocalPort $Port -ErrorAction SilentlyContinue
    return $null -ne $connection
}

# Check if port 8080 is already in use
if (Test-Port -Port 8080) {
    Write-Host "⚠️  Port 8080 is already in use!" -ForegroundColor Yellow
    Write-Host "Backend may already be running or another service is using the port." -ForegroundColor Yellow
    $response = Read-Host "Do you want to kill the process and restart? (Y/N)"
    if ($response -eq 'Y' -or $response -eq 'y') {
        $process = Get-NetTCPConnection -LocalPort 8080 | Select-Object -ExpandProperty OwningProcess -Unique
        Stop-Process -Id $process -Force
        Write-Host "✓ Process killed" -ForegroundColor Green
        Start-Sleep -Seconds 2
    }
}

# Start Backend
Write-Host "🚀 Starting Backend Server..." -ForegroundColor Green
$backendPath = Join-Path $PSScriptRoot "server\src\cultivapp\cultivapp"
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$backendPath'; Write-Host 'Starting Spring Boot Backend...' -ForegroundColor Cyan; .\mvnw.cmd spring-boot:run"

Write-Host "⏳ Waiting 20 seconds for backend to initialize..." -ForegroundColor Yellow
Start-Sleep -Seconds 20

# Start Frontend
Write-Host "🚀 Starting Frontend Server..." -ForegroundColor Green
$frontendPath = Join-Path $PSScriptRoot "client\src"
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$frontendPath'; Write-Host 'Starting React Frontend...' -ForegroundColor Cyan; npm run dev"

Write-Host ""
Write-Host "====================================" -ForegroundColor Cyan
Write-Host "     Servers are starting!         " -ForegroundColor Cyan
Write-Host "====================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "📡 Backend:  http://localhost:8080" -ForegroundColor Green
Write-Host "🌐 Frontend: http://localhost:5173" -ForegroundColor Green
Write-Host ""
Write-Host "Check the new windows for server logs." -ForegroundColor Gray
Write-Host "Press Ctrl+C in each window to stop the servers." -ForegroundColor Gray
Write-Host ""
