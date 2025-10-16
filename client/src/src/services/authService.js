const API_URL = "http://localhost:8080/api/auth";


export async function loginUser(email, password) {
  try {
    // Send POST request to backend login endpoint
    const response = await fetch(`${API_URL}/login`, {
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
    
    throw error;
  }
}

export function logoutUser() {
 
  localStorage.removeItem("token");
}


export function getToken() {
  return localStorage.getItem("token");
}

export function isAuthenticated() {
  return !!getToken(); 
}


export async function registerUser(nombre, apellido, email, password) {
  const response = await fetch(`${API_URL}/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ nombre, apellido, email, password }),
  });

  if (!response.ok) {
    
    const error = await response.json().catch(() => null);
    throw new Error(error?.message || "Error al registrar el usuario");
  }

  return await response.json();
}


