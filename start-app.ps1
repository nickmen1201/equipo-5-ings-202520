# Quick start script - Opens both backend and frontend in separate terminals
# Usage: .\start-app.ps1

Write-Host "🚀 Starting CultivApp - Full Stack Application" -ForegroundColor Green
Write-Host ""

# Get the current directory (project root)
$projectRoot = Get-Location

Write-Host "✅ Project root: $projectRoot" -ForegroundColor Cyan
Write-Host ""

Write-Host "📋 Starting Backend in new window..." -ForegroundColor Yellow
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$projectRoot'; .\start-backend.ps1"

Write-Host "⏳ Waiting 5 seconds for backend to initialize..." -ForegroundColor Yellow
Start-Sleep -Seconds 5

Write-Host "📋 Starting Frontend in new window..." -ForegroundColor Yellow
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$projectRoot'; .\start-frontend.ps1"

Write-Host ""
Write-Host "✅ Both terminals opened!" -ForegroundColor Green
Write-Host ""
Write-Host "📝 Next steps:" -ForegroundColor Cyan
Write-Host "   1. Wait for backend to show 'Started CultivappApplication'"
Write-Host "   2. Wait for frontend to show 'Local: http://localhost:5173/'"
Write-Host "   3. Open browser: http://localhost:5173"
Write-Host "   4. Login with: admin@cultivapp.com / Admin123!"
Write-Host "   5. Navigate to: http://localhost:5173/admin/especies"
Write-Host ""
Write-Host "Press any key to exit this window..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
