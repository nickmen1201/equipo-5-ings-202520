import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getCultivoById, updateCultivo } from '../services/CultivoService';
import { getAllEspecies } from '../services/especiesService';
import { getEtapasByEspecie } from '../services/EtapaService';
import NavBar from '../Components/NavBar';

export default function CropEdit() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [especies, setEspecies] = useState([]);
  const [etapas, setEtapas] = useState([]);
  const [formData, setFormData] = useState({
    nombre: '',
    areaHectareas: '',
    etapaActual: '',
    estado: '',
    rendimientoKg: '',
    especie: { id: '' },
    usuario: { id: '' }
  });
  const [error, setError] = useState('');

  useEffect(() => {
    fetchData();
  }, [id]);

  const fetchData = async () => {
    try {
      setLoading(true);
      const [cultivoData, especiesData] = await Promise.all([
        getCultivoById(id),
        getAllEspecies()
      ]);
      
      setFormData({
        nombre: cultivoData.nombre,
        areaHectareas: cultivoData.areaHectareas,
        etapaActual: cultivoData.etapaActual || '',
        estado: cultivoData.estado || 'ACTIVO',
        rendimientoKg: cultivoData.rendimientoKg || '',
        especie: { id: cultivoData.especie.id },
        usuario: { id: cultivoData.usuario.id }
      });
      setEspecies(especiesData);
      
      // Fetch etapas for the current especie
      if (cultivoData.especie.id) {
        const etapasData = await getEtapasByEspecie(cultivoData.especie.id);
        setEtapas(etapasData.sort((a, b) => a.orden - b.orden));
      }
    } catch (err) {
      setError('Error al cargar los datos');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    
    if (name === 'especieId') {
      setFormData(prev => ({
        ...prev,
        especie: { id: parseInt(value) }
      }));
    } else {
      setFormData(prev => ({
        ...prev,
        [name]: value
      }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    if (!formData.nombre || !formData.areaHectareas || !formData.especie.id) {
      setError('Todos los campos son obligatorios');
      return;
    }

    const payload = {
      usuarioId: parseInt(formData.usuario.id),
      especieId: parseInt(formData.especie.id),
      nombre: formData.nombre,
      areaHectareas: parseFloat(formData.areaHectareas),
      etapaActual: formData.etapaActual ? parseInt(formData.etapaActual) : null,
      estado: formData.estado || 'ACTIVO',
      rendimientoKg: formData.rendimientoKg ? parseFloat(formData.rendimientoKg) : null
    };

    try {
      console.log('Submitting edit payload:', payload);
      console.log('Area value:', formData.areaHectareas, '-> parsed:', parseFloat(formData.areaHectareas));
      await updateCultivo(id, payload);
      navigate(`/cultivos/${id}`);
    } catch (err) {
      if (err.message.includes('archivado')) {
        setError('No editable mientras esté archivado');
      } else {
        setError('Error al actualizar el cultivo');
      }
      console.error(err);
    }
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center h-screen">
        <p className="text-gray-600">Cargando...</p>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <NavBar />
      
      <div className="max-w-2xl mx-auto p-6">
        <h1 className="text-3xl font-bold text-gray-800 mb-6">Editar Cultivo</h1>

        {error && (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit} className="bg-white rounded-lg shadow-md p-6 space-y-4">
          <div>
            <label className="block text-gray-700 font-medium mb-2">
              Nombre del cultivo <span className="text-red-500">*</span>
            </label>
            <input
              type="text"
              name="nombre"
              value={formData.nombre}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-transparent"
              required
            />
          </div>

          <div>
            <label className="block text-gray-700 font-medium mb-2">
              Especie
            </label>
            <input
              type="text"
              value={especies.find(e => e.id === formData.especie.id)?.nombre || 'Cargando...'}
              disabled
              className="w-full px-4 py-2 border border-gray-300 rounded-lg bg-gray-100 text-gray-600 cursor-not-allowed"
            />
            <p className="text-sm text-gray-500 mt-1">La especie no puede ser modificada</p>
          </div>

          <div>
            <label className="block text-gray-700 font-medium mb-2">
              Área (hectáreas) <span className="text-red-500">*</span>
            </label>
            <input
              type="number"
              name="areaHectareas"
              value={formData.areaHectareas}
              onChange={handleChange}
              step="0.01"
              min="0.01"
              max="100000"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-transparent"
              required
            />
          </div>

          <div>
            <label className="block text-gray-700 font-medium mb-2">
              Etapa Actual
            </label>
            <select
              name="etapaActual"
              value={formData.etapaActual}
              onChange={handleChange}
              className="w-full px-4 py-2 pr-8 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-transparent"
            >
              <option value="">Seleccionar etapa</option>
              {etapas.map((etapa) => (
                <option key={etapa.id} value={etapa.orden}>
                  {etapa.orden}. {etapa.nombre.replace(/_/g, ' ')}
                </option>
              ))}
            </select>
          </div>

          <div>
            <label className="block text-gray-700 font-medium mb-2">
              Estado
            </label>
            <select
              name="estado"
              value={formData.estado}
              onChange={handleChange}
              className="w-full px-4 py-2 pr-8 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-transparent"
            >
              <option value="ACTIVO">Activo</option>
              <option value="COSECHADO">Cosechado</option>
              <option value="PERDIDO">Perdido</option>
            </select>
          </div>

          <div>
            <label className="block text-gray-700 font-medium mb-2">
              Rendimiento (kg)
            </label>
            <input
              type="number"
              name="rendimientoKg"
              value={formData.rendimientoKg}
              onChange={handleChange}
              step="0.01"
              min="0"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-transparent"
            />
          </div>

          <div className="flex gap-4 pt-4">
            <button
              type="button"
              onClick={() => navigate(`/cultivos/${id}`)}
              className="flex-1 bg-gray-300 hover:bg-gray-400 text-gray-800 py-2 px-4 rounded-lg font-medium transition"
            >
              Cancelar
            </button>
            <button
              type="submit"
              className="flex-1 bg-green-600 hover:bg-green-700 text-white py-2 px-4 rounded-lg font-medium transition"
            >
              Guardar Cambios
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
