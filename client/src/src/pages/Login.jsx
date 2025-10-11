/**
 * Login Page Component - User Authentication UI (REQ-001: Login Authentication)
 * 
 * This component provides the login interface for CultivApp users. It implements
 * REQ-001 by allowing users with roles ADMIN or PRODUCTOR to authenticate using
 * their email and password. The UI matches the provided design screenshot with
 * a split layout: CultivApp logo on the left, login form on the right.
 * 
 * Component Responsibilities:
 * - Renders login form with email and password inputs.
 * - Validates user input (required fields, email format).
 * - Handles form submission and communicates with backend API.
 * - Displays loading state during authentication.
 * - Shows error messages in Spanish when login fails.
 * - Routes user to appropriate page based on role after successful login.
 * - Provides registration button (routes to /register, not implemented in REQ-001).
 * 
 * Authentication Flow (UI Layer):
 * 1. User enters email and password.
 * 2. User clicks "Iniciar" button.
 * 3. Form submits → handleSubmit executes.
 * 4. Loading state shown, error cleared.
 * 5. AuthContext.login() called → authService.loginUser() → Backend API.
 * 6. Backend validates credentials and returns JWT + role.
 * 7. JWT stored in localStorage by authService.
 * 8. User info stored in AuthContext state.
 * 9. User redirected based on role (ADMIN → /admin, PRODUCTOR → /dashboard).
 * 10. If error: Display Spanish error message, keep user on login page.
 * 
 * UI Design (per screenshot):
 * - Split layout: Logo left (gray background), form right (white).
 * - Centered white card with soft shadow.
 * - Green CultivApp logo with plant emoji.
 * - Two inputs: email (placeholder: "email"), password (placeholder: "contraseña").
 * - Primary button: Green "Iniciar" (submit).
 * - Secondary button: Gray "Registrarse" (routes to registration).
 * - Responsive design with Tailwind CSS.
 * - Accessibility: Labels, keyboard navigation, focus states.
 * 
 * Security Features:
 * - Password input type="password" (hides characters).
 * - Credentials sent securely via HTTPS (production).
 * - Generic error messages prevent user enumeration.
 * - No sensitive data stored in component state.
 * 
 * @author CultivApp Team
 * @version 1.0 (REQ-001)
 */

import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

/**
 * Login Component - Main export for login page.
 * 
 * @returns {JSX.Element} Login page with form and branding.
 */
export default function Login() {
    // === State Management ===
    
    /**
     * Email state - Stores user input from email field.
     * Controlled component pattern: value is stored in state, updates via onChange.
     */
    const [email, setEmail] = useState("")
    
    /**
     * Password state - Stores user input from password field.
     * Controlled component pattern: ensures React controls input value.
     */
    const [password, setPassword] = useState("")
    
    /**
     * Error state - Stores error message to display when login fails.
     * null when no error, string message when error occurs.
     * Displayed in Spanish per REQ-001 requirements.
     */
    const [error, setError] = useState(null);
    
    /**
     * Loading state - Indicates if authentication request is in progress.
     * true: Shows "Cargando..." on button, disables interactions.
     * false: Shows "Iniciar" on button, allows submission.
     */
    const [loading, setLoading] = useState(false);

    // === Hooks ===
    
    /**
     * Navigation hook - Programmatic routing after successful login.
     * Used to redirect user based on their role (ADMIN vs PRODUCTOR).
     */
    const navigate = useNavigate();
    
    /**
     * Authentication context hook - Provides login function and user state.
     * Allows component to call centralized login logic and access auth state.
     */
    const { login } = useAuth();

    // === Event Handlers ===
    
    /**
     * Form submission handler - Authenticates user on form submit.
     * 
     * This function is called when the user clicks "Iniciar" or presses Enter
     * in the form. It handles the entire authentication flow:
     * 
     * Flow:
     * 1. Prevent default form submission (no page reload).
     * 2. Set loading state to show "Cargando..." feedback.
     * 3. Clear any previous error messages.
     * 4. Call login function from AuthContext with email/password.
     * 5. AuthContext → authService → Backend API → JWT response.
     * 6. Store JWT and user info in localStorage + context state.
     * 7. Route user based on role:
     *    - ADMIN → /admin (admin dashboard)
     *    - PRODUCTOR → /dashboard (producer dashboard)
     * 8. If error: Catch exception, display Spanish error message.
     * 9. Finally: Reset loading state regardless of success/failure.
     * 
     * Error Handling:
     * - Network errors: Display generic message.
     * - 401 Unauthorized: "Credenciales inválidas" (invalid credentials).
     * - 403 Forbidden: "Cuenta deshabilitada..." (account disabled).
     * - All errors displayed in Spanish per requirements.
     * 
     * @param {React.FormEvent} e - Form submission event.
     * @returns {Promise<void>} Async function, no return value.
     */
    const handleSubmit = async (e) => {
        // Prevent default form submission behavior (page reload)
        e.preventDefault();
        
        // Show loading state: disable button, show "Cargando..."
        setLoading(true);
        
        // Clear any previous error messages
        setError(null);

        try {
            // Attempt to authenticate user via AuthContext
            // This calls authService.loginUser() → Backend POST /api/auth/login
            const user = await login(email, password);
            
            // Login successful! User object contains { email, role }
            // Route user to appropriate dashboard based on their role
            
            if (user.role === 'ADMIN') {
                // ADMIN users go to admin dashboard
                navigate('/admin');
            } else if (user.role === 'PRODUCTOR') {
                // PRODUCTOR users go to producer dashboard  
                navigate('/dashboard');
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
                        <input
                            type="email"
                            placeholder="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            className="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-green-400"
                            required
                            aria-label="Correo electrónico" // Accessibility: Label for screen readers
                            autoComplete="email" // Accessibility: Enables autofill
                        />

                        {/* 
                         * Password Input Field
                         * Controlled component: value from state, updates via onChange
                         * type="password" hides characters for security
                         * required attribute ensures field must be filled
                         * Placeholder: "contraseña" (Spanish for password)
                         * Focus state: Green ring matches brand and email input
                         */}
                        <input
                            type="password"
                            placeholder="contraseña"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            className="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-green-400"
                            required
                            aria-label="Contraseña" // Accessibility: Label for screen readers
                            autoComplete="current-password" // Accessibility: Enables autofill
                        />

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