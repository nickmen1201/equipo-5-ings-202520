import { Routes, Route, Navigate } from 'react-router-dom'
import { useAuth } from './context/AuthContext'
import NavBar from './components/NavBar'
import Login from './pages/Login'
import Home from './pages/Home';
import Register from './pages/Register';
import CropsPage from './pages/CropsPage';
import Especies from './pages/Especies';


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
            <CropsPage />
          </ProtectedRoute>
        } />
        <Route path="/admin" element={
          <ProtectedRoute>
            <div>
              <NavBar />
              <div className="container mx-auto p-6">
                <h1 className="text-3xl font-semibold mb-4">Panel de Administración</h1>
                <p className="text-gray-600">
                  Bienvenido al panel de administración de CultivApp.
                </p>
              </div>
            </div>
          </ProtectedRoute>
        } />
        <Route path="/home" element={
          <ProtectedRoute>
            <Home />
          </ProtectedRoute>
        } />
        <Route path="/admin/especies" element={
          <ProtectedRoute>
            <Especies />
          </ProtectedRoute>
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
      </Routes>
    </>
  )
}

export default App