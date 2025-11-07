import React from 'react'
import { BiEdit } from 'react-icons/bi'
import { FaDroplet, FaFlask, FaLeaf, FaSeedling, FaTrash, FaWrench } from 'react-icons/fa6'
import { GiFarmTractor } from 'react-icons/gi';




export default function RuleBox({descripcion,tipo,intervaloDias,id,onDelete}) {

   
  const handleDelete = () => {
    if (onDelete) onDelete(id)
  }

  const tipos = {
    MANTENIMIENTO: {
      signo: <FaWrench className="w-3.5 h-3.5" />,
      barra: "bg-gray-700",
      pillBg: "bg-gray-200",
      pillText: "text-gray-700"
    },
    RIEGO: {
      signo: <FaDroplet className="w-3.5 h-3.5" />,
      barra: "bg-blue-700",
      pillBg: "bg-blue-50",
      pillText: "text-blue-700"
    },
    COSECHA: {
      signo: <FaLeaf className="w-3.5 h-3.5" />,
      barra: "bg-green-700",
      pillBg: "bg-green-50",
      pillText: "text-green-700"
    },
    FERTILIZACION: {
      signo: <FaFlask className="w-3.5 h-3.5" />,
      barra: "bg-yellow-600",
      pillBg: "bg-yellow-50",
      pillText: "text-yellow-700"
      
    },
    PREPARACION: {
      signo: <GiFarmTractor className="w-3.5 h-3.5" />,
      barra: "bg-orange-700",
      pillBg: "bg-orange-50",
      pillText: "text-orange-700"
    },
    SIEMBRA: {
      signo: <FaSeedling className="w-3.5 h-3.5" />,
      barra: "bg-lime-700",
      pillBg: "bg-lime-50",
      pillText: "text-lime-700"
    },
  }


  return (
  <div className="bg-white rounded-lg shadow-sm border border-gray-200 overflow-hidden hover:shadow-md hover:shadow-blue-100 transition-shadow">
      <div className="flex">
        <div className={`w-1.5 ${tipos[tipo].barra}`}></div>
        <div className="flex-1 p-4">
          <div className="flex items-start justify-between">
            <div className="flex-1">
              <h3 className="font-semibold text-gray-900 mb-2">{descripcion}</h3>
              <div className="flex items-center gap-2 mb-3">
                <span className={`inline-flex items-center gap-1 px-2.5 py-1 rounded-full text-xs font-medium ${tipos[tipo].pillBg} ${tipos[tipo].pillText}  border border-black`} >
                  {tipos[tipo].signo}
                  {tipo}
                </span>
                <span className="text-sm text-gray-500">•</span>
                <span className="text-sm text-gray-600">Cada {intervaloDias} días</span>
              </div>
            </div>
            <div className="flex gap-2 ml-4">
              <button className="p-2 text-gray-400 hover:text-blue-600 hover:bg-blue-50 rounded-md transition-colors">
                <BiEdit className="w-5 h-5" />
              </button>
              <button onClick={handleDelete} className="p-2 text-gray-400 hover:text-red-600 hover:bg-red-50 rounded-md transition-colors">
                <FaTrash  className="w-5 h-5" />
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}
