# ✅ INSTRUCCIONES QUE FUNCIONAN AL 100%

## 🎯 MÉTODO 1: Comando Directo (PROBADO Y FUNCIONA)

### Backend:
```powershell
cd c:\Users\LENOVO\OneDrive\Escritorio\equipo-5-ings-202520\server\src\cultivapp\cultivapp; & ".\mvnw.cmd" spring-boot:run
```

**Espera ver**: `Started CultivappApplication in X seconds`

### Frontend (en otra terminal):
```powershell
cd c:\Users\LENOVO\OneDrive\Escritorio\equipo-5-ings-202520\client\src; npm run dev
```

**Espera ver**: `Local: http://localhost:5173/`

---

## 🎯 MÉTODO 2: Usar los Scripts (Actualizados)

### Desde el directorio raíz del proyecto:

```powershell
# Terminal 1 - Backend
.\start-backend.ps1

# Terminal 2 - Frontend
.\start-frontend.ps1
```

---

## 🌐 Acceder a la Aplicación

1. **Abrir navegador**: `http://localhost:5173`
2. **Login como Admin**:
   - Email: `admin@cultivapp.com`
   - Password: `Admin123!`
3. **Ir a Especies**: `http://localhost:5173/admin/especies`

---

## ✅ Backend CONFIRMADO Funcionando

```
Started CultivappApplication in 15.326 seconds
Tomcat started on port 8080 (http)
H2 console available at '/h2-console'
Database available at 'jdbc:h2:file:./server/.data/cultivapp-dev'
```

---

## 📝 NOTAS IMPORTANTES

1. **El problema era**: Los scripts originales no mantenían el contexto del directorio
2. **La solución**: Usar el comando completo con `;` para ejecutar en el directorio correcto
3. **Maven Wrapper funciona**: No necesitas instalar Maven por separado
4. **Java 21 detectado**: Todo está configurado correctamente

---

## 🚨 Si el Backend NO inicia

Verifica que NO haya otro proceso usando el puerto 8080:
```powershell
netstat -ano | findstr :8080
# Si hay algo, mata el proceso:
taskkill /PID <número> /F
```

---

**AHORA YA SABES CÓMO INICIAR LA APP** 🎉
