const API_URL = 'http://localhost:8080/api/etapas';

const getAuthHeaders = () => {
  const token = localStorage.getItem('token');
  return {
    'Content-Type': 'application/json',
    'Authorization': token ? `Bearer ${token}` : '',
  };
};

export const createEtapa = async (etapaRequest) => {
  const res = await fetch(API_URL, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(etapaRequest),
  });
  if (!res.ok) throw new Error('Error creating etapa');
  return res.json();
};

export const createEtapasBatch = async (etapaRequests) => {
  const res = await fetch(`${API_URL}/batch`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(etapaRequests),
  });
  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || 'Error creating etapas batch');
  }
  return res.json();
};

export const getEtapasByEspecie = async (especieId) => {
  const res = await fetch(`${API_URL}/especie/${especieId}`, { headers: getAuthHeaders() });
  if (!res.ok) throw new Error('Error fetching etapas for especie');
  return res.json();
};

export const addReglaToEtapa = async (etapaId, reglaId) => {
  const res = await fetch(`${API_URL}/${etapaId}/reglas/${reglaId}`, {
    method: 'POST',
    headers: getAuthHeaders(),
  });
  if (!res.ok) throw new Error('Error adding regla to etapa');
  return res.json();
};

export const removeReglaFromEtapa = async (etapaId, reglaId) => {
  const res = await fetch(`${API_URL}/${etapaId}/reglas/${reglaId}`, {
    method: 'DELETE',
    headers: getAuthHeaders(),
  });
  if (!res.ok) throw new Error('Error removing regla from etapa');
  return res.json();
};

export const getTiposEtapas = async () => {
  const res = await fetch(`${API_URL}/tipos`, { headers: getAuthHeaders() });
  if (!res.ok) throw new Error('Error fetching tipos etapas');
  return res.json();
};

export default {
  createEtapa,
  createEtapasBatch,
  getEtapasByEspecie,
  addReglaToEtapa,
  removeReglaFromEtapa,
  getTiposEtapas,
};

