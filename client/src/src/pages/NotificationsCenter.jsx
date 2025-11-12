import React, { useState, useEffect } from 'react';
import { Bell, X, Check, AlertCircle, Info, CheckCircle, XCircle } from 'lucide-react';
import NotificationBox from '../Components/NotificationBox';
import { deleteNotificacion, getAllNotificaciones, toogleNotificacion,toogleAllNotificacion,deleteAllNotificacion } from '../services/notificacionService';

const NotificationCenter = ({setIsOpen}) => {
  const [notifications, setNotifications] = useState([]);
  
  const [filter, setFilter] = useState('all');

  useEffect(() => {
  const fetchNotificaciones = async () => {
    try {
      const data = await getAllNotificaciones();
      setNotifications(data);

      
    } catch (error) {
      console.error('Error al obtener notificaciones:', error);
    }
  };

  fetchNotificaciones();
}, []);

  

  const unreadCount = notifications.filter(n => !n.read).length;

  

  const markAsRead =async (id) => {
    await toogleNotificacion(id)
    
    setNotifications(notifications.map(n => 
      n.id === id ? { ...n, leida: true } : n
    ));
  };

  const markAllAsRead = async () => {
    await toogleAllNotificacion();
    setNotifications(notifications.map(n => ({ ...n, leida: true })));
  };

  const handleDeleteNotification = async(id) => {
    await deleteNotificacion(id)
    setNotifications(notifications.filter(n => n.id !== id));
  };

  const clearAll = async() => {
    await deleteAllNotificacion();
    setNotifications([]);
  };

  const filteredNotifications = notifications.filter(n => {
    if (filter === 'unread') return !n.leida;
    if (filter === 'read') return n.leida;
    return true;
  });

  return (
          <div className="absolute right-0 mt-4 w-[500px] bg-white rounded-2xl shadow-2xl overflow-hidden z-50 animate-in fade-in slide-in-from-top-5 duration-300">
            {/* Header */}
            <div style={{backgroundImage: `url("https://www.transparenttextures.com/patterns/skulls.png"),linear-gradient(to right, #059669, #15803d)`}} className="p-4 bg-cover bg-blend-overlay">
              <div className="flex items-center justify-between mb-3">
                <h3 className="text-white font-bold text-lg">Notificaciones</h3>
                <button
                  onClick={() => setIsOpen(false)}
                  className="text-white hover:bg-white/20 rounded-lg p-1 transition-colors"
                >
                  <X className="w-5 h-5" />
                </button>
              </div>
              
              {/* Filtros */}
              <div className="flex gap-2">
                <button
                  onClick={() => setFilter('all')}
                  className={`px-3 py-1 rounded-lg text-sm transition-colors ${
                    filter === 'all' 
                      ? 'bg-white text-green-600 font-semibold' 
                      : 'bg-white/20 text-white hover:bg-white/30'
                  }`}
                >
                  Todas
                </button>
                <button
                  onClick={() => setFilter('unread')}
                  className={`px-3 py-1 rounded-lg text-sm transition-colors ${
                    filter === 'unread' 
                      ? 'bg-white text-green-600 font-semibold' 
                      : 'bg-white/20 text-white hover:bg-white/30'
                  }`}
                >
                  No leídas
                </button>
                <button
                  onClick={() => setFilter('read')}
                  className={`px-3 py-1 rounded-lg text-sm transition-colors ${
                    filter === 'read' 
                      ? 'bg-white text-green-600 font-semibold' 
                      : 'bg-white/20 text-white hover:bg-white/30'
                  }`}
                >
                  Leídas
                </button>
              </div>
            </div>

            {/* Acciones rápidas */}
            {notifications.length > 0 && (
              <div className="px-4 py-2 bg-gray-50 border-b flex justify-between items-center">
                <button
                  onClick={markAllAsRead}
                  className="text-sm text-blue-600 hover:text-blue-700 font-medium flex items-center gap-1"
                >
                  <Check className="w-4 h-4" />
                  Marcar todas como leídas
                </button>
                <button
                  onClick={clearAll}
                  className="text-sm text-red-600 hover:text-red-700 font-medium"
                >
                  Limpiar todo
                </button>
              </div>
            )}

            {/* Lista de notificaciones */}
            <div className="max-h-96 overflow-y-auto">
              {filteredNotifications.length === 0 ? (
                <div className="p-8 text-center text-gray-400">
                  <Bell className="w-12 h-12 mx-auto mb-3 opacity-50" />
                  <p className="font-medium">No hay notificaciones</p>
                </div>
              ) : (
                filteredNotifications.reverse().map((notification) => (
                  <NotificationBox  notification={notification} markAsRead={markAsRead} handleDeleteNotificacion={handleDeleteNotification} />
                ))

              )}
            </div>
          </div>
  );
};
export default NotificationCenter