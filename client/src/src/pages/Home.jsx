import React from 'react'
import NavBar from '../components/NavBar'
import PageBox from '../components/PageBox'
import alerts from '../assets/alerts.png'
import tasks from '../assets/tasksimage.png'

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

export default function Home() {
  return (
    <div className="flex flex-col min-h-screen bg-gradient-to-b from-green-50 to-white">
      <NavBar />

      {/* Encabezado */}
      <div className="text-center mt-10 mb-6">
        <h1 className="text-3xl font-bold text-gray-800">
          Bienvenido a <span className="text-emerald-600">CultiVApp</span>
        </h1>
        <p className="text-gray-500 mt-2 text-sm">
          Gestiona tus cultivos, tareas y alertas de manera eficiente 🌱
        </p>
      </div>

      {/* Contenedor de secciones */}
      <div className="flex flex-wrap justify-center gap-6 px-6 pb-10">
        {pages.map((page, index) => (
          <PageBox key={index} page={page} />
        ))}
      </div>
    </div>
  )
}
