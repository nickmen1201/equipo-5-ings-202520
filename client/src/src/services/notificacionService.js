const API_URL = 'http://localhost:8080/api/notificaciones';

const getAuthHeaders = () => {
  const token = localStorage.getItem('token');
  return {
    'Content-Type': 'application/json',
    'Authorization': token ? `Bearer ${token}` : '',
  };
};


export const getAllNotificaciones = async () => {
  try {
    const userString= localStorage.getItem('user');
    const user = JSON.parse(userString);
    const response = await fetch(`${API_URL}/${user.id}`, {
      headers: getAuthHeaders(),
    });
    if (!response.ok) {
      throw new Error('Error al obtener las notificaciones');
    }
    const data = await response.json();
    return data;
  }
  catch (error) {
    console.error('Error fetching Notificaciones:', error);
    throw error;
  }
};
export const deleteNotificacion = async (id) => {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      headers: getAuthHeaders(),
      method: 'DELETE'
    });
    if (!response.ok) {
      throw new Error('Error al eliminar las notificaciones');
    }
  }
  catch (error) {
    console.error('Error deleting Notifitions:', error);
    throw error;
  }
};

export const deleteAllNotificacion = async () => {
  try {
    const userString= localStorage.getItem('user');
    const user = JSON.parse(userString);
    const response = await fetch(`${API_URL}/usuario/${user.id}`, {
      headers: getAuthHeaders(),
      method: 'DELETE'
    });
    if (!response.ok) {
      throw new Error('Error al eliminar todas las notificaciones');
    }
  }
  catch (error) {
    console.error('Error deleting all Notifitions:', error);
    throw error;
  }
};



export const toogleNotificacion = async (id) => {
  try {
    const response = await fetch(`${API_URL}/${id}/leido`, {
      headers: getAuthHeaders(),
    });
    if (!response.ok) {
      throw new Error('Error al cambiar el estado de la notificación');
    }
    
  }
  catch (error) {
    console.error('Error toogling notifications:', error);
    throw error;
  }
};

export const toogleAllNotificacion = async () => {
  try {
    const userString= localStorage.getItem('user');
    const user = JSON.parse(userString);
    const response = await fetch(`${API_URL}/usuario/leido/${user.id}`, {
      headers: getAuthHeaders(),
    });
    if (!response.ok) {
      throw new Error('Error al cambiar el estado de todas la notificación');
    }
    
  }
  catch (error) {
    console.error('Error toogling all notifications:', error);
    throw error;
  }
};
export default {getAllNotificaciones,toogleNotificacion,deleteNotificacion,toogleAllNotificacion,deleteAllNotificacion};