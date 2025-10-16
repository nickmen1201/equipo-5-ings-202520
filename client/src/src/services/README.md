# Services

## What does this folder do?
Encapsulates API calls (authentication, species, crops). Central place for fetch/headers/auth.

## How to install this part
No separate installation. Requires environment to run the app (Node 20+).

## How to run this part
Use `npm run dev` from `client/src`. Services are imported by pages/components.

## Standards to consider
- Keep pure functions returning parsed data or throwing errors.
- Document function signatures with JSDoc.
- Centralize base URL and auth header handling.

## JavaScript version
ES2022+.

## Database requirements
None. Services talk to backend at http://localhost:8080 which manages the database.
