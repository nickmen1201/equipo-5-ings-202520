import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { TbTemperature } from "react-icons/tb";
import { FaArrowLeft, FaCloudRain } from "react-icons/fa";
import { IoIosAddCircle } from "react-icons/io";
import NavBar from "../components/NavBar";
import CropBox from "../components/CropBox";
import { useAuth } from "../context/AuthContext";
import { getCultivosByUsuarioId } from "../services/CultivoService";

export default function CropsPage({ city, image, temperature, rain }) {
  const { user } = useAuth();
  const [crops, setCrops] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!user?.id) return;

    async function fetchCultivos() {
      try {
        const data = await getCultivosByUsuarioId(user.id);
        setCrops(data);
      } catch (error) {
        console.error("Error al obtener cultivos:", error);
      } finally {
        setLoading(false);
      }
    }

    fetchCultivos();
  }, [user?.id]);

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col h-dvh">
      <NavBar />

      {/* Header con ciudad y clima */}
      <header className="flex justify-between items-center bg-white shadow-sm p-4 md:p-6 border-b">
        <div className="flex items-center gap-3">
          <Link
            to="/home"
            className="p-2 rounded-full hover:bg-gray-100 transition"
          >
            <FaArrowLeft className="text-gray-600 text-lg" />
          </Link>
          {image && (
            <img
              src={image}
              alt="City"
              className="w-14 h-14 rounded-xl object-cover border"
            />
          )}
          <h1 className="text-2xl md:text-3xl font-bold text-gray-800">
            {city}
          </h1>
        </div>

        <div className="flex gap-4">
          <div className="rounded-xl flex flex-col p-2 items-center border bg-gray-50 w-24">
            <TbTemperature className="text-orange-500 text-3xl mb-1" />
            <p className="text-xl font-semibold">{temperature}°</p>
            <p className="text-gray-500 text-sm">Temperatura</p>
          </div>
          <div className="rounded-xl flex flex-col p-2 items-center border bg-gray-50 w-24">
            <FaCloudRain className="text-blue-500 text-3xl mb-1" />
            <p className="text-xl font-semibold">{rain}%</p>
            <p className="text-gray-500 text-sm">Lluvia</p>
          </div>
        </div>
      </header>

      {/* Contenido principal */}
      <main className="flex-1 bg-[#E8F5E9] py-8 px-6 md:px-16 overflow-y-scroll">
        {loading ? (
          <div className="flex justify-center items-center h-64 text-gray-500">
            Cargando cultivos...
          </div>
        ) : (
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 xl:grid-cols-4 gap-6 justify-items-center">
            {/* Botón para agregar cultivo */}
            <Link
              to="/cultivo/nuevo"
              className="bg-white p-6 flex flex-col items-center justify-center gap-3 rounded-2xl shadow-md hover:shadow-lg hover:scale-105 transition-transform w-full max-w-xs"
            >
              <IoIosAddCircle className="text-6xl text-emerald-300" />
              <p className="font-semibold text-emerald-700 text-center">
                Sembrar nuevo cultivo
              </p>
            </Link>

            {/* Lista de cultivos */}
            {crops && crops.length > 0 ? (
              crops.map((c, index) => (
                <CropBox
                  key={index}
                  cropName={c.nombre}
                  image={c.especieImagenUrl}
                  percentage={c.progreso}
                  cropSpecie={c.especieNombre}
                  cropHectares={c.areaHectareas}
                  description={c.especieDescripcion}
                />
              ))
            ) : (
              <p className="col-span-full text-gray-500 text-center mt-4">
                No tienes cultivos registrados aún.
              </p>
            )}
          </div>
        )}
      </main>
    </div>
  );
}
