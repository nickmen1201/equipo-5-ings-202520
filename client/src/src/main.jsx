/**
 * Application Entry Point - React App Initialization (REQ-001: Login Authentication)
 * 
 * This file is the entry point for the CultivApp React application. It sets up
 * the root of the React component tree and configures global providers such as
 * routing (BrowserRouter) and authentication context (AuthProvider).
 * 
 * Setup Order (Outer to Inner):
 * 1. StrictMode: Enables additional checks and warnings in development.
 * 2. BrowserRouter: Provides routing capabilities throughout the app.
 * 3. AuthProvider: Provides authentication state and functions globally.
 * 4. App: The main application component with routes and UI.
 * 
 * Provider Pattern:
 * - BrowserRouter wraps the app to enable client-side routing.
 * - AuthProvider wraps the app to provide auth state to all components.
 * - Both use React Context API to avoid prop drilling.
 * 
 * REQ-001 Integration:
 * - BrowserRouter enables navigation between login and dashboard pages.
 * - AuthProvider centralizes authentication state for login flow.
 * - StrictMode helps catch potential issues during development.
 * 
 * @author CultivApp Team
 * @version 1.0 (REQ-001)
 */

import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter } from 'react-router-dom'
import { AuthProvider } from './context/AuthContext.jsx'
import './index.css'
import App from './App.jsx'

/**
 * Render the React application to the DOM.
 * 
 * This code finds the root DOM element (id="root" in index.html) and renders
 * the React component tree inside it.
 * 
 * Component Hierarchy:
 * - StrictMode: Development tool for highlighting potential problems.
 * - BrowserRouter: Enables routing with URLs (e.g., /login, /dashboard).
 * - AuthProvider: Provides authentication context to all child components.
 * - App: Main component containing routes and application logic.
 * 
 * StrictMode Benefits:
 * - Identifies unsafe lifecycle methods.
 * - Warns about deprecated APIs.
 * - Detects unexpected side effects (runs effects twice in dev).
 * - Only active in development, no impact on production.
 * 
 * BrowserRouter:
 * - Uses HTML5 history API for clean URLs (no # in URL).
 * - Enables navigation with Link, useNavigate, etc.
 * - Synchronizes UI with URL changes.
 * 
 * AuthProvider:
 * - Makes auth state (user, login, logout) available to all components.
 * - Uses React Context to avoid passing props through every level.
 * - Centralizes authentication logic for entire app.
 */
createRoot(document.getElementById('root')).render(
  <StrictMode>
    <BrowserRouter>
      <AuthProvider>
        <App />
      </AuthProvider>
    </BrowserRouter>
  </StrictMode>,
)