INSERT INTO usuarios (username, password, nombre_completo, rol) VALUES
('medico', 'medico123', 'Dra. Ana Solis', 'MEDICO'),
('farmaceutico', 'farmacia123', 'Carlos Rojas', 'FARMACEUTICO');

INSERT INTO medicamentos (codigo, nombre, stock, precio_unitario) VALUES
('AMOX-500', 'Amoxicilina 500 mg', 80, 1250.00),
('PARA-500', 'Paracetamol 500 mg', 150, 300.00),
('IBU-400', 'Ibuprofeno 400 mg', 60, 850.00),
('LORA-10', 'Loratadina 10 mg', 45, 950.00);

INSERT INTO recetas_medicas (codigo_receta, paciente_nombre, medico_id, estado, fecha_emision)
VALUES ('RX-2026-001', 'Maria Gonzalez', 1, 'PENDIENTE', CURRENT_TIMESTAMP);

INSERT INTO detalles_receta (receta_id, medicamento_id, cantidad, dosis_indicada)
VALUES (1, 2, 10, 'Una tableta cada 8 horas');
