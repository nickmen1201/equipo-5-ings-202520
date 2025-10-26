/**
 * Login Page Component (REQ-001)
 * 
 * Authenticates users (ADMIN/PRODUCTOR) via email/password:
 * - Validates credentials via backend API
 * - Stores JWT token in localStorage
 * - Routes user based on role (ADMIN → /admin, PRODUCTOR → /dashboard)
 * - Shows loading state and error messages in Spanish
 */

import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Login() {
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);

    const navigate = useNavigate();
    const { login } = useAuth();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError(null);

        try {
            const user = await login(email, password);
            
            // Login successful! User object contains { email, role }
            // Route user to appropriate dashboard based on their role
            
            if (user.role === 'ADMIN') {
                // ADMIN users go to admin dashboard
                navigate('/admin');
            } else if (user.role === 'PRODUCTOR') {
                // PRODUCTOR users go to producer dashboard  
                navigate('/home');
            } else {
                // Fallback: Unknown role (should not happen)
                // Route to generic home page
                navigate('/home');
            }
        }
        catch (err) {
            // Login failed: Network error, invalid credentials, disabled account, etc.
            // Display error message to user in Spanish
            setError(err.message || 'Error al iniciar sesión');
        }
        finally {
            // Always reset loading state after attempt (success or failure)
            // Re-enables button and changes text back to "Iniciar"
            setLoading(false);
        }
    }

    /**
     * Registration button handler - Routes to registration page.
     * 
     * This function is called when user clicks "Registrarse" button.
     * Note: Registration is NOT implemented in REQ-001, this just sets up
     * the routing for future implementation.
     * 
     * @returns {void}
     */
    const handleRegister = () => {
        navigate('/register');
    }

    // === Component Render ===
    
    return (
        /* Main container - Full screen height, centered content, light gray background */
        <div className="flex items-center justify-center h-screen bg-gray-50">
            {/* 
             * Card container - White background, rounded corners, shadow
             * Split layout: Logo left (50%), Form right (50%)
             * Max width: 700px, responsive
             */}
            <div className="flex bg-white rounded-2xl shadow-lg overflow-hidden w-[700px] max-w-full">
                
                {/* 
                 * LEFT SECTION: Branding Area
                 * Gray background with CultivApp logo and name
                 * Provides visual identity and balance to the form
                 */}
                <div className="flex flex-col items-center justify-center w-1/2 bg-gray-50 p-8">
                    <div className="flex flex-col items-center">
                        {/* 
                         * Logo Container
                         * Green circular background (#60C37B - brand color)
                         * Contains plant emoji representing agricultural focus
                         * Rounded corners (rounded-2xl) for modern look
                         */}
                        <div className="bg-[#60C37B] text-white rounded-2xl w-16 h-16 flex items-center justify-center mb-3">
                            <span className="text-3xl font-bold">🌱</span>
                        </div>
                        
                        {/* 
                         * App Name
                         * CultivApp branding text below logo
                         * Semibold weight, gray color for professional look
                         */}
                        <h1 className="text-gray-700 font-semibold text-lg">CultivApp</h1>
                    </div>
                </div>

                {/* 
                 * RIGHT SECTION: Login Form
                 * White background with email/password inputs and buttons
                 * Implements REQ-001 authentication UI
                 */}
                <div className="w-1/2 p-10">
                    {/* 
                     * Form Title
                     * "Iniciar Sesión" (Start Session) in Spanish per requirements
                     * Centered, large, dark text for clear hierarchy
                     */}
                    <h2 className="text-2xl font-semibold mb-6 text-gray-800 text-center">
                        Iniciar Sesión
                    </h2>

                    {/* 
                     * Login Form
                     * Handles user authentication for REQ-001
                     * Submits on Enter key or "Iniciar" button click
                     * space-y-4 adds consistent vertical spacing between elements
                     */}
                    <form className="space-y-4" onSubmit={handleSubmit}>
                        {/* 
                         * Error Message Display
                         * Only shown when error state is not null
                         * Red text for high visibility
                         * Displays Spanish error messages from backend
                         */}
                        {error && (
                            <p 
                                className="text-red-500 text-sm"
                                role="alert" // Accessibility: Announces errors to screen readers
                                aria-live="polite" // Accessibility: Polite announcement (not interrupt)
                            >
                                {error}
                            </p>
                        )}

                        {/* 
                         * Email Input Field
                         * Controlled component: value from state, updates via onChange
                         * type="email" provides browser validation for email format
                         * required attribute ensures field must be filled
                         * Placeholder: "email" (lowercase, minimal design)
                         * Focus state: Green ring (#60C37B) matches brand color
                         */}
                        <div className="flex flex-col">
    <label
      htmlFor="email"
      className="text-gray-700 font-medium mb-1"
    >
      Correo electrónico
    </label>
    <input
      id="email"
      type="email"
      value={email}
      onChange={(e) => setEmail(e.target.value)}
      className="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-green-400"
      required
      aria-label="Correo electrónico"
      autoComplete="email"
    />
  </div>

  {/* Password Input */}
  <div className="flex flex-col">
    <label
      htmlFor="password"
      className="text-gray-700 font-medium mb-1"
    >
      Contraseña
    </label>
    <input
      id="password"
      type="password"
      value={password}
      onChange={(e) => setPassword(e.target.value)}
      className="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-green-400"
      required
      aria-label="Contraseña"
      autoComplete="current-password"
    />
  </div>

                        {/* 
                         * Submit Button - "Iniciar" (Start/Login)
                         * Primary action button in brand green color (#60C37B)
                         * type="submit" triggers form submission (calls handleSubmit)
                         * disabled={loading} prevents multiple submissions
                         * Shows "Cargando..." (Loading...) when loading=true
                         * Shows "Iniciar" (Login) when loading=false
                         * Hover effect: Darker green (#4CAF68)
                         * Disabled state: 50% opacity for visual feedback
                         */}
                        <button
                            type="submit"
                            disabled={loading}
                            className="w-full bg-[#60C37B] text-white py-2 rounded-lg hover:bg-[#4CAF68] transition disabled:opacity-50 disabled:cursor-not-allowed"
                            aria-busy={loading} // Accessibility: Indicates loading state
                        >
                            {loading ? "Cargando..." : "Iniciar"}
                        </button>

                        {/* 
                         * Registration Button - "Registrarse" (Register)
                         * Secondary action button with gray border
                         * type="button" prevents form submission
                         * Routes to /register page (not implemented in REQ-001)
                         * Hover effect: Light gray background
                         * Provides clear call-to-action for new users
                         */}
                        <button
                            type="button"
                            onClick={handleRegister}
                            className="w-full border border-gray-300 text-gray-700 py-2 rounded-lg hover:bg-gray-100 transition"
                        >
                            Registrarse
                        </button>
                    </form>
                </div>
            </div>
        </div>
    );
}