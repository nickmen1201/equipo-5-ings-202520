import React, { useEffect, useState } from 'react'
import NavBar from '../Components/NavBar'
import RuleBox from '../components/RuleBox'
import { createRegla, searchReglas, deleteRegla, getAllReglas, getTiposDeReglas } from "../services/ReglaService"
import { FaArrowLeft } from 'react-icons/fa'
import { Link } from 'react-router-dom'

export default function Rules() {
  const [rules, setRules] = useState([])
  const [tiposRegla, setTiposRegla] = useState([])

  const [reglaData, setReglaData] = useState({
    descripcion: '',
    tipo: '',
    intervaloDias: 1
  })

  const [message, setMessage] = useState(null)
  const [loading, setLoading] = useState(false)

  // ✅ Manejo de creación con validaciones y errores controlados
  const handleSubmit = async (e) => {
    e.preventDefault()
    setMessage(null)

    // Validación previa
    if (!reglaData.descripcion.trim() || reglaData.descripcion.length < 5) {
      setMessage({ type: 'error', text: 'La descripción debe tener al menos 5 caracteres.' })
      return
    }

    if (!reglaData.tipo) {
      setMessage({ type: 'error', text: 'Debes seleccionar un tipo de regla.' })
      return
    }

    setLoading(true)
    try {
      const created = await createRegla(reglaData)
      if (!created || !created.id) {
        throw new Error('Respuesta inválida del servidor.')
      }

      // Actualiza estado sin duplicar ni sobrescribir
      setRules(prev => [...prev, created])
      setMessage({ type: 'success', text: 'Regla creada correctamente.' })

      setTimeout(() => setMessage(null), 3000)

      // Reiniciar formulario
      setReglaData({ descripcion: '', tipo: tiposRegla[0] || '', intervaloDias: 1 })
    } catch (err) {
      console.error(' Error al crear regla:', err)
      setMessage({
        type: 'error',
        text: err.response?.data?.message || 'Ocurrió un error al crear la regla.'
      })
      setTimeout(() => setMessage(null), 3000)

    } finally {
      setLoading(false)
      
    }
  }

  const handleChange = (e) => {
    const { name, value } = e.target
    setReglaData((prev) => ({ ...prev, [name]: value }))
  }

  //  Eliminar con confirmación y manejo de errores
  const handleDelete = async (id) => {
    if (!window.confirm('¿Seguro que deseas eliminar esta regla?')) return

    try {
      await deleteRegla(id)
      setRules(prev => prev.filter(rule => rule.id !== id))
      setMessage({ type: 'success', text: 'Regla eliminada correctamente.' })
    } catch (err) {
      console.error('❌ Error al eliminar regla:', err)
      setMessage({
        type: 'error',
        text: err.response?.data?.message || 'Error al eliminar la regla.'
      })
    }
  }

  //  Filtro por tipo de regla
  const handleFilter = async (filtro) => {
    setLoading(true)
    try {
      const filtered = await searchReglas(filtro)
      setRules(filtered)
    } catch (err) {
      console.error(' Error al filtrar reglas:', err)
      setMessage({ type: 'error', text: 'Error al filtrar las reglas.' })
    } finally {
      setLoading(false)
    }
  }

  //  Mostrar todas las reglas
  const handleShowAll = async () => {
    setLoading(true)
    try {
      const allRules = await getAllReglas()
      setRules(allRules)
    } catch (err) {
      console.error(' Error al obtener todas las reglas:', err)
      setMessage({ type: 'error', text: 'No se pudieron cargar las reglas.' })
    } finally {
      setLoading(false)
    }
  }

  
  useEffect(() => {
    async function fetchReglas() {
      setLoading(true)
      try {
        const [data, dataTipos] = await Promise.all([
          getAllReglas(),
          getTiposDeReglas()
        ])
        setRules(data)
        setTiposRegla(dataTipos)
        setReglaData((prev) => ({
          ...prev,
          tipo: dataTipos?.length ? dataTipos[0] : ''
        }))
      } catch (error) {
        console.error(' Error al obtener reglas o tipos:', error)
        setMessage({ type: 'error', text: 'Error al cargar las reglas o tipos disponibles.' })
      } finally {
        setLoading(false)
      }
    }

    fetchReglas()
  }, [])

  return (
    <div
      style={{ backgroundImage: "url(https://www.transparenttextures.com/patterns/clean-gray-paper.png)" }}
      className="h-screen bg-gray-100 flex"
    >
      <NavBar />

      <main className="max-w-7xl mx-auto flex flex-col lg:flex-row gap-6 mt-16 items-center">
        <Link
          to="/admin"
          className="p-2 rounded-full hover:bg-gray-100 transition"
        >
          <FaArrowLeft className="text-gray-600 text-lg" />
        </Link>

        <div className="bg-white shadow-md rounded-lg p-6 h-9/12 relative">
          <h1 className="text-2xl font-semibold text-gray-800 mb-4">MOTOR DE REGLAS</h1>

          {/* 🟡 Loader simple */}
          {loading && (
            <div className="absolute inset-0 bg-white/60 flex items-center justify-center rounded-lg">
              <p className="text-gray-600 font-medium">Cargando...</p>
            </div>
          )}

          <form
            className="grid grid-cols-1 md:grid-cols-3 gap-4 items-end"
            onSubmit={handleSubmit}
          >
            <div className="md:col-span-2">
              <label className="block text-sm font-medium text-gray-500 mb-1">Descripción</label>
              <input
                type="text"
                className="w-full border border-gray-200 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-green-300"
                placeholder="Ej: regar cada 2 días"
                minLength={5}
                required
                value={reglaData.descripcion}
                onChange={handleChange}
                name="descripcion"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Intervalo Días</label>
              <input
                type="number"
                // min={1}
                className="w-full border border-gray-200 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-green-300"
                required
                value={reglaData.intervaloDias}
                onChange={handleChange}
                name="intervaloDias"
              />
            </div>

            <div className="md:col-span-1">
              <label className="block text-sm font-medium text-gray-700 mb-1">Tipo</label>
              <select
                name="tipo"
                required
                value={reglaData.tipo}
                onChange={handleChange}
                className="w-full border border-gray-200 rounded-md py-2 bg-white focus:outline-none focus:ring-2 focus:ring-green-300"
              >
                {tiposRegla.map((tipo) => (
                  <option key={tipo} value={tipo}>{tipo}</option>
                ))}
              </select>
            </div>

            <div className="md:col-span-1 flex justify-end">
              <button
                type="submit"
                disabled={loading}
                className="px-4 py-2 bg-green-600 text-white rounded-md hover:bg-green-700 transition disabled:opacity-50 disabled:cursor-not-allowed"
              >
                Guardar Regla
              </button>
            </div>
          </form>

          <section className="mt-6 bg-gray-50 border border-dashed border-gray-200 rounded-md p-4">
            <h2 className="text-lg font-medium text-gray-800 mb-2">Preview</h2>
            <div className="text-sm text-gray-700">
              <p className="mb-1"><strong>Descripción:</strong> {reglaData.descripcion || '—'}</p>
              <p className="mb-1"><strong>Intervalo (días):</strong> {reglaData.intervaloDias}</p>
              <p className="mb-1"><strong>Tipo:</strong> {reglaData.tipo || '—'}</p>
            </div>
          </section>
           {message && (
            <div
              className={`mb-4 p-3 rounded-md text-sm font-medium ${
                message.type === 'success'
                  ? 'bg-green-100 text-green-700 border border-green-300'
                  : 'bg-red-100 text-red-700 border border-red-300'
              }`}
            >
              {message.text}
            </div>
          )}
        </div>

        {/* Panel lateral */}
        <aside className="w-full h-11/12 rounded-xl p-6 flex flex-col gap-5 overflow-hidden">
          <div className="bg-white shadow-md p-6 rounded-2xl border border-gray-100">
            <h2 className="text-lg font-semibold text-gray-800 mb-4 flex items-center gap-2">
              <span className="inline-block w-2 h-6 bg-green-600 rounded"></span>
              Tipo de regla
            </h2>
            <div className="flex flex-wrap gap-3">
              {tiposRegla.map((tipo) => (
                <button
                  key={tipo}
                  onClick={() => handleFilter([tipo])}
                  className="px-4 py-2 rounded-xl bg-green-600 text-white font-medium shadow-sm 
                    hover:bg-green-700 hover:scale-105 active:scale-95 transition-all duration-200"
                >
                  {tipo}
                </button>
              ))}
              <button
                onClick={handleShowAll}
                className="px-4 py-2 rounded-xl border border-green-600 text-green-700 font-medium 
                  hover:bg-green-50 hover:text-green-800 transition-colors duration-200"
              >
                Ver todo
              </button>
            </div>
          </div>

          <div className="flex-1 overflow-y-auto bg-white rounded-lg shadow-inner p-4 space-y-3 max-h-[70vh] aside-rules">
            {rules.length > 0 ? (
              rules.map((r, i) => (
                <RuleBox
                  key={i}
                  id={r.id}
                  descripcion={r.descripcion}
                  tipo={r.tipo}
                  intervaloDias={r.intervaloDias}
                  onDelete={handleDelete}
                />
              ))
            ) : (
              <p className="text-gray-500 text-sm text-center">No hay reglas registradas.</p>
            )}
          </div>
        </aside>
      </main>
    </div>
  )
}
