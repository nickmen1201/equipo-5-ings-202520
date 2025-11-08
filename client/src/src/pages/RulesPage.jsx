import React, { useEffect, useState } from 'react'
import NavBar from '../components/NavBar'
import RuleBox from '../Components/RuleBox'
import { createRegla,deleteRegla,getAllReglas,getTiposDeReglas } from "../services/ReglaService";
import { TiPointOfInterest } from 'react-icons/ti';

export default function RulesPage() {

    const [rules,setRules] = useState([])
    const [tiposRegla,setTiposRegla]= useState([])

    const [reglaData,setReglaData] = useState({
        descripcion: '',
        tipo:'',
        intervaloDias:1
    })

    const [message, setMessage] = useState(null)

    const handleSubmit = async (e) => {
        e.preventDefault()
        setMessage(null)
        try {
            // createRegla is imported from services/ReglaService
            const created = await createRegla(reglaData)
            // refresh rules list
            const all = await getAllReglas()
            setRules(all)
            setMessage({ type: 'success', text: 'Regla creada correctamente.' })
            // reset form
            setReglaData({ descripcion: '', tipo: tiposRegla[0] || '', intervaloDias: 1 })
        } catch (err) {
            console.error('Error creating regla', err)
            setMessage({ type: 'error', text: 'Error al crear la regla.' })
        }
    }

    const handleChange = (e) => {
    const { name, value } = e.target
    setReglaData((prev) => ({ ...prev, [name]: value }))
  }

  const handleDelete = async (id) => {
  try {
    await deleteRegla(id)
    // Elimina del estado local sin volver a llamar a la API
    setRules(prev => prev.filter(rule => rule.id !== id))
  } catch (err) {
    console.error("Error al eliminar regla", err)
  }
}

 


    useEffect(() => {
        async function fetchReglas() {
          try {
            const data = await getAllReglas();
            const dataTipos= await getTiposDeReglas();
            setRules(data);
                        setTiposRegla(dataTipos);
                        // ensure the form has a valid default type when tipos arrive
                        setReglaData((prev) => ({ ...prev, tipo: dataTipos && dataTipos.length ? dataTipos[0] : '' }))

          }
          catch (error) {
            console.error("Error al obtener reglas o tipos:", error);
          } 
        //   finally {
        //     // setLoading(false);
        //   }
        }

        fetchReglas();
    }, []);

    

    

    return (
           <div className="h-screen bg-gray-100 flex ">
            <NavBar />
                <main className="max-w-7xl mx-auto  flex flex-col lg:flex-row gap-6 mt-16  items-center ">
                <div className="bg-white  shadow-md rounded-lg p-6 h-9/12">
                    <h1 className="text-2xl font-semibold text-gray-800 mb-4">MOTOR DE REGLAS</h1>

                    <form className="grid grid-cols-1 md:grid-cols-3 gap-4 items-end" onSubmit={handleSubmit}>
                        <div className="md:col-span-2">
                            <label className="block text-sm font-medium text-gray-500 mb-1">Descripción</label>
                            <input
                                type="text"
                                className="w-full border border-gray-200 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-green-300"
                                placeholder="ej :regar cada 2 dias"
                                minLength={5}
                                required
                                value={reglaData.descripcion}
                                onChange={handleChange}
                                name='descripcion'
                            />
                        </div>

                        <div>
                            <label className="block text-sm font-medium text-gray-700 mb-1">Intervalo Dias</label>
                            <input
                                type="number"
                                min={1}
                                className="w-full border border-gray-200 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-green-300"
                                required
                                value={reglaData.intervaloDias}
                                onChange={handleChange}
                                name='intervaloDias'
                            />
                        </div>

                        <div className="md:col-span-1">
                            <label className="block text-sm font-medium text-gray-700 mb-1">Tipo</label>
                            <select name="tipo" required value={reglaData.tipo} onChange={handleChange} className="w-full border border-gray-200 rounded-md  py-2 bg-white focus:outline-none focus:ring-2 focus:ring-green-300">                    
                                {tiposRegla.map((tipo) => (
                                    <option key={tipo} value={tipo}>{tipo}</option>
                                ))}
                            </select>
                        </div>

                        

                        <div className="md:col-span-1 flex justify-end">
                            <button type="submit" className="px-1 py-2 bg-green-600 text-white rounded-md hover:bg-green-700 transition">Guardar Regla</button>
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
                </div>
                 <aside className="w-full h-11/12  rounded-xl p-6 flex flex-col gap-5 overflow-hidden ">
                        <div className='bg-gray-200 p-4 rounded-2xl'>
                            <h2 className="text-lg font-semibold text-gray-800 mb-3">
                            Tipo de regla
                            </h2>
                            <div className="flex flex-wrap gap-3">
                                {
                                    tiposRegla.map(tipo =>(
                                        <button className="px-3 py-1.5 rounded-md bg-green-700 text-white hover:bg-green-700 transition">
                                             {tipo}
                                        </button>
                                    ) )
                                }
                            </div>
                        </div>

                        <div className="flex-1 overflow-y-auto bg-white rounded-lg shadow-inner p-4 space-y-3 max-h-[70vh] aside-rules">
                            {
                                rules.map((r,i)=>{return <RuleBox key={i} id={r.id} descripcion={r.descripcion} tipo={r.tipo} intervaloDias={r.intervaloDias} onDelete={handleDelete} /> })
                            }
                        </div>
                    </aside>
            </main>
        </div>
    )
}
