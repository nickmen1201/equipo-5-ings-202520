/**
 * Authentication Context - Global State Management (REQ-001: Login Authentication)
 * 
 * This context provides authentication state and functions throughout the React
 * application. It uses React Context API to avoid prop drilling and provide
 * a centralized authentication state management solution.
 * 
 * Context Responsibilities:
 * - Stores current user's authentication status (logged in or not).
 * - Provides user information (email, role) to all components.
 * - Offers login/logout functions that can be called from any component.
 * - Persists authentication state across page reloads using localStorage.
 * 
 * Benefits of Using Context:
 * - Avoids passing auth props through multiple component levels (prop drilling).
 * - Provides single source of truth for authentication state.
 * - Makes authentication state easily accessible from any component.
 * - Simplifies code by centralizing auth logic.
 * 
 * REQ-001 Integration:
 * - Wraps the entire application to provide auth state globally.
 * - Components can access current user and login status via useAuth hook.
 * - Login component uses context to store user info after successful login.
 * - Protected routes use context to check if user is authenticated.
 * 
 * @author CultivApp Team
 * @version 1.0 (REQ-001)
 */

import React, { createContext, useContext, useState, useEffect } from 'react';
import { loginUser as loginUserService, logoutUser as logoutUserService, getToken } from '../services/authService';

/**
 * Authentication Context - Provides auth state to entire app.
 * 
 * Contains:
 * - user: Current user object { email, role } or null if not logged in.
 * - login: Function to authenticate user and store credentials.
 * - logout: Function to clear authentication and redirect to login.
 * - loading: Boolean indicating if auth state is being initialized.
 */
const AuthContext = createContext(null);

/**
 * Authentication Provider Component - Wraps app to provide auth context.
 * 
 * This component manages the authentication state for the entire application.
 * It should wrap the root of your app (typically in main.jsx or App.jsx).
 * 
 * State Management:
 * - user: Stores current user's email and role, null if not logged in.
 * - loading: True while checking for existing token on mount, false after.
 * 
 * Initialization Flow (on page load):
 * 1. Component mounts and checks localStorage for existing JWT token.
 * 2. If token exists, extracts user info and sets user state (auto-login).
 * 3. If no token, user state remains null (not logged in).
 * 4. Sets loading to false to signal initialization is complete.
 * 
 * Note: This implementation extracts user info from localStorage.
 * For production, consider decoding the JWT token to get user info,
 * or making an API call to fetch current user details.
 * 
 * @param {Object} props - Component props.
 * @param {React.ReactNode} props.children - Child components to wrap.
 * @returns {JSX.Element} Provider component wrapping children.
 * 
 * Usage Example:
 * ```jsx
 * // In main.jsx or App.jsx
 * <AuthProvider>
 *   <App />
 * </AuthProvider>
 * ```
 */
