import React, { useState } from "react";
import { registerUser } from "../services/authService";

export default function Register() {
  const [nombre, setNombre] = useState("");
  const [apellido, setApellido] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirm, setConfirm] = useState("");
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

 const handleSubmit = async (e) => {
    e.preventDefault(); 
    setLoading(true);
    setError(null);

    try {
      const data = await registerUser(nombre, apellido, email, password);
      console.log("Usuario registrado:", data);
      window.location.href = "/login"; 
    } catch (err) {
      setError(err.message);
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
              onChange={(e) => setNombre(e.target.value)}
              className="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-green-400"
              required
            />
            <input
              type="text"
              placeholder="Apellido"
              value={apellido}
              onChange={(e) => setApellido(e.target.value)}
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