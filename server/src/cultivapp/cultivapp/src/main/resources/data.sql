-- Insert users without explicit id so the IDENTITY column advances correctly
-- Using DO NOTHING to prevent duplicate entries on subsequent runs
INSERT INTO usuarios (email, password, nombre, apellido, ciudad, rol, activo, fecha_registro)
SELECT 'admin@cultivapp.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Admin', 'Sistema', 'Medellin', 'ADMIN', TRUE, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'admin@cultivapp.com');

INSERT INTO usuarios (email, password, nombre, apellido, ciudad, rol, activo, fecha_registro)
SELECT 'productor@cultivapp.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Juan', 'Pérez', 'apartado', 'PRODUCTOR', TRUE, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'productor@cultivapp.com');

-- Insert 8 base species for the catalog
-- Using WHERE NOT EXISTS to prevent duplicates on subsequent runs
INSERT INTO especies (nombre, nombre_cientifico, descripcion, agua_semanal_mm, imagen_url, activo, fecha_creacion)
SELECT 
  'Tomate', 
  'Solanum lycopersicum', 
  'Planta herbácea de fruto rojo, rico en licopeno y vitaminas. Ideal para climas cálidos y templados.', 
  25, 
  'https://images.unsplash.com/photo-1592924357228-91a4daadcfea?w=400', 
  TRUE, 
  CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM especies WHERE nombre = 'Tomate');

INSERT INTO especies (nombre, nombre_cientifico, descripcion, agua_semanal_mm, imagen_url, activo, fecha_creacion)
SELECT 
  'Maíz', 
  'Zea mays', 
  'Cereal de alto valor nutricional, base de la alimentación en América. Requiere suelos fértiles y buen drenaje.', 
  30, 
  'https://images.unsplash.com/photo-1551754655-cd27e38d2076?w=400', 
  TRUE, 
  CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM especies WHERE nombre = 'Maíz');

INSERT INTO especies (nombre, nombre_cientifico, descripcion, agua_semanal_mm, imagen_url, activo, fecha_creacion)
SELECT 
  'Frijol', 
  'Phaseolus vulgaris', 
  'Leguminosa rica en proteínas, fija nitrógeno al suelo. Excelente para rotación de cultivos.', 
  20, 
  'https://images.unsplash.com/photo-1589927986089-35812388d1f4?w=400', 
  TRUE, 
  CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM especies WHERE nombre = 'Frijol');

INSERT INTO especies (nombre, nombre_cientifico, descripcion, agua_semanal_mm, imagen_url, activo, fecha_creacion)
SELECT 
  'Lechuga', 
  'Lactuca sativa', 
  'Hortaliza de hoja verde, ciclo corto. Perfecta para cultivos urbanos y huertos caseros.', 
  15, 
  'https://images.unsplash.com/photo-1622206151226-18ca2c9ab4a1?w=400', 
  TRUE, 
  CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM especies WHERE nombre = 'Lechuga');

INSERT INTO especies (nombre, nombre_cientifico, descripcion, agua_semanal_mm, imagen_url, activo, fecha_creacion)
SELECT 
  'Zanahoria', 
  'Daucus carota', 
  'Raíz comestible rica en betacaroteno. Requiere suelos sueltos y profundos para buen desarrollo.', 
  18, 
  'https://images.unsplash.com/photo-1598170845058-32b9d6a5da37?w=400', 
  TRUE, 
  CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM especies WHERE nombre = 'Zanahoria');

INSERT INTO especies (nombre, nombre_cientifico, descripcion, agua_semanal_mm, imagen_url, activo, fecha_creacion)
SELECT 
  'Papa', 
  'Solanum tuberosum', 
  'Tubérculo versátil y nutritivo. Cultivo de alto rendimiento en climas frescos y templados.', 
  22, 
  'https://images.unsplash.com/photo-1518977676601-b53f82aba655?w=400', 
  TRUE, 
  CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM especies WHERE nombre = 'Papa');

INSERT INTO especies (nombre, nombre_cientifico, descripcion, agua_semanal_mm, imagen_url, activo, fecha_creacion)
SELECT 
  'Cebolla', 
  'Allium cepa', 
  'Bulbo aromático esencial en cocina. Requiere suelos bien drenados y riego constante.', 
  20, 
  'https://images.unsplash.com/photo-1618512496248-a07fe83aa8cb?w=400', 
  TRUE, 
  CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM especies WHERE nombre = 'Cebolla');

INSERT INTO especies (nombre, nombre_cientifico, descripcion, agua_semanal_mm, imagen_url, activo, fecha_creacion)
SELECT 
  'Pimentón', 
  'Capsicum annuum', 
  'Fruto dulce o picante, rico en vitamina C. Excelente para cultivos en invernadero o campo abierto.', 
  25, 
  'https://images.unsplash.com/photo-1563565375-f3fdfdbefa83?w=400', 
  TRUE, 
  CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM especies WHERE nombre = 'Pimentón');
