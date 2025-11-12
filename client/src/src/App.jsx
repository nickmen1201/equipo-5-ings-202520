import { Routes, Route, Navigate } from 'react-router-dom'
import { useAuth } from './context/AuthContext'
import Login from './pages/Login'
import Home from './pages/Home';
import Register from './pages/Register';
import CropsPage from './pages/CropsPage';
import Especies from './pages/Especies';
import CropForm from './components/CropForm';
import CultivoDetail from './pages/CultivoDetail';
import CropEdit from './pages/CropEdit';
import AdminHome from './pages/AdminHome';
import Rules from './pages/Rules';
import NotificationCenter from './pages/NotificationsCenter';
import Alertas from './Components/Alertas';


function ProtectedRoute({ children }) {
  const { user, loading } = useAuth();
  if (loading) {
    return (
      <div className="flex items-center justify-center h-screen">
        <p className="text-gray-600">Cargando...</p>
      </div>
    );
  }
  return user ? children : <Navigate to="/login" replace />;
}

function AdminRoute({ children }) {
  const { user, loading } = useAuth();
  
  if (loading) {
    return (
      <div className="flex items-center justify-center h-screen">
        <p className="text-gray-600">Cargando...</p>
      </div>
    );
  }
  
  if (!user) {
    return <Navigate to="/login" replace />;
  }
  
  if (user.role !== 'ADMIN') {
    return <Navigate to="/home" replace />;
  }
  
  return children;
}


function App() {
  return (
    <>
      <Routes>
        <Route path="/login" element={<Login />} />

        <Route path="/register" element={
          <Register />
        } />
       
        <Route path="/cultivos" element={
          <ProtectedRoute>
            <CropsPage city="Medellín" rain={30} temperature={22}  />
          </ProtectedRoute>
        } />
        <Route path="/cultivos/:id" element={
          <ProtectedRoute>
            <CultivoDetail />
          </ProtectedRoute>
        } />
        <Route path="/cultivos/:id/editar" element={
          <ProtectedRoute>
            <CropEdit />
          </ProtectedRoute>
        } />
        <Route path="/admin" element={
          <AdminRoute>
            <AdminHome />
          </AdminRoute>
        } />
        <Route path="/home" element={
          <ProtectedRoute>
            <Home />
          </ProtectedRoute>
        } />
         <Route path="/alertas" element={
          <ProtectedRoute>
            <Alertas />
          </ProtectedRoute>
        } />
        <Route path="/admin/especies" element={
          <AdminRoute>
            <Especies />
          </AdminRoute>
        } />
        <Route path="/" element={<Navigate to="/login" replace />} />
        <Route path="*" element={
          <div className="flex items-center justify-center h-screen">
            <div className="text-center">
              <h1 className="text-4xl font-bold mb-4">404</h1>
              <p className="text-gray-600">Página no encontrada</p>
            </div>
          </div>
        } />
        <Route path="/cultivo/nuevo" element={
          <ProtectedRoute>
            <CropForm />
          </ProtectedRoute>
        } />
        <Route path="/admin/motor" element={
          <AdminRoute>
            <Rules/>
          </AdminRoute>
        } />
        <Route path="/notificaciones" element={<NotificationCenter/>} />
      </Routes>
    </>
  )
}

export default App