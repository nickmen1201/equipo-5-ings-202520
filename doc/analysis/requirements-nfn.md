# Requisitos No Funcionales – CultivApp

> *P0* crítico/MVP, *P1* alto, *P2* medio

---

## RNF01 – Disponibilidad del servicio (*P0*)
*Descripción:* El front y el microservicio de clima deben estar disponibles.  
*Criterio:* Uptime mensual ≥ *99.0%* en MVP (medido con ping/healthcheck y logs).

---

## RNF02 – Desempeño de consulta de clima (*P0*)
*Descripción:* Respuesta rápida a peticiones de clima.  
*Criterio:* P95 de /clima *≤ 1200 ms* con caché; P95 de la UI *≤ 2000 ms* en red 4G.

---

## RNF03 – Seguridad básica (*P0*)
*Descripción:* Autenticación y protección de credenciales.  
*Criterio:* Passwords hash con algoritmo resistente (p. ej. bcrypt/argon2), TLS en tránsito, bloqueo tras *5 intentos* fallidos y expiración de sesión *≤ 12 h*.

---

## RNF04 – Usabilidad mínima (*P0*)
*Descripción:* UI clara para no expertos.  
*Criterio:* 3 tareas clave (registrar cultivo, ver alerta, registrar actividad) completables en *≤ 3 clics* cada una en pruebas con ≥ 5 usuarios.

---

## RNF05 – Accesibilidad (*P1*)
*Descripción:* Cumplir criterios básicos de accesibilidad.  
*Criterio:* Contraste AA, focus visible, navegación por teclado y etiquetas ARIA en formularios.

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
