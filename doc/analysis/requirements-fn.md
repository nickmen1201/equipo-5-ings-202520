# Requisitos Funcionales
> Nota de supuestos (aplican a todos los requisitos y se validan en demo y pruebas):  
> (S1) Horario local: América/Bogotá.  
> (S2) “Productor” crea y gestiona sus propios datos; “Admin” gestiona catálogos/bandejas.  
> (S3) Datos de lluvia del SIATA expresados en mm/24 h.  
> (S4) Calendario permite navegación y agendamiento solo ≤ 30 días hacia adelante.  
> (S5) Bitácora solo texto plano UTF-8.  
> (S6) Auditoría mínima: quién (id usuario), qué (acción), cuándo (timestamp). Validación: revisión de UI y consultas de auditoría.

---
## REQ-001
**AG:** AG-FN

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


## REQ-002
**AG:** AG-FN

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

## REQ-003
**AG:** AG-FN

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

## REQ-004
**AG:** AG-FN

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

## REQ-005
**AG:** AG-FN

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

## REQ-006
**AG:** AG-FN

**Título:** Plantillas de tareas en /admin/tareas

**Descripción:** El sistema debe permitir que un Admin cree y gestione plantillas de tareas (por ejemplo: riego, fertilización). Cada plantilla tiene un nombre, tipo y una regla que indica cuándo se ejecuta (ejemplo: “Fertilizar a 20 días desde siembra”). Estas plantillas se usarán para generar tareas automáticamente en nuevos cultivos y evitar inconsistencias.

**Criterios de aceptación:**
```
Scenario: Crear plantilla
Given Admin en /admin/tareas
When creo "Fertilizar (20 días)"
Then se habilita para nuevos cultivos

Scenario: Evitar duplicados exactos
Given existe "Riego estándar"
When intento crear "Riego estándar"
Then veo "Ya existe una plantilla con ese nombre"
```
**Prioridad:** P0

## REQ-007
**AG:** AG-FN

**Título:** Scraping SIATA programado + reintento + /clima/actualizar

**Descripción:** Ejecutar scraping a las 06:00 y 18:00 con 1 reintento después de 15 min si falla. Botón "Actualizar ahora" en /clima/actualizar (solo Admin) y en /cultivos/:id (Productor, solo su zona). Valor: datos oportunos pese a falta de API.

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

## REQ-008
**AG:** AG-FN

**Título:** Fallback manual de lluvia en /clima/manual

**Descripción:** Si no hay datos válidos en 12 h, permitir cargar lluvia manual (mm/24 h) por zona/cultivo con fuente y comentario. Valor: continuidad operativa.

**Criterios de aceptación:**
```
Scenario: Cargar lluvia manual habilitado
Given última actualización > 12 h
When abro /clima/manual
Then puedo ingresar "lluvia=6.2 mm" y guardar

Scenario: Rango inválido
Given estoy en /clima/manual
When ingreso -3 mm
Then veo "Valor fuera de rango (0–200 mm)" y no se guarda
```
**Prioridad:** P0

## REQ-009
**AG:** AG-FN

**Título:** Motor de reglas determinísticas (riego/fertilización)

**Descripción:** Evaluar por cultivo: No regar si lluvia ≥ 5 mm/24 h (umbral por defecto) y generar fertilización a N días desde siembra (según plantilla/especie). Valor: decisiones claras y reproducibles.

**Criterios de aceptación:**
```
Scenario: Bloqueo de riego por lluvia
Given lluvia registrada 5.0 mm/24 h
When abro el calendario de riego
Then las franjas de riego del día aparecen "bloqueadas por lluvia"

Scenario: Programación de fertilización
Given cultivo con siembra hace 20 días y plantilla N=20
When entro a /cultivos/:id
Then veo tarea "Fertilizar" marcada "hoy"

Scenario: Umbral exacto (borde)
Given lluvia 4.9 mm/24 h
Then el riego no se bloquea por regla de lluvia
```
**Prioridad:** P0

## REQ-010
**AG:** AG-FN

**Título:** Centro de alertas en /alertas

**Descripción:** Generar alertas accionables: "No riegues hoy" y "Fertilizar hoy/mañana", con enlace a la acción. Valor: guía diaria.

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


## REQ-011
**AG:** AG-FN

**Título:** Detalle de cultivo en /cultivos/:id con clima y próximas tareas

**Descripción:** Mostrar tarjeta de clima (lluvia última 24 h, hora de actualización) y lista de próximas tareas generadas por reglas. Valor: contexto operativo por cultivo.

**Criterios de aceptación:**
```
Scenario: Visualización de clima
Given cultivo con lluvia 3.2 mm y actualización 06:00
When abro /cultivos/:id
Then veo "Lluvia 24 h: 3.2 mm (06:00)"

Scenario: Sin datos climáticos
Given scraping no ha corrido y no hay fallback
When abro /cultivos/:id
Then veo "Sin datos en las últimas 12 h" y un botón "Ingresar manual"
```
**Prioridad:** P1

## REQ-012
**AG:** AG-FN

**Título:** Calendario (30 días) en /calendario y /cultivos/:id/calendario

