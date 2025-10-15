# REQ-005: Catálogo Mínimo de Especies - Implementation Guide

## 📋 Overview
This document describes the complete implementation of REQ-005: **Catálogo mínimo de especies** for CultivApp. Admin users can now manage agricultural species through `/admin/especies` with full CRUD functionality.

## ✅ Implementation Complete

### Backend (Spring Boot)

#### 1. **Model Updates**
- **File**: `Especie.java`
- **Changes**:
  - Added `descripcion` (TEXT) field for species description
  - Added `diasFertilizacion` (INTEGER) field for recommended fertilization days
  - Added `imagenUrl` (VARCHAR 500) field for optional species images

#### 2. **Repository Layer**
- **File**: `CultivoRepository.java`
- **New Method**: `existsByEspecieId(Integer especieId)`
  - Used to check if a species has associated crops before deletion
  - Implements REQ-005 business rule: cannot delete species with active crops

#### 3. **DTOs**
- **EspecieDTO.java**: Response DTO with all species fields
- **EspecieRequest.java**: Request DTO with validation annotations
  - `@NotBlank` for required fields (nombre)
  - `@NotNull` and `@Positive` for numeric fields
  - Spanish validation messages

#### 4. **Service Layer**
- **File**: `EspecieService.java`
- **Methods**:
  - `getAllEspecies()`: Get all species
  - `getEspecieById(Integer id)`: Get single species
  - `createEspecie(EspecieRequest)`: Create new species
  - `updateEspecie(Integer id, EspecieRequest)`: Update existing species
  - `deleteEspecie(Integer id)`: Soft delete (marks as inactive)
- **Business Logic**:
  - Prevents deletion if species has associated crops
  - Throws `EspecieHasCultivosException` with message: "No se puede eliminar: tiene cultivos asociados"
  - Auto-converts between entities and DTOs

#### 5. **Controller Layer**
- **File**: `EspecieController.java`
- **Endpoints**:
  - `GET /api/especies` - Get all species
  - `GET /api/especies/{id}` - Get species by ID
  - `POST /api/especies` - Create new species (Admin)
  - `PUT /api/especies/{id}` - Update species (Admin)
  - `DELETE /api/especies/{id}` - Delete species (Admin)
- **Exception Handlers**:
  - 404 for species not found
  - 409 (Conflict) for species with associated crops

### Frontend (React + Vite)

#### 1. **Service Layer**
- **File**: `especiesService.js`
- **Methods**:
  - `getAllEspecies()`: Fetch all species
  - `getEspecieById(id)`: Fetch single species
  - `createEspecie(data)`: Create new species
  - `updateEspecie(id, data)`: Update species
  - `deleteEspecie(id)`: Delete species
- **Features**:
  - JWT token authorization
  - Error handling with Spanish messages
  - Handles 409 conflict for deletion validation

#### 2. **Components**

##### EspecieCard.jsx
- Displays species in card format
- Shows: image, common name, scientific name, description, fertilization days
- Action buttons: Edit, Delete
- Responsive design with Tailwind CSS
- Default placeholder image for species without images

##### EspecieForm.jsx
- Modal form for create/edit operations
- Fields:
  - Common name (required)
  - Scientific name
  - Description (textarea)
  - Fertilization days
  - Image URL
  - Cycle days (required)
  - Germination days (required)
  - Flowering days (required)
  - Harvest days (required)
  - Weekly water in mm
- Client-side validation with error messages
- Loading states during submission
- Responsive 2-column grid layout

#### 3. **Pages**

##### Especies.jsx (`/admin/especies`)
- Main admin page for species management
- Features:
  - Card grid layout (responsive: 1-4 columns)
  - "Nueva Especie" button to create new species
  - Edit functionality via card buttons
  - Delete with confirmation dialog
  - Success/error message alerts
  - Loading states
  - Empty state with call-to-action
- Integration:
  - Uses NavBar component for consistency
  - Protected route (requires authentication)
  - Full CRUD operations via service layer

#### 4. **Routing**
- **File**: `App.jsx`
- **New Route**: `/admin/especies`
- Protected with `<ProtectedRoute>` wrapper
- Only accessible to authenticated users (Admin role recommended for production)

## 🎨 Design & UI Consistency

- **Brand Color**: `#60C37B` (CultivApp green)
- **Card Design**: White background, rounded corners, shadow on hover
- **Buttons**: 
  - Green for primary actions (create, save)
  - Blue for edit actions
  - Red for delete actions
- **Forms**: Modal overlay with clean, organized layout
- **Responsive**: Mobile-first design with Tailwind CSS breakpoints

## 🔒 Security Notes

**Current Implementation**:
- All endpoints are currently public (for development)
- JWT token is sent with requests but not enforced

**Production TODO**:
- Add `@PreAuthorize("hasRole('ADMIN')")` to CUD endpoints in `EspecieController`
- Update `SecurityConfig` to enable JWT validation
- Implement role-based access control in frontend routing

## 🧪 Testing Guide

### Functional Tests

#### Test 1: Create Species ✅
1. Navigate to `/admin/especies`
2. Click "Nueva Especie"
3. Fill form with:
   - Nombre: "Maíz"
   - Nombre Científico: "Zea mays"
   - Descripción: "Cultivo de alto valor nutricional"
   - Días de Fertilización: 20
   - Ciclo: 120
   - Germinación: 7
   - Floración: 60
   - Cosecha: 120
4. Click "Crear"
5. **Expected**: Species appears in grid, success message shown