-- Insertar reglas predeterminadas
-- Reglas de riego
INSERT INTO reglas (tipo, descripcion, intervalo_dias)
SELECT 'RIEGO', 'Riego ligero diario', 1
WHERE NOT EXISTS (SELECT 1 FROM reglas WHERE tipo = 'RIEGO' AND intervalo_dias = 1);

INSERT INTO reglas (tipo, descripcion, intervalo_dias)
SELECT 'RIEGO', 'Riego moderado cada 2 días', 2
WHERE NOT EXISTS (SELECT 1 FROM reglas WHERE tipo = 'RIEGO' AND intervalo_dias = 2);

INSERT INTO reglas (tipo, descripcion, intervalo_dias)
SELECT 'RIEGO', 'Riego profundo semanal', 7
WHERE NOT EXISTS (SELECT 1 FROM reglas WHERE tipo = 'RIEGO' AND intervalo_dias = 7);

-- Reglas de fertilización
INSERT INTO reglas (tipo, descripcion, intervalo_dias)
SELECT 'FERTILIZACION', 'Fertilización básica quincenal', 15
WHERE NOT EXISTS (SELECT 1 FROM reglas WHERE tipo = 'FERTILIZACION' AND intervalo_dias = 15);

INSERT INTO reglas (tipo, descripcion, intervalo_dias)
SELECT 'FERTILIZACION', 'Fertilización completa mensual', 30
WHERE NOT EXISTS (SELECT 1 FROM reglas WHERE tipo = 'FERTILIZACION' AND intervalo_dias = 30);

-- Reglas de mantenimiento
INSERT INTO reglas (tipo, descripcion, intervalo_dias)
SELECT 'MANTENIMIENTO', 'Control de malezas semanal', 7
WHERE NOT EXISTS (SELECT 1 FROM reglas WHERE tipo = 'MANTENIMIENTO' AND descripcion = 'Control de malezas semanal');

INSERT INTO reglas (tipo, descripcion, intervalo_dias)
SELECT 'MANTENIMIENTO', 'Inspección de plagas quincenal', 15
WHERE NOT EXISTS (SELECT 1 FROM reglas WHERE tipo = 'MANTENIMIENTO' AND descripcion = 'Inspección de plagas quincenal');

-- Reglas de cosecha
INSERT INTO reglas (tipo, descripcion, intervalo_dias)
SELECT 'COSECHA', 'Revisión de madurez cada 3 días', 3
WHERE NOT EXISTS (SELECT 1 FROM reglas WHERE tipo = 'COSECHA' AND intervalo_dias = 3);

INSERT INTO reglas (tipo, descripcion, intervalo_dias)
SELECT 'COSECHA', 'Cosecha semanal', 7
WHERE NOT EXISTS (SELECT 1 FROM reglas WHERE tipo = 'COSECHA' AND intervalo_dias = 7);

-- Reglas de siembra
INSERT INTO reglas (tipo, descripcion, intervalo_dias)
SELECT 'SIEMBRA', 'Preparación del suelo', 1
WHERE NOT EXISTS (SELECT 1 FROM reglas WHERE tipo = 'SIEMBRA' AND descripcion = 'Preparación del suelo');

INSERT INTO reglas (tipo, descripcion, intervalo_dias)
SELECT 'SIEMBRA', 'Siembra de semillas', 1
WHERE NOT EXISTS (SELECT 1 FROM reglas WHERE tipo = 'SIEMBRA' AND descripcion = 'Siembra de semillas');

-- Reglas de preparación
INSERT INTO reglas (tipo, descripcion, intervalo_dias)
SELECT 'PREPARACION', 'Análisis de suelo', 1
WHERE NOT EXISTS (SELECT 1 FROM reglas WHERE tipo = 'PREPARACION' AND descripcion = 'Análisis de suelo');

INSERT INTO reglas (tipo, descripcion, intervalo_dias)
SELECT 'PREPARACION', 'Preparación de terreno', 1
WHERE NOT EXISTS (SELECT 1 FROM reglas WHERE tipo = 'PREPARACION' AND descripcion = 'Preparación de terreno');

-- -- Ahora vamos a crear etapas predeterminadas para cada especie
-- -- Función para obtener el ID de una especie por nombre
-- CREATE OR REPLACE FUNCTION get_especie_id(nombre_especie VARCHAR) RETURNS INTEGER AS $$
-- BEGIN
--     RETURN (SELECT id FROM especies WHERE nombre = nombre_especie);
-- END;
-- $$ LANGUAGE plpgsql;

