import { useEffect, useState } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { getCultivoById, toggleEstadoCultivo } from '../services/CultivoService'
import { FaArrowLeft, FaSmile } from 'react-icons/fa';
import NavBar from '../components/NavBar';
import { GiFertilizerBag, GiWateringCan } from 'react-icons/gi';
import { GrTools } from 'react-icons/gr';
import ProgressBar from '../Components/ProgressBar';
import { FiSmile } from 'react-icons/fi';
import { MdWorkspacePremium } from 'react-icons/md';
import { FaHeartCircleExclamation } from 'react-icons/fa6';
import { ejecutarTarea } from '../services/TareaService';

export default function CultivoDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [cultivo, setCultivo] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

 

  const iconsProgress=(progress) => {
    if(progress < 50){
      return <FaHeartCircleExclamation className='text-red-600' />
    }else if (progress >=50 && progress <= 75){
      return <MdWorkspacePremium className='text-yellow-400' />
    }
    else{
      return <FiSmile className='text-green-700' />
    }
  }

  useEffect(() => {
    
    fetchCultivo();
  }, [id]);

  const fetchCultivo = async () => {
    try {
      setLoading(true);
      const data = await getCultivoById(id);
      setCultivo(data);
      console.log('Cultivo data:', data);
    } catch (err) {
      setError('Error al cargar el cultivo');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

   const handleEjecutar =async (id) =>{
    await ejecutarTarea(id)
    await fetchCultivo();
  }

  const handleToggleEstado = async () => {
    try {
      console.log('Toggling estado for cultivo:', id);
      await toggleEstadoCultivo(id);
      console.log('Toggle successful, reloading...');
      fetchCultivo(); // Reload data
    } catch (err) {
      console.error('Toggle estado error:', err);
      alert('Error al cambiar el estado: ' + err.message);
    }
  };

  const handleEdit = () => {
    if (isArchived()) {
      alert('No editable mientras esté archivado');
      return;
    }
    navigate(`/cultivos/${id}/editar`);
  };

  const isArchived = () => {
    return cultivo?.estado === 'COSECHADO' || cultivo?.estado === 'PERDIDO';
  };

  //necesito mandar el # de etapas
  const calculateProgress = () => {
    if (!cultivo?.especie?.cicloDias || !cultivo?.fechaSiembra) return 0;
    
    const today = new Date();
    const startDate = new Date(cultivo.fechaSiembra);
    const daysPassed = Math.floor((today - startDate) / (1000 * 60 * 60 * 24));
    const progress = Math.min(Math.floor((daysPassed / cultivo.especie.cicloDias) * 100), 100);
    
    return progress;
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center h-screen">
        <p className="text-gray-600">Cargando...</p>
      </div>
    );
  }

  if (error || !cultivo) {
    return (
      <div className="flex items-center justify-center h-screen">
        <p className="text-red-600">{error || 'Cultivo no encontrado'}</p>
      </div>
    );
  }

  const progress = calculateProgress();
  const estadoColor = cultivo.estado === 'ACTIVO' ? 'text-green-600' : 'text-gray-500';

  return (
    <div className="h-screen overflow-hidden bg-gray-50">
      <NavBar />
      
      <div className="max-w-7xl mx-auto mt-16">
        {/* Header */}
        <div className="flex items-center justify-between mb-6">
          <Link to="/cultivos" className="flex items-center text-gray-600 hover:text-gray-800">
            <FaArrowLeft className="mr-2" />
            Volver
          </Link>
          <h1 className="text-2xl font-bold text-gray-800">Detalle de Cultivo</h1>
          <div className="w-20"></div> {/* Spacer for alignment */}
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6 h-[calc(100vh-6rem)]">
  {/* --- Columna Izquierda (fija) --- */}
  <div className="lg:col-span-1 ">
    <div className="bg-white rounded-2xl shadow-md p-4 sticky top-20 h-fit">
      <img
        src={
          cultivo.especie?.imagenUrl ||
          'https://images.nationalgeographic.org/image/upload/t_RL2_search_thumb/v1638892233/EducationHub/photos/crops-growing-in-thailand.jpg'
        }
        alt={cultivo.nombre}
        className="w-full h-48 object-cover rounded-xl mb-4"
      />

      <h2 className="text-2xl font-bold text-gray-800 mb-4">{cultivo.nombre}</h2>

      <div className="space-y-3">
        <div>
          <p className="text-sm text-gray-500">Fecha de creación</p>
          <p className="font-medium">
            {new Date(cultivo.fechaSiembra).toLocaleDateString('es-ES', {
              day: 'numeric',
              month: 'long',
              year: 'numeric',
            })}
          </p>
        </div>

        <div>
          <p className="text-sm text-gray-500">Etapa Actual</p>
          <p className={`font-medium ${estadoColor}`}>
            {cultivo.etapaActualInfo?.nombre || 'N/A'}
          </p>
        </div>

        <div>
          <p className="text-sm text-gray-500">Progreso</p>
          <div className="flex items-center gap-3">
            <div className="flex-1 bg-gray-200 rounded-full h-3">
              <div
                className="bg-green-500 h-3 rounded-full transition-all"
                style={{ width: `${Math.round((cultivo.etapaActual*100)/cultivo.especie.totalEtapas)*10/10}%` }}
              ></div>
            </div>
            <span className="text-sm font-medium">{Math.round((cultivo.etapaActual*100)/cultivo.especie.totalEtapas)*10/10}%</span>
          </div>
        </div>

        <div>
          <p className="text-sm text-gray-500">Superficie</p>
          <p className="font-medium">{cultivo.areaHectareas} ha</p>
        </div>

        <div>
          <p className="text-sm text-gray-500">Nombre científico</p>
          <p className="font-medium italic">
            {cultivo.especie?.nombreCientifico || 'N/A'}
          </p>
        </div>

        <div>
          <p className="text-sm text-gray-500">Notas</p>
          <p className="font-medium">{cultivo.notas || 'Ninguna'}</p>
        </div>
      </div>
    </div>
  </div>

  {/* --- Columna Derecha (scrollable) --- */}
  <div
    className="lg:col-span-2 overflow-y-auto pr-2 pb-8 space-y-4"
    style={{ maxHeight: 'calc(100vh - 6rem)' }}
  >
    {/* 🔹 SECCIÓN DE SALUD */}
    <div className="bg-white rounded-2xl shadow-md p-6">
      <h3 className="text-xl font-bold text-gray-800 mb-4">Salud</h3>
      <div className="space-y-3">
        <div className="flex items-center justify-between p-4 bg-gray-50 rounded-lg">
          <div className="font-medium text-gray-700 flex items-center gap-2">
            <GiWateringCan />
            <span>Salud Riego</span>
          </div>
          <div className="flex items-center gap-3">
            <ProgressBar value={cultivo.saludRiego} showLabel={false} />
            <span className="text-3xl text-gray-500">{iconsProgress(cultivo.saludRiego)}</span>
          </div>
        </div>

        <div className="flex items-center justify-between p-4 bg-gray-50 rounded-lg">
          <div className="font-medium text-gray-700 flex items-center gap-2">
            <GiFertilizerBag />
            <span>Salud Fertilización</span>
          </div>
          <div className="flex items-center gap-3">
            <ProgressBar value={cultivo.saludFertilizacion} showLabel={false} />
            <span className="text-3xl text-gray-500">{iconsProgress(cultivo.saludFertilizacion)}</span>
          </div>
        </div>

        <div className="flex items-center justify-between p-4 bg-gray-50 rounded-lg">
          <div className="font-medium text-gray-700 flex items-center gap-2">
            <GrTools />
            <span>Salud Mantenimiento</span>
          </div>
          <div className="flex items-center gap-3">
            <ProgressBar value={cultivo.saludMantenimiento} showLabel={false} />
            <span className="text-3xl text-gray-500">{iconsProgress(cultivo.saludMantenimiento)}</span>
          </div>
        </div>
      </div>
    </div>

  

    
    {/* 🔹 TAREAS */}
<div className="bg-white rounded-2xl shadow-md p-6">
  <h3 className="text-xl font-bold text-gray-800 mb-4">Tareas del cultivo</h3>

  {cultivo.tareas && cultivo.tareas.length > 0 ? (
    <div className="space-y-3">
      {cultivo.tareas.map((tarea) => {
        // Calcular días restantes hasta vencimiento
        const hoy = new Date();
        const fechaVencimiento = new Date(tarea.fechaVencimiento);
        const diasRestantes = Math.ceil((fechaVencimiento - hoy) / (1000 * 60 * 60 * 24));

        return (
          <div
            key={tarea.id}
            className={`flex items-center justify-between p-4 rounded-lg border ${
              tarea.realizada
                ? 'bg-green-50 border-green-300'
                : tarea.vencida
                ? 'bg-red-50 border-red-300'
                : 'bg-gray-50 border-gray-200'
            }`}
          >
            <div>
              <p className="font-medium text-gray-800">{tarea.descripcionRegla}</p>
              <p className="text-sm text-gray-500">
                Programada para: {new Date(tarea.fechaProgramada).toLocaleDateString('es-ES')}
              </p>
            </div>

            <div className="flex items-center gap-3">
              <span
                className={`text-sm font-medium ${
                  tarea.vencida
                    ? 'text-red-600'
                    : tarea.realizada
                    ? 'text-green-600'
                    : 'text-gray-600'
                }`}
              >
                {tarea.vencida
                  ? 'Vencida'
                  : tarea.realizada
                  ? 'Completada'
                  : diasRestantes > 0
                  ? `En ${diasRestantes} día${diasRestantes > 1 ? 's' : ''}`
                  : 'Hoy'}
              </span>
            </div>{

            tarea.realizada || tarea.vencida ? null :
            <button onClick={()=>handleEjecutar(tarea.id)} className="bg-blue-500 hover:bg-blue-600 text-white py-2 px-4 rounded-lg font-medium transition">
              ejecutar
            </button>
            }
          </div>
        );
      })}
    </div>
  ) : (
    <p className="text-gray-500">No hay tareas registradas para este cultivo.</p>
  )}
</div>

    {/* 🔹 BOTONES */}
    <div className="flex gap-4">
      <button
        onClick={handleEdit}
        className={`flex-1 py-3 rounded-lg font-medium transition ${
          isArchived()
            ? 'bg-gray-300 text-gray-500 cursor-not-allowed'
            : 'bg-blue-600 hover:bg-blue-700 text-white'
        }`}
      >
        Editar
      </button>

      <button
        onClick={handleToggleEstado}
        className="flex-1 bg-orange-500 hover:bg-orange-600 text-white py-3 rounded-lg font-medium transition"
      >
        {cultivo.estado === 'ACTIVO' ? 'Archivar' : 'Activar'}
      </button>
    </div>
  </div>
</div>

      </div>
    </div>
  );
}
