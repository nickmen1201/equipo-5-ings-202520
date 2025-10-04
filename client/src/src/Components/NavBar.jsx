import React from 'react'
import { FaBell } from "react-icons/fa6";

export default function NavBar() {
  return (
    <nav className="bg-[#60C37B] flex justify-between items-center px-4 py-2">
      {/* Logo */}
      <div className="flex items-center gap-2">
        <div className="bg-white rounded-lg p-1">
          {/* Aquí podrías poner un ícono de planta o una imagen */}
          <span className="text-green-500 font-bold">🌱</span>
        </div>
        <span className="text-white font-semibold text-lg">CultivApp</span>
      </div>

      {/* Notificación y avatar */}
      <div className="flex items-center gap-4">
        {/* Notificación con badge */}
        <div className="relative">
            <FaBell className='text-white' />
          <span className="absolute -top-2 -right-2 bg-red-500 text-white text-xs font-bold rounded-full px-1.5">
            10
          </span>
        </div>

        {/* Avatar */}
        <img
          src="https://i.pravatar.cc/40"
          alt="user avatar"
          className="w-8 h-8 rounded-full border-2 border-white"
        />
      </div>
    </nav>
  )
}
