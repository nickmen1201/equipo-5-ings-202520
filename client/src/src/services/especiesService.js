const BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080';
const API_URL = `${BASE_URL}/api/admin/especies`;
const PUBLIC_API_URL = `${BASE_URL}/api/especies`;


/**
 * Get authorization header with JWT token
 */
function getAuthHeaders() {
  const token = localStorage.getItem("token");
  return {
    "Content-Type": "application/json",
    "Authorization": token ? `Bearer ${token}` : ""
  };
}

/**
 * Get all species (public endpoint for regular users)
 */
export async function getAllEspecies() {
  try {
    const response = await fetch(PUBLIC_API_URL, {
      method: "GET",
      headers: getAuthHeaders()
    });

    if (!response.ok) {
      throw new Error("Error al cargar especies");
    }

    const data = await response.json();
    return data;
  } catch (error) {
    throw error;
  }
}

/**
 * Get all species (admin only - admin endpoint)
 */
export async function getAllEspeciesAdmin() {
  try {
    const response = await fetch(API_URL, {
      method: "GET",
      headers: getAuthHeaders()
    });

    if (!response.ok) {
      if (response.status === 401) {
        throw new Error("No autorizado. Por favor inicia sesión.");
      }
      if (response.status === 403) {
        throw new Error("No tienes permisos para acceder a este recurso.");
      }
      throw new Error("Error al cargar especies");
    }

    const data = await response.json();
    return data;
  } catch (error) {
    throw error;
  }
}

/**
 * Get single species by ID (admin only)
 */
export async function getEspecieById(id) {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      method: "GET",
      headers: getAuthHeaders()
    });

    if (!response.ok) {
      if (response.status === 404) {
        throw new Error("Especie no encontrada");
      }
      throw new Error("Error al cargar especie");
    }

    const data = await response.json();
    return data;
  } catch (error) {
    throw error;
  }
}

/**
 * Create new species (admin only)
 */
export async function createEspecie(especieData) {
  try {
    const response = await fetch(API_URL, {
      method: "POST",
      headers: getAuthHeaders(),
      body: JSON.stringify(especieData)
    });

    if (!response.ok) {
      if (response.status === 400) {
        const error = await response.json();
        throw new Error(error.message || "Datos inválidos");
      }
      if (response.status === 401) {
        throw new Error("No autorizado. Por favor inicia sesión.");
      }
      if (response.status === 403) {
        throw new Error("No tienes permisos para crear especies.");
      }
      throw new Error("Error al crear especie");
    }

    const data = await response.json();
    return data;
  } catch (error) {
    throw error;
  }
}

/**
 * Update existing species (admin only)
 */
export async function updateEspecie(id, especieData) {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      method: "PUT",
      headers: getAuthHeaders(),
      body: JSON.stringify(especieData)
    });

    if (!response.ok) {
      if (response.status === 400) {
        const error = await response.json();
        throw new Error(error.message || "Datos inválidos");
      }
      if (response.status === 404) {
        throw new Error("Especie no encontrada");
      }
      if (response.status === 401) {
        throw new Error("No autorizado. Por favor inicia sesión.");
      }
      if (response.status === 403) {
        throw new Error("No tienes permisos para actualizar especies.");
      }
      throw new Error("Error al actualizar especie");
    }

    const data = await response.json();
    return data;
  } catch (error) {
    throw error;
  }
}

/**
 * Delete species (admin only)
 */
export async function deleteEspecie(id) {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      method: "DELETE",
      headers: getAuthHeaders()
    });

    if (!response.ok) {
      if (response.status === 404) {
        throw new Error("Especie no encontrada");
      }
      if (response.status === 401) {
        throw new Error("No autorizado. Por favor inicia sesión.");
      }
      if (response.status === 403) {
        throw new Error("No tienes permisos para eliminar especies.");
      }
      if (response.status === 409) {
        throw new Error("No se puede eliminar: hay cultivos asociados a esta especie.");
      }
      throw new Error("Error al eliminar especie");
    }

    return true;
  } catch (error) {
    throw error;
  }
}
