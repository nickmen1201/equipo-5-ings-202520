/**
 * EspecieCard Component - Species Card Display (REQ-005)
 * 
 * Displays a single species as a card with:
 * - Image (or placeholder if no image)
 * - Common name
 * - Scientific name
 * - Description (truncated)
 * - Edit button
 * - Delete button (with confirmation)
 * 
 * @author CultivApp Team
 * @version 1.0 (REQ-005)
 */

import React from 'react';
import { FaEdit, FaTrash } from 'react-icons/fa';

/**
 * EspecieCard Component
 * 
 * @param {Object} props - Component props
 * @param {Object} props.especie - Species data object
 * @param {number} props.especie.id - Species ID
 * @param {string} props.especie.nombre - Common name
 * @param {string} props.especie.nombreCientifico - Scientific name
 * @param {string} props.especie.descripcion - Description
 * @param {string} props.especie.imagenUrl - Image URL
 * @param {number} props.especie.diasFertilizacion - Fertilization days
 * @param {Function} props.onEdit - Callback when Edit button is clicked
 * @param {Function} props.onDelete - Callback when Delete button is clicked
 * @returns {JSX.Element} Species card component
 */
export default function EspecieCard({ especie, onEdit, onDelete }) {
    // Truncate description to 120 characters
    const truncateText = (text, maxLength) => {
        if (!text) return "Sin descripción";
        return text.length > maxLength 
            ? text.substring(0, maxLength) + "..." 
            : text;
    };

    // Default placeholder image for species without image
    const defaultImage = "https://via.placeholder.com/300x200/60C37B/ffffff?text=🌱";

    return (
        <div className="bg-white rounded-xl shadow-md overflow-hidden hover:shadow-lg transition-shadow duration-300">
            {/* Species Image */}
            <div className="h-48 bg-gray-200 overflow-hidden">
                <img
                    src={especie.imagenUrl || defaultImage}
                    alt={especie.nombre}
                    className="w-full h-full object-cover"
                    onError={(e) => { e.target.src = defaultImage; }}
                />
            </div>

            {/* Card Content */}
            <div className="p-4">
                {/* Common Name */}
                <h3 className="text-xl font-bold text-gray-800 mb-1">
                    {especie.nombre}
                </h3>

                {/* Scientific Name */}
                {especie.nombreCientifico && (
                    <p className="text-sm italic text-gray-500 mb-2">
                        {especie.nombreCientifico}
                    </p>
                )}

                {/* Description */}
                <p className="text-gray-600 text-sm mb-3 min-h-[60px]">
                    {truncateText(especie.descripcion, 120)}
                </p>

                {/* Fertilization Info */}
                {especie.diasFertilizacion && (
                    <div className="mb-4">
                        <span className="text-xs bg-green-100 text-green-700 px-2 py-1 rounded-full">
                            🌿 Fertilización cada {especie.diasFertilizacion} días
                        </span>
                    </div>
                )}

                {/* Action Buttons */}
                <div className="flex gap-2">
                    {/* Edit Button */}
                    <button
                        onClick={() => onEdit(especie)}
                        className="flex-1 flex items-center justify-center gap-2 bg-blue-500 text-white py-2 px-4 rounded-lg hover:bg-blue-600 transition"
                        aria-label={`Editar ${especie.nombre}`}
                    >
                        <FaEdit /> Editar
                    </button>

                    {/* Delete Button */}
                    <button
                        onClick={() => onDelete(especie)}
                        className="flex items-center justify-center gap-2 bg-red-500 text-white py-2 px-4 rounded-lg hover:bg-red-600 transition"
                        aria-label={`Eliminar ${especie.nombre}`}
                    >
                        <FaTrash />
                    </button>
                </div>
            </div>
        </div>
    );
}