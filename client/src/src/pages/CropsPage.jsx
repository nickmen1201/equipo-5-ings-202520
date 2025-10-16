import { TbTemperature, TbTemperatureFahrenheit } from 'react-icons/tb'
import CropBox from '../components/CropBox'
import { FaArrowLeft, FaCloudRain } from 'react-icons/fa'
import NavBar from '../components/NavBar'
import cropImage from'../assets/cropmage.png'
import { IoIosAddCircle } from 'react-icons/io'
import { Link } from 'react-router-dom'

export default function CropsPage({city,image,temperature,rain}) {
    const cultivos = [
  {
    nombre: "Maíz lote 1",
    imagen: cropImage,
    progreso: 60,
  },
  {
    nombre: "Tomate invernadero",
    imagen: cropImage,
    progreso: 45,
  },
  {
    nombre: "Fresas parcela 2",
    imagen: cropImage,
    progreso: 80,
  },
  {
    nombre: "Café zona alta",
    imagen: cropImage,
    progreso: 30,
  },
  {
    nombre: "Lechuga lote 3",
    imagen: cropImage,
    progreso: 90,
  },
]

  return (
    <div className=''>
        <NavBar />
        <div className='w-full flex justify-between h-32 bg-gray-50 p-4 items-center'>
            <div>
                <Link to='/home'><span><FaArrowLeft /></span></Link>
                <img src={image} alt="" />
            </div>
            <p className='text-3xl font-bold'>{city}</p>
            <div className='flex gap-2 h-full'>
                <div className='rounded-2xl flex flex-col p-2 items-center border-2 h-full w-24'>
                    <TbTemperature className='text-orange-500  text-2xl ' />
                    <p className='text-2xl'>{temperature}°</p>
                    <p className='text-gray-600'>Temperatura</p>
                </div>
                <div className='rounded-2xl flex flex-col p-2 items-center border-2 h-full w-24'>
                    <FaCloudRain className='text-2xl text-blue-500' />
                    <p className='text-2xl'>{rain}%</p>
                    <p className='text-gray-600'>Prob lluvia</p>
                </div>
            </div>
        </div>
        <div className='bg-[#DBEFD7] w-full p-4 '>
          <div className='grid grid-cols-3 w-10/12 gap-2 '>
            {cultivos.map(c=>(
                <CropBox cropName={c.nombre} percentage={c.progreso} image={c.imagen} />
            ))}
            <div className="bg-white items-center justify-center p-2 flex flex-col gap-2 rounded-2xl shadow-md overflow-hidden w-full max-w-xs mx-auto transition-transform hover:scale-105">
              <IoIosAddCircle className='text-5xl text-green-200' />
              <p className="font-medium text-sm sm:text-base text-emerald-700 ">
                Sembrar nuevo cultivo
              </p>
            </div>
          </div>
        </div>
    </div>
  )
}
