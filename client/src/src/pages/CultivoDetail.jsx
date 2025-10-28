import { useEffect, useState } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { getCultivoById, toggleEstadoCultivo } from '../services/CultivoService'
import { FaArrowLeft } from 'react-icons/fa';
import NavBar from '../components/NavBar';

export default function CultivoDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [cultivo, setCultivo] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchCultivo();
  }, [id]);

  const fetchCultivo = async () => {
    try {
      setLoading(true);
      const data = await getCultivoById(id);
      setCultivo(data);
    } catch (err) {
      setError('Error al cargar el cultivo');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

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
    <div className="min-h-screen bg-gray-50">
      <NavBar />
      
      <div className="max-w-7xl mx-auto p-6">
        {/* Header */}
        <div className="flex items-center justify-between mb-6">
          <Link to="/cultivos" className="flex items-center text-gray-600 hover:text-gray-800">
            <FaArrowLeft className="mr-2" />
            Volver
          </Link>
          <h1 className="text-2xl font-bold text-gray-800">Detalle de Cultivo</h1>
          <div className="w-20"></div> {/* Spacer for alignment */}
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          {/* Left Column - Image and Details */}
          <div className="lg:col-span-1">
            <div className="bg-white rounded-2xl shadow-md p-4">
              <img
                src={cultivo.especie?.imagenUrl || 'https://images.nationalgeographic.org/image/upload/t_RL2_search_thumb/v1638892233/EducationHub/photos/crops-growing-in-thailand.jpg'}
                alt={cultivo.nombre}
                className="w-full h-48 object-cover rounded-xl mb-4"
              />
              
              <h2 className="text-2xl font-bold text-gray-800 mb-4">{cultivo.nombre}</h2>
              
              <div className="space-y-3">
                <div>
                  <p className="text-sm text-gray-500">Fecha de siembra</p>
                  <p className="font-medium">{new Date(cultivo.fechaSiembra).toLocaleDateString('es-ES', { day: 'numeric', month: 'long', year: 'numeric' })}</p>
                </div>
                
                <div>
                  <p className="text-sm text-gray-500">Estado</p>
                  <p className={`font-medium ${estadoColor}`}>{cultivo.etapaActual || 'N/A'}</p>
                </div>
                
                <div>
                  <p className="text-sm text-gray-500">Progreso</p>
                  <div className="flex items-center gap-3">
                    <div className="flex-1 bg-gray-200 rounded-full h-3">
                      <div
                        className="bg-green-500 h-3 rounded-full transition-all"
                        style={{ width: `${progress}%` }}
                      ></div>
                    </div>
                    <span className="text-sm font-medium">{progress}%</span>
                  </div>
                </div>
                
                <div>
                  <p className="text-sm text-gray-500">Superficie</p>
                  <p className="font-medium">{cultivo.areaHectareas} ha</p>
                </div>
                
                <div>
                  <p className="text-sm text-gray-500">Nombre científico</p>
                  <p className="font-medium italic">{cultivo.especie?.nombreCientifico || 'N/A'}</p>
                </div>
                
                <div>
                  <p className="text-sm text-gray-500">Notas</p>
                  <p className="font-medium">{cultivo.notas || 'Ninguna'}</p>
                </div>
              </div>
            </div>
          </div>

          {/* Right Column - Alerts and Tasks */}
          <div className="lg:col-span-2 space-y-4">
            {/* Alert Box */}
            <div className="bg-pink-100 rounded-2xl p-6 flex items-center justify-between">
              <div>
                <h3 className="text-lg font-semibold text-gray-800 mb-2">Riego pendiente para hoy</h3>
                <button className="bg-pink-400 hover:bg-pink-500 text-white px-4 py-2 rounded-lg text-sm font-medium transition">
                  Hace 4 horas
                </button>
              </div>
            </div>

            {/* Tasks Section */}
            <div className="bg-white rounded-2xl shadow-md p-6">
              <h3 className="text-xl font-bold text-gray-800 mb-4">Próximas tareas</h3>
              
              <div className="space-y-3">
                <div className="flex items-center justify-between p-4 bg-gray-50 rounded-lg">
                  <span className="font-medium text-gray-700">Riego</span>
                  <div className="flex items-center gap-3">
                    <span className="text-sm text-gray-500">En 2 días</span>
                    <button className="bg-blue-200 text-blue-700 px-4 py-1 rounded-lg text-sm font-medium">
                      Próximo
                    </button>
                  </div>
                </div>
                
                <div className="flex items-center justify-between p-4 bg-gray-50 rounded-lg">
                  <span className="font-medium text-gray-700">Fertilizante</span>
                  <div className="flex items-center gap-3">
                    <span className="text-sm text-gray-500">En 2 días</span>
                    <button className="bg-blue-200 text-blue-700 px-4 py-1 rounded-lg text-sm font-medium">
                      Próximo
                    </button>
                  </div>
                </div>
              </div>
            </div>

            {/* Action Buttons */}
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
