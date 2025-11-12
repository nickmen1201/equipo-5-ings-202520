import React from 'react'
import { Link } from 'react-router-dom'
import cropImage from '../assets/cropmage.png'

export default function CropBox({ id, percentage, image, cropName, cropSpecie, cropHectares, description }) {
  return (
    <Link to={`/cultivos/${id}`} className="w-full max-w-xs mx-auto">
      <div className="bg-white flex flex-col rounded-2xl shadow-lg overflow-hidden w-full transition-transform hover:scale-105 cursor-pointer">
        {/* Imagen superior */}
        <img
          src={image || cropImage}
          alt={cropName}
          className="w-full h-40 object-cover"
        />

        {/* Contenido central */}
        <div className="flex flex-col items-center text-center px-4 py-3">
          
          <h2 className="font-bold text-xl text-gray-800">{cropName}</h2>
          <p className="text-gray-500 text-sm mb-2">{cropSpecie}</p>
          <p className="text-gray-600 text-sm">
            {description}
          </p>
        </div>

        {/* Franja inferior tipo "stats" */}
        <div className="flex justify-around bg-emerald-700 text-white py-2 text-center text-sm font-semibold rounded-b-2xl">
          <div>
            <p>{cropHectares} ha</p>
            <span className="text-xs font-normal">ÁREA</span>
          </div>
          <div>
            <p>{Math.round(percentage * 10) / 10 || 0}%</p>
            <span className="text-xs font-normal">PROGRESO</span>
          </div>
          <div>
            <p>12</p>
            <span className="text-xs font-normal">TAREAS</span>
          </div>
        </div>
      </div>
    </Link>
  )
}
