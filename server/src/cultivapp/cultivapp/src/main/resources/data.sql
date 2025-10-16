-- Insert users without explicit id so the IDENTITY column advances correctly
INSERT INTO usuarios (email, password, nombre, apellido, ciudad, telefono, rol, activo, fecha_registro) VALUES 
('admin@cultivapp.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Admin', 'Sistema', 'Medellin', '3001234567', 'ADMIN', TRUE, CURRENT_TIMESTAMP),
('productor@cultivapp.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Juan', 'Pérez', 'apartado', '30000202', 'PRODUCTOR', TRUE, CURRENT_TIMESTAMP);

-- Insert 8 base species for the catalog
INSERT INTO especies (nombre, nombre_cientifico, descripcion, ciclo_dias, dias_germinacion, dias_floracion, dias_cosecha, agua_semanal_mm, dias_fertilizacion, imagen_url, activo, fecha_creacion) VALUES 
('Tomate', 'Solanum lycopersicum', 'Planta herbácea de fruto rojo, rico en licopeno y vitaminas. Ideal para climas cálidos y templados.', 120, 7, 45, 75, 25, 30, 'https://images.unsplash.com/photo-1592924357228-91a4daadcfea?w=400', TRUE, CURRENT_TIMESTAMP),
('Maíz', 'Zea mays', 'Cereal de alto valor nutricional, base de la alimentación en América. Requiere suelos fértiles y buen drenaje.', 150, 10, 60, 90, 30, 40, 'https://images.unsplash.com/photo-1551754655-cd27e38d2076?w=400', TRUE, CURRENT_TIMESTAMP),
('Frijol', 'Phaseolus vulgaris', 'Leguminosa rica en proteínas, fija nitrógeno al suelo. Excelente para rotación de cultivos.', 90, 5, 35, 60, 20, 25, 'https://images.unsplash.com/photo-1615485290382-441e4d049cb5?w=400', TRUE, CURRENT_TIMESTAMP),
('Lechuga', 'Lactuca sativa', 'Hortaliza de hoja verde, ciclo corto. Perfecta para cultivos urbanos y huertos caseros.', 60, 4, 30, 45, 15, 20, 'https://images.unsplash.com/photo-1622206151226-18ca2c9ab4a1?w=400', TRUE, CURRENT_TIMESTAMP),
('Zanahoria', 'Daucus carota', 'Raíz comestible rica en betacaroteno. Requiere suelos sueltos y profundos para buen desarrollo.', 100, 14, 50, 70, 18, 35, 'https://images.unsplash.com/photo-1598170845058-32b9d6a5da37?w=400', TRUE, CURRENT_TIMESTAMP),
('Papa', 'Solanum tuberosum', 'Tubérculo versátil y nutritivo. Cultivo de alto rendimiento en climas frescos y templados.', 120, 15, 40, 90, 22, 30, 'https://images.unsplash.com/photo-1518977676601-b53f82aba655?w=400', TRUE, CURRENT_TIMESTAMP),
('Cebolla', 'Allium cepa', 'Bulbo aromático esencial en cocina. Requiere suelos bien drenados y riego constante.', 140, 10, 60, 100, 20, 45, 'https://images.unsplash.com/photo-1618512496248-a07fe83aa8cb?w=400', TRUE, CURRENT_TIMESTAMP),
('Pimentón', 'Capsicum annuum', 'Fruto dulce o picante, rico en vitamina C. Excelente para cultivos en invernadero o campo abierto.', 130, 8, 50, 80, 25, 35, 'https://images.unsplash.com/photo-1563565375-f3fdfdbefa83?w=400', TRUE, CURRENT_TIMESTAMP);
