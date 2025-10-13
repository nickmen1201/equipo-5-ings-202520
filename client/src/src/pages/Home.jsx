import React from 'react'
import NavBar from '../components/Navbar'
import PageBox from '../components/PageBox'
import alerts from '../assets/alerts.png'

const pages =[
    {name:'Alertas',image:alerts},
    {name:'Mi cultivos',image:'https://images.nationalgeographic.org/image/upload/t_RL2_search_thumb/v1638892233/EducationHub/photos/crops-growing-in-thailand.jpg'},
    {name:'tareas',image:'https://images.nationalgeographic.org/image/upload/t_RL2_search_thumb/v1638892233/EducationHub/photos/crops-growing-in-thailand.jpg'},
    
]

export default function Home() {
  return (
    <div className='flex flex-col'>
        <NavBar />
        <div className='flex gap-3 items-center justify-center mt-10'>
            {
            pages.map(page=>{return(
                <PageBox page={page} />
            )
            })
            }
        </div>
    </div>
  )
}


