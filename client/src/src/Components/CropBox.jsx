import React from 'react'

export default function ({percentage,image,cropName}) {
   return (
    <div className="bg-white p-2 flex flex-col gap-2 rounded-2xl shadow-md overflow-hidden w-full max-w-xs mx-auto transition-transform hover:scale-105">
      <div className="relative">
        <span className={` text-xs font-semibold px-2 py-1 rounded-full ${percentage < 50 ? 'bg-red-100 text-red-600' : 
          percentage >=50 && percentage<75 ? 'text-blue-600 bg-blue-100' : 
          'text-green-600 bg-green-300'}`}
        >
          {percentage}%
        </span>
      </div>
      <img
          src={image}
          alt={cropName}
          className="rounded-2xl w-11/12 h-36 object-cover sm:h-40 md:h-32 lg:h-32 bg-amber-700"
        />
      <div className="p-3">
        <p className="text-gray-800 font-medium text-sm sm:text-base truncate">
          {cropName}
        </p>
      </div>
    </div>
  );
}

