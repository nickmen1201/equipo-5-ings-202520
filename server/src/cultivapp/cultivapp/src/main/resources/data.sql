-- CultivApp Seed Data
-- Sample data for development and testing
-- Passwords are BCrypt hashed

-- Insert countries
INSERT INTO paises (id, nombre) VALUES 
(1, 'Colombia'),
(2, 'México'),
(3, 'Argentina');

-- Insert cities
INSERT INTO ciudades (id, nombre, id_pais) VALUES 
(1, 'Bogotá', 1),
(2, 'Medellín', 1),
(3, 'Cali', 1),
(4, 'Ciudad de México', 2),
(5, 'Buenos Aires', 3);

-- Insert users (password hashes are BCrypt encoded)
-- Both users use password: "password" for easy testing
-- Hash: $2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi
-- You can generate new hashes at: https://bcrypt-generator.com/ (strength 10)
-- Or use BCryptPasswordEncoder in Java to generate your own
INSERT INTO usuarios (id, email, password, nombre, apellido, id_ciudad, telefono, rol, activo, fecha_registro) VALUES 
(1, 'admin@cultivapp.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Admin', 'Sistema', 1, '3001234567', 'ADMIN', TRUE, CURRENT_TIMESTAMP),
(2, 'productor@cultivapp.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Juan', 'Pérez', 2, '3109876543', 'PRODUCTOR', TRUE, CURRENT_TIMESTAMP);

-- Insert growth stages
INSERT INTO etapas (id, nombre, descripcion) VALUES 
(1, 'GERMINACION', 'Etapa inicial de germinación de la semilla'),
(2, 'CRECIMIENTO', 'Etapa de crecimiento vegetativo'),
(3, 'FLORACION', 'Etapa de floración'),
(4, 'MADURACION', 'Etapa de maduración del fruto'),
(5, 'COSECHADO', 'Cultivo cosechado');

-- Insert plant species catalog
INSERT INTO especies (id, nombre, nombre_cientifico, ciclo_dias, dias_germinacion, dias_floracion, dias_cosecha, agua_semanal_mm, activo, fecha_creacion) VALUES 
(1, 'Tomate', 'Solanum lycopersicum', 120, 7, 45, 90, 25, TRUE, CURRENT_TIMESTAMP),
(2, 'Maíz', 'Zea mays', 150, 10, 60, 120, 30, TRUE, CURRENT_TIMESTAMP),
(3, 'Papa', 'Solanum tuberosum', 100, 15, 50, 90, 20, TRUE, CURRENT_TIMESTAMP),
(4, 'Café', 'Coffea arabica', 365, 30, 180, 270, 35, TRUE, CURRENT_TIMESTAMP),
(5, 'Frijol', 'Phaseolus vulgaris', 90, 7, 40, 75, 15, TRUE, CURRENT_TIMESTAMP),
(6, 'Zanahoria', 'Daucus carota', 75, 14, 35, 65, 18, TRUE, CURRENT_TIMESTAMP),
(7, 'Lechuga', 'Lactuca sativa', 60, 7, 30, 50, 22, TRUE, CURRENT_TIMESTAMP),
(8, 'Cebolla', 'Allium cepa', 120, 10, 60, 100, 20, TRUE, CURRENT_TIMESTAMP);

-- Insert sample crop for producer
INSERT INTO cultivos (id, usuario_id, especie_id, nombre, fecha_siembra, area_hectareas, etapa_actual, estado, rendimiento_kg, fecha_creacion, fecha_actualizacion) VALUES 
(1, 2, 1, 'Tomates Lote A', '2025-09-01', 2.50, 'CRECIMIENTO', 'ACTIVO', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample tasks for the crop
INSERT INTO tareas (id, cultivo_id, tipo, descripcion, fecha_programada, completada, fecha_completada, notas, fecha_creacion) VALUES 
(1, 1, 'RIEGO', 'Riego matutino del lote de tomates', '2025-10-15', FALSE, NULL, 'Verificar presión del sistema de riego', CURRENT_TIMESTAMP),
(2, 1, 'FERTILIZACION', 'Aplicar fertilizante NPK 10-10-10', '2025-10-20', FALSE, NULL, 'Dosis: 50kg por hectárea', CURRENT_TIMESTAMP);

-- Insert sample rules for alerts
INSERT INTO reglas (id, etapa_id, descripcion, fecha_generacion) VALUES 
(1, 1, 'Verificar humedad del suelo durante germinación', CURRENT_TIMESTAMP),
(2, 2, 'Monitorear plagas durante crecimiento vegetativo', CURRENT_TIMESTAMP),
(3, 3, 'Controlar polinización durante floración', CURRENT_TIMESTAMP);

-- Insert sample alert for the crop
INSERT INTO alertas (id, cultivo_id, regla_id, leida, fecha_generacion, fecha_vencimiento) VALUES 
(1, 1, 2, FALSE, CURRENT_TIMESTAMP, DATEADD('DAY', 7, CURRENT_TIMESTAMP));

-- Insert sample climate data
INSERT INTO datos_climaticos (id, fecha, temperatura_min, temperatura_max, precipitacion_mm, humedad_relativa, velocidad_viento_kmh, fecha_actualizacion) VALUES 
(1, '2025-10-10', 15.5, 28.3, 0.0, 65.5, 12.3, CURRENT_TIMESTAMP),
(2, '2025-10-11', 16.2, 29.1, 2.5, 70.2, 10.8, CURRENT_TIMESTAMP),
(3, '2025-10-12', 14.8, 27.5, 0.0, 68.3, 15.2, CURRENT_TIMESTAMP);

-- Insert system configuration
INSERT INTO configuracion_sistema (id, clave, valor, descripcion, tipo, categoria) VALUES 
(1, 'MAX_UPLOAD_SIZE_MB', '10', 'Tamaño máximo de archivos subidos en MB', 'NUMERO', 'SISTEMA'),
(2, 'ALERTA_DIAS_VENCIMIENTO', '7', 'Días hasta vencimiento de alertas', 'NUMERO', 'ALERTAS'),
(3, 'APP_NAME', 'CultivApp', 'Nombre de la aplicación', 'TEXTO', 'GENERAL'),
(4, 'NOTIFICACIONES_ACTIVAS', 'true', 'Activar notificaciones del sistema', 'BOOLEAN', 'NOTIFICACIONES');

-- Reset all auto-increment sequences to continue from the last inserted ID
-- This is CRITICAL to avoid primary key violations when inserting new records
-- H2 uses SYSTEM_SEQUENCE_<TABLE>_<COLUMN> naming for SERIAL columns
ALTER TABLE paises ALTER COLUMN id RESTART WITH 4;
ALTER TABLE ciudades ALTER COLUMN id RESTART WITH 6;
ALTER TABLE usuarios ALTER COLUMN id RESTART WITH 3;
ALTER TABLE etapas ALTER COLUMN id RESTART WITH 6;
ALTER TABLE especies ALTER COLUMN id RESTART WITH 9;
ALTER TABLE cultivos ALTER COLUMN id RESTART WITH 2;
ALTER TABLE tareas ALTER COLUMN id RESTART WITH 3;
ALTER TABLE reglas ALTER COLUMN id RESTART WITH 4;
ALTER TABLE alertas ALTER COLUMN id RESTART WITH 2;
ALTER TABLE datos_climaticos ALTER COLUMN id RESTART WITH 4;
ALTER TABLE configuracion_sistema ALTER COLUMN id RESTART WITH 5;
