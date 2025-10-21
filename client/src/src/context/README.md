# Context

## What does this folder do?
React Context providers (e.g., authentication) for global state.

## How to install this part
No separate installation. Ensure app wraps routes with the Context provider in `main.jsx`.

## How to run this part
Run the app from `client/src` with `npm run dev`.

## Standards to consider
- Keep context minimal; avoid over-fetching.
- Document context value shape in code comments/JSDoc.

## JavaScript version
ES2022+ with JSX.

## Database requirements
None. Context talks to services which call the backend.
