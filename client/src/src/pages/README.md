# Pages

## What does this folder do?
Top-level React route pages (e.g., Home, Login, Register, Crops, Especies).

## How to install this part
No separate installation. Pages compile as part of the Vite app from `client/src`.

## How to run this part
`npm run dev` from `client/src` and visit http://localhost:5173.

## Standards to consider
- Use React Router for navigation.
- Keep data fetching inside services; pages orchestrate UI and state.

## JavaScript version
ES2022+ with JSX.

## Database requirements
None directly. Pages consume data via services from the backend API.
