const BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080';
const API_URL = `${BASE_URL}/api/cultivos`;


// Helper function to get authorization headers
const getAuthHeaders = () => {
  const token = localStorage.getItem('token');
  console.log('getAuthHeaders - Token exists:', !!token);
  return {
    'Content-Type': 'application/json',
    'Authorization': token ? `Bearer ${token}` : '',
  };
};

export const getCultivosByUsuarioId = async (usuarioId) => {
  try {
    console.log('Calling API with usuarioId:', usuarioId);
    const url = `${API_URL}/usuario/${usuarioId}`;
    console.log('Full URL:', url);
    const response = await fetch(url, {
      headers: getAuthHeaders(),
    });
    console.log('Response status:', response.status);
    if (!response.ok) {
      throw new Error('Error al obtener los cultivos del usuario');
    }
    const data = await response.json();
    console.log('Data received from API:', data);
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
    console.log('Creating cultivo with data:', cultivo);
    const response = await fetch(API_URL, {
      method: 'POST',
      headers: getAuthHeaders(),
      body: JSON.stringify(cultivo),
    });
    console.log('Create response status:', response.status);
    if (!response.ok) {
      const errorText = await response.text();
      console.error('Create cultivo error:', errorText);
      throw new Error('Error al crear el cultivo');
    }
    const data = await response.json();
    console.log('Created cultivo:', data);
    return data;
  } catch (error) {
    console.error('Error creating cultivo:', error);
    throw error;
  }
};

export const updateCultivo = async (id, cultivo) => {
  try {
    console.log('Updating cultivo', id, 'with data:', cultivo);
    const response = await fetch(`${API_URL}/${id}`, {
      method: 'PUT',
      headers: getAuthHeaders(),
      body: JSON.stringify(cultivo),
    });
    console.log('Update response status:', response.status);
    if (!response.ok) {
      const errorText = await response.text();
      console.error('Update cultivo error:', errorText);
      throw new Error('Error al actualizar el cultivo');
    }
    const data = await response.json();
    console.log('Updated cultivo:', data);
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
      const errorText = await response.text();
      console.error('Toggle estado error:', response.status, errorText);
      throw new Error('Error al cambiar el estado del cultivo');
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Error toggling estado:', error);
    throw error;
  }
};
