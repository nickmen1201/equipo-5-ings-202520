# Requisitos Funcionales

## REQ-001

**Título:** Inicio de sesión básico en /login

**Descripción:** El sistema debe permitir que un usuario existente (con rol Admin o Productor) se autentique mediante correo electrónico y contraseña en la ruta /login.
Este requisito asegura que solo usuarios autorizados puedan acceder a la plataforma, previniendo accesos no autorizados y protegiendo la información sensible. La autenticación debe validar las credenciales ingresadas contra los registros almacenados de forma segura (por ejemplo, usando contraseñas hasheadas) y, en caso de ser correctas, generar una sesión activa o token de autenticación que permita al usuario navegar por las funcionalidades de la aplicación según sus permisos.

**Criterios de aceptación:**
```
Scenario: Login válido
Given estoy en /login
And ingreso "user@demo.com" y "Password123"
When presiono "Ingresar"
Then veo el tablero en /cultivos y mi nombre en la barra superior

Scenario: Credenciales inválidas
Given estoy en /login
When ingreso correo válido y contraseña incorrecta
Then veo "Credenciales inválidas" sin revelar cuál campo falló

Scenario: Cuenta deshabilitada
Given mi cuenta está deshabilitada por Admin
When intento iniciar sesión
Then veo "Cuenta deshabilitada, contacte al administrador" y permanezco en /login
```
**Prioridad:** P0

**Esfuerzo:** 3

---

## REQ-002

**Título:** Registro de Productor en /register

**Descripción:** El sistema debe permitir la creación de nuevos usuarios con rol Productor a través de la ruta /register.
Durante el registro, el formulario debe solicitar obligatoriamente nombre, correo electrónico único y contraseña.

**Criterios de aceptación:**
```
Scenario: Registro exitoso
Given estoy en /register
And ingreso nombre, correo único y contraseña válida
When confirmo
Then se crea el usuario Productor y se redirige a /cultivos

Scenario: Correo duplicado
Given existe un usuario con correo X
When intento registrarme con correo X
Then veo "El correo ya está registrado" y no se crea el usuario

Scenario: Contraseña débil
Given estoy en /register
When ingreso contraseña de 5 caracteres
Then veo "La contraseña debe tener al menos 8 caracteres"
```
**Prioridad:** P0

**Esfuerzo:** 2

---

## REQ-003

**Título:** Control de acceso por rol 

**Descripción:** Restringir las vistas de administración (/admin/especies, /admin/tareas) solo a Admin. el Productor no tiene acceso a /admin.

**Criterios de aceptación:**
```
Scenario: Admin accede a /admin/especies
Given soy Admin autenticado
When navego a /admin/especies
Then veo el CRUD de especies

Scenario: Productor intenta /admin/tareas
Given soy Productor autenticado
When navego a /admin/tareas
Then recibo 403 y un mensaje "No autorizado"
```
**Prioridad:** P1

**Esfuerzo:** 3

---
## REQ-004

**Título:** CRUD de Cultivos en /cultivos y /cultivos/:id

**Descripción:** Crear, editar (nombre, especie, fecha siembra, superficie,etc), archivar/activar cultivos. Valor: base de datos operativa para reglas.

**Criterios de aceptación:**
```
Scenario: Crear cultivo
Given estoy en /cultivos y presiono "Nuevo cultivo"
When completo nombre, especie del catálogo y fecha de siembra
Then se crea y aparece en la lista

Scenario: Editar cultivo archivado
Given un cultivo está archivado
When intento editarlo
Then veo "No editable mientras esté archivado"

Scenario: Validaciones
Given estoy creando cultivo
When omito nombre o especie
Then veo mensajes de campo requerido y no se guarda
```
**Prioridad:** P0

**Esfuerzo:** 2

---
## REQ-005

**Título:** Catálogo mínimo de especies en /admin/especies

**Descripción:** Admin gestiona especies (crear/editar/eliminar) con parámetros base (p. ej., días recomendados para fertilización por defecto).

**Criterios de aceptación:**
```
Scenario: Crear especie
Given soy Admin en /admin/especies
When creo "Maíz" con fertilización a 20 días
Then aparece en el selector de /cultivos/nuevo

Scenario: Eliminar especie en uso
Given "Maíz" está asociada a cultivos activos
When intento eliminarla
Then veo "No se puede eliminar: tiene cultivos asociados"
```
**Prioridad:** P0

