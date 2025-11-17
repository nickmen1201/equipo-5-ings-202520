import React from 'react'
import NavBar from '../Components/NavBar'
import especies from '../assets/Especies.png'
import PageBox from '../Components/PageBox'

export default function AdminHome() {
    const pages = [
      { name: 'Especies',
        link: '/admin/especies',
        image: especies
     }, 
      {
        name: 'Motor de Reglas',
        link: '/admin/motor',
        image:
          'https://images.nationalgeographic.org/image/upload/t_RL2_search_thumb/v1638892233/EducationHub/photos/crops-growing-in-thailand.jpg',
      },
      
    ]
  return (
    <div>
        <NavBar />
        <div className="container mx-auto p-6 mt-16">
            <h1 className="text-3xl font-semibold mb-4">Panel de Administración</h1>
            <p className="text-gray-600">
                Bienvenido al panel de administración de CultivApp.
            </p>
            <div className='gap-3.5 flex'>
                {
                    pages.map((page,index)=> <PageBox  key={index} page={page} />)
                }
            </div>
        </div>
    </div>
  )
}
