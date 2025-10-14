-- CultivApp Database Schema
-- File-based H2 with PostgreSQL compatibility mode
-- All tables, foreign keys, constraints, and indexes

-- Drop tables in reverse order of dependencies to avoid FK constraints
DROP TABLE IF EXISTS alertas CASCADE;
DROP TABLE IF EXISTS tareas CASCADE;
DROP TABLE IF EXISTS cultivos CASCADE;
DROP TABLE IF EXISTS reglas CASCADE;
DROP TABLE IF EXISTS etapas CASCADE;
DROP TABLE IF EXISTS datos_climaticos CASCADE;
DROP TABLE IF EXISTS configuracion_sistema CASCADE;
DROP TABLE IF EXISTS especies CASCADE;
DROP TABLE IF EXISTS usuarios CASCADE;
DROP TABLE IF EXISTS ciudades CASCADE;
DROP TABLE IF EXISTS paises CASCADE;

-- Paises table: country catalog
CREATE TABLE paises (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- Ciudades table: city catalog within countries
CREATE TABLE ciudades (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    id_pais INT NOT NULL,
    CONSTRAINT fk_ciudades_pais FOREIGN KEY (id_pais) REFERENCES paises(id)
);

-- Usuarios table: system users (admin and producers)
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    id_ciudad INT,
    telefono VARCHAR(20),
    rol VARCHAR(20) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_usuarios_ciudad FOREIGN KEY (id_ciudad) REFERENCES ciudades(id)
);

-- Create index on email for faster login queries
CREATE INDEX idx_usuarios_email ON usuarios(email);
CREATE INDEX idx_usuarios_ciudad ON usuarios(id_ciudad);

-- Especies table: plant species catalog
CREATE TABLE especies (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    nombre_cientifico VARCHAR(150),
    ciclo_dias INT NOT NULL,
    dias_germinacion INT NOT NULL,
    dias_floracion INT NOT NULL,
    dias_cosecha INT NOT NULL,
    agua_semanal_mm INT,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Cultivos table: farmer's crops
CREATE TABLE cultivos (
    id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    especie_id INT NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    fecha_siembra DATE NOT NULL,
    area_hectareas DECIMAL(6,2),
    etapa_actual VARCHAR(20),
    estado VARCHAR(20),
    rendimiento_kg DECIMAL(8,2),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_cultivos_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    CONSTRAINT fk_cultivos_especie FOREIGN KEY (especie_id) REFERENCES especies(id)
);

-- Create indexes for foreign keys to improve query performance
CREATE INDEX idx_cultivos_usuario ON cultivos(usuario_id);
CREATE INDEX idx_cultivos_especie ON cultivos(especie_id);

-- Tareas table: tasks for crops
CREATE TABLE tareas (
    id SERIAL PRIMARY KEY,
    cultivo_id INT NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    descripcion VARCHAR(300) NOT NULL,
    fecha_programada DATE NOT NULL,
    completada BOOLEAN NOT NULL DEFAULT FALSE,
    fecha_completada TIMESTAMP,
    notas TEXT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_tareas_cultivo FOREIGN KEY (cultivo_id) REFERENCES cultivos(id)
);

-- Create indexes for task queries
CREATE INDEX idx_tareas_cultivo ON tareas(cultivo_id);
CREATE INDEX idx_tareas_fecha ON tareas(fecha_programada);

-- Etapas table: growth stages catalog
CREATE TABLE etapas (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL,
    descripcion VARCHAR(200)
);

-- Reglas table: alert rules for growth stages
CREATE TABLE reglas (
    id SERIAL PRIMARY KEY,
    etapa_id INT NOT NULL,
    descripcion TEXT NOT NULL,
    fecha_generacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reglas_etapa FOREIGN KEY (etapa_id) REFERENCES etapas(id)
);

CREATE INDEX idx_reglas_etapa ON reglas(etapa_id);

-- Alertas table: alerts generated for crops
CREATE TABLE alertas (
    id SERIAL PRIMARY KEY,
    cultivo_id INT NOT NULL,
    regla_id INT NOT NULL,
    leida BOOLEAN NOT NULL DEFAULT FALSE,
    fecha_generacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_vencimiento TIMESTAMP,
    CONSTRAINT fk_alertas_cultivo FOREIGN KEY (cultivo_id) REFERENCES cultivos(id),
    CONSTRAINT fk_alertas_regla FOREIGN KEY (regla_id) REFERENCES reglas(id)
);

-- Create indexes for alert queries
CREATE INDEX idx_alertas_cultivo ON alertas(cultivo_id);
CREATE INDEX idx_alertas_regla ON alertas(regla_id);

-- Datos_climaticos table: climate data records
CREATE TABLE datos_climaticos (
    id SERIAL PRIMARY KEY,
    fecha DATE NOT NULL,
    temperatura_min DECIMAL(4,1),
    temperatura_max DECIMAL(4,1),
    precipitacion_mm DECIMAL(6,2),
    humedad_relativa DECIMAL(5,2),
    velocidad_viento_kmh DECIMAL(5,2),
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create index on date for faster climate data queries
CREATE INDEX idx_datos_climaticos_fecha ON datos_climaticos(fecha);

-- Configuracion_sistema table: system configuration settings
CREATE TABLE configuracion_sistema (
    id SERIAL PRIMARY KEY,
    clave VARCHAR(50) NOT NULL,
    valor VARCHAR(500) NOT NULL,
    descripcion VARCHAR(200),
    tipo VARCHAR(20),
    categoria VARCHAR(50)
);
