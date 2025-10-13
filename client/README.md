# CultivApp Frontend

React + Vite web application for agricultural crop management.

---

## Tech Stack

- React 18
- Vite (build tool)
- React Router (navigation)
- Context API (state management)
- Axios (HTTP client)
- Tailwind CSS (styling)

---

## Quick Start

```bash
cd client
npm install  # First time only
npm run dev
```
→ UI on http://localhost:5173

---

## Project Structure

```
src/
├── Components/     # Reusable UI components
│   └── NavBar.jsx
├── pages/          # Page components
│   └── Login.jsx
├── services/       # API communication
│   └── authService.js
├── context/        # React Context
│   └── AuthContext.jsx
├── App.jsx         # Main app component
├── main.jsx        # Entry point
└── index.css       # Global styles
```

---

## Features

**REQ-001: Login**
- Email/password authentication
- JWT token storage (localStorage)
- Role-based routing (ADMIN → /admin, PRODUCTOR → /dashboard)
- Error messages in Spanish

---

## Development

**Run dev server:**
```bash
npm run dev
```

**Build for production:**
```bash
npm run build
```

**Preview production build:**
```bash
npm run preview
```

**Lint code:**
```bash
npm run lint
```

---

## Configuration

**API URL:** `src/services/authService.js` (default: http://localhost:8080)  
**Port:** `vite.config.js` (default: 5173)

---

## API Integration

All API calls go through service files in `src/services/`:
- `authService.js` - Authentication (login, token management)

Example:
```javascript
import { loginUser } from '../services/authService';

const response = await loginUser(email, password);
// Returns: { token, role }
```
