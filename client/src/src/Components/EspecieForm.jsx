import React, { useState, useEffect } from 'react';
import { FaTimes } from 'react-icons/fa';
import CropsStage from './CropsStage';

export default function EspecieForm({ isOpen, onClose, onSubmit: parentOnSubmit, initialData = null, isLoading = false }) {
    const [formData, setFormData] = useState({
        nombre: '',
        nombreCientifico: '',
        descripcion: '',
        imagenUrl: '',
        aguaSemanalMm: ''
    });

    const [errors, setErrors] = useState({});
    const [etapasFromCrops, setEtapasFromCrops] = useState([]);

    useEffect(() => {
      
        if (initialData) {
            setFormData({
                nombre: initialData.nombre || '',
                nombreCientifico: initialData.nombreCientifico || '',
                descripcion: initialData.descripcion || '',
                imagenUrl: initialData.imagenUrl || '',
                aguaSemanalMm: initialData.aguaSemanalMm || ''
            });
        } else {
            // Reset form for create mode
            setFormData({
                nombre: '',
                nombreCientifico: '',
                descripcion: '',
                imagenUrl: '',
                aguaSemanalMm: ''
            });
        }
        setErrors({});
    }, [initialData, isOpen]);

    // Handle input changes
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
        // Clear error for this field
        if (errors[name]) {
            setErrors(prev => ({
                ...prev,
                [name]: null
            }));
        }
    };

    // Validate form    añadir las etapas
    const validate = () => {
        const newErrors = {};

        if (!formData.nombre.trim()) {
            newErrors.nombre = 'El nombre es obligatorio';
        }

        if (etapasFromCrops.length < 3) {
            newErrors.etapas = 'Se requieren al menos 3 etapas.';
        }

        if(!newErrors.etapas){
        for (const etapa of etapasFromCrops) {
            if (!etapa.reglaIds || etapa.reglaIds.length < 1) {
            newErrors.etapas = 'Cada etapa debe tener al menos 1 regla.';
            }
        }
        
        }
        // if(!newErrors.etapas){
        // for (const etapa of etapasFromCrops) {
        //     if (etapa.duracionDias <= 0) {
        //     newErrors.etapas = 'Cada etapa debe durar al menos 1 dia.';
        //     }
        // }
        
        // }

        

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    // Handle form submission
   const handleSubmit = async (e) => {
        e.preventDefault();
        console.log(errors)

        console.log(etapasFromCrops)
        if (!validate()) return;

        const submitData = { ...formData, etapas: etapasFromCrops };

        try {
            await parentOnSubmit(submitData);
        } catch (error) {
            console.error("Error saving especie:", error);
            setErrors({ form: 'Error al guardar la especie. Inténtalo de nuevo.' });
        }
    }

    if (!isOpen) return null

    return (
        /* Modal Overlay */
        <div  className="fixed inset-0 backdrop-blur-sm bg-black/55  flex items-center justify-center z-50 p-4">
            {/* Modal Container */}
            <div  className="bg-white  rounded-2xl shadow-xl max-w-2xl w-full max-h-[90vh] ">
                {/* Modal Header */}
                <div className="flex justify-between items-center p-6 border-b">
                    <h2 className="text-2xl font-bold text-gray-800">
                        {initialData ? 'Editar Especie' : 'Nueva Especie'}
                    </h2>
                    <button
                        onClick={onClose}
                        className="text-gray-500 hover:text-gray-700 transition"
                        aria-label="Cerrar"
                    >
                        <FaTimes size={24} />
                    </button>
                </div>

                {/* Modal Body - Form */}
                <form onSubmit={handleSubmit} className="p-6">
                    <div className="space-y-4">
                        {/* Common Name */}
                        <div>
                            <label className="block text-sm font-medium text-gray-700 mb-1">
                                Nombre Común <span className="text-red-500">*</span>
                            </label>
                            <input
                                type="text"
                                name="nombre"
                                value={formData.nombre}
                                onChange={handleChange}
                                className={`w-full border ${errors.nombre ? 'border-red-500' : 'border-gray-300'} rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-green-400`}
                                placeholder="Ej: Maíz"
                            />
                            {errors.nombre && <p className="text-red-500 text-xs mt-1">{errors.nombre}</p>}
                        </div>

                        {/* Scientific Name */}
                        <div>
                            <label className="block text-sm font-medium text-gray-700 mb-1">
                                Nombre Científico
                            </label>
                            <input
                                type="text"
                                name="nombreCientifico"
                                value={formData.nombreCientifico}
                                onChange={handleChange}
                                className="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-green-400"
                                placeholder="Ej: Zea mays"
                            />
                        </div>

                        {/* Description */}
                        <div>
                            <label className="block text-sm font-medium text-gray-700 mb-1">
                                Descripción
                            </label>
                            <textarea
                                name="descripcion"
                                value={formData.descripcion}
                                onChange={handleChange}
                                rows="3"
                                className="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-green-400"
                                placeholder="Descripción de la especie..."
                            />
                        </div>

                        {/* Image URL */}
                        <div>
                            <label className="block text-sm font-medium text-gray-700 mb-1">
                                URL de Imagen
                            </label>
                            <input
                                type="url"
                                name="imagenUrl"
                                value={formData.imagenUrl}
                                onChange={handleChange}
                                className="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-green-400"
                                placeholder="https://ejemplo.com/imagen.jpg"
                            />
                        </div>

                        {/* Growth Parameters - Grid */}
                        <div className="grid grid-cols-2 gap-4">
                        {/* Weekly Water */}
                        <div>
                            <label className="block text-sm font-medium text-gray-700 mb-1">
                            Agua Semanal (mm)
                            </label>
                            <input
                            type="number"
                            name="aguaSemanalMm"
                            value={formData.aguaSemanalMm}
                            onChange={handleChange}
                            min="0"
                            className="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-green-400"
                            placeholder="Ej: 50"
                            />
                        </div>

                        {/* CropsStage */}
                        <div>
                            <CropsStage onEtapasChange={setEtapasFromCrops} />
                            {errors.etapas && (
                            <p className="text-red-500 text-xs mt-1 italic">{errors.etapas}</p>
                            )}
                        </div>
                        </div>
                        <div>
                            {initialData && initialData.etapas && initialData.etapas.map(etapa => (<div>{etapa.nombre}</div>))}
                        </div>
                    </div>
                    

                    {/* Form Actions */}
                    <div className="flex gap-3 mt-6">
                        <button
                            type="button"
                            onClick={onClose}
                            className="flex-1 py-2 px-4 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50 transition"
                            disabled={isLoading}
                        >
                            Cancelar
                        </button>
                        <button
                            type="submit"
                            className="flex-1 py-2 px-4 bg-[#60C37B] text-white rounded-lg hover:bg-[#4CAF68] transition disabled:opacity-50 disabled:cursor-not-allowed"
                            disabled={isLoading}
                        >
                            {isLoading ? 'Guardando...' : initialData ? 'Actualizar' : 'Crear'}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}