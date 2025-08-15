# Requisitos No Funcionales

## REQ-001
**AG:** AG-NFN  
**Título:** Disponibilidad del servicio  
**Descripción:** La aplicación debe mantener un uptime mensual ≥ 99% para garantizar el acceso continuo a datos y recomendaciones.  

**Criterios de aceptación:**  
- Monitorear la disponibilidad con herramientas automáticas (ej. UptimeRobot).  
- Registrar y reportar el tiempo total de indisponibilidad mensual.  

**Prioridad:** P0  

---

## REQ-002
**AG:** AG-NFN  
**Título:** Compatibilidad multiplataforma  
**Descripción:** La aplicación debe ser funcional en navegadores modernos y dispositivos Android (≥8) e iOS (≥14), con diseño responsive.  

**Criterios de aceptación:**  
- Probar funcionalidad en Chrome, Firefox, Edge y Safari.  
- Validar usabilidad en al menos 3 resoluciones de pantalla distintas.  

**Prioridad:** P1  

---

## REQ-003
**AG:** AG-NFN  
**Título:** Rendimiento en consulta climática  
**Descripción:** El tiempo de respuesta de las consultas a datos climáticos debe ser ≤ 3 s en condiciones de conectividad rural promedio (latencia 100-500 ms).  

**Criterios de aceptación:**  
- Simular consultas con latencia de 300 ms y verificar respuesta ≤ 3 s.  
- Probar con 20 solicitudes consecutivas y cumplir el tiempo objetivo.  

**Prioridad:** P1  

---

## REQ-004
**AG:** AG-NFN  
**Título:** Escalabilidad  
**Descripción:** El sistema debe soportar un aumento del 200% en usuarios activos sin degradar el rendimiento más de un 10% en tiempos de respuesta.  

**Criterios de aceptación:**  
- Realizar prueba de carga con el doble de usuarios concurrentes que el promedio.  
- Verificar que el tiempo de respuesta no aumente más de un 10%.  

**Prioridad:** P1  

---

## REQ-005
**AG:** AG-NFN  
**Título:** Seguridad de datos  
**Descripción:** Toda comunicación debe realizarse mediante HTTPS y la información sensible debe almacenarse cifrada.  

**Criterios de aceptación:**  
- Ejecutar pruebas con OWASP ZAP para validar cifrado en tránsito.  
- Verificar que los datos almacenados estén cifrados en reposo.  

**Prioridad:** P0 

---

## RNF06 – Portabilidad/Despliegue (*P1*)
*Descripción:* Arranque reproducible.  
*Criterio:* Contenedores con Dockerfile/Compose y variables .env; ambiente listo con *1 comando*.

---

## RNF07 – Mantenibilidad (*P1*)
*Descripción:* Código y documentación comprensibles.  
*Criterio:* Linter activo, estructura de módulos, README de servicios y comentarios de 1–2 líneas por regla clave.

---

## RNF08 – Observabilidad (*P2*)
*Descripción:* Logs y métricas mínimas.  
*Criterio:* Logs con nivel, timestamp y request-id; métrica de latencia y tasa de error para /clima.

---

## RNF09 – Calidad de datos de clima (*P2*)
*Descripción:* Validación y normalización del scraping.  
*Criterio:* Esquema JSON validado; si la fuente falla, degradar con *cache ≤ 2 h* y flag source="cache".

---

## RNF10 – Cumplimiento legal y datos personales (*P2*)
*Descripción:* Manejo responsable de datos.  
*Criterio:* Política de privacidad visible; solo datos mínimos (correo, nombre); eliminación de cuenta bajo solicitud.
