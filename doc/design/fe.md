# Decisiones Frontend

En esta sección se presentan los frameworks/herramientas frontend considerados para la aplicación **CultivApp**. Se analizan sus ventajas y desventajas y se justifica la decisión final.

---

## Opciones consideradas

### 1. React (con Vite)
Biblioteca de UI basada en componentes, con ecosistema masivo (routing, estados, queries, UI kits).  
Vite se utiliza como _bundler_ para desarrollo rápido y _builds_ ligeros.

### 2. Vue 3 (con Vite)
Framework progresivo con sintaxis intuitiva (Options/Composition API), buena curva de aprendizaje y ecosistema oficial sólido (Vue Router, Pinia).

### 3. Angular
Framework completo de Google, muy estructurado y opinado. Incluye en el core routing, formularios, inyección de dependencias y CLI.

### 4. Svelte / SvelteKit
Framework reactivo con compilación a JS muy optimizada.  
SvelteKit agrega routing, SSR/SSG y _file-based routing_.

---

## Ventajas y desventajas

###  React
**Ventajas**
- Ecosistema más amplio del mercado: múltiples opciones para estado (Redux Toolkit/Zustand), datos remotos (TanStack Query), UI (Tailwind, shadcn/ui), gráficos (Recharts/ECharts), mapas (Leaflet/MapLibre).
- Curva de adopción conocida por el equipo; abundantes recursos y comunidad.
- Excelente integración con **TypeScript** y herramientas modernas (Vite, ESLint, Prettier, Vitest, React Testing Library).
- Flexibilidad de arquitectura: SPA, PWA, SSR (con frameworks como Next si más adelante se requiere).
- Amplio soporte para accesibilidad (ARIA), i18n y testing.

**Desventajas**
- No es “baterías incluidas”: hay que elegir librerías (decisiones iniciales).
- Patrón mental de hooks puede tener curva de aprendizaje para casos avanzados.

---

### Vue 3
**Ventajas**
- Sintaxis clara; **Composition API** facilita reutilización de lógica.
- Ecosistema oficial coherente (Router, Pinia) con poca fricción.
- Excelente DX con Vite y plantillas iniciales bien cuidadas.

**Desventajas**
- Menor oferta de librerías especializadas (particularmente para analítica agrícola y componentes muy específicos) frente a React.
- Menor experiencia previa del equipo.

---

### Angular
**Ventajas**
- Marco integral y opinado: arquitectura escalable desde el día 1 (DI, módulos, formularios reactivos).
- Herramientas de empresa (CLI, testing, _schematics_) y estándares consistentes.

**Desventajas**
- Curva de aprendizaje más alta; _boilerplate_ mayor.
- Peso inicial del _bundle_ superior; puede impactar dispositivos de gama media/baja usados por productores.

---

### Svelte / SvelteKit
**Ventajas**
- Excelente rendimiento por compilación; _bundles_ muy pequeños.
- Reactividad simple, menos código para la misma UI.

**Desventajas**
- Ecosistema más joven; menor disponibilidad de componentes listos (charts, mapas, formularios complejos) que necesitamos desde el MVP.
- Menos perfiles con experiencia en el mercado local.

---

## Decisión final

La opción seleccionada para el frontend es **React (con Vite + TypeScript)**.

### Razones
- **Ecosistema y modularidad**: necesitamos dashboards, formularios CRUD, mapas y gráficos. React ofrece la mayor variedad y madurez en estas áreas (Leaflet/MapLibre para mapas, Recharts/ECharts para analítica, TanStack Query para caché y sincronización de datos).
- **Productividad del equipo**: ya trabajamos con componentes, POO y TypeScript; React maximiza el “transfer learning” del equipo y acelera las entregas.
- **Rendimiento y dispositivos objetivo**: con Vite, _code-splitting_ y PWA, podemos ofrecer tiempos de carga competitivos y **soporte offline** (clave para productores con conectividad limitada).
- **Escalabilidad**: React permite crecer hacia SSR o microfrontends si el proyecto lo requiere sin reescrituras radicales.
- **Comunidad/soporte**: documentación, tutoriales y soluciones a problemas comunes disponible

