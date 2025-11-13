import React from 'react';
import { FaEdit, FaTrash } from 'react-icons/fa';

export default function EspecieCard({ especie, onEdit, onDelete }) {

    // Truncate description to 120 characters
    const truncateText = (text, maxLength) => {
        if (!text) return "Sin descripción";
        return text.length > maxLength 
            ? text.substring(0, maxLength) + "..." 
            : text;
    };

 ;


    return (
        <div  className="bg-white rounded-xl shadow-md overflow-hidden hover:shadow-lg transition-shadow duration-300">
            {/* Species Image */}
            <div className="h-48 bg-gray-200 overflow-hidden">
                <img
                    src={especie.imagenUrl || "https://assets.weforum.org/article/image/0TyeFjEH-JWAtAkDM2avJddpN9K6weGrlYTefabtFi0.jpeg"}
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