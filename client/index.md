# CultivApp - Frontend (Cliente)

## Descripción General
Aplicación frontend basada en React para la gestión de cultivos agrícolas, construida con Vite, React Router v7 y Tailwind CSS v4.

## Stack Tecnológico
- React 19.x
- Vite 5.4.20
- React Router v7
- Tailwind CSS v4
- Lucide React (icons)
- Date-fns (date formatting)

## Instalación

### Prerrequisitos
- Node.js 18.x or 20.x
- npm (included with Node.js)

### Pasos
```powershell
# Navegar al directorio del cliente
cd client/src

# Instalar dependencias
npm install
```

## Ejecución de la Aplicación

### Modo Desarrollo
```powershell
npm run dev
```
The application will start on `http://localhost:5173`

### Production Build
```powershell
npm run build
```

### Preview Production Build
```powershell
npm run preview
```

## Project Structure
```
src/
├── components/     # Reusable UI components
├── pages/          # Page components (routes)
├── services/       # API service layer
├── context/        # React Context providers
└── assets/         # Static assets
```

## Key Features
- User authentication (login/register)
- Species catalog management
- Crop CRUD operations
- Growth stage tracking with progress visualization
- Task management based on crop stages
- Responsive design

## Environment Variables
```env
VITE_API_URL=http://localhost:8080/api
```

## Standards and Best Practices
- ESLint for code quality
- Component-based architecture
- Service layer for API calls
- Context API for global state management
- Tailwind CSS for styling

---

## Testing

This section defines the critical smoke tests that must be executed on every deployment to ensure core functionality is operational.

### 1. User Authentication Flow

**¿Qué flujo cubre y por qué debe hacer parte del smoke test?**
- Covers the complete authentication flow: registration, login, JWT token generation, and session persistence
- Critical because without authentication, users cannot access the application
- Validates that the AuthContext properly stores and retrieves user credentials
- Ensures JWT tokens are correctly received and stored in localStorage

**¿Cómo se ejecuta?**
1. Navigate to `/register` page
2. Fill form with valid data: name, lastname, email, city, password
3. Submit registration form
4. Verify redirect to home page after successful registration
5. Logout and navigate to `/login`
6. Enter registered email and password
7. Submit login form
8. Verify successful authentication and redirect to home
9. Verify user information is displayed in NavBar
10. Refresh page and verify session persists (token in localStorage)

**¿Qué tipo de test lo prueba?**
- **End-to-End (e2e)**: Tests the complete user journey across multiple pages and API interactions
- Could also be complemented with Integration tests for AuthContext and authService

---

### 2. Species Catalog Visualization

**¿Qué flujo cubre y por qué debe hacer parte del smoke test?**
- Covers the display of species catalog with 8 default especies (Tomate, Maíz, Frijol, Lechuga, Zanahoria, Papa, Cebolla, Pimentón)
- Critical because species are the foundation for creating crops
- Validates that the API connection is working and data is correctly fetched and rendered
- Ensures images, descriptions, and water requirements are displayed

**¿Cómo se ejecuta?**
1. Navigate to `/especies` page
2. Verify page loads without errors
3. Verify 8 species cards are displayed
4. Verify each card shows: name, scientific name, description, image, and water requirements
5. Click on a species card
6. Verify species detail view opens with complete information
7. Verify "Crear Cultivo" button is functional

**¿Qué tipo de test lo prueba?**
- **Integration**: Tests the integration between frontend components and the especies API endpoint
- Tests EspeciesPage component, EspecieCard component, and especiesService

---

### 3. Crop Creation with Stage Initialization

**¿Qué flujo cubre y por qué debe hacer parte del smoke test?**
- Covers the complete crop creation flow including species selection, area input, and automatic stage initialization
- Critical because crop creation is the main feature of the application
- Validates that crops are correctly created with etapaActual = 1 (PREPARACION stage)
- Ensures input validation works (area limit, required fields)

