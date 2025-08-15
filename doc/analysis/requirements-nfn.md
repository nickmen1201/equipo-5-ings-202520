# Requisitos No Funcionales

## REQ-021
**AG:** AG-NFN  
**Título:** Disponibilidad del servicio  
**Descripción:** La aplicación debe mantener un uptime mensual ≥ 99% para garantizar el acceso continuo a datos y recomendaciones.  

**Criterios de aceptación:**  
- Monitorear la disponibilidad con herramientas automáticas (ej. UptimeRobot).  
- Registrar y reportar el tiempo total de indisponibilidad mensual.  

**Prioridad:** P0  

---

## REQ-022
**AG:** AG-NFN  
**Título:** Compatibilidad multiplataforma  
**Descripción:** La aplicación debe ser funcional en navegadores modernos y dispositivos Android (≥8) e iOS (≥14), con diseño responsive.  

**Criterios de aceptación:**  
- Probar funcionalidad en Chrome, Firefox, Edge y Safari.  
- Validar usabilidad en al menos 3 resoluciones de pantalla distintas.  

**Prioridad:** P1  

---

## REQ-023
**AG:** AG-NFN  
**Título:** Rendimiento en consulta climática  
**Descripción:** El tiempo de respuesta de las consultas a datos climáticos debe ser ≤ 3 s en condiciones de conectividad rural promedio (latencia 100-500 ms).  

**Criterios de aceptación:**  
- Simular consultas con latencia de 300 ms y verificar respuesta ≤ 3 s.  
- Probar con 20 solicitudes consecutivas y cumplir el tiempo objetivo.  

**Prioridad:** P1  

---

## REQ-024
**AG:** AG-NFN  
**Título:** Escalabilidad  
**Descripción:** El sistema debe soportar un aumento del 200% en usuarios activos sin degradar el rendimiento más de un 10% en tiempos de respuesta.  

**Criterios de aceptación:**  
- Realizar prueba de carga con el doble de usuarios concurrentes que el promedio.  
- Verificar que el tiempo de respuesta no aumente más de un 10%.  

**Prioridad:** P1  

--- 
## REQ-025 
**AG:** AG-NFN 

**Título:** Seguridad de datos 

**Descripción:** Toda comunicación debe realizarse mediante HTTPS y la información sensible debe almacenarse cifrada. 

**Criterios de aceptación:** 
- Ejecutar pruebas con OWASP ZAP para validar cifrado en tránsito.
- Verificar que los datos almacenados estén cifrados en reposo. 

**Prioridad:** P0 

---

## REQ-026  
**AG:** AG-NFN  
**Título:** Consumo reducido de recursos  
**Descripción:** La aplicación móvil no debe consumir más de 5 MB de datos al día en uso estándar y debe minimizar el uso de batería.  

**Criterios de aceptación:**  
- Medir consumo de datos durante 1 hora de uso activo.  
- Medir consumo de batería con Android Profiler y iOS Instruments.  

**Prioridad:** P2  

---

## REQ-027  
**AG:** AG-NFN  
**Título:** Facilidad de uso  
**Descripción:** La interfaz debe permitir que un usuario sin experiencia previa complete las funciones básicas en ≤ 2 minutos sin ayuda externa.  

**Criterios de aceptación:**  
- Prueba de usabilidad con al menos 5 usuarios rurales reales.  
- Verificar que todos logren registrar un cultivo y consultar clima sin asistencia.  

**Prioridad:** P1  

---

## REQ-028  
**AG:** AG-NFN  
**Título:** Mantenibilidad con mínima interrupción  
**Descripción:** Las actualizaciones del sistema no deben interrumpir el servicio más de 5 minutos.  

**Criterios de aceptación:**  
- Documentar tiempos de indisponibilidad en cada despliegue.  
- Validar despliegues con tiempo fuera ≤ 5 minutos.  

**Prioridad:** P2  

---

## REQ-029  
**AG:** AG-NFN  
**Título:** Confiabilidad de datos climáticos  
**Descripción:** La información climática debe actualizarse al menos cada 15 minutos para asegurar precisión en las recomendaciones.  

**Criterios de aceptación:**  
- Verificar que las actualizaciones automáticas se ejecuten cada ≤ 15 min.  
- Revisar que no se muestren datos con antigüedad mayor a 15 min.  

**Prioridad:** P1  

---

## REQ-030  
**AG:** AG-NFN  
**Título:** Portabilidad de datos  
**Descripción:** Los usuarios deben poder exportar sus registros en formato CSV o PDF para compartir con terceros.  

**Criterios de aceptación:**  
- Generar exportación y validar apertura en Excel y lectores de PDF.  
- Verificar que la exportación incluya fecha y datos completos.  

**Prioridad:** P2  

---