**Esfuerzo:** 1

---

## REQ-007

**Título:** Scraping SIATA programado + reintento + /clima/actualizar

**Descripción:** Ejecutar scraping a las 6 am y 6pm con 1 reintento después de 15 min si falla. Botón "Actualizar ahora" en /clima/actualizar (solo Admininstrador) y en /cultivos/:id (Productor, solo su zona). nos ayuda con los datos oportunos  aun sin tener API.

**Criterios de aceptación:**
```
Scenario: Ejecución programada exitosa
Given son 06:00
When corre el scraping
Then se actualiza lluvia (mm/24 h) y hora de última actualización

Scenario: Falla y reintento
Given el primer intento falla por timeout
When pasan 15 minutos
Then se ejecuta un reintento automático y registra resultado en auditoría

Scenario: Botón "Actualizar ahora"
Given soy Admin en /clima/actualizar
When hago clic en "Actualizar ahora"
Then veo el estado "En curso" y resultado final
```
**Prioridad:** P0

**Esfuerzo:** 5

---

## REQ-009

**Título:** Motor de reglas determinísticas (riego/fertilización)

**Descripción:** El sistema debe evaluar automáticamente cada cultivo usando reglas predefinidas.Riego, No se debe regar si la lluvia registrada en las últimas 24 horas es mayor o igual a 5 mm (valor por defecto).Fertilización: Se debe programar una tarea de fertilización cuando se cumpla el número de días desde la siembra definido en la plantilla o en la configuración de la especie.Estas reglas permiten tomar decisiones consistentes y repetibles sin intervención manual.

**Criterios de aceptación:**
```
Scenario: Bloqueo de riego por lluvia
Given lluvia registrada 5.0 mm/24 h
When abro el calendario de riego
Then las franjas de riego del día aparecen "bloqueadas por lluvia"

Scenario: Programación de fertilización
Given cultivo con siembra hace 20 días
When entro a /cultivos/:id
Then veo tarea "Fertilizar" marcada "hoy"

Scenario: Umbral exacto (borde)
Given lluvia 4.9 mm/24 h
Then el riego no se bloquea por regla de lluvia
```
**Prioridad:** P0

**Esfuerzo:** 1

---
## REQ-010

**Título:** Centro de alertas en /alertas

**Descripción:** Generar alertas accionables: "No riegues hoy" y "Fertilizar hoy/mañana", con enlace a la acción. Esto ayuda a que el usuario tenga una guía diaria de lo que debe hacer.

Criterios de aceptación:.

**Criterios de aceptación:**
```
Scenario: Alertas visibles
Given reglas activas generan dos alertas
When abro /alertas
Then veo listado con fecha, cultivo, acción y botón "Ir"

Scenario: Sin alertas
Given no hay condiciones
When abro /alertas
Then veo "Sin alertas por hoy"
```
**Prioridad:** P0

**Esfuerzo:** 2

---

## REQ-011

**Título:** Lista de próximas tareas

**Descripción:** Lista de próximas tareas generadas por el motor de reglas del REQ-009. Esto le permitira al usuario saber qué hacer con cada cultivo segun el contexto (clima,etapa del cultivo,con plaga ,etc ).

**Criterios de aceptación:**
```
Scenario: Próximas tareas según etapa del cultivo
Given cultivo de café en etapa de floración
And el motor de reglas REQ-009 genera la tarea "Monitorear apertura de flores"
When abro /cultivos/:id
Then veo en "Próximas tareas" la entrada "Monitorear apertura de flores"
And debajo la nota "Condición: Etapa del cultivo: floración"
```
**Prioridad:** P2

**Esfuerzo:** 2

---

## REQ-013

**Título:** Recomendaciones automáticas de fertilización

**Descripción:** El sistema debe generar recomendaciones de fertilización para cada cultivo basándose en los requerimientos nutricionales de la especie cultivada.

La recomendación debe incluir el tipo de fertilizante, cantidad sugerida y fecha de aplicación. Estas sugerencias se actualizarán automáticamente cuando se ingresen nuevos datos o cambie la etapa de crecimiento del cultivo.

