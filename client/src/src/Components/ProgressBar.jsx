export default function ProgressBar({ value = 0, height = "h-3", showLabel = true }) {
  const clamped = Math.max(0, Math.min(100, Number(value)));

  // Asignar degradado según el rango
  let gradient;
  if (clamped < 50) {
    // Bajo (rojo)
    gradient = "linear-gradient(90deg, #ef4444, #f87171)";
  } else if (clamped <= 75) {
    // Medio (amarillo)
    gradient = "linear-gradient(90deg, #facc15, #fde047)";
  } else {
    // Alto (verde)
    gradient = "linear-gradient(90deg, #22c55e, #4ade80)";
  }

  // Color del texto (opcionalmente el mismo tono)
  const textColor =
    clamped < 50
      ? "text-red-500"
      : clamped <= 75
      ? "text-yellow-500"
      : "text-green-500";

  return (
    <div className="flex items-center gap-3">
      {/* Contenedor de la barra */}
      <div
        className={`w-72 bg-gray-200 rounded-full overflow-hidden ${height}`}
        role="progressbar"
        aria-valuemin="0"
        aria-valuemax="100"
        aria-valuenow={clamped}
      >
        {/* Progreso con degradado */}
        <div
          className="h-full transition-all duration-500"
          style={{
            width: `${clamped}%`,
            background: gradient,
          }}
        />
      </div>

      {/* Etiqueta de porcentaje */}
      {showLabel && (
        <div className={`text-sm w-10 text-right font-medium ${textColor}`}>
          {clamped}%
        </div>
      )}
    </div>
  );
}