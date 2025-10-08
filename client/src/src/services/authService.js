const API_URL = "http://localhost:8080/api/auth/login"; // cambia según tu backend

export async function loginUser(email, password) {
  try {
    const response = await fetch(API_URL, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, password }),
    });

    if (!response.ok) {
      throw new Error("Credenciales inválidas");
    }

    const data = await response.json();
    localStorage.setItem("token", data.token); // guarda el JWT
    return data;
  } catch (error) {
    throw error;
  }
}
