import React, { useState } from "react";
import { loginUser, registerUser } from "../services/authService";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function Register() {

   const { login } = useAuth();

  const navigate = useNavigate();
  const [nombre, setNombre] = useState("");
  const [apellido, setApellido] = useState("");
  const [email, setEmail] = useState("");
  const [ciudad, setCiudad] = useState("");
  const [password, setPassword] = useState("");
  const [confirm, setConfirm] = useState("");
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  // Validar en tiempo real mientras escriben
  const handleNombreChange = (e) => {
    const value = e.target.value;
    // Solo permitir letras y espacios
    if (/^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]*$/.test(value)) {
      setNombre(value);
    }
  };

  const handleApellidoChange = (e) => {
    const value = e.target.value;
    // Solo permitir letras y espacios
    if (/^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]*$/.test(value)) {
      setApellido(value);
    }
  };

  const handleCiudadChange = (e) => {
    const value = e.target.value;
    // Solo permitir letras, espacios y guiones
    if (/^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s\-]*$/.test(value)) {
      setCiudad(value);
    }
  };

  const handleSubmit = async (e) => {
  e.preventDefault();
  setLoading(true);
  setError(null);

  // Validar nombre (solo letras y espacios, mínimo 2 caracteres)
  const nameRegex = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]{2,50}$/;
  if (!nameRegex.test(nombre.trim())) {
    setError("El nombre debe contener solo letras y tener entre 2 y 50 caracteres");
    setLoading(false);
    return;
  }

  // Validar apellido (solo letras y espacios, mínimo 2 caracteres)
  if (!nameRegex.test(apellido.trim())) {
    setError("El apellido debe contener solo letras y tener entre 2 y 50 caracteres");
    setLoading(false);
    return;
  }

  // Validar ciudad (letras, espacios y guiones, mínimo 2 caracteres)
  const cityRegex = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s\-]{2,50}$/;
  if (!cityRegex.test(ciudad.trim())) {
    setError("La ciudad debe contener solo letras y tener entre 2 y 50 caracteres");
    setLoading(false);
    return;
  }

  // Validar email (estructura básica)
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email.trim())) {
    setError("El correo electrónico no tiene una estructura válida");
    setLoading(false);
    return;
  }

  // Validar contraseña (mínimo 6 caracteres)
  if (password.length < 6) {
    setError("La contraseña debe tener mínimo 6 caracteres");
    setLoading(false);
    return;
  }

  // Validar que las contraseñas coincidan
  if (password !== confirm) {
    setError("Las contraseñas no coinciden");
    setLoading(false);
    return;
  }

  try {
    // Registrar el usuario
    await registerUser(nombre, apellido, email, password,ciudad);

    // Usar la función login del contexto (esta ya hace setUser y guarda en localStorage)
    const user = await login(email, password);
    console.log("Login automático exitoso:", user);

    // Redirigir según el rol que retorna login
    if (user.role === "ADMIN") navigate("/admin");
    else navigate("/home");

  } catch (err) {
    console.error(err);
    setError(err.message || "Error al registrarse");
  } finally {
    setLoading(false);
  }
};

  return (
    <div className="flex items-center justify-center h-screen bg-gray-50">
      <div className="flex bg-white rounded-2xl shadow-lg overflow-hidden w-[700px] max-w-full">
        
        {/* Lado Izquierdo */}
        <div className="flex flex-col items-center justify-center w-1/2 bg-gray-50 p-8">
          <div className="flex flex-col items-center">
            <div className="bg-[#60C37B] text-white rounded-2xl w-16 h-16 flex items-center justify-center mb-3">
              <span className="text-3xl font-bold">🌱</span>
            </div>
            <h1 className="text-gray-700 font-semibold text-lg">CultivApp</h1>
          </div>
        </div>

        {/* Lado Derecho */}
        <div className="w-1/2 p-10">
          <h2 className="text-2xl font-semibold mb-6 text-gray-800 text-center">
            Registrarse
          </h2>

          <form onSubmit={handleSubmit} className="space-y-4">
            {error && <p className="text-red-500 text-sm">{error}</p>}

            <input
              type="text"
              placeholder="Nombre"
              value={nombre}
              onChange={handleNombreChange}
              className="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-green-400"
              required
            />
            <input
              type="text"
              placeholder="Apellido"
              value={apellido}
              onChange={handleApellidoChange}
              className="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-green-400"
              required
            />
             <input
              type="text"
              placeholder="Ciudad"
              value={ciudad}
              onChange={handleCiudadChange}
              className="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-green-400"
              required
            />

            <input
              type="email"
              placeholder="Correo electrónico"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-green-400"
              required
            />

            <input
              type="password"
              placeholder="Contraseña"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-green-400"
              required
            />

            <input
              type="password"
              placeholder="Confirmar contraseña"
              value={confirm}
              onChange={(e) => setConfirm(e.target.value)}
              className="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-green-400"
              required
            />

            <button
              type="submit"
              disabled={loading}
              className="w-full bg-[#60C37B] text-white py-2 rounded-lg hover:bg-[#4CAF68] transition disabled:opacity-50"
            >
              {loading ? "Registrando..." : "Registrarse"}
            </button>

            <button
              type="button"
              onClick={() => (window.location.href = "/login")}
              className="w-full border border-gray-300 text-gray-700 py-2 rounded-lg hover:bg-gray-100 transition"
            >
              Ya tengo una cuenta
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}