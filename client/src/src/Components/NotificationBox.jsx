import React from 'react'
import { GiCarnivorousPlant } from 'react-icons/gi'
import { formatDistanceToNow } from 'date-fns';
import { es } from 'date-fns/locale';



export default function NotificationBox({notification,markAsRead,handleDeleteNotificacion}) {
  return (
   <div
    key={notification.id}
    className={`p-4 border-b  transition-colors ${
    !notification.leida ? 'bg-[#d5e3d7]/50' : 'bg-gray-200'
    }`}
    >
        <div className="flex gap-3">
          <div className={` w-10 h-10 text-3xl text-emerald-500 rounded-full flex items-center justify-center`}>
             <GiCarnivorousPlant />
          </div>

          <div className="flex-1 min-w-0">
            <div className="flex items-start justify-between gap-2 mb-1">
              <h4 className={`font-semibold text-sm ${!notification.leida ? 'text-gray-900'  : 'text-gray-600'}`}>
                {notification.mensaje}
              </h4>
              <span className="text-xs text-black whitespace-nowrap">
                {formatDistanceToNow(new Date(notification.fecha), { addSuffix: true, locale: es })}
              </span>
            </div>

            
            <div className="flex gap-2">
              {!notification.leida && (
                <button
                  onClick={() => markAsRead(notification.id)}
                  className="text-xs text-blue-600 hover:text-blue-700 font-medium"
                >
                  Marcar como leída
                </button>
              )}
              <button
                onClick={() => handleDeleteNotificacion(notification.id)}
                className="text-xs text-red-600 hover:text-red-700 font-medium"
              >
                Eliminar
              </button>
            </div>
          </div>
        </div>
    </div>
  )
}
