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
    async function fetchCultivos() {
      try {
        if (!user?.id) {
          console.error("Usuario no disponible");
          setLoading(false);
          return;
        }
        const data = await getCultivosByUsuarioId(user.id);
        setCrops(data);
      } catch (error) {
        console.error("Error al obtener cultivos:", error);
      } finally {
        setLoading(false);
      }
    }

    fetchCultivos();
  }, [user]);

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col h-dvh">
      <NavBar />

      {/* Header con ciudad y clima */}
      <header className="flex justify-between items-center mt-16 bg-white shadow-sm p-2 md:p-3 border-b">

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
          
        </div>
        <div>
          <h1 className="text-2xl md:text-3xl font-bold text-gray-800">
            {city}
          </h1>
        </div>

        <div className="flex gap-2 md:gap-3">
          <div className="rounded-lg flex flex-col p-1 items-center border bg-gray-50 w-16 md:w-20">
            <TbTemperature className="text-orange-500 text-2xl mb-0.5 md:text-3xl" />
            <p className="text-sm md:text-base font-semibold">{temperature}°</p>
            <p className="text-gray-500 text-[10px] md:text-xs">Temp.</p>
          </div>

          <div className="rounded-lg flex flex-col p-1 items-center border bg-gray-50 w-16 md:w-20">
            <FaCloudRain className="text-blue-500 text-2xl mb-0.5 md:text-3xl" />
            <p className="text-sm md:text-base font-semibold">{rain}%</p>
            <p className="text-gray-500 text-[10px] md:text-xs">Lluvia</p>
          </div>
        </div>
      </header>

      {/* Contenido principal */}
      <main style={{backgroundImage:'url("https://www.transparenttextures.com/patterns/skulls.png")'}} className="flex-1 bg-[#738f76] py-8 px-6 md:px-16 overflow-y-scroll">
        {loading ? (
          <div className="flex justify-center items-center h-64 text-white">
            Cargando cultivos...
          </div>
        ) : (
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 xl:grid-cols-4 gap-6 justify-items-center">
            {/* Botón para agregar cultivo */}
            <Link
              to="/cultivo/nuevo"
              className="bg-white p-6 flex flex-col items-center justify-center gap-3 rounded-2xl shadow-md hover:shadow-lg hover:scale-105 transition-transform w-full max-w-xs"
            >
              <IoIosAddCircle className="text-6xl text-[#738f76]" />
              <p className="font-semibold text-emerald-700 text-center">
                Sembrar nuevo cultivo
              </p>
            </Link>

            {/* Lista de cultivos */}
            {crops && crops.length > 0 ? (
              crops.reverse().map((c, index) => (
                <CropBox
                  key={c.id || index}
                  id={c.id}
                  cropName={c.nombre}
                  image={c.especieImagenUrl}
                  percentage={(c.etapaActual*100)/c.totalEtapas}
                  cropSpecie={c.especieNombre}
                  cropHectares={c.areaHectareas}
                  description={c.especieDescripcion}
                />
              ))
            ) : (
              <p className="col-span-full text-gray-200 text-center mt-4">
                No tienes cultivos registrados aún.
              </p>
            )}
          </div>
        )}
      </main>
    </div>
  );
}
