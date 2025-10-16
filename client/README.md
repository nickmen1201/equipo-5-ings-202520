# Cultivos Frontend (Sample)

This project is a React (Vite) frontend for managing crops and species. Below are answers to the questions from the provided attachment, plus installation and run instructions for Windows PowerShell.

## What does this folder / project do?

- This folder contains the frontend of a small application to manage crops and species.
- It is built with React (v19) and uses Vite as the fast bundler/development server.
- It provides pages to view crops and species, create new crops, and basic authentication (login/register). The main components live in `src/components` and pages in `src/pages`.

## How do I install this part of the project?

Prerequisites:
- Node.js (recommended: 18.x or 20.x) and npm (the modern npm shipped with Node). The project does not pin a Node version in `package.json`, but using Node 18+ is recommended for the current dependencies.

Steps (PowerShell):

```powershell
# Change to the project folder
cd C:\Users\USER\pruebafrontcultivos

# Install dependencies
npm install
```

Notes:
- `package.json` contains useful scripts (`dev`, `build`, `preview`).
- If you use yarn or pnpm, adapt commands accordingly (`yarn` / `pnpm install`).

## How do I run this part of the project?

Main commands (PowerShell):

```powershell
# Start the development server (Vite)
npm run dev

# Build production bundle
npm run build

# Preview the built production site locally
npm run preview
```

The dev server usually serves at `http://localhost:5173` (Vite). Check the console output for the exact URL.

## What standards should I follow for this part of the project?

- Linter: The project includes `eslint` and a `npm run lint` script. Follow the ESLint rules configured in the repository. There is an `eslint.config.js` file in the root.
- Documentation: Use simple JSDoc comments on shared functions if you expose utilities or services (for example, `services/authService.js`, `services/CultivoService.js`).
- Folder structure: Keep `components`, `pages`, `services`, and `context` organized and consistent.

## Which language/version is used (JS/Python/Java)?

- This is a frontend application written in JavaScript (ES modules). The `package.json` declares `"type": "module"`, so imports use ESM syntax.
- There is no Python or Java in this repository.

## What do I need for the database?

- This repository appears to be frontend-only. It does not include a backend or a database.
- To enable persistence, you'll need a backend that exposes a REST (or GraphQL) API and a database (for example, PostgreSQL, MySQL, or MongoDB), depending on your preference.

Minimum recommendation for local development:
- A simple backend (Node/Express, FastAPI, etc.) that implements the endpoints expected by the frontend (check `src/services/` for hints about routes).
- A relational database (Postgres, MySQL) or a NoSQL DB (MongoDB). For quick prototyping, SQLite or a local PostgreSQL instance works fine.

Backend fields/endpoints to verify (suggested):
- Authentication endpoints: `/login`, `/register`.
- Crops and species endpoints: `/cultivos`, `/especies`, `/cultivos/:id`.

## Scripts and dependencies

- Scripts:
  - `dev`: starts Vite in development mode.
  - `build`: creates a production build with Vite.
  - `preview`: serves the produced build locally.
  - `lint`: runs ESLint on the project.
- Key dependencies:
  - `react` / `react-dom` (v19.x)
  - `react-router-dom` (v7)
  - `tailwindcss` and `@tailwindcss/vite` (v4)
  - `react-icons`

## Development and best practices

- Keep components small and reusable (`src/components`).
- Put business logic and HTTP requests in `src/services`.
- Use `context` for global state (for example, `AuthContext.jsx`).
- Run `npm run lint` before commits to keep code style consistent.

