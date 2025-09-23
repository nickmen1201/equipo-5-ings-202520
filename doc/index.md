# Documentación – CultivApp

## ¿Cuál es el problema?
En Colombia, gran parte de la producción agrícola depende de dos factores: **recursos económicos** o **intuición heredada**. Para quienes no cuentan con acceso a tecnología de alto costo (sensores, estaciones meteorológicas propias, sistemas de riego automatizados), las decisiones diarias sobre el cultivo se toman con base en la experiencia, el “ojo” y la costumbre.

Esto genera un riesgo alto de pérdida de productividad y calidad, ya que variables como el clima, la etapa fenológica del cultivo o el momento óptimo para una tarea (riego, fertilización, control de plagas) pueden pasar inadvertidas o malinterpretarse.

A nivel macro, esta falta de tecnificación **amplía la brecha** entre productores con acceso a datos y quienes no lo tienen, limita la competitividad y dificulta la adopción de buenas prácticas. Además, perpetúa una dependencia en conocimientos no sistematizados, que no siempre se transmiten de manera efectiva a las nuevas generaciones.



## ¿A quién nos dirigimos y qué planteamos?
- Productores rurales sin acceso a sensores/capex alto.
- Secretarías de Agricultura, fundaciones/cooperativas y agencias de extensión.
- Grupos de investigación y docentes que trabajen agro y transformación digital.
- Personas que exploran el trabajo agrícola por primera vez y no tienen acceso a tecnología de punta o asesores con conocimientos amplios.

**CultivApp** busca cerrar esta brecha mencionada anteriormente, proporcionando una herramienta **accesible, de bajo costo y orientada al contexto real del productor**. La aplicación combina dos fuentes clave de información:
1. **Datos climáticos reales** consultados desde el SIATA (Sistema de Alerta Temprana del Valle de Aburrá) mediante **web scraping** o a través de un **microservicio/API** propio que procese y estandarice la información.
2. **Datos registrados por el propio productor** sobre sus cultivos (tipo de planta, fecha de siembra, prácticas de manejo, ubicación).

Con esta información, el sistema **cruza y procesa los datos** para ofrecer recomendaciones y alertas como:
- “No riegues hoy, va a llover.”
- “Han pasado 20 días desde la siembra de tu cultivo de maíz: es momento de fertilizar.”
- “Probabilidad alta de lluvia en las próximas 24 horas, considera ajustar tu plan de fumigación.”

De esta forma, **CultivApp** no reemplaza la experiencia del agricultor, sino que brinda una valiosa herramienta que **potencia la labor del campo con información precisa y oportuna**, democratizando el acceso a la toma de decisiones basadas en datos y fomentando incluso un cambio generacional.

---

## Navegación de documentación

- [Análisis](analysis/index.md) — Índice, requisitos funcionales y no funcionales con priorización

- [Tablero Kanban - CultivApp](https://trello.com/b/irs5xrZO/tablero-kanban-mvp-cultivapp) 


---

## Estado de ramas
- **development**: integración de equipo.
- **feature/analysis-1**: contenidos de este taller – fase de análisis. 
