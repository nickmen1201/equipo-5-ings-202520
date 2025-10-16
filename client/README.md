# Prueba Front Cultivos

Este proyecto es una aplicación frontend en React (Vite) para manejar cultivos y especies. A continuación se responden las preguntas indicadas en el archivo adjunto, y se incluyen instrucciones de instalación y ejecución para Windows PowerShell.

## ¿Qué hace esta carpeta / proyecto?

- Esta carpeta contiene el frontend de una pequeña aplicación para gestionar cultivos y especies.
- Está construída con React (v19) y Vite como bundler/desarrollo rápido.
- Ofrece páginas para ver cultivos, especies, crear nuevos cultivos, y autenticación básica (login/register). Los componentes principales están en `src/components` y las páginas en `src/pages`.

## ¿Cómo se instala esta parte del proyecto?

Requisitos previos:
- Node.js (recomendado: 18.x o 20.x) y npm (la versión moderna incluida con Node). Aunque el proyecto no exige una versión concreta en `package.json`, usar Node 18+ es una buena práctica con las dependencias actuales.

Pasos (PowerShell):

```powershell
# Sitúate en la carpeta del proyecto
cd C:\Users\USER\pruebafrontcultivos

# Instala dependencias
npm install
```

Notas:
- `package.json` incluye scripts útiles (`dev`, `build`, `preview`).
- Si usas yarn o pnpm puedes adaptar los comandos (`yarn` / `pnpm install`).

## ¿Cómo se corre esta parte del proyecto?

Comandos principales (PowerShell):

```powershell
# Arrancar el servidor de desarrollo (Vite)
npm run dev

# Construir la versión de producción
npm run build

# Previsualizar la build (local)
npm run preview
```

El servidor de desarrollo abrirá normalmente en `http://localhost:5173` (Vite). Revisa la salida en consola para la URL exacta.

## ¿Qué estándares debo tener en cuenta en esta parte del proyecto?

- Linter: El proyecto incluye `eslint` y un script `npm run lint`. Sigue las reglas configuradas por ESLint. No hay un archivo `eslintrc` mostrado aquí, pero `eslint.config.js` está presente en la raíz.
- Documentación: Usa comentarios JSDoc simples en funciones compartidas si vas a exponer utilidades o servicios (por ejemplo, `services/authService.js`, `services/CultivoService.js`).
- Estructura de carpetas: Mantener `components`, `pages`, `services` y `context` claros y coherentes.

## ¿Qué versión de JS (o Python o Java) usa?

- Esta es una aplicación frontend escrita en JavaScript (ES modules). El `package.json` usa `"type": "module"`, así que los imports usan sintaxis ESM.
- No se usan Python ni Java en este repositorio.

## ¿Qué necesito para la base de datos?

- Este repositorio parece ser sólo el frontend. No incluye una base de datos ni un backend.
- Para que la app funcione con persistencia, necesitarás un backend que exponga una API REST (o GraphQL) y una base de datos (por ejemplo, PostgreSQL, MySQL o MongoDB) según tus preferencias.

Recomendación mínima para desarrollo local:
- Un backend simple (Node/Express, FastAPI, etc.) que responda a los endpoints que espera el frontend (revisa `services/` para pistas de rutas usadas).
- Una base de datos relacional (Postgres o MySQL) o NoSQL (MongoDB). Para prototipado, SQLite o una instancia local de PostgreSQL funcionan bien.

Campos a verificar en el backend (sugeridos):
- Endpoints de autenticación: `/login`, `/register`.
- Endpoints de cultivos y especies: `/cultivos`, `/especies`, `/cultivos/:id`.

## Scripts y dependencias 

- Scripts:
  - `dev`: inicia Vite en modo desarrollo.
  - `build`: crea la build de producción con Vite.
  - `preview`: sirve la build producida localmente.
  - `lint`: ejecuta ESLint en el proyecto.
- Dependencias importantes:
  - `react` / `react-dom` (v19.x)
  - `react-router-dom` (v7)
  - `tailwindcss` y `@tailwindcss/vite` (v4)
  - `react-icons`

## Desarrollo y buenas prácticas

- Mantener los componentes pequeños y reutilizables (`src/components`).
- Colocar lógica de negocio y peticiones HTTP en `src/services`.
- Uso de `context` para estado global (por ejemplo, `AuthContext.jsx`).
- Ejecutar `npm run lint` antes de commits para mantener el estilo.

