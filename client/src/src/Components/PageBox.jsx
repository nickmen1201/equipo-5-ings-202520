import React from 'react'
import { Link } from 'react-router-dom'

export default function PageBox({page}) {
  return (
    <Link to={page.link} >
    <div className="flex flex-col justify-center items-center w-96 h-60 p-4 rounded-2xl bg-[#E9F7EF] gap-3 shadow-md hover:shadow-lg transition-shadow duration-300 hover:scale-[1.02] cursor-pointer">
      <img
        src={page.image}
        alt={page.name}
        className="w-full h-44 rounded-2xl object-cover"
      />
      <h2 className="text-lg font-semibold text-gray-700">{page.name}</h2>
    </div>
    </Link>
  );
}
