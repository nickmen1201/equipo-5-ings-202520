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

**Descripción:** Permitir que un usuario existente (Admin o Productor) autentique con correo y contraseña en /login. Aporta valor al restringir el acceso y reducir ambigüedades al fijar campos y mensajes de error concretos.

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

**Descripción:** Alta de nuevos Productores con nombre, correo único y contraseña (mín. 8 caracteres) en /register. Valor: habilita la adopción. Ambigüedad reducida con validaciones explícitas y unicidad de correo.

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

**Título:** Control de acceso por rol (RBAC mínimo)

**Descripción:** Restringir vistas de administración (/admin/especies, /admin/tareas, /admin/auditoria) solo a Admin. Productor sin acceso a /admin. Valor: seguridad y orden operativo. Validación: intento de acceso directo devuelve 403 y UI oculta enlaces.

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
**Descripción:** Crear, editar (nombre, especie, fecha siembra, superficie ha), archivar/activar cultivos. Valor: base de datos operativa para reglas. Ambigüedad reducida con rutas y campos concretos.
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

**Descripción:** Admin gestiona especies (crear/editar/eliminar) con parámetros base (p. ej., días recomendados para fertilización por defecto). Valor: estandariza configuraciones.

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

**Título:** Catálogo de tareas plantilla en /admin/tareas

**Descripción:** Plantillas determinísticas (riego, fertilización). Campos: nombre, tipo, regla (p. ej., "fertilizar a N días desde siembra"). Valor: consistencia en generación de tareas.

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


## RF11 – Gestión de ubicaciones del productor (*P1*)
*Feature:* Múltiples ubicaciones  
*Scenario:* Añadir/editar ubicaciones  
*Given* que opero en varias fincas  
*When* agrego una nueva ubicación con coordenadas/municipio  
*Then* puedo asignarla a nuevos cultivos

---

## RF12 – Panel con estado de cultivos (*P1*)
*Feature:* Dashboard simple  
*Scenario:* Ver resumen por cultivo  
*Given* varios cultivos  
*When* abro el panel  
*Then* veo tarjetas con estado, próximas tareas y alerta de lluvia

---

## RF13 – Historial exportable (*P1*) 
*Feature:* Exportar bitácora  
*Scenario:* Descargar CSV  
*Given* actividades registradas  
*When* exporto el historial  
*Then* descargo CSV con fecha, tipo, notas y clima asociado

---

## RF14 – Catálogo de especies base (*P1*)
*Feature:* Biblioteca de pautas  
*Scenario:* Seleccionar especie con reglas predefinidas  
*Given* lista base de cultivos (ej. café, maíz, hortalizas)  
*When* selecciono “maíz”  
*Then* se precargan reglas fenológicas sugeridas editables

---

## RF15 – Microservicio de clima (Scraping→API) (*P1*)
*Feature:* Integración con servicio clima interno  
*Scenario:* Consumir API de clima propia  
*Given* un endpoint interno /clima?lat&lon  
*When* consulto el clima  
*Then* recibo JSON normalizado (lluvia, temp, fecha, fuente) y cacheado

---

## RF16 – Reglas configurables por cultivo (*P2*)
*Feature:* Editor de reglas  
*Scenario:* Ajustar umbral de lluvia  
*Given* un cultivo  
*When* modifico el umbral de lluvia de 60% a 70%  
*Then* las recomendaciones futuras usan el nuevo umbral

---

## RF17 – Roles básicos (*P2*)
*Feature:* Rol Admin  
*Scenario:* Administrar catálogo y usuarios  
*Given* rol Admin  
*When* ingreso al panel de administración  
*Then* puedo gestionar especies base y desactivar usuarios

---

## RF18 – Auditoría mínima (*P2*)
*Feature:* Trazabilidad  
*Scenario:* Ver quién cambió una regla  
*Given* que se actualizó la pauta de fertilización  
*When* abro el historial de cambios  
*Then* veo usuario, fecha, cambio anterior y nuevo

---

## RF19 – Mapa simple de ubicaciones (*P2*)
*Feature:* Mapa de cultivos  
*Scenario:* Ver cultivos en el mapa  
*Given* cultivos con coordenadas  
*When* abro la vista mapa  
*Then* veo marcadores por cultivo con acceso a su detalle

---

## RF20 – Salud del servicio clima (*P2*)
*Feature:* Monitor de integración  
*Scenario:* Ver estado del microservicio de clima  
*Given* un panel de estado  
*When* reviso “Clima SIATA”  
*Then* veo “OK / Degradado / Caído” con último ping y latencia
