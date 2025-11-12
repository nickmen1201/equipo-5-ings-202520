const API_URL = 'http://localhost:8080/api/tareas';

// Helper function to get authorization headers
const getAuthHeaders = () => {
  const token = localStorage.getItem('token');
  console.log('getAuthHeaders - Token exists:', !!token);
  return {
    'Content-Type': 'application/json',
    'Authorization': token ? `Bearer ${token}` : '',
  };
};


export const ejecutarTarea = async (tareaId) => {
  const res = await fetch(`${API_URL}/ejecutar`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(tareaId),
  });
  if (!res.ok) throw new Error('Error executing tarea');
  return; 
};