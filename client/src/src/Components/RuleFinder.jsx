import React, { useEffect, useState } from 'react'
import { getAllReglas, getTiposDeReglas, deleteRegla, searchReglas } from '../services/ReglaService'

export default function RuleFinder({
  onRulesSelected = () => {},
  onClose = () => {},
  stage = null,
  selectedIds: initialSelectedIds = [] // recibimos los ids ya seleccionados
}) {
  const [rules, setRules] = useState([])
  const [tiposRegla, setTiposRegla] = useState([])
  const [selectedIds, setSelectedIds] = useState(initialSelectedIds)

  //  sincroniza cuando el padre cambia los seleccionados
  useEffect(() => {
    setSelectedIds(initialSelectedIds)
  }, [initialSelectedIds])

  //  cargar reglas y tipos
  useEffect(() => {
    async function fetchReglas() {
      try {
        const data = await getAllReglas()
        const dataTipos = await getTiposDeReglas()
        setRules(data)
        setTiposRegla(dataTipos)
      } catch (error) {
        console.error('Error al obtener reglas o tipos:', error)
      }
    }
    fetchReglas()
  }, [])


  //  seleccionar o deseleccionar regla
  const toggleSelect = (id) => {
    setSelectedIds((prev) =>
      prev.includes(id) ? prev.filter((x) => x !== id) : [...prev, id]
    )
  }

  //  enviar las seleccionadas al componente padre
  const handleAddSelected = () => {
    const selectedRules = rules.filter((r) => selectedIds.includes(r.id))
    onRulesSelected(selectedRules)
  }

  //  Mostrar todas las reglas
    const handleShowAll = async () => {
      // setLoading(true)
      try {
        const allRules = await getAllReglas()
        setRules(allRules)
      } catch (err) {
        console.error(' Error al obtener todas las reglas:', err)
        // setMessage({ type: 'error', text: 'No se pudieron cargar las reglas.' })
      } finally {
        // setLoading(false)
      }
    }

  
    //  Filtro por tipo de regla
    const handleFilter = async (filtro) => {
      // setLoading(true)
      try {
        const filtered = await searchReglas(filtro)
        setRules(filtered)
      } catch (err) {
        console.error(' Error al filtrar reglas:', err)
        // setMessage({ type: 'error', text: 'Error al filtrar las reglas.' })
      } 
    }
  

  return (
    <div className="w-full rounded-xl p-6 flex flex-col gap-5 overflow-hidden">
      {/* Sección tipos */}
       <div className="bg-white shadow-md p-6 rounded-2xl border border-gray-100">
          <h2 className="text-lg font-semibold text-gray-800 mb-4 flex items-center gap-2">
            <span className="inline-block w-2 h-6 bg-green-600 rounded"></span>
            Tipo de regla
          </h2>
          <div className="flex flex-wrap gap-3">
            {tiposRegla.map((tipo) => (
            <button
            type='button'
              key={tipo}
              onClick={() => handleFilter([tipo])}
              className="px-4 py-2 rounded-xl bg-green-600 text-white font-medium shadow-sm 
             hover:bg-green-700 hover:scale-105 active:scale-95 transition-all duration-200"
            >
              {tipo}
            </button>
            ))}
            <button
            type='button'
            onClick={handleShowAll}
            className="px-4 py-2 rounded-xl border border-green-600 text-green-700 font-medium 
                        hover:bg-green-50 hover:text-green-800 transition-colors duration-200"
            >
            Ver todo
            </button>
        </div>
      </div>
      
 
      {/* Lista de reglas */}
      <div className="flex-1 overflow-y-auto bg-white rounded-lg shadow-inner p-4 space-y-3 max-h-[70vh] aside-rules">
        {rules.map((r) => (
          <div
            key={r.id}
            className="flex items-center justify-between p-2 border rounded"
            onClick={() => toggleSelect(r.id)}
          >
            <div className="flex items-center gap-3">
              <input
                type="checkbox"
                checked={selectedIds.includes(r.id)}
                className="w-4 h-4"
                readOnly
              />
              <div>
                <div className="font-medium">{r.descripcion}</div>
                <div className="text-xs text-gray-500">
                  {r.tipo} • {r.intervaloDias} días
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>

      {/* Botones */}
      <div className="flex justify-end gap-2">
        <button
          type='button'
          onClick={onClose}
          className="px-3 py-1 rounded bg-gray-100 hover:bg-gray-200"
        >
          Cerrar
        </button>
        <button
          type='button'
          onClick={handleAddSelected}
          className="px-3 py-1 rounded bg-green-600 text-white hover:bg-green-700"
        >
          Agregar seleccionadas
        </button>
      </div>
    </div>
  )
}