**Descripción:** Navegación y agendamiento máx. 30 días hacia adelante; franjas de 1:40 h iniciando en pares 08:00–16:00 (termina 17:40). Riego se bloquea si lluvia ≥ umbral. Valor: planificación simple y controlada.

**Criterios de aceptación:**
```
Scenario: Límite de 30 días
Given estoy en /calendario
When intento seleccionar una fecha a 31 días
Then veo "La fecha excede el máximo de 30 días"

Scenario: Generación de franjas
Given selecciono un día habilitado
Then veo franjas 08:00, 09:40, 11:20, 13:00, 14:40, 16:20

Scenario: Bloqueo por lluvia
Given lluvia ≥ umbral
Then las franjas de riego del día aparecen deshabilitadas con tooltip de regla
```
**Prioridad:** P0

## REQ-013
**AG:** AG-FN

**Título:** Ajustes de umbrales y preferencias en /ajustes

**Descripción:** Productor ajusta umbral de lluvia (2–20 mm, por defecto 5 mm) y ventana de avisos (p. ej., avisar con 1–3 días de anticipación). Valor: adaptación local.

**Criterios de aceptación:**
```
Scenario: Guardar umbral válido
Given ingreso 7 mm como umbral
When guardo
Then las reglas usan 7 mm en mi cuenta

Scenario: Fuera de rango
Given ingreso 25 mm
Then veo "Rango permitido 2–20 mm"
```
**Prioridad:** P1

## REQ-014
**AG:** AG-FN

**Título:** Bitácora de campo (texto) en /cultivos/:id/bitacora

**Descripción:** Registrar entradas de texto con fecha, autor y etiqueta (riego, fertilización, observación). Valor: trazabilidad operativa sin imágenes/IA.

**Criterios de aceptación (Gherkin):**
```
Scenario: Nueva entrada
Given escribo "Observación de plaga leve"
When guardo
Then veo la entrada con timestamp y autor

Scenario: Entrada vacía
Given el texto está vacío
Then el botón "Guardar" está deshabilitado
```
**Prioridad:** P1

## REQ-015
**AG:** AG-FN

**Título:** Auditoría mínima en /admin/auditoria

**Descripción:** Registrar (S6) para altas/bajas/ediciones, scraping y cambios de umbrales. Filtros por usuario, acción y rango de fechas. Valor: evidencia y control.

**Criterios de aceptación:**
```
Scenario: Registro de scraping
Given corre scraping 06:00
Then se registra entrada "scraping: éxito/fallo" con duración

Scenario: Filtro por usuario
Given hay múltiples acciones
When filtro por usuario X
Then solo veo acciones de X
```
**Prioridad:** P1

## REQ-016
**AG:** AG-FN

**Título:** Exportación CSV en /exportar

**Descripción:** Exportar cultivos, tareas programadas y bitácora a CSV (UTF‑8, separador coma). Valor: portabilidad.

**Criterios de aceptación:**
```
Scenario: Exportar cultivos
Given entro a /exportar
When descargo "cultivos.csv"
Then el archivo contiene encabezados y registros visibles en /cultivos

Scenario: Fechas y números
Given exporto
Then fechas ISO‑8601 y mm con punto decimal
```
**Prioridad:** P1

## REQ-017
**AG:** AG-FN

**Título:** Notificaciones internas (campana)

**Descripción:** Indicador de notificaciones no leídas en la barra superior y panel emergente con las últimas 20. Valor: atención rápida sin correo.

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

## REQ-018
**AG:** AG-FN

**Título:** Carga de datos semilla en /admin/demo-datos

**Descripción:** Botón para cargar datos semilla (1 Admin, 1 Productor demo, 2 especies, 3 cultivos y 10 entradas de bitácora) si el scraping falla el día de la entrega. Valor: aseguramiento de demo.

**Criterios de aceptación:**
```
Scenario: Carga única protegida
Given no hay datos de demo cargados
When presiono "Cargar datos semilla"
Then se puebla la base y el botón queda deshabilitado

Scenario: Segundo intento
Given los datos semilla ya están cargados
When presiono de nuevo
Then veo "Ya se cargaron los datos de demo"
```
**Prioridad:** P1

## REQ-019
**AG:** AG-FN

**Título:** Panel de salud del sistema en /admin/salud

**Descripción:** Mostrar última hora de scraping, duración, éxito/fallo y edad del dato climático (en minutos). Valor: diagnóstico rápido.

**Criterios de aceptación:**
```
Scenario: Semáforo de frescura
Given última actualización hace 130 min
Then veo indicador "Amarillo" y sugerencia "Actualizar ahora"
```
**Prioridad:** P2

## REQ-020
**AG:** AG-FN

**Título:** Tour de ayuda inicial

**Descripción:** Tour de 5 pasos en primera visita (login→cultivos→detalle→calendario→alertas) con opción “No volver a mostrar”. Valor: onboarding autónomo.

**Criterios de aceptación:**
```
Scenario: Mostrar una sola vez
Given es mi primera sesión
Then veo el tour
And al finalizar no vuelve a aparecer en la próxima sesión
```
**Prioridad:** P2
