import React from 'react'
import { Link } from 'react-router-dom'

export default function PageBox({ page }) {
  return (
    <Link to={page.link} className="w-full max-w-xs">
      <div className="flex flex-col items-center bg-white rounded-2xl shadow-md overflow-hidden transition-all duration-300 hover:shadow-xl hover:-translate-y-1 cursor-pointer">
        
        {/* Imagen superior */}
        <img
          src={page.image}
          alt={page.name}
          className="w-full h-44 object-cover"
        />

        {/* Contenido inferior */}
        <div className="flex flex-col items-center justify-center px-4 py-3 bg-[#E9F7EF] w-full">
          <h2 className="text-lg font-semibold text-gray-800 mb-1">{page.name}</h2>
          <p className="text-sm text-gray-500">Explorar sección</p>
        </div>
      </div>
    </Link>
  )
}
