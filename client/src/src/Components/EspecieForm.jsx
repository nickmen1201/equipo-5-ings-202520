/**
 * EspecieForm Component - Species Create/Edit Form (REQ-005)
 * 
 * Modal form for creating or editing a species with validation.
 * Supports both create and edit modes with the same form.
 * 
 * Fields:
 * - Common name (required)
 * - Scientific name
 * - Description
 * - Fertilization days (required)
 * - Image URL
 * - Cycle days (required)
 * - Germination days (required)
 * - Flowering days (required)
 * - Harvest days (required)
 * - Weekly water in mm
 * 
 * @author CultivApp Team
 * @version 1.0 (REQ-005)
 */

import React, { useState, useEffect } from 'react';
import { FaTimes } from 'react-icons/fa';

/**
 * EspecieForm Component
 * 
 * @param {Object} props - Component props
 * @param {boolean} props.isOpen - Whether modal is open
 * @param {Function} props.onClose - Callback to close modal
 * @param {Function} props.onSubmit - Callback when form is submitted
 * @param {Object} props.initialData - Initial data for edit mode (null for create)
 * @param {boolean} props.isLoading - Whether form is submitting
 * @returns {JSX.Element} Form modal component
 */
export default function EspecieForm({ isOpen, onClose, onSubmit, initialData = null, isLoading = false }) {
    const [formData, setFormData] = useState({
        nombre: '',
        nombreCientifico: '',
        descripcion: '',
        diasFertilizacion: '',
        imagenUrl: '',
        cicloDias: '',
        diasGerminacion: '',
        diasFloracion: '',
        diasCosecha: '',
        aguaSemanalMm: ''
    });

    const [errors, setErrors] = useState({});

    // Load initial data when editing
    useEffect(() => {
        if (initialData) {
            setFormData({
                nombre: initialData.nombre || '',
                nombreCientifico: initialData.nombreCientifico || '',
                descripcion: initialData.descripcion || '',
                diasFertilizacion: initialData.diasFertilizacion || '',
                imagenUrl: initialData.imagenUrl || '',
                cicloDias: initialData.cicloDias || '',
                diasGerminacion: initialData.diasGerminacion || '',
                diasFloracion: initialData.diasFloracion || '',
                diasCosecha: initialData.diasCosecha || '',
                aguaSemanalMm: initialData.aguaSemanalMm || ''
            });
        } else {
            // Reset form for create mode
            setFormData({
                nombre: '',
                nombreCientifico: '',
                descripcion: '',
                diasFertilizacion: '',
                imagenUrl: '',
                cicloDias: '',
                diasGerminacion: '',
                diasFloracion: '',
                diasCosecha: '',
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

    // Validate form
    const validate = () => {
        const newErrors = {};

        if (!formData.nombre.trim()) {
            newErrors.nombre = 'El nombre es obligatorio';
        }

        if (!formData.cicloDias || formData.cicloDias <= 0) {
            newErrors.cicloDias = 'El ciclo de días es obligatorio y debe ser positivo';
        }

        if (!formData.diasGerminacion || formData.diasGerminacion <= 0) {
            newErrors.diasGerminacion = 'Los días de germinación son obligatorios y deben ser positivos';
        }

        if (!formData.diasFloracion || formData.diasFloracion <= 0) {
            newErrors.diasFloracion = 'Los días de floración son obligatorios y deben ser positivos';
        }

        if (!formData.diasCosecha || formData.diasCosecha <= 0) {
            newErrors.diasCosecha = 'Los días de cosecha son obligatorios y deben ser positivos';
        }

        if (formData.diasFertilizacion && formData.diasFertilizacion <= 0) {
            newErrors.diasFertilizacion = 'Los días de fertilización deben ser positivos';
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    // Handle form submission
    const handleSubmit = (e) => {
        e.preventDefault();
        
        if (!validate()) {
            return;
        }

        // Convert numeric fields to integers
        const submitData = {
            ...formData,
            cicloDias: parseInt(formData.cicloDias),
            diasGerminacion: parseInt(formData.diasGerminacion),
            diasFloracion: parseInt(formData.diasFloracion),
            diasCosecha: parseInt(formData.diasCosecha),
            diasFertilizacion: formData.diasFertilizacion ? parseInt(formData.diasFertilizacion) : null,
            aguaSemanalMm: formData.aguaSemanalMm ? parseInt(formData.aguaSemanalMm) : null
        };

        onSubmit(submitData);
    };

    if (!isOpen) return null;

    return (
        /* Modal Overlay */
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
            {/* Modal Container */}
            <div className="bg-white rounded-2xl shadow-xl max-w-2xl w-full max-h-[90vh] overflow-y-auto">
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
                            {/* Fertilization Days */}
                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-1">
                                    Días de Fertilización
                                </label>
                                <input
                                    type="number"
                                    name="diasFertilizacion"
                                    value={formData.diasFertilizacion}
                                    onChange={handleChange}
                                    min="1"
                                    className={`w-full border ${errors.diasFertilizacion ? 'border-red-500' : 'border-gray-300'} rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-green-400`}
                                    placeholder="Ej: 20"
                                />
                                {errors.diasFertilizacion && <p className="text-red-500 text-xs mt-1">{errors.diasFertilizacion}</p>}
                            </div>

                            {/* Cycle Days */}
                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-1">
                                    Ciclo (días) <span className="text-red-500">*</span>
                                </label>
                                <input
                                    type="number"
                                    name="cicloDias"
                                    value={formData.cicloDias}
                                    onChange={handleChange}
                                    min="1"
                                    className={`w-full border ${errors.cicloDias ? 'border-red-500' : 'border-gray-300'} rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-green-400`}
                                    placeholder="Ej: 120"
                                />
                                {errors.cicloDias && <p className="text-red-500 text-xs mt-1">{errors.cicloDias}</p>}
                            </div>

                            {/* Germination Days */}
                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-1">
                                    Germinación (días) <span className="text-red-500">*</span>
                                </label>
                                <input
                                    type="number"
                                    name="diasGerminacion"
                                    value={formData.diasGerminacion}
                                    onChange={handleChange}
                                    min="1"
                                    className={`w-full border ${errors.diasGerminacion ? 'border-red-500' : 'border-gray-300'} rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-green-400`}
                                    placeholder="Ej: 7"
                                />
                                {errors.diasGerminacion && <p className="text-red-500 text-xs mt-1">{errors.diasGerminacion}</p>}
                            </div>

                            {/* Flowering Days */}
                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-1">
                                    Floración (días) <span className="text-red-500">*</span>
                                </label>
                                <input
                                    type="number"
                                    name="diasFloracion"
                                    value={formData.diasFloracion}
                                    onChange={handleChange}
                                    min="1"
                                    className={`w-full border ${errors.diasFloracion ? 'border-red-500' : 'border-gray-300'} rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-green-400`}
                                    placeholder="Ej: 60"
                                />
                                {errors.diasFloracion && <p className="text-red-500 text-xs mt-1">{errors.diasFloracion}</p>}
                            </div>

                            {/* Harvest Days */}
                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-1">
                                    Cosecha (días) <span className="text-red-500">*</span>
                                </label>
                                <input
                                    type="number"
                                    name="diasCosecha"
                                    value={formData.diasCosecha}
                                    onChange={handleChange}
                                    min="1"
                                    className={`w-full border ${errors.diasCosecha ? 'border-red-500' : 'border-gray-300'} rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-green-400`}
                                    placeholder="Ej: 120"
                                />
                                {errors.diasCosecha && <p className="text-red-500 text-xs mt-1">{errors.diasCosecha}</p>}
                            </div>

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
