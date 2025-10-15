# Run this script from the project root to start the backend
# Usage: .\start-backend.ps1

Write-Host "🚀 Starting CultivApp Backend..." -ForegroundColor Green
Write-Host ""

# Get absolute path to backend directory
$backendPath = Join-Path $PSScriptRoot "server\src\cultivapp\cultivapp"

Write-Host "📂 Backend directory: $backendPath" -ForegroundColor Cyan
Write-Host ""
Write-Host "⏳ Starting Spring Boot application..." -ForegroundColor Yellow
Write-Host "   (This may take 30-60 seconds)" -ForegroundColor Yellow
Write-Host ""

# Navigate and start Spring Boot using absolute path
Set-Location $backendPath
& ".\mvnw.cmd" spring-boot:run
