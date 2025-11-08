const API_URL = 'http://localhost:8080/api/reglas';

// Helper function to get authorization headers
const getAuthHeaders = () => {
  const token = localStorage.getItem('token');
  console.log('getAuthHeaders - Token exists:', !!token);
  return {
    'Content-Type': 'application/json',
    'Authorization': token ? `Bearer ${token}` : '',
  };
};


export const getAllReglas = async () => {
  try {
    const response = await fetch(API_URL, {
      headers: getAuthHeaders(),
    });
    if (!response.ok) {
      throw new Error('Error al obtener las reglas');
    }
    const data = await response.json();
    return data;
  }
  catch (error) {
    console.error('Error fetching Reglas:', error);
    throw error;
  }
};
export const getTiposDeReglas = async () => {
  try {
    const response = await fetch(`${API_URL}/tipos`, {
      headers: getAuthHeaders(),
    });
    if (!response.ok) {
      throw new Error('Error al obtener los tipos de reglas');
    }
    const data = await response.json();
    return data;
  }
  catch (error) {
    console.error('Error fetching Reglas:', error);
    throw error;
  }
};



export const createRegla = async (regla) => {
  try {
    const response = await fetch(API_URL, {
      method: 'POST',
      headers: getAuthHeaders(),
      body: JSON.stringify(regla),
    });
    if (!response.ok) {
      const errorText = await response.text();
      throw new Error('Error al crear la regla');
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Error creating Regla:', error);
    throw error;
  }
};



export const deleteRegla = async (id) => {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      method: 'DELETE',
      headers: getAuthHeaders(),
    });
    if (!response.ok) {
      throw new Error('Error al eliminar la regla');
    }
  } catch (error) {
    console.error('Error deleting regla:', error);
    throw error;
  }
};