#### Test 2: Edit Species ✅
1. Click "Editar" on any species card
2. Modify fields (e.g., change fertilization days to 25)
3. Click "Actualizar"
4. **Expected**: Card updates with new data, success message shown

#### Test 3: Delete Species (No Associated Crops) ✅
1. Click trash icon on species without crops
2. Confirm deletion in dialog
3. **Expected**: Species removed from grid, success message shown

#### Test 4: Delete Species (With Associated Crops) ✅
1. Create a crop associated with a species
2. Try to delete that species
3. **Expected**: Error message "No se puede eliminar: tiene cultivos asociados"

#### Test 5: Validation ✅
1. Click "Nueva Especie"
2. Submit form without required fields
3. **Expected**: Red error messages under required fields

## 📦 Database Schema

### New Columns in `especies` Table
```sql
ALTER TABLE especies
ADD COLUMN descripcion TEXT,
ADD COLUMN dias_fertilizacion INTEGER,
ADD COLUMN imagen_url VARCHAR(500);
```

## 🚀 Running the Application

### Backend
```bash
cd server/src/cultivapp/cultivapp
./mvnw spring-boot:run
# Or on Windows:
mvnw.cmd spring-boot:run
```

### Frontend
```bash
cd client/src
npm install
npm run dev
```

### Access
- Frontend: http://localhost:5173
- Backend API: http://localhost:8080
- Admin Page: http://localhost:5173/admin/especies

## 📝 API Documentation

### GET /api/especies
**Response**: Array of species objects
```json
[
  {
    "id": 1,
    "nombre": "Maíz",
    "nombreCientifico": "Zea mays",
    "descripcion": "Cultivo de alto valor nutricional",
    "diasFertilizacion": 20,
    "imagenUrl": "https://...",
    "cicloDias": 120,
    "diasGerminacion": 7,
    "diasFloracion": 60,
    "diasCosecha": 120,
    "aguaSemanalMm": 50,
    "activo": true
  }
]
```

### POST /api/especies
**Request Body**:
```json
{
  "nombre": "Maíz",
  "nombreCientifico": "Zea mays",
  "descripcion": "Cultivo de alto valor nutricional",
  "diasFertilizacion": 20,
  "imagenUrl": "https://...",
  "cicloDias": 120,
  "diasGerminacion": 7,
  "diasFloracion": 60,
  "diasCosecha": 120,
  "aguaSemanalMm": 50
}
```
**Response**: Created species object (201 Created)

### PUT /api/especies/{id}
**Request Body**: Same as POST
**Response**: Updated species object (200 OK)

### DELETE /api/especies/{id}
**Response**: 
- 204 No Content (success)
- 409 Conflict (has associated crops)

## 🎯 Acceptance Criteria - Met

✅ Admin can create species with all required fields
✅ Admin can edit existing species
✅ Admin can delete species (with validation)
✅ Species appear in `/admin/especies` page
✅ Card-based UI with image, name, scientific name, description
✅ Fertilization days displayed on cards
✅ Deletion blocked if species has associated crops with error message
✅ Success and error messages in Spanish
✅ Responsive design
✅ Modal form for create/edit
✅ Confirmation dialog for deletion

## 📚 Code Structure

```
server/
└── src/main/java/com/cultivapp/cultivapp/
    ├── controller/
    │   └── EspecieController.java          ← REST endpoints
    ├── service/
    │   └── EspecieService.java             ← Business logic
    ├── repository/
    │   ├── EspecieRepository.java          ← Data access
    │   └── CultivoRepository.java          ← Updated with exists check
    ├── dto/
    │   ├── EspecieDTO.java                 ← Response DTO
    │   └── EspecieRequest.java             ← Request DTO
    └── model/
        └── Especie.java                     ← Updated entity

client/
└── src/src/
    ├── services/
    │   └── especiesService.js              ← API calls
    ├── Components/
    │   ├── EspecieCard.jsx                 ← Species card
    │   └── EspecieForm.jsx                 ← Create/edit form
    ├── pages/
    │   └── Especies.jsx                    ← Main admin page
    └── App.jsx                              ← Updated with route
```

## 🔄 Integration with Existing Code

- ✅ Follows existing naming conventions (camelCase for Java, Spanish field names)
- ✅ Uses same authentication pattern (localStorage token, AuthContext)
- ✅ Matches UI design system (Tailwind CSS, brand colors, component styles)
- ✅ Consistent error handling (Spanish messages, try-catch patterns)
- ✅ Protected routes pattern (ProtectedRoute wrapper)
- ✅ NavBar integration for consistent header
- ✅ No breaking changes to existing modules

## 🎓 Team Notes

- All code is commented in English for team collaboration
- Spanish text for user-facing messages (UI, errors, validation)
- Modular architecture for easy maintenance
- DTOs separate concerns (API contracts vs domain models)
- Service layer contains all business logic
- Controller is thin, delegates to service
- Frontend components are reusable and composable
- Clean separation between pages, components, and services

## 🐛 Known Issues / Future Enhancements

1. **Security**: Add role-based authorization in production
2. **Image Upload**: Currently only supports URLs, consider file upload
3. **Pagination**: For large catalogs, add pagination to species grid
4. **Search/Filter**: Add search bar to filter species by name
5. **Soft Delete UI**: Option to view/restore inactive species
6. **Crop Association**: Show count of associated crops on species cards
7. **Validation**: Add backend file type validation for image URLs

---

**Implementation Date**: October 14, 2025
**Branch**: `feature/REQ-005-catalogo-minimo-especies`
**Status**: ✅ Complete and Ready for Testing