**¿Cómo se ejecuta?**
1. Login as PRODUCTOR user
2. Navigate to home page or crops page
3. Click "Crear Cultivo" button
4. Fill form:
   - Crop name (required)
   - Select species from dropdown (required)
   - Enter area in hectares (min: 0.01, max: 100000)
5. Submit form
6. Verify crop is created successfully
7. Verify redirect to crop detail page
8. Verify crop shows "Etapa 1" and correct total stages for selected species
9. Verify progress bar displays correctly (not "Infinity%" or 0%)

**¿Qué tipo de test lo prueba?**
- **End-to-End (e2e)**: Tests complete user flow from navigation to API call to data persistence
- Involves CropForm component, CultivoService, and backend API integration

---

### 4. Crop Stage Progression and Progress Bar

**¿Qué flujo cubre y por qué debe hacer parte del smoke test?**
- Covers the visualization and update of crop growth stages with correct progress calculation
- Critical because stage tracking is a core feature for task generation and crop management
- Validates that totalEtapas is calculated correctly from etapas table
- Ensures progress bar shows accurate percentage based on (etapaActual / totalEtapas) * 100

**¿Cómo se ejecuta?**
1. Navigate to an existing crop detail page (`/cultivos/{id}`)
2. Verify current stage is displayed (e.g., "Etapa Actual: 1")
3. Verify progress bar shows percentage (e.g., "12%" for etapa 1 of 8)
4. Verify stage name is displayed if available from etapaActualInfo
5. Click "Editar" button
6. Change "Etapa Actual" dropdown to next stage (e.g., from 1 to 2)
7. Save changes
8. Return to detail page
9. Verify progress bar updated correctly (e.g., "25%" for etapa 2 of 8)
10. Verify no "Infinity%" or "NaN" errors appear

**¿Cómo se ejecuta?**
- **Integration**: Tests the integration between CultivoDetail component, CropEdit component, and backend etapas system
- Validates correct data transformation and calculation in frontend

---

### 5. Input Validation on Critical Forms

**¿Qué flujo cubre y por qué debe hacer parte del smoke test?**
- Covers real-time input validation on registration and crop forms
- Critical because it prevents invalid data from being sent to backend and causing errors
- Validates that users cannot enter invalid characters (numbers in name fields, extremely large hectares)
- Ensures user experience is smooth with immediate feedback

**¿Cómo se ejecuta?**

**Registration Form:**
1. Navigate to `/register`
2. Try entering numbers in "Nombre" field (e.g., "Juan123")
3. Verify numbers are blocked in real-time
4. Try entering numbers in "Apellido" field
5. Verify numbers are blocked
6. Try entering invalid email (e.g., "test@")
7. Verify validation message appears
8. Try entering password less than 6 characters
9. Verify validation prevents submission

**Crop Form:**
1. Navigate to crop creation form
2. Try entering area less than 0.01
3. Verify validation error
4. Try entering area greater than 100000
5. Verify validation prevents values above limit
6. Leave required field empty and try submitting
7. Verify browser validation prevents submission

**¿Qué tipo de test lo prueba?**
- **Unit**: Tests individual validation functions (handleNombreChange, handleApellidoChange, handleCiudadChange)
- **Integration**: Tests form component behavior with validation logic
- Could also be tested with e2e to verify end-user experience

---

## Running Tests

Currently, the project does not have automated tests implemented. To add testing infrastructure:

### Unit & Integration Tests (Recommended: Vitest)
```powershell
# Install Vitest
npm install -D vitest @testing-library/react @testing-library/jest-dom

# Run tests
npm run test
```

### End-to-End Tests (Recommended: Playwright or Cypress)
```powershell
# Install Playwright
npm install -D @playwright/test

# Run e2e tests
npx playwright test
```

## Deployment Checklist
- [ ] Run `npm run build` successfully
- [ ] Execute all 5 smoke tests manually or automated
- [ ] Verify no console errors in browser
- [ ] Verify API connectivity with backend
- [ ] Verify environment variables are correctly set
- [ ] Test on target browsers (Chrome, Firefox, Edge)

## Contact
For questions or issues, contact the development team.
