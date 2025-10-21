import React, { useState } from 'react'
import { useAuth } from '../context/AuthContext'
import { useNavigate } from 'react-router-dom'

export default function CropForm() {
  const { user } = useAuth() // obtenemos el usuario actual (para incluir su id)
  const navigate = useNavigate()

  const [formData, setFormData] = useState({
    especieId: '',
    nombre: '',
    fechaSiembra: '',
    areaHectareas: '',
    etapaActual: '',
    estado: 'ACTIVO',
    rendimientoKg: ''
  })

  const [message, setMessage] = useState(null)

  // Maneja los cambios en los campos del formulario
  const handleChange = (e) => {
    const { name, value } = e.target
    setFormData((prev) => ({ ...prev, [name]: value }))
  }

  // Envía los datos al backend
  const handleSubmit = async (e) => {
    e.preventDefault()

    if (!user?.id) {
      alert('Usuario no autenticado')
      return
    }

    const payload = {
      usuario: { id: user.id },
      especie: { id: Number(formData.especieId) },
      nombre: formData.nombre,
      fechaSiembra: formData.fechaSiembra,
      areaHectareas: parseFloat(formData.areaHectareas),
      etapaActual: formData.etapaActual,
      estado: formData.estado,
      rendimientoKg: parseFloat(formData.rendimientoKg)
    }

    try {
      const response = await fetch('http://localhost:8080/api/cultivos', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      })

      if (!response.ok) {
        throw new Error('Error al crear el cultivo')
      }

      setMessage(' Cultivo creado exitosamente!')
      setFormData({
        especieId: '',
        nombre: '',
        fechaSiembra: '',
        areaHectareas: '',
        etapaActual: '',
        estado: 'ACTIVO',
        rendimientoKg: ''
      })

      navigate('/cultivos')

  

    } catch (error) {
      console.error(error)
      setMessage('Error al crear el cultivo')
    }
  }

  return (
    <div className="max-w-md mx-auto bg-white shadow-lg rounded-2xl p-6 mt-8">
      <h2 className="text-2xl font-semibold mb-4 text-green-700">Nuevo Cultivo</h2>
      <form onSubmit={handleSubmit} className="flex flex-col gap-4">
        <input
          type="text"
          name="nombre"
          value={formData.nombre}
          onChange={handleChange}
          placeholder="Nombre del cultivo"
          className="border p-2 rounded-md"
          required
        />

        <input
          type="number"
          name="especieId"
          value={formData.especieId}
          onChange={handleChange}
          placeholder="ID de la especie"
          className="border p-2 rounded-md"
          required
        />

        <input
          type="date"
          name="fechaSiembra"
          value={formData.fechaSiembra}
          onChange={handleChange}
          className="border p-2 rounded-md"
          required
        />

        <input
          type="number"
          step="0.01"
          name="areaHectareas"
          value={formData.areaHectareas}
          onChange={handleChange}
          placeholder="Área (hectáreas)"
          className="border p-2 rounded-md"
          required
        />

        <select
          name="etapaActual"
          value={formData.etapaActual}
          onChange={handleChange}
          className="border p-2 rounded-md"
          required
        >
          <option value="">Seleccionar etapa</option>
          <option value="GERMINACION">Germinación</option>
          <option value="CRECIMIENTO">Crecimiento</option>
          <option value="FLORACION">Floración</option>
          <option value="COSECHA">Cosecha</option>
        </select>

        <select
          name="estado"
          value={formData.estado}
          onChange={handleChange}
          className="border p-2 rounded-md"
          required
        >
          <option value="ACTIVO">Activo</option>
          <option value="INACTIVO">Inactivo</option>
        </select>

        <input
          type="number"
          step="0.01"
          name="rendimientoKg"
          value={formData.rendimientoKg}
          onChange={handleChange}
          placeholder="Rendimiento (kg)"
          className="border p-2 rounded-md"
        />

        <button
          type="submit"
          className="bg-green-600 hover:bg-green-700 text-white p-2 rounded-md transition"
        >
          Crear Cultivo
        </button>
      </form>

      {message && (
        <p className="text-center mt-4 text-sm text-gray-700">{message}</p>
      )}
    </div>
  )
}
