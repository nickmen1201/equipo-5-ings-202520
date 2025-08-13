# Requisitos Funcionales (Gherkin) – CultivApp

> *P0* crítico/MVP, *P1* alto, *P2* medio.

---

## RF01 – Registro de productor (*P0*)
*Feature:* Registro de cuenta de productor  
*Scenario:* Crear cuenta con datos mínimos  
*Given* que soy un productor sin cuenta  
*When* registro nombre, correo y contraseña válidos  
*Then* el sistema crea mi cuenta y me inicia sesión

---

## RF02 – Autenticación segura (*P0*)
*Feature:* Inicio de sesión  
*Scenario:* Iniciar sesión con credenciales válidas  
*Given* que tengo una cuenta activa  
*When* ingreso correo y contraseña correctos  
*Then* accedo al panel del productor

---

## RF03 – Gestión de cultivos (*P0*)
*Feature:* Crear cultivo  
*Scenario:* Registrar cultivo con datos básicos  
*Given* que estoy autenticado  
*When* creo un cultivo con especie, fecha de siembra y ubicación  
*Then* el cultivo queda guardado y visible en mi panel

---

## RF04 – Calendario del cultivo (*P0*)
*Feature:* Línea de tiempo  
*Scenario:* Ver calendario con hitos  
*Given* un cultivo registrado  
*When* abro el detalle del cultivo  
*Then* veo calendario con hitos (siembra, riego, fertilización) y próximos eventos

---

## RF05 – Registro de actividades (*P0*)
*Feature:* Bitácora del cultivo  
*Scenario:* Registrar riego/fertilización  
*Given* un cultivo existente  
*When* registro una actividad (tipo, fecha, notas)  
*Then* se agrega a la bitácora y actualiza el historial del cultivo

---

## RF06 – Consulta de clima por ubicación (*P0*)
*Feature:* Clima SIATA  
*Scenario:* Obtener clima actual y pronóstico local  
*Given* un cultivo con ubicación (municipio/vereda)  
*When* el sistema consulta el microservicio de clima  
*Then* obtiene temperatura, lluvia esperada y pronóstico 3–5 días

---

## RF07 – Motor de reglas de riego (*P0*)
*Feature:* Reglas de riego con clima real  
*Scenario:* Recomendar no regar si lloverá  
*Given* cultivo con ubicación y fecha de siembra  
*And* pronóstico de lluvia ≥ umbral en próximas 24 h  
*When* el sistema evalúa reglas de riego  
*Then* genera recomendación: “No riegues hoy, va a llover”

---

## RF08 – Reglas fenológicas básicas (*P0*)
*Feature:* Reglas por días desde siembra  
*Scenario:* Recordar fertilización a los N días  
*Given* cultivo de especie X con pauta “fertilizar a los 20 días”  
*When* se cumple el día 20 ± tolerancia  
*Then* el sistema genera recordatorio de fertilización

---

## RF09 – Notificaciones in‑app (*P0*)
*Feature:* Centro de alertas  
*Scenario:* Ver alertas recientes  
*Given* que tengo alertas generadas  
*When* abro el centro de notificaciones  
*Then* veo lista con fecha, cultivo y recomendación

---

## RF10 – Edición de cultivo (*P1*)
*Feature:* Actualizar datos de cultivo  
*Scenario:* Cambiar fecha de siembra  
*Given* cultivo existente  
*When* edito la fecha de siembra  
*Then* se recalculan reglas y próximos hitos

---

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
