import React, { useEffect, useState } from 'react'
import RuleFinder from './RuleFinder'
import EtapaService, { getTiposEtapas } from '../services/EtapaService'


const STAGES = {
    PREPARACION: 'Preparación',
    SIEMBRA: 'Siembra',
    GERMINACION: 'Germinación',
    DESARROLLO_VEGETATIVO: 'Desarrollo Vegetativo',
    FLORACION: 'Floración',
    FRUCTIFICACION: 'Fructificación',
    MADURACION: 'Maduración',
    COSECHA: 'Cosecha'
}
 

const Modal = ({ isOpen, onClose, children }) => {
    if (!isOpen) return null;

    return (
        <div 
            className="fixed  inset-0 bg-black/50 flex items-center justify-center z-50"
            onClick={onClose} // cierra si das clic afuera
        >
            <div
                className="bg-white rounded-lg p-6 w-full max-w-4xl max-h-[90vh] overflow-y-auto shadow-lg relative z-50"
                onClick={(e) => e.stopPropagation()} // evita que el clic dentro cierre el modal
            >
                <div className="flex justify-between items-center mb-4 sticky top-0  bg-white pb-2 border-b">
                    <h2 className="text-xl font-semibold">Configurar Etapas</h2>
                    <button
                        type='button'
                        onClick={onClose}
                        className="text-gray-500 hover:text-gray-700"
                    >
                        ✕
                    </button>
                </div>

                {children}
            </div>
        </div>
    );
};


