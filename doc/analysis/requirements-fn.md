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

**Título:** Inicio de sesión básico en /login

**Descripción:** El sistema debe permitir que un usuario existente (con rol Admin o Productor) se autentique mediante correo electrónico y contraseña en la ruta /login.
Este requisito asegura que solo usuarios autorizados puedan acceder a la plataforma, previniendo accesos no autorizados y protegiendo la información sensible. La autenticación debe validar las credenciales ingresadas contra los registros almacenados de forma segura (por ejemplo, usando contraseñas hasheadas) y, en caso de ser correctas, generar una sesión activa o token de autenticación que permita al usuario navegar por las funcionalidades de la aplicación según sus permisos.

**Criterios de aceptación:**
```
Scenario: Login válido
Given estoy en /login
And ingreso "user@demo.com" y "Password123"
When presiono "Ingresar"
Then veo el tablero en /home

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
Then se crea el usuario Productor y se redirige a /home

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

**Descripción:** Crear, editar (nombre, especie, fecha siembra, superficie,etc), archivar/activar cultivos.

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
## REQ-006

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

**Esfuerzo:** 2

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
## REQ-008 (revisión de coherencia)

**Título:** Registro  manual de lluvia en /clima/manual

**Descripción:** Si el sistema no recibe datos de lluvia válidos durante más de 12 horas, debe permitir que el usuario ingrese manualmente la cantidad de lluvia registrada en las últimas 24 horas (en milímetros) para una zona o cultivo. Al registrar este dato, también se debe poder indicar la fuente de la información y un comentario opcional. Esto garantiza que el sistema pueda seguir funcionando aunque falle la fuente automática.

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

**Esfuerzo:** 2
---
## REQ-009

**Título:** Motor de reglas determinísticas (riego/fertilización)

**Descripción:** El sistema debe evaluar automáticamente cada cultivo usando reglas predefinidas.Riego, se debe regar si pasan x días, según reglas. Fertilización: Se debe programar una tarea de fertilización cuando se cumpla el número de días desde la siembra definido en la plantilla o en la configuración de la especie.Estas reglas permiten tomar decisiones consistentes y repetibles sin intervención manual.

**Criterios de aceptación:**
```
Scenario: Programación de riego
Given cultivo con siembra hace 1 día y plantilla N=1
When entro a /cultivos/:id
Then veo tarea "Riego" marcada "Pendiente"

Scenario: Programación de fertilización
Given cultivo con siembra hace 20 días y plantilla N=20
When entro a /cultivos/:id
Then veo tarea "Fertilizante" marcada "Pendiente"

```
**Prioridad:** P0

**Esfuerzo:** 1

---
## REQ-010

**Título:** Centro de alertas en /alertas

**Descripción:** Generar alertas accionables: "No riegues hoy" y "Fertilizar hoy", con enlace a la acción. Esto ayuda a que el usuario tenga una guía diaria de lo que debe hacer.

Criterios de aceptación:.

**Criterios de aceptación:**
```
Scenario: Alertas visibles
Given reglas activas generan dos alertas
When abro /alertas
Then veo listado con fecha, cultivo, acción y botón "Ir al cultivo"

Scenario: Sin alertas
Given no hay condiciones
When abro /alertas
Then veo "Sin alertas por hoy"
```
**Prioridad:** P0

**Esfuerzo:** 2

---
## REQ-011

**Título:** Detalle de cultivo en /cultivos/:id con próximas tareas

**Descripción:** Mostrar una tarjeta con lista de próximas tareas generadas por el motor de reglas del REQ-009. Esto le permitirá al usuario saber qué hacer con cada cultivo segun el contexto.

**Criterios de aceptación:**
```
Scenario: Programación de riego
Given cultivo con plantilla
When entro a /cultivos/:id
Then veo tarea "Riego" marcada "Próxima"

Scenario: Programación de fertilización
Given cultivo con plantilla
When entro a /cultivos/:id
Then veo tarea "Fertilizante" marcada "Próxima"
```
**Prioridad:** P2

**Esfuerzo:** 2

---
## REQ-012

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

**Esfuerzo:** 5

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
## REQ-014

**Título:** Bitácora de campo  en /cultivos/:id/bitacora

**Descripción:** Registrar entradas de texto con fecha, autor y etiqueta (riego, fertilización, observación).La bitácora sirve como un registro histórico donde el productor puede documentar lo que observa o las acciones realizadas en el campo, manteniendo la información organizada y accesible.

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

**Esfuerzo:** 1

---
## REQ-015

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

**Esfuerzo:** 1

**Esfuerzo:**

---
## REQ-016

**Título:** Exportación CSV en /exportar

**Descripción:** Exportar cultivos, tareas programadas y bitácora a CSV . Esta función facilita la portabilidad de la información para análisis externos, respaldos o intercambio de datos. El archivo CSV debe generarse con un formato estándar y codificación UTF-8 para asegurar su compatibilidad..

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
## REQ-019

**Título:** Panel de salud del sistema en /admin/salud

**Descripción:** El sistema debe mostrar en la ruta /admin/salud un panel con información clave para diagnosticar rápidamente el estado de los procesos y datos( Última hora de scraping ejecutado, Duración de la última ejecución,Resultado (éxito o fallo),tiempo de la data climática en minutos desde su última actualización)


**Criterios de aceptación:**
```
Scenario: Semáforo de frescura
Given última actualización hace 130 min
Then veo indicador "Amarillo" y sugerencia "Actualizar ahora"
```
**Prioridad:** P2

**Esfuerzo:** 1

---
## REQ-020 (Hay que cambiarlo)

**Título:** Registro de minerales del suelo en /cultivos/:id/suelo

**Descripción:** El sistema debe permitir registrar y actualizar la composición mineral del suelo para cada cultivo, especificando valores en mg/kg o ppm (partes por millón) de nutrientes clave. La información debe guardarse con la fecha de medición y el usuario que la registró. Esto permite hacer seguimiento de la fertilidad del suelo y planificar riegos o fertilizaciones de forma más precisa.

**Criterios de aceptación:**
```
Scenario: Registrar minerales del suelo
  Given estoy en /cultivos/123/suelo
  When ingreso N=45 ppm, P=10 ppm, K=30 ppm, Ca=15 ppm, Mg=8 ppm
  And presiono "Guardar"
  Then los valores quedan registrados con la fecha y mi usuario

Scenario: Editar valores existentes
  Given ya existe un registro de minerales para el cultivo
  When modifico N=50 ppm y guardo
  Then el nuevo valor se actualiza con la fecha y mi usuario

Scenario: Valores fuera de rango
  Given estoy en /cultivos/123/suelo
  When ingreso N=-5 ppm
  Then veo "Valor inválido" y no se guarda el registro
```
**Prioridad:** P1
