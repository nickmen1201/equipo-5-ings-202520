-- Seed script to populate etapas for all especies with proper reglas associations
-- This script creates a complete stage progression for each species with realistic durations

-- Clean up existing etapas (if any) to start fresh
DELETE FROM etapas_reglas;
DELETE FROM etapas;

-- ============================================================
-- TOMATE (Solanum lycopersicum) - 8 stages, ~120 days total
-- ============================================================
INSERT INTO etapas (nombre, especie_id, duracion_dias, orden) VALUES
('PREPARACION', 1, 7, 1),
('SIEMBRA', 1, 3, 2),
('GERMINACION', 1, 10, 3),
('DESARROLLO_VEGETATIVO', 1, 30, 4),
('FLORACION', 1, 20, 5),
('FRUCTIFICACION', 1, 25, 6),
('MADURACION', 1, 20, 7),
('COSECHA', 1, 5, 8);

-- ============================================================
-- MAÍZ (Zea mays) - 7 stages, ~100 days total
-- ============================================================
INSERT INTO etapas (nombre, especie_id, duracion_dias, orden) VALUES
('PREPARACION', 2, 7, 1),
('SIEMBRA', 2, 2, 2),
('GERMINACION', 2, 8, 3),
('DESARROLLO_VEGETATIVO', 2, 40, 4),
('FLORACION', 2, 15, 5),
('MADURACION', 2, 25, 6),
('COSECHA', 2, 3, 7);

-- ============================================================
-- FRIJOL (Phaseolus vulgaris) - 7 stages, ~75 days total
-- ============================================================
INSERT INTO etapas (nombre, especie_id, duracion_dias, orden) VALUES
('PREPARACION', 3, 7, 1),
('SIEMBRA', 3, 2, 2),
('GERMINACION', 3, 7, 3),
('DESARROLLO_VEGETATIVO', 3, 25, 4),
('FLORACION', 3, 15, 5),
('FRUCTIFICACION', 3, 15, 6),
('COSECHA', 3, 4, 7);

-- ============================================================
-- LECHUGA (Lactuca sativa) - 5 stages, ~60 days total (no flowering)
-- ============================================================
INSERT INTO etapas (nombre, especie_id, duracion_dias, orden) VALUES
('PREPARACION', 4, 7, 1),
('SIEMBRA', 4, 2, 2),
('GERMINACION', 4, 7, 3),
('DESARROLLO_VEGETATIVO', 4, 40, 4),
('COSECHA', 4, 4, 5);

-- ============================================================
-- ZANAHORIA (Daucus carota) - 6 stages, ~90 days total
-- ============================================================
INSERT INTO etapas (nombre, especie_id, duracion_dias, orden) VALUES
('PREPARACION', 5, 7, 1),
('SIEMBRA', 5, 3, 2),
('GERMINACION', 5, 14, 3),
('DESARROLLO_VEGETATIVO', 5, 50, 4),
('MADURACION', 5, 14, 5),
('COSECHA', 5, 2, 6);

-- ============================================================
-- PAPA (Solanum tuberosum) - 7 stages, ~110 days total
-- ============================================================
INSERT INTO etapas (nombre, especie_id, duracion_dias, orden) VALUES
('PREPARACION', 6, 7, 1),
('SIEMBRA', 6, 2, 2),
('GERMINACION', 6, 15, 3),
('DESARROLLO_VEGETATIVO', 6, 40, 4),
('FLORACION', 6, 15, 5),
('MADURACION', 6, 28, 6),
('COSECHA', 6, 3, 7);

-- ============================================================
-- CEBOLLA (Allium cepa) - 6 stages, ~120 days total
-- ============================================================
INSERT INTO etapas (nombre, especie_id, duracion_dias, orden) VALUES
('PREPARACION', 7, 7, 1),
('SIEMBRA', 7, 3, 2),
('GERMINACION', 7, 14, 3),
('DESARROLLO_VEGETATIVO', 7, 70, 4),
('MADURACION', 7, 24, 5),
('COSECHA', 7, 2, 6);

-- ============================================================
-- PIMENTÓN (Capsicum annuum) - 8 stages, ~100 days total
-- ============================================================
INSERT INTO etapas (nombre, especie_id, duracion_dias, orden) VALUES
('PREPARACION', 8, 7, 1),
('SIEMBRA', 8, 3, 2),
('GERMINACION', 8, 12, 3),
('DESARROLLO_VEGETATIVO', 8, 30, 4),
('FLORACION', 8, 15, 5),
('FRUCTIFICACION', 8, 20, 6),
('MADURACION', 8, 10, 7),
('COSECHA', 8, 3, 8);

