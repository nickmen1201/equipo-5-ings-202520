import React, { useState, useEffect } from 'react';
import { FaArrowLeft, FaPlus } from 'react-icons/fa';
import NavBar from '../components/NavBar';
import EspecieCard from '../components/EspecieCard';
import EspecieForm from '../components/EspecieForm';
import { 
    getAllEspecies, 
    createEspecie, 
    updateEspecie, 
    deleteEspecie 
} from '../services/especiesService';
import { Link } from 'react-router-dom';

export default function Especies() {
    const [especies, setEspecies] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);
    
    // Form modal state
    const [isFormOpen, setIsFormOpen] = useState(false);
    const [editingEspecie, setEditingEspecie] = useState(null);
    const [isSubmitting, setIsSubmitting] = useState(false);
    
    // Delete confirmation state
    const [deleteConfirm, setDeleteConfirm] = useState(null);

    // Load species on mount
    useEffect(() => {
        loadEspecies();
    }, []);

    /**
     * Load all species from API
     */
    const loadEspecies = async () => {
        try {
            setLoading(true);
            setError(null);
            const data = await getAllEspecies();
            setEspecies([...data].reverse());
        } catch (err) {
            setError('Error al cargar especies: ' + err.message);
        } finally {
            setLoading(false);
        }
    }


    /**
     * Open form for creating new species
     */
    const handleCreate = () => {
        setEditingEspecie(null);
        setIsFormOpen(true);
    };

    /**
     * Open form for editing existing species
     */
    const handleEdit = (especie) => {
        setEditingEspecie(especie);
        setIsFormOpen(true);
    };

    /**
     * Close form modal
     */
    const handleCloseForm = () => {
        setIsFormOpen(false);
        setEditingEspecie(null);
    };

    /**
     * Submit form (create or update)
     */
    const handleSubmit = async (formData) => {
        try {
            setIsSubmitting(true);
            setError(null);
            
            if (editingEspecie) {
                // Update existing species
                await updateEspecie(editingEspecie.id, formData);
                setSuccess('Especie actualizada exitosamente');
            } else {
                // Create new species
                await createEspecie(formData);
                setSuccess('Especie creada exitosamente');
            }
            
            // Reload species list
            await loadEspecies();
            
            // Close form
            handleCloseForm();
            
            // Clear success message after 3 seconds
            setTimeout(() => setSuccess(null), 3000);
        } catch (err) {
            setError(err.message || 'Error al guardar especie');
        } finally {
            setIsSubmitting(false);
        }
    };

    /**
     * Show delete confirmation dialog
     */
    const handleDeleteClick = (especie) => {
        setDeleteConfirm(especie);
    };

    /** 
     * Confirm and execute deletion
     */
    const handleDeleteConfirm = async () => {
        if (!deleteConfirm) return;
        
        try {
            setError(null);
            await deleteEspecie(deleteConfirm.id);
            setSuccess('Especie eliminada exitosamente');
            
            // Reload species list
            await loadEspecies();
            
            // Clear success message after 3 seconds
            setTimeout(() => setSuccess(null), 3000);
        } catch (err) {
            // Show error (e.g., species has associated crops)
            setError(err.message || 'Error al eliminar especie');
        } finally {
            setDeleteConfirm(null);
        }
    };

    /**
     * Cancel deletion
     */
    const handleDeleteCancel = () => {
        setDeleteConfirm(null);
    };

    return (
        <div style={{backgroundImage:"url(https://www.transparenttextures.com/patterns/clean-gray-paper.png)"}} className="min-h-screen bg-gray-50 mt-14 ">
            {/* Navigation Bar */}
            <NavBar />
            
            {/* Main Content */}
            <div className="container mx-auto px-4 py-8">
                {/* Page Header */}
                <div className="flex justify-between items-center mb-8">
                    <div>
                        <Link to="/admin" className="flex items-center text-gray-600 hover:text-gray-800">
                            <FaArrowLeft className="mr-2" />
                            Volver
                        </Link>
                        <h1 className="text-3xl font-bold text-gray-800">
                            Administrar Especies
                        </h1>
                        <p className="text-gray-600 mt-1">
                            Gestiona el catálogo de especies agrícolas
                        </p>
                    </div>
                    
                    {/* Create Button */}
                    <button
                        onClick={handleCreate}
                        className="flex items-center gap-2 bg-[#60C37B] text-white px-6 py-3 rounded-lg hover:bg-[#4CAF68] transition shadow-md"
                    >
                        <FaPlus /> Nueva Especie
                    </button>
                </div>

                {/* Success Message */}
                {success && (
                    <div className="mb-6 p-4 bg-green-100 border border-green-400 text-green-700 rounded-lg">
                        {success}
                    </div>
                )}

                {/* Error Message */}
                {error && (
                    <div className="mb-6 p-4 bg-red-100 border border-red-400 text-red-700 rounded-lg">
                        {error}
                    </div>
                )}

                {/* Loading State */}
                {loading && (
                    <div className="flex justify-center items-center h-64">
                        <p className="text-gray-600 text-lg">Cargando especies...</p>
                    </div>
                )}

                {/* Empty State */}
                {!loading && especies.length === 0 && (
                    <div className="flex flex-col items-center justify-center h-64 text-center">
                        <div className="text-6xl mb-4">🌱</div>
                        <h2 className="text-xl font-semibold text-gray-700 mb-2">
                            No hay especies registradas
                        </h2>
                        <p className="text-gray-500 mb-4">
                            Comienza creando tu primera especie agrícola
                        </p>
                        <button
                            onClick={handleCreate}
                            className="flex items-center gap-2 bg-[#60C37B] text-white px-6 py-2 rounded-lg hover:bg-[#4CAF68] transition"
                        >
                            <FaPlus /> Nueva Especie
                        </button>
                    </div>
                )}

                {/* Species Grid */}
                {!loading && especies.length > 0 && (
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
                        {especies.map((especie) => (
                            <EspecieCard
                                key={especie.id}
                                especie={especie}
                                onEdit={handleEdit}
                                onDelete={handleDeleteClick}
                            />
                        ))}
                    </div>
                )}
            </div>

            {/* Form Modal */}
            <EspecieForm
                isOpen={isFormOpen}
                onClose={handleCloseForm}
                onSubmit={handleSubmit}
                initialData={editingEspecie}
                isLoading={isSubmitting}
            />

            {/* Delete Confirmation Dialog */}
            {deleteConfirm && (
                <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
                    <div className="bg-white rounded-2xl shadow-xl max-w-md w-full p-6">
                        <h3 className="text-xl font-bold text-gray-800 mb-4">
                            Confirmar Eliminación
                        </h3>
                        <p className="text-gray-600 mb-6">
                            ¿Estás seguro de que deseas eliminar la especie{' '}
                            <strong>{deleteConfirm.nombre}</strong>?
                            Esta acción no se puede deshacer.
                        </p>
                        <div className="flex gap-3">
                            <button
                                onClick={handleDeleteCancel}
                                className="flex-1 py-2 px-4 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50 transition"
                            >
                                Cancelar
                            </button>
                            <button
                                onClick={handleDeleteConfirm}
                                className="flex-1 py-2 px-4 bg-red-500 text-white rounded-lg hover:bg-red-600 transition"
                            >
                                Eliminar
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}