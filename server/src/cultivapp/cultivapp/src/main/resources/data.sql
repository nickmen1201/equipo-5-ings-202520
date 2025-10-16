-- Insert users without explicit id so the IDENTITY column advances correctly
INSERT INTO usuarios (email, password, nombre, apellido, ciudad, telefono, rol, activo, fecha_registro) VALUES 
('admin@cultivapp.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Admin', 'Sistema', 'Medellin', '3001234567', 'ADMIN', TRUE, CURRENT_TIMESTAMP),
('productor@cultivapp.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Juan', 'Pérez', 'apartado', '30000202', 'PRODUCTOR', TRUE, CURRENT_TIMESTAMP);