**Criterios de aceptación:**
```
Scenario: Recomendación generada automáticamente
  Given cultivo de maíz con registro de suelo N=25 ppm, P=8 ppm, K=20 ppm
  And la especie maíz requiere N=40 ppm, P=12 ppm, K=25 ppm
  When accedo al detalle del cultivo
  Then veo una recomendación "Aplicar fertilizante NPK 15-15-15, 50 kg/ha, esta semana"

Scenario: Actualización por nuevos datos
  Given cultivo con recomendación vigente
  When registro nuevos valores de suelo que cumplen los requerimientos
  Then la recomendación cambia a "No es necesaria fertilización en este momento"
```
**Prioridad:** P1

**Esfuerzo:** 3

---

## REQ-017

**Título:** Notificaciones internas

**Descripción:** El sistema debe mostrar un indicador visual en la barra superior con la cantidad de notificaciones no leídas.
Al hacer clic en este indicador, debe abrirse un panel emergente que muestre las últimas 20 notificaciones en orden cronológico, con su fecha, título y un breve resumen.
Esto permite que el usuario atienda rápidamente las novedades sin depender del correo electrónico.

**Criterios de aceptación:**
```
Scenario: Badge de no leídas
Given tengo 3 notificaciones nuevas
Then veo un badge "3" en la campana

Scenario: Marcar como leídas
When abro el panel y presiono "Marcar todo como leído"
Then el badge desaparece
```
**Prioridad:** P1

**Esfuerzo:** 1

---
## REQ-018

**Título:** Dashboard de rendimiento por cultivo

**Descripción:** El sistema debe mostrar un panel visual para cada cultivo que incluya:

Producción acumulada (kg cosechados por periodo. Ingresado por usuario).

Producción real vs Producción proyectada (permite al productor entender si su cultivo está rindiendo al máximo o hay cosas por mejorar)

Tareas realizadas vs. pendientes, separadas por tipo (riego, fertilización, mantenimiento).

Este panel debe ayudar a los productores y administradores a evaluar la salud y productividad de cada cultivo, identificar patrones y optimizar decisiones de manejo.

**Criterios de aceptación:**
```
Scenario: Ver dashboard de cultivo
  Given estoy en /cultivos/123/dashboard
  When el cultivo tiene datos de crecimiento, cosecha y tareas
  Then veo gráficas de crecimiento, producción y tareas con datos actualizados

Scenario: Filtro por fechas
  Given estoy en el dashboard del cultivo
  When selecciono rango de fechas "01/01/2025 a 31/03/2025"
  Then las gráficas muestran solo datos de ese periodo

Scenario: Cultivo sin datos
  Given cultivo recién registrado sin tareas ni cosechas
  When entro al dashboard
  Then veo mensaje "No hay datos suficientes para mostrar métricas"

Scenario: Comparativa de rendimiento
  Given cultivo con registros de años anteriores
  When selecciono "Comparar con año previo"
  Then veo gráficas superpuestas de crecimiento y producción año contra año
```
**Prioridad:** P1

**Esfuerzo:** 3

---

# Requisitos no funcionales

## REQ-021


**Título:** Disponibilidad del servicio  
**Descripción:** La aplicación debe mantener un uptime mensual ≥ 90% para garantizar el acceso continuo a datos y recomendaciones.  

**Criterios de aceptación:**  
- Monitorear la disponibilidad con herramientas automáticas (ej. UptimeRobot).  
- Registrar y reportar el tiempo total de indisponibilidad mensual.  

**Prioridad:** P0  

**Esfuerzo:** 3

---

## REQ-025 
**AG:** AG-NFN 

**Título:** Seguridad de datos 

**Descripción:** Toda comunicación debe realizarse mediante HTTPS y la información sensible debe almacenarse cifrada. 

**Criterios de aceptación:** 
- Ejecutar pruebas con OWASP ZAP para validar cifrado en tránsito.
- Verificar que los datos almacenados estén cifrados en reposo. 

**Prioridad:** P0 

**Esfuerzo:** 1

---

## REQ-027  


**Título:** Facilidad de uso  
**Descripción:** La interfaz debe permitir que un usuario sin experiencia previa complete las funciones básicas en ≤ 2 minutos sin ayuda externa.  

**Criterios de aceptación:**  
- Prueba de usabilidad con al menos 5 usuarios rurales reales.  
- Verificar que todos logren registrar un cultivo y consultar clima sin asistencia.  

**Prioridad:** P1  

**Esfuerzo:** 3

---
