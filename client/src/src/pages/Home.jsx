import React from 'react'
import NavBar from '../components/NavBar'
import PageBox from '../components/PageBox'
import alerts from '../assets/alerts.png'
import tasks from '../assets/tasksimage.png'
import { useAuth } from '../context/AuthContext'

const pages = [
  { name: 'Alertas', link: '/alertas', image: alerts },
  {
    name: 'Mis Cultivos',
    link: '/cultivos',
    image:
      'https://images.nationalgeographic.org/image/upload/t_RL2_search_thumb/v1638892233/EducationHub/photos/crops-growing-in-thailand.jpg',
  },
  { name: 'Tareas', link: '/tareas', image: tasks },
]

const adminPages = [
  {
    name: 'Panel de Administración',
    link: '/admin',
    image: 'https://images.unsplash.com/photo-1454165804606-c3d57bc86b40?w=400&h=300&fit=crop',
  },
]

export default function Home() {
  const { user } = useAuth();
  
  // Combine pages based on user role
  const displayPages = user?.role === 'ADMIN' ? [...pages, ...adminPages] : pages;
  
  return (
    <div className="flex flex-col min-h-screen bg-gradient-to-b from-green-50 to-white">
      <NavBar />

      {/* Encabezado */}
      <div className="text-center mt-10 mb-6">
        <h1 className="text-3xl font-bold text-gray-800">
          Bienvenido a <span className="text-emerald-600">CultivApp</span>
        </h1>
        <p className="text-gray-500 mt-2 text-sm">
          Gestiona tus cultivos, tareas y alertas de manera eficiente 🌱
        </p>
      </div>

      {/* Contenedor de secciones */}
      <div className="flex flex-wrap justify-center gap-6 px-6 pb-10">
        {displayPages.map((page, index) => (
          <PageBox key={index} page={page} />
        ))}
      </div>
    </div>
  )
}