-- ============================================================
-- ASSOCIATE REGLAS TO ETAPAS
-- ============================================================

-- Link PREPARACION stages to preparation reglas (Analysis + Preparation)
INSERT INTO etapas_reglas (etapa_id, regla_id)
SELECT e.id, r.id
FROM etapas e
CROSS JOIN reglas r
WHERE e.nombre = 'PREPARACION' 
  AND r.tipo IN ('PREPARACION');

-- Link SIEMBRA stages to siembra + riego reglas
INSERT INTO etapas_reglas (etapa_id, regla_id)
SELECT e.id, r.id
FROM etapas e
CROSS JOIN reglas r
WHERE e.nombre = 'SIEMBRA' 
  AND (r.tipo = 'SIEMBRA' OR (r.tipo = 'RIEGO' AND r.intervalo_dias = 1));

-- Link GERMINACION stages to daily watering
INSERT INTO etapas_reglas (etapa_id, regla_id)
SELECT e.id, r.id
FROM etapas e
CROSS JOIN reglas r
WHERE e.nombre = 'GERMINACION' 
  AND r.tipo = 'RIEGO' AND r.intervalo_dias = 1;

-- Link DESARROLLO_VEGETATIVO stages to regular watering, fertilization, and maintenance
INSERT INTO etapas_reglas (etapa_id, regla_id)
SELECT e.id, r.id
FROM etapas e
CROSS JOIN reglas r
WHERE e.nombre = 'DESARROLLO_VEGETATIVO' 
  AND (
    (r.tipo = 'RIEGO' AND r.intervalo_dias = 2) OR
    (r.tipo = 'FERTILIZACION' AND r.intervalo_dias = 15) OR
    (r.tipo = 'MANTENIMIENTO' AND r.intervalo_dias IN (7, 15))
  );

-- Link FLORACION stages to moderate watering and maintenance
INSERT INTO etapas_reglas (etapa_id, regla_id)
SELECT e.id, r.id
FROM etapas e
CROSS JOIN reglas r
WHERE e.nombre = 'FLORACION' 
  AND (
    (r.tipo = 'RIEGO' AND r.intervalo_dias = 2) OR
    (r.tipo = 'MANTENIMIENTO' AND r.intervalo_dias = 7) OR
    (r.tipo = 'FERTILIZACION' AND r.intervalo_dias = 15)
  );

-- Link FRUCTIFICACION stages to regular watering and fertilization
INSERT INTO etapas_reglas (etapa_id, regla_id)
SELECT e.id, r.id
FROM etapas e
CROSS JOIN reglas r
WHERE e.nombre = 'FRUCTIFICACION' 
  AND (
    (r.tipo = 'RIEGO' AND r.intervalo_dias = 2) OR
    (r.tipo = 'FERTILIZACION' AND r.intervalo_dias = 15) OR
    (r.tipo = 'MANTENIMIENTO' AND r.intervalo_dias = 15)
  );

-- Link MADURACION stages to moderate watering and maturity checks
INSERT INTO etapas_reglas (etapa_id, regla_id)
SELECT e.id, r.id
FROM etapas e
CROSS JOIN reglas r
WHERE e.nombre = 'MADURACION' 
  AND (
    (r.tipo = 'RIEGO' AND r.intervalo_dias = 7) OR
    (r.tipo = 'COSECHA' AND r.intervalo_dias = 3) OR
    (r.tipo = 'MANTENIMIENTO' AND r.intervalo_dias = 7)
  );

-- Link COSECHA stages to harvest reglas and light maintenance
INSERT INTO etapas_reglas (etapa_id, regla_id)
SELECT e.id, r.id
FROM etapas e
CROSS JOIN reglas r
WHERE e.nombre = 'COSECHA' 
  AND (
    (r.tipo = 'COSECHA') OR
    (r.tipo = 'RIEGO' AND r.intervalo_dias = 7)
  );

-- Verification queries (uncomment to check results)
-- SELECT e.nombre AS especie, COUNT(et.id) AS total_etapas 
-- FROM especies e 
-- LEFT JOIN etapas et ON e.id = et.especie_id 
-- GROUP BY e.id, e.nombre 
-- ORDER BY e.id;

-- SELECT es.nombre AS especie, et.nombre AS etapa, et.orden, et.duracion_dias,
--        COUNT(r.id) AS total_reglas
-- FROM especies es
-- JOIN etapas et ON es.id = et.especie_id
-- LEFT JOIN etapas_reglas er ON et.id = er.etapa_id
-- LEFT JOIN reglas r ON er.regla_id = r.id
-- GROUP BY es.id, es.nombre, et.id, et.nombre, et.orden, et.duracion_dias
-- ORDER BY es.id, et.orden;
