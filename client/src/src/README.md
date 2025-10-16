# Frontend Source

## What does this folder do?
Holds the React application source code (components, pages, services, context, assets) built with Vite.

## How to install this part
- Prerequisite: Node.js 20+ and npm.
- From `client/src` run: `npm install`.

## How to run this part
- From `client/src` run: `npm run dev`.
- App will be available at http://localhost:5173.

## Standards to consider
- ESLint enabled (see `eslint.config.js`).
- Prefer JSDoc for public utilities/services.
- Use React functional components and hooks.

## JavaScript version
- ECMAScript 2022+ (Node 20, Vite 5, React 18/19 compatible).

## Database requirements
- None directly on the frontend. It consumes the backend REST API at http://localhost:8080.
