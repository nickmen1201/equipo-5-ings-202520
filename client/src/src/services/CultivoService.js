const API_URL = 'http://localhost:8080/api/cultivos';

// Helper function to get authorization headers
const getAuthHeaders = () => {
  const token = localStorage.getItem('token');
  return {
    'Content-Type': 'application/json',
    'Authorization': token ? `Bearer ${token}` : '',
  };
};

export const getCultivosByUsuarioId = async (usuarioId) => {
  try {
    const response = await fetch(`${API_URL}/usuario/${usuarioId}`, {
      headers: getAuthHeaders(),
    });
    if (!response.ok) {
      throw new Error('Error al obtener los cultivos del usuario');
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Error fetching cultivos:', error);
    throw error;
  }
};

export const getAllCultivos = async () => {
  try {
    const response = await fetch(API_URL, {
      headers: getAuthHeaders(),
    });
    if (!response.ok) {
      throw new Error('Error al obtener los cultivos');
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Error fetching cultivos:', error);
    throw error;
  }
};

export const getCultivoById = async (id) => {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      headers: getAuthHeaders(),
    });
    if (!response.ok) {
      throw new Error('Error al obtener el cultivo');
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Error fetching cultivo:', error);
    throw error;
  }
};

export const createCultivo = async (cultivo) => {
  try {
    const response = await fetch(API_URL, {
      method: 'POST',
      headers: getAuthHeaders(),
      body: JSON.stringify(cultivo),
    });
    if (!response.ok) {
      throw new Error('Error al crear el cultivo');
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Error creating cultivo:', error);
    throw error;
  }
};

export const updateCultivo = async (id, cultivo) => {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      method: 'PUT',
      headers: getAuthHeaders(),
      body: JSON.stringify(cultivo),
    });
    if (!response.ok) {
      throw new Error('Error al actualizar el cultivo');
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Error updating cultivo:', error);
    throw error;
  }
};

export const deleteCultivo = async (id) => {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      method: 'DELETE',
      headers: getAuthHeaders(),
    });
    if (!response.ok) {
      throw new Error('Error al eliminar el cultivo');
    }
  } catch (error) {
    console.error('Error deleting cultivo:', error);
    throw error;
  }
};

export const toggleEstadoCultivo = async (id) => {
  try {
    const response = await fetch(`${API_URL}/${id}/estado`, {
      method: 'PATCH',
      headers: getAuthHeaders(),
    });
    if (!response.ok) {
      throw new Error('Error al cambiar el estado del cultivo');
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Error toggling estado:', error);
    throw error;
  }
};
