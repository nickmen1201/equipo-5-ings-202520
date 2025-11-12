import React, { useState, useEffect } from 'react'
import { useAuth } from '../context/AuthContext'
import { Link, useNavigate } from 'react-router-dom'
import { FaArrowLeft } from 'react-icons/fa'

export default function CropForm() {
  const { user } = useAuth() // obtenemos el usuario actual (para incluir su id)
  const navigate = useNavigate()

  const [formData, setFormData] = useState({
    especieId: '',
    nombre: '',
    areaHectareas: '',
    etapaActual: '',
    estado: 'ACTIVO',
    rendimientoKg: ''
  })

  const [especies, setEspecies] = useState([])
  const [message, setMessage] = useState(null)
  const [loading, setLoading] = useState(true)

  // Fetch especies when component mounts
  useEffect(() => {
    const fetchEspecies = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await fetch('http://localhost:8080/api/especies', {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });
        if (!response.ok) {
          throw new Error('Error al obtener las especies')
        }
        const data = await response.json()
        setEspecies(data)
      } catch (error) {
        console.error('Error fetching especies:', error)
        setMessage('Error al cargar las especies')
      } finally {
        setLoading(false)
      }
    }

    fetchEspecies()
  }, [])

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
      usuarioId: parseInt(user.id),
      especieId: parseInt(formData.especieId),
      nombre: formData.nombre,
      areaHectareas: parseFloat(formData.areaHectareas),
      etapaActual: formData.etapaActual || null,
      estado: formData.estado || 'ACTIVO',
      rendimientoKg: formData.rendimientoKg ? parseFloat(formData.rendimientoKg) : null
    }

    try {
      console.log('Creating cultivo with payload:', payload);
      const token = localStorage.getItem('token');
      console.log('Token exists:', !!token);
      console.log('Token value:', token ? token.substring(0, 20) + '...' : 'NO TOKEN');
      
      const response = await fetch('http://localhost:8080/api/cultivos', {
        method: 'POST',
        headers: { 
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(payload)
      })

      console.log('Response status:', response.status);
      
      if (!response.ok) {
        const errorText = await response.text();
        console.error('Error creating cultivo:', errorText);
        throw new Error('Error al crear el cultivo')
      }

      const result = await response.json();
      console.log('Created cultivo:', result);

      setMessage('Cultivo creado exitosamente!')
      setFormData({
        especieId: '',
        nombre: '',
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

  if (loading) {
    return (
      <div className="flex items-center justify-center h-screen">
        <p className="text-gray-600">Cargando especies...</p>
      </div>
    )
  }

  return (
  <div className="max-w-md mx-auto  from-green-50 to-white shadow-2xl rounded-3xl p-8 mt-10 border border-green-100">
    {/* 🔙 Botón volver */}
    <Link
      to="/cultivos"
      className="flex items-center text-gray-600 hover:text-green-700 transition mb-4"
    >
      <FaArrowLeft className="mr-2" />
      Volver
    </Link>

    {/* 🌾 Título */}
    <h2 className="text-3xl font-bold mb-6 text-green-700 text-center">
      Nuevo Cultivo
    </h2>

    {/* 🌱 Formulario */}
    <form
      onSubmit={handleSubmit}
      className="flex flex-col gap-5"
    >
      {/* Nombre */}
      <div className="flex flex-col">
        <label className="text-sm font-medium text-gray-600 mb-1">
          Nombre del cultivo
        </label>
        <input
          type="text"
          name="nombre"
          value={formData.nombre}
          onChange={handleChange}
          placeholder="Ej: Palmeras, Tomates..."
          className="border border-gray-300 focus:border-green-500 focus:ring-2 focus:ring-green-200 p-3 rounded-lg outline-none transition"
          required
        />
      </div>

      {/* Especie */}
      <div className="flex flex-col">
        <label className="text-sm font-medium text-gray-600 mb-1">
          Especie
        </label>
        <select
          name="especieId"
          value={formData.especieId}
          onChange={handleChange}
          className="border border-gray-300 focus:border-green-500 focus:ring-2 focus:ring-green-200 p-3 rounded-lg outline-none transition appearance-none"
          required
        >
          <option value="">Seleccionar especie</option>
          {especies.map((especie) => (
            <option key={especie.id} value={especie.id}>
              {especie.nombre}
            </option>
          ))}
        </select>
      </div>

      {/* Área */}
      <div className="flex flex-col">
        <label className="text-sm font-medium text-gray-600 mb-1">
          Área (hectáreas)
        </label>
        <input
          type="number"
          step="0.01"
          name="areaHectareas"
          value={formData.areaHectareas}
          onChange={handleChange}
          placeholder="Ej: 5.5"
          className="border border-gray-300 focus:border-green-500 focus:ring-2 focus:ring-green-200 p-3 rounded-lg outline-none transition"
          min="0.01"
          required
        />
      </div>

      {/* Botón enviar */}
      <button
        type="submit"
        className="bg-green-600 hover:bg-green-700 text-white font-semibold py-3 rounded-lg shadow-md hover:shadow-lg transition transform hover:-translate-y-0.5"
      >
        Crear Cultivo
      </button>
    </form>

    {/* Mensaje */}
    {message && (
      <p className="text-center mt-5 text-sm text-gray-700 bg-green-50 border border-green-100 p-3 rounded-lg">
        {message}
      </p>
    )}
  </div>
);

}
