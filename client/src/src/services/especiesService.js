/**
 * Especies Service - API Layer for Species Management (REQ-005)
 * 
 * This service handles all species-related API calls from the frontend to the backend.
 * It provides a clean interface for UI components to perform CRUD operations on species
 * without worrying about HTTP details, error handling, or token management.
 * 
 * REQ-005 Integration:
 * - Communicates with backend /api/especies endpoints
 * - Sends/receives species data (name, scientific name, description, etc.)
 * - Includes JWT token in Authorization header for protected operations
 * 
 * @author CultivApp Team
 * @version 1.0 (REQ-005)
 */

const API_URL = "http://localhost:8080/api/especies";

/**
 * Get JWT token from localStorage for authenticated requests
 * @returns {string|null} JWT token or null if not logged in
 */
const getAuthToken = () => {
    return localStorage.getItem("token");
};

/**
 * Get authorization headers for API requests
 * @returns {Object} Headers object with Content-Type and Authorization
 */
const getAuthHeaders = () => {
    const token = getAuthToken();
    return {
        "Content-Type": "application/json",
        ...(token && { "Authorization": `Bearer ${token}` })
    };
};

/**
 * Get all species from the catalog
 * 
 * @returns {Promise<Array>} Promise that resolves to array of species objects
 * @throws {Error} Throws error if request fails
 */
export const getAllEspecies = async () => {
    try {
        const response = await fetch(API_URL, {
            method: "GET",
            headers: getAuthHeaders()
        });
        
        if (!response.ok) {
            throw new Error("Error al obtener especies");
        }
        
        return await response.json();
    } catch (error) {
        console.error("Error fetching especies:", error);
        throw error;
    }
};

/**
 * Get a single species by ID
 * 
 * @param {number} id - Species ID
 * @returns {Promise<Object>} Promise that resolves to species object
 * @throws {Error} Throws error if request fails
 */
export const getEspecieById = async (id) => {
    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: "GET",
            headers: getAuthHeaders()
        });
        
        if (!response.ok) {
            throw new Error("Especie no encontrada");
        }
        
        return await response.json();
    } catch (error) {
        console.error("Error fetching especie:", error);
        throw error;
    }
};

/**
 * Create a new species (Admin only)
 * 
 * @param {Object} especieData - Species data object
 * @param {string} especieData.nombre - Common name (required)
 * @param {string} especieData.nombreCientifico - Scientific name
 * @param {string} especieData.descripcion - Description
 * @param {number} especieData.diasFertilizacion - Recommended fertilization days
 * @param {string} especieData.imagenUrl - Image URL
 * @param {number} especieData.cicloDias - Growth cycle days (required)
 * @param {number} especieData.diasGerminacion - Germination days (required)
 * @param {number} especieData.diasFloracion - Flowering days (required)
 * @param {number} especieData.diasCosecha - Harvest days (required)
 * @param {number} especieData.aguaSemanalMm - Weekly water in mm
 * @returns {Promise<Object>} Promise that resolves to created species object
 * @throws {Error} Throws error if creation fails
 */
export const createEspecie = async (especieData) => {
    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: getAuthHeaders(),
            body: JSON.stringify(especieData)
        });
        
        if (!response.ok) {
            const errorData = await response.json().catch(() => ({}));
            throw new Error(errorData.message || "Error al crear especie");
        }
        
        return await response.json();
    } catch (error) {
        console.error("Error creating especie:", error);
        throw error;
    }
};

/**
 * Update an existing species (Admin only)
 * 
 * @param {number} id - Species ID
 * @param {Object} especieData - Updated species data
 * @returns {Promise<Object>} Promise that resolves to updated species object
 * @throws {Error} Throws error if update fails
 */
export const updateEspecie = async (id, especieData) => {
    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: "PUT",
            headers: getAuthHeaders(),
            body: JSON.stringify(especieData)
        });
        
        if (!response.ok) {
            const errorData = await response.json().catch(() => ({}));
            throw new Error(errorData.message || "Error al actualizar especie");
        }
        
        return await response.json();
    } catch (error) {
        console.error("Error updating especie:", error);
        throw error;
    }
};

/**
 * Delete a species (Admin only)
 * 
 * @param {number} id - Species ID
 * @returns {Promise<void>} Promise that resolves when deletion is complete
 * @throws {Error} Throws error if deletion fails (e.g., species has associated crops)
 */
export const deleteEspecie = async (id) => {
    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: "DELETE",
            headers: getAuthHeaders()
        });
        
        if (!response.ok) {
            // Handle 409 Conflict: species has associated crops
            if (response.status === 409) {
                const errorData = await response.json();
                throw new Error(errorData.message || "No se puede eliminar: tiene cultivos asociados");
            }
            throw new Error("Error al eliminar especie");
        }
    } catch (error) {
        console.error("Error deleting especie:", error);
        throw error;
    }
};
