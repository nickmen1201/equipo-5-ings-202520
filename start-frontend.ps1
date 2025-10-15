# Run this script from the project root to start the frontend
# Usage: .\start-frontend.ps1

Write-Host "🚀 Starting CultivApp Frontend..." -ForegroundColor Green
Write-Host ""

# Get absolute path to frontend directory
$frontendPath = Join-Path $PSScriptRoot "client\src"

Write-Host "📂 Frontend directory: $frontendPath" -ForegroundColor Cyan
Write-Host ""

# Navigate to frontend directory
Set-Location $frontendPath

# Check if node_modules exists
if (-Not (Test-Path "node_modules")) {
    Write-Host "📦 Installing dependencies (first time only)..." -ForegroundColor Yellow
    npm install
    Write-Host ""
}

Write-Host "⏳ Starting Vite dev server..." -ForegroundColor Yellow
Write-Host ""

# Start Vite
npm run dev