export default function CropsStage({ onSaveEtapas = null, especieId = null, onEtapasChange = null }) {
    const [isModalOpen, setIsModalOpen] = useState(false)
    const [selectedStages, setSelectedStages] = useState([])
    const [stageDurations, setStageDurations] = useState({})
    const [ruleCounts, setRuleCounts] = useState({})
    const [stageRuleIds, setStageRuleIds] = useState({}) 
    const [isRuleFinderOpen, setIsRuleFinderOpen] = useState(false)
    const [currentStage, setCurrentStage] = useState(null)
    const [tiposEtapa, setTiposEtapa] = useState([])

    // Notify parent when etapas configuration changes
    useEffect(() => {
        if (typeof onEtapasChange === 'function') {
            const etapas = buildEtapasRequests(null)
            onEtapasChange(etapas)
        }
        
    }, [selectedStages, stageDurations, stageRuleIds])

    useEffect(() => {
    async function fetchEtapas(){
        try{
            const dataTipos = await getTiposEtapas();

            setTiposEtapa(dataTipos);

        }
        catch(error){
            console.error("Error al obtener tipos de etapas:", error);
        }
    }
    fetchEtapas()

  
}, [])


    // Move stage up in order
    const moveStageUp = (index) => {
        if (index === 0) return
        const newStages = [...selectedStages]
        ;[newStages[index - 1], newStages[index]] = [newStages[index], newStages[index - 1]]
        setSelectedStages(newStages)
    }

    // Move stage down in order
    const moveStageDown = (index) => {
        if (index === selectedStages.length - 1) return
        const newStages = [...selectedStages]
        ;[newStages[index], newStages[index + 1]] = [newStages[index + 1], newStages[index]]
        setSelectedStages(newStages)
    }

    // Toggle stage selection and manage durations
    const toggleStage = (stage) => {
        setSelectedStages(prev => {
            const newStages = prev.includes(stage)
                ? prev.filter(s => s !== stage)
                : [...prev, stage];
            
            // Clean up duration if stage is deselected
            if (prev.includes(stage)) {
                setStageDurations(prevDurations => {
                    const newDurations = { ...prevDurations };
                    delete newDurations[stage];
                    return newDurations;
                });
            }
            
            return newStages;
        });
    }

    // Handle duration changes
    const handleDurationChange = (stage, value) => {
        const numValue = value === '' ? '' : Math.max(1, parseInt(value) || 0);
        setStageDurations(prev => ({
            ...prev,
            [stage]: numValue
        }));
    }

    // Add rules button handler
    const handleAddRules = (stage) => {
        setCurrentStage(stage);
        setIsRuleFinderOpen(true);
    };

const handleRuleSelect = (selectedRules) => {
  if (!currentStage) return;

  const newIds = selectedRules.map(r => r.id);

 setStageRuleIds(prev => {
  const current = Array.isArray(prev[currentStage]) ? prev[currentStage] : []
  const merged = Array.from(new Set([...current, ...newIds]))
  return { ...prev, [currentStage]: merged }
})

setRuleCounts(prev => ({
  ...prev,
  [currentStage]: (Array.isArray(prev[currentStage]) ? prev[currentStage].length : 0) + newIds.length
}))

  setIsRuleFinderOpen(false);
  setCurrentStage(null);
};


    // Build etapa requests from current state. Caller must include especieId.
    const buildEtapasRequests = (especieId) => {
        return selectedStages.map((stageKey, idx) => ({
            nombre: stageKey, // expects TipoEtapa enum on backend
            especieId: especieId || null,
            duracionDias: stageDurations[stageKey] ? parseInt(stageDurations[stageKey]) : 0,
            orden: idx + 1,
            reglaIds: stageRuleIds[stageKey] || []
        }))
    }

    return (
        <div className="p-4 ">
            <button
                type='button'
                onClick={() => setIsModalOpen(true)}
                className="mb-4 bg-green-600 text-white px-4 py-2 rounded-md hover:bg-green-700 transition"
            >
                Configurar Etapas del Cultivo
            </button>

            <Modal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)}>
                <div className="space-y-4  ">
                    <div className="grid grid-cols-1 gap-3">
                        {tiposEtapa.map((key) => (
                            <div
                                key={key}
                                className="flex items-center justify-between bg-gray-50 p-3 rounded-lg"
                            >
                                <div className="flex items-center gap-3">
                                    <input
                                        type="checkbox"
                                        checked={selectedStages.includes(key)}
                                        onChange={() => toggleStage(key)}
                                        className="w-4 h-4 text-green-600"
                                    />
                                    <span>{key}</span>
                                </div>
                                
                                <div className="flex items-center gap-3">
                                    {selectedStages.includes(key) && (
                                        <>
                                            <div className="flex items-center gap-4">
                                                <div className="flex items-center gap-2">
                                                    <span className="text-sm text-gray-500 font-medium">
                                                        Orden: {selectedStages.indexOf(key) + 1}
                                                    </span>
                                                    <div className="flex gap-1">
                                                        <button
                                                            onClick={() => moveStageUp(selectedStages.indexOf(key))}
                                                            disabled={selectedStages.indexOf(key) === 0}
                                                            className="p-1 text-gray-500 hover:text-gray-700 disabled:opacity-30"
                                                            title="Mover arriba"
                                                            type='button'
                                                        >
                                                            <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4" viewBox="0 0 20 20" fill="currentColor">
                                                                <path fillRule="evenodd" d="M14.707 12.707a1 1 0 01-1.414 0L10 9.414l-3.293 3.293a1 1 0 01-1.414-1.414l4-4a1 1 0 011.414 0l4 4a1 1 0 010 1.414z" clipRule="evenodd" />
                                                            </svg>
                                                        </button>
                                                        <button
                                                            onClick={() => moveStageDown(selectedStages.indexOf(key))}
                                                            disabled={selectedStages.indexOf(key) === selectedStages.length - 1}
                                                            className="p-1 text-gray-500 hover:text-gray-700 disabled:opacity-30"
                                                            title="Mover abajo"
                                                            type='button'
                                                        >
                                                            <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4" viewBox="0 0 20 20" fill="currentColor">
                                                                <path fillRule="evenodd" d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clipRule="evenodd" />
                                                            </svg>
                                                        </button>
                                                    </div>
                                                </div>
                                                <div className="flex items-center gap-2">
                                                    <span className="text-sm text-gray-500">Duración:</span>
                                                    <div className="flex items-center">
                                                        <input
                                                            type="number"
                                                            // min="1"
                                                            value={stageDurations[key] || ''}
                                                            onChange={(e) => handleDurationChange(key, e.target.value)}
                                                            className="w-16 px-2 py-1 text-sm border rounded focus:outline-none focus:border-blue-500"
                                                            placeholder="Días"
                                                        />
                                                        <span className="text-sm text-gray-500 ml-1">días</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div className="flex items-center gap-2 ml-2 border-l pl-4">
                                                <span className="text-sm text-gray-500 flex items-center gap-1">
                                                    <span>Reglas:</span>
                                                    <span className="font-medium">{ruleCounts[key] || 0}</span>
                                                </span>
                                                <button
                                                    type='button'
                                                    onClick={() => handleAddRules(key)}
                                                    className="px-2 py-1 text-sm text-blue-500 hover:text-blue-600 hover:bg-blue-50 rounded"
                                                >
                                                    + Regla
                                                </button>
                                            </div>
                                        </>
                                    )}
                                </div>
                            </div>
                        ))}
                        
                    </div>

                    <div className="flex justify-end gap-3 mt-6 pt-4 border-t">
                        <button
                            onClick={() => setIsModalOpen(false)}
                            className="px-4 py-2 text-gray-600 hover:text-gray-800"
                            type='button'
                        >
                            Cerrar
                        </button>
                        <button
                            type='butotn'
                            onClick={async () => {
                                const requests = buildEtapasRequests(especieId)
                                try {
                                    if (onSaveEtapas) {
                                        await onSaveEtapas(requests)
                                    } else if (especieId) {
                                        await EtapaService.createEtapasBatch(requests)
                                    } else {
                                        console.warn('No especieId provided and no onSaveEtapas handler. Etapas built:', requests)
                                    }
                                    setIsModalOpen(false)
                                } catch (err) {
                                    console.error('Error saving etapas:', err)
                                    // Optionally: show UI error
                                }
                            }}
                            className="px-4 py-2 bg-green-600 text-white rounded-md hover:bg-green-700"
                        >
                            Guardar Orden
                        </button>
                    </div>
                </div>
            </Modal>

            {/* RuleFinder Modal */}
            <Modal isOpen={isRuleFinderOpen} onClose={() => {
                setIsRuleFinderOpen(false)
                setCurrentStage(null)
            }}>
                <div>
                    <h3 className="text-lg font-medium text-gray-900 mb-4">
                        Añadir reglas para etapa: {currentStage ? STAGES[currentStage] : ''}
                    </h3>
                   <RuleFinder
  onRulesSelected={handleRuleSelect}
  onClose={() => {
    setIsRuleFinderOpen(false);
    setCurrentStage(null);
  }}
  stage={currentStage}
  selectedIds={stageRuleIds[currentStage] || []} // ✅ aquí está la clave
/>
                </div>
            </Modal>
        </div>
    )
}