export function AuthProvider({ children }) {
  // State: Current user object or null if not logged in
  const [user, setUser] = useState(null);
  
  // State: Loading flag for initial auth check
  const [loading, setLoading] = useState(true);

  /**
   * Effect Hook - Checks for existing authentication on component mount.
   * 
   * This effect runs once when the app first loads. It checks if a JWT
   * token exists in localStorage (from a previous login). If found, it
   * restores the user's authentication state, allowing them to remain
   * logged in across page reloads.
   * 
   * Flow:
   * 1. Check localStorage for JWT token.
   * 2. If token exists, retrieve stored user info (email, role).
   * 3. Set user state to restore logged-in status.
   * 4. Set loading to false to indicate initialization is complete.
   * 
   * Security Note: In production, consider validating the token with
   * the backend to ensure it hasn't been invalidated or expired.
   */
  useEffect(() => {
    // Check if a JWT token exists in localStorage
    const token = getToken();
    
    if (token) {
      // Token exists - user was previously logged in
      // Restore user info from localStorage
      const storedUser = localStorage.getItem('user');
      
      if (storedUser) {
        // Parse and set user state to restore authentication
        setUser(JSON.parse(storedUser));
      }
    }
    
    // Mark initialization as complete
    setLoading(false);
  }, []); // Empty dependency array = run once on mount

  /**
   * Logs in a user with email and password.
   * 
   * This function is called by the Login component when the user submits
   * the login form. It calls the authentication service to validate
   * credentials with the backend, then stores the user info in state
   * and localStorage for persistence.
   * 
   * Login Flow:
   * 1. Call loginUserService (authService.js) to authenticate with backend.
   * 2. Backend validates credentials and returns JWT + role.
   * 3. authService stores JWT in localStorage (key: "token").
   * 4. Create user object with email and role.
   * 5. Store user object in component state (triggers re-render).
   * 6. Store user object in localStorage (for persistence across reloads).
   * 7. Return user object to caller for routing decisions.
   * 
   * State Changes:
   * - Sets user state to { email, role }, triggering re-render of all
   *   components that consume AuthContext.
   * - Components can now access current user via useAuth() hook.
   * 
   * @param {string} email - User's email address (username).
   * @param {string} password - User's plain-text password.
   * @returns {Promise<Object>} Promise resolving to user object { email, role }.
   * @throws {Error} If login fails (invalid credentials, network error, etc.).
   * 
   * Usage Example:
   * ```jsx
   * const { login } = useAuth();
   * 
   * try {
   *   const user = await login("admin@cultivapp.com", "Admin123!");
   *   // Redirect based on user.role...
   * } catch (error) {
   *   // Display error message...
   * }
   * ```
   */
  const login = async (email, password) => {
    // Call authentication service to validate credentials with backend
    const data = await loginUserService(email, password);
    
    // Create user object from backend response
    const userData = {
      email: email,
      role: data.role // ADMIN or PRODUCTOR
    };
    
    // Update state - triggers re-render of components using AuthContext
    setUser(userData);
    
    // Persist user info in localStorage for auto-login on page reload
    localStorage.setItem('user', JSON.stringify(userData));
    
    // Return user data to caller (Login component) for routing
    return userData;
  };

  /**
   * Logs out the current user.
   * 
   * This function clears all authentication data from state and localStorage,
   * effectively logging the user out. After logout, the user is redirected
   * to the login page.
   * 
   * Logout Flow:
   * 1. Call logoutUserService to remove JWT from localStorage.
   * 2. Remove user info from localStorage.
   * 3. Set user state to null (triggers re-render as logged out).
   * 4. Redirect to login page.
   * 
   * Note: This is a client-side logout only. The JWT token remains valid
   * on the backend until it expires. For complete logout, backend token
   * invalidation would be needed (not implemented in REQ-001).
   * 
   * Usage Example:
   * ```jsx
   * const { logout } = useAuth();
   * 
   * <button onClick={logout}>Cerrar Sesión</button>
   * ```
   */
  const logout = () => {
    // Remove JWT token from localStorage
    logoutUserService();
    
    // Remove user info from localStorage
    localStorage.removeItem('user');
    
    // Clear user state - triggers re-render as logged out
    setUser(null);
    
    // Redirect to login page
    window.location.href = '/login';
  };

  /**
   * Context value object provided to all children components.
   * 
   * This object is accessible via useAuth() hook in any component
   * wrapped by AuthProvider.
   * 
   * Properties:
   * - user: Current user object { email, role } or null if not logged in.
   * - login: Function to authenticate user (email, password) => Promise<user>.
   * - logout: Function to clear authentication and redirect to login.
   * - loading: Boolean indicating if initial auth check is in progress.
   */
  const value = {
    user,      // Current user or null
    login,     // Login function
    logout,    // Logout function
    loading    // Loading state
  };

  // Render provider with value, wrapping all children
  // Children can access auth state via useAuth() hook
  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
}

/**
 * Custom Hook - Access authentication context from any component.
 * 
 * This hook provides a convenient way to access the authentication context
 * from any component within the AuthProvider tree. It ensures the component
 * is properly wrapped by AuthProvider.
 * 
 * Returns:
 * - user: Current user object { email, role } or null.
 * - login: Function to authenticate user.
 * - logout: Function to log out user.
 * - loading: Boolean indicating initialization state.
 * 
 * Error Handling:
 * - Throws error if used outside AuthProvider (developer error).
 * - Ensures components can't access auth context without proper setup.
 * 
 * @returns {Object} Authentication context value.
 * @throws {Error} If used outside of AuthProvider.
 * 
 * Usage Example:
 * ```jsx
 * function MyComponent() {
 *   const { user, login, logout, loading } = useAuth();
 *   
 *   if (loading) return <div>Loading...</div>;
 *   
 *   if (!user) {
 *     return <div>Please log in</div>;
 *   }
 *   
 *   return (
 *     <div>
 *       <p>Welcome, {user.email}!</p>
 *       <p>Role: {user.role}</p>
 *       <button onClick={logout}>Logout</button>
 *     </div>
 *   );
 * }
 * ```
 */
export function useAuth() {
  // Get context value
  const context = useContext(AuthContext);
  
  // Ensure component is wrapped by AuthProvider
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  
  return context;
}
