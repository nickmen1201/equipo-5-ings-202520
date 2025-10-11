/**
 * Main Application Component - Root Component with Routing (REQ-001: Login Authentication)
 * 
 * This is the main application component that defines the routing structure for
 * CultivApp. It uses React Router to handle navigation between different pages
 * and implements protected routes for authenticated users only.
 * 
 * Component Responsibilities:
 * - Defines all application routes (/login, /dashboard, /admin, etc.).
 * - Implements protected routes that require authentication.
 * - Provides consistent layout structure (navbar, content area).
 * - Handles loading states during authentication initialization.
 * 
 * Routing Structure:
 * - Public Routes: Accessible without authentication (/login, /register).
 * - Protected Routes: Require authentication (/dashboard, /admin, /home).
 * - Role-Based Routes: Restrict access based on user role (future enhancement).
 * 
 * REQ-001 Implementation:
 * - /login: Login page where users authenticate.
 * - /dashboard: Main page for PRODUCTOR users after login.
 * - /admin: Main page for ADMIN users after login.
 * - Protected routes redirect to /login if user is not authenticated.
 * 
 * @author CultivApp Team
 * @version 1.0 (REQ-001)
 */

import { Routes, Route, Navigate } from 'react-router-dom'
import { useAuth } from './context/AuthContext'
import NavBar from './Components/NavBar'
import Login from './pages/Login'

/**
 * Protected Route Component - Redirects to login if not authenticated.
 * 
 * This component wraps protected pages and ensures only authenticated users
 * can access them. If user is not logged in, they are redirected to /login.
 * 
 * Flow:
 * 1. Check if user is authenticated via AuthContext.
 * 2. If authenticated: Render the protected content (children).
 * 3. If not authenticated: Redirect to /login page.
 * 
 * Usage:
 * ```jsx
 * <Route path="/dashboard" element={
 *   <ProtectedRoute>
 *     <DashboardPage />
 *   </ProtectedRoute>
 * } />
 * ```
 * 
 * @param {Object} props - Component props.
 * @param {React.ReactNode} props.children - The protected content to render if authenticated.
 * @returns {JSX.Element} Children if authenticated, Navigate to /login if not.
 */
function ProtectedRoute({ children }) {
  // Get authentication state from context
  const { user, loading } = useAuth();
  
  // Show loading state while checking authentication
  // Prevents flash of login page before auth state is determined
  if (loading) {
    return (
      <div className="flex items-center justify-center h-screen">
        <p className="text-gray-600">Cargando...</p>
      </div>
    );
  }
  
  // If user is authenticated, render the protected content
  // If not authenticated, redirect to login page
  return user ? children : <Navigate to="/login" replace />;
}

/**
 * Main App Component - Defines application routes and layout.
 * 
 * @returns {JSX.Element} Application with routing.
 */
function App() {
  return (
    <>
      {/* 
       * Routes Component - Container for all route definitions.
       * Matches current URL against defined routes and renders the matching component.
       */}
      <Routes>
        {/* 
         * Public Route: Login Page
         * Path: /login
         * Accessible without authentication.
         * Implements REQ-001 login functionality.
         */}
        <Route path="/login" element={<Login />} />
        
        {/* 
         * Public Route: Registration Page
         * Path: /register
         * Placeholder for future registration feature (not REQ-001).
         * Currently shows a message that registration is not implemented.
         */}
        <Route path="/register" element={
          <div className="flex items-center justify-center h-screen">
            <div className="text-center">
              <h1 className="text-2xl font-semibold mb-4">Registro</h1>
              <p className="text-gray-600">La funcionalidad de registro se implementará en un requisito futuro.</p>
            </div>
          </div>
        } />
        
        {/* 
         * Protected Route: Producer Dashboard
         * Path: /dashboard
         * Accessible only to authenticated PRODUCTOR users.
         * Main landing page after PRODUCTOR login.
         */}
        <Route path="/dashboard" element={
          <ProtectedRoute>
            <div>
              <NavBar />
              <div className="container mx-auto p-6">
                <h1 className="text-3xl font-semibold mb-4">Dashboard del Productor</h1>
                <p className="text-gray-600">
                  Bienvenido al dashboard de CultivApp. Aquí podrás gestionar tus cultivos.
                </p>
              </div>
            </div>
          </ProtectedRoute>
        } />
        
        {/* 
         * Protected Route: Admin Dashboard
         * Path: /admin
         * Accessible only to authenticated ADMIN users.
         * Main landing page after ADMIN login.
         */}
        <Route path="/admin" element={
          <ProtectedRoute>
            <div>
              <NavBar />
              <div className="container mx-auto p-6">
                <h1 className="text-3xl font-semibold mb-4">Panel de Administración</h1>
                <p className="text-gray-600">
                  Bienvenido al panel de administración de CultivApp.
                </p>
              </div>
            </div>
          </ProtectedRoute>
        } />
        
        {/* 
         * Protected Route: Home Page
         * Path: /home
         * Generic home page for authenticated users (fallback).
         */}
        <Route path="/home" element={
          <ProtectedRoute>
            <div>
              <NavBar />
              <div className="container mx-auto p-6">
                <h1 className="text-3xl font-semibold mb-4">Inicio</h1>
                <p className="text-gray-600">Bienvenido a CultivApp.</p>
              </div>
            </div>
          </ProtectedRoute>
        } />
        
        {/* 
         * Default Route: Redirect to Login
         * Path: / (root)
         * Redirects to /login page as default landing.
         * Users must authenticate before accessing protected content.
         */}
        <Route path="/" element={<Navigate to="/login" replace />} />
        
        {/* 
         * Catch-All Route: 404 Not Found
         * Path: * (any unmatched route)
         * Shows a 404 error page for invalid URLs.
         */}
        <Route path="*" element={
          <div className="flex items-center justify-center h-screen">
            <div className="text-center">
              <h1 className="text-4xl font-bold mb-4">404</h1>
              <p className="text-gray-600">Página no encontrada</p>
            </div>
          </div>
        } />
      </Routes>
    </>
  )
}

export default App