-- -- Crear etapas para cada especie
-- DO $$ 
-- DECLARE
--     especie_record RECORD;
--     regla_prep_id INTEGER;
--     regla_siembra_id INTEGER;
--     regla_riego_id INTEGER;
--     regla_fert_id INTEGER;
--     regla_mant_id INTEGER;
--     regla_cosecha_id INTEGER;
-- BEGIN
--     -- Obtener IDs de reglas comunes
--     SELECT id INTO regla_prep_id FROM reglas WHERE tipo = 'PREPARACION' AND descripcion = 'Preparación de terreno' LIMIT 1;
--     SELECT id INTO regla_siembra_id FROM reglas WHERE tipo = 'SIEMBRA' AND descripcion = 'Siembra de semillas' LIMIT 1;
--     SELECT id INTO regla_riego_id FROM reglas WHERE tipo = 'RIEGO' AND intervalo_dias = 2 LIMIT 1;
--     SELECT id INTO regla_fert_id FROM reglas WHERE tipo = 'FERTILIZACION' AND intervalo_dias = 15 LIMIT 1;
--     SELECT id INTO regla_mant_id FROM reglas WHERE tipo = 'MANTENIMIENTO' AND descripcion = 'Control de malezas semanal' LIMIT 1;
--     SELECT id INTO regla_cosecha_id FROM reglas WHERE tipo = 'COSECHA' AND intervalo_dias = 7 LIMIT 1;

--     -- Iterar sobre cada especie
--     FOR especie_record IN SELECT * FROM especies LOOP
--         -- Crear etapa de PREPARACION
--         INSERT INTO etapas (nombre, especie_id, duracion_dias, orden)
--         SELECT 'PREPARACION', especie_record.id, 7, 1
--         WHERE NOT EXISTS (
--             SELECT 1 FROM etapas 
--             WHERE especie_id = especie_record.id AND nombre = 'PREPARACION'
--         );

--         -- Crear etapa de SIEMBRA
--         INSERT INTO etapas (nombre, especie_id, duracion_dias, orden)
--         SELECT 'SIEMBRA', especie_record.id, 7, 2
--         WHERE NOT EXISTS (
--             SELECT 1 FROM etapas 
--             WHERE especie_id = especie_record.id AND nombre = 'SIEMBRA'
--         );

--         -- Crear etapa de CRECIMIENTO
--         INSERT INTO etapas (nombre, especie_id, duracion_dias, orden)
--         SELECT 'CRECIMIENTO', especie_record.id, 7, 3
--         WHERE NOT EXISTS (
--             SELECT 1 FROM etapas 
--             WHERE especie_id = especie_record.id AND nombre = 'CRECIMIENTO'
--         );

--         -- Crear etapa de COSECHA
--         INSERT INTO etapas (nombre, especie_id, duracion_dias, orden)
--         SELECT 'COSECHA', especie_record.id, 7, 4
--         WHERE NOT EXISTS (
--             SELECT 1 FROM etapas 
--             WHERE especie_id = especie_record.id AND nombre = 'COSECHA'
--         );

--         -- Asociar reglas a las etapas
--         -- Preparación
--         INSERT INTO etapas_reglas (etapa_id, regla_id)
--         SELECT e.id, regla_prep_id
--         FROM etapas e
--         WHERE e.especie_id = especie_record.id AND e.nombre = 'PREPARACION'
--         AND NOT EXISTS (
--             SELECT 1 FROM etapas_reglas er WHERE er.etapa_id = e.id AND er.regla_id = regla_prep_id
--         );

--         -- Siembra
--         INSERT INTO etapas_reglas (etapa_id, regla_id)
--         SELECT e.id, regla_id
--         FROM etapas e, UNNEST(ARRAY[regla_siembra_id, regla_riego_id, regla_fert_id]) regla_id
--         WHERE e.especie_id = especie_record.id AND e.nombre = 'SIEMBRA'
--         AND NOT EXISTS (
--             SELECT 1 FROM etapas_reglas er WHERE er.etapa_id = e.id AND er.regla_id = regla_id
--         );

--         -- Crecimiento
--         INSERT INTO etapas_reglas (etapa_id, regla_id)
--         SELECT e.id, regla_id
--         FROM etapas e, UNNEST(ARRAY[regla_riego_id, regla_fert_id, regla_mant_id]) regla_id
--         WHERE e.especie_id = especie_record.id AND e.nombre = 'CRECIMIENTO'
--         AND NOT EXISTS (
--             SELECT 1 FROM etapas_reglas er WHERE er.etapa_id = e.id AND er.regla_id = regla_id
--         );

--         -- Cosecha
--         INSERT INTO etapas_reglas (etapa_id, regla_id)
--         SELECT e.id, regla_id
--         FROM etapas e, UNNEST(ARRAY[regla_cosecha_id, regla_riego_id, regla_mant_id]) regla_id
--         WHERE e.especie_id = especie_record.id AND e.nombre = 'COSECHA'
--         AND NOT EXISTS (
--             SELECT 1 FROM etapas_reglas er WHERE er.etapa_id = e.id AND er.regla_id = regla_id
--         );
--     END LOOP;
-- END $$;

-- -- Limpiar la función temporal
-- DROP FUNCTION IF EXISTS get_especie_id(VARCHAR);
