/**
 * Authentication Service - Frontend API Layer (REQ-001: Login Authentication)
 * 
 * This service handles all authentication-related API calls from the frontend to the backend.
 * It provides a clean interface for the UI components to perform login operations without
 * worrying about HTTP details, error handling, or token management.
 * 
 * Service Layer Responsibilities:
 * - Makes HTTP requests to the backend authentication API.
 * - Handles request/response serialization (JSON).
 * - Manages JWT token storage in browser localStorage.
 * - Provides consistent error handling and user-friendly error messages.
 * - Abstracts API details from UI components (separation of concerns).
 * 
 * REQ-001 Integration:
 * - Communicates with backend POST /api/auth/login endpoint.
 * - Sends user credentials (email/password) and receives JWT token.
 * - Stores JWT token locally for use in subsequent authenticated requests.
 * 
 * Security Considerations:
 * - Credentials are sent over HTTPS in production (http in development).
 * - JWT token is stored in localStorage (persistent across browser sessions).
 * - Token is included in Authorization header for protected API requests.
 * 
 * @author CultivApp Team
 * @version 1.0 (REQ-001)
 */

/**
 * Backend API base URL for authentication endpoints.
 * 
 * Points to the Spring Boot backend running on localhost:8080.
 * In production, this should be replaced with the actual backend URL
 * (e.g., https://api.cultivapp.com) using environment variables.
 * 
 * Configuration Options:
 * - Development: http://localhost:8080
 * - Production: Should use HTTPS and actual domain
 * - Can be made configurable via environment variables (Vite: import.meta.env)
 */
const API_URL = "http://localhost:8080/api/auth/login";

/**
 * Authenticates a user with email and password, returns JWT token.
 * 
 * This function implements the frontend side of REQ-001 login. It sends
 * the user's credentials to the backend, receives a JWT token if successful,
 * and stores it for future use.
 * 
 * Login Flow (Frontend → Backend):
 * 1. User submits login form with email/password.
 * 2. UI component calls this function.
 * 3. Function sends POST request to backend /api/auth/login.
 * 4. Backend validates credentials and returns JWT + role.
 * 5. Function stores JWT in localStorage.
 * 6. Function returns response data to UI component.
 * 7. UI component routes user based on their role.
 * 
 * HTTP Request Details:
 * - Method: POST
 * - URL: http://localhost:8080/api/auth/login
 * - Headers: Content-Type: application/json
 * - Body: { "email": "user@example.com", "password": "password123" }
 * 
 * HTTP Response (Success - 200 OK):
 * - Body: { "token": "eyJhbGci...", "role": "ADMIN" }
 * - Token is stored in localStorage with key "token".
 * - Role is returned to caller for routing decisions.
 * 
 * HTTP Response (Failure):
 * - 401 Unauthorized: Invalid email or password.
 * - 403 Forbidden: Account is disabled.
 * - 500 Internal Server Error: Backend error.
 * 
 * Error Handling:
 * - Network errors (backend down) throw generic error.
 * - HTTP errors (4xx, 5xx) throw descriptive error with Spanish message.
 * - Errors are caught by UI component and displayed to user.
 * 
 * Token Storage:
 * - JWT is stored in browser localStorage (key: "token").
 * - localStorage persists across browser sessions (until cleared).
 * - Token is automatically sent with future API requests via Authorization header.
 * - Alternative: sessionStorage (clears on browser close) for higher security.
 * 
 * @param {string} email - The user's email address (used as username).
 * @param {string} password - The user's plain-text password (sent securely via HTTPS in production).
 * @returns {Promise<Object>} Promise that resolves to response data containing:
 *                           - token: JWT token string for authentication
 *                           - role: User's role (ADMIN or PRODUCTOR) for authorization
 * @throws {Error} Throws error with Spanish message if login fails:
 *                - "Credenciales inválidas" for 401/403 errors
 *                - Generic error message for network/server errors
 * 
 * Usage Example:
 * ```javascript
 * try {
 *   const { token, role } = await loginUser("admin@cultivapp.com", "Admin123!");
 *   console.log("Login successful, token:", token);
 *   console.log("User role:", role);
 *   // Route user based on role...
 * } catch (error) {
 *   console.error("Login failed:", error.message);
 *   // Display error to user...
 * }
 * ```
 */
export async function loginUser(email, password) {
  try {
    // Send POST request to backend login endpoint
    const response = await fetch(API_URL, {
      method: "POST", // HTTP POST method for login
      headers: { 
        "Content-Type": "application/json" // Tell server we're sending JSON
      },
      body: JSON.stringify({ email, password }) // Convert credentials to JSON string
    });

    // Check if response status is not OK (2xx)
    if (!response.ok) {
      // Backend returned error (401, 403, 500, etc.)
      // Throw generic error message in Spanish for user display
      throw new Error("Credenciales inválidas");
    }

    // Parse JSON response from backend
    const data = await response.json();
    // Expected format: { token: "eyJhbGci...", role: "ADMIN" }

    // Store JWT token in browser localStorage for future authenticated requests
    // Key: "token" - Used to retrieve token when making protected API calls
    // Value: JWT token string from backend
    localStorage.setItem("token", data.token);

    // Return response data (token + role) to caller (UI component)
    // UI component will use role for routing decisions
    return data;
  } catch (error) {
    // Re-throw error to be handled by UI component
    // UI will display error message to user
    throw error;
  }
}

/**
 * Logs out the current user by removing the JWT token.
 * 
 * This function clears the authentication token from localStorage,
 * effectively logging the user out. After calling this, any subsequent
 * API requests will fail authentication.
 * 
 * Logout Flow:
 * 1. UI component calls this function (e.g., user clicks "Logout" button).
 * 2. Function removes JWT token from localStorage.
 * 3. UI component redirects user to login page.
 * 
 * Note: This is a client-side logout only. The JWT token itself remains
 * valid until it expires. For a complete logout, backend token blacklisting
 * would be needed (not implemented in REQ-001).
 * 
 * Usage Example:
 * ```javascript
 * logoutUser();
 * // Redirect to login page
 * window.location.href = "/login";
 * ```
 */
export function logoutUser() {
  // Remove JWT token from localStorage
  localStorage.removeItem("token");
}

/**
 * Retrieves the stored JWT token from localStorage.
 * 
 * This function is useful for:
 * - Checking if a user is logged in (token exists).
 * - Getting the token to include in Authorization headers for protected API calls.
 * - Validating token expiration before making requests.
 * 
 * @returns {string|null} The JWT token string if logged in, null if not logged in.
 * 
 * Usage Example:
 * ```javascript
 * const token = getToken();
 * if (token) {
 *   // User is logged in, make authenticated request
 *   fetch("/api/protected-endpoint", {
 *     headers: { "Authorization": `Bearer ${token}` }
 *   });
 * } else {
 *   // User is not logged in, redirect to login
 *   window.location.href = "/login";
 * }
 * ```
 */
export function getToken() {
  return localStorage.getItem("token");
}

/**
 * Checks if a user is currently logged in.
 * 
 * Determines login status by checking if a JWT token exists in localStorage.
 * This is a simple check and doesn't validate if the token is expired or valid.
 * 
 * @returns {boolean} True if a token exists (user is logged in), false otherwise.
 * 
 * Usage Example:
 * ```javascript
 * if (isAuthenticated()) {
 *   // Show logged-in UI
 * } else {
 *   // Show login UI or redirect to login page
 * }
 * ```
 */
export function isAuthenticated() {
  return !!getToken(); // Convert to boolean: null/undefined -> false, string -> true
}
