-- TABLA DISCIPULO --
-- INSERT
INSERT INTO discipulo (nombre, apellido, dni, email, telefono)
VALUES ('Juan', 'Pérez', '12345678', 'juan@example.com', '1234567890');

-- SELECT
SELECT * FROM discipulo;

-- UPDATE
UPDATE discipulo
SET nombre = 'Juan Carlos', email = 'juan.carlos@example.com'
WHERE id_discípulo = 1;

-- DELETE
DELETE FROM discipulo
WHERE id_discípulo = 1;


-- TABLA DISCIPLINA --
-- INSERT
INSERT INTO disciplina (nombre)
VALUES ('Discipulado Básico');

-- SELECT
SELECT * FROM disciplina;

-- UPDATE
UPDATE disciplina
SET nombre = 'Discipulado Avanzado'
WHERE id_disciplina = 1;

-- DELETE
DELETE FROM disciplina
WHERE id_disciplina = 1;

-- TABLA DISCIPULADO --
-- INSERT
INSERT INTO discipulado (id_disciplina, nombre, anio)
VALUES (1, 'Primer Año', 2024);

-- SELECT
SELECT * FROM discipulado;

-- UPDATE
UPDATE discipulado
SET nombre = 'Segundo Año', anio = 2025
WHERE id_discipulado = 1;

-- DELETE
DELETE FROM discipulado
WHERE id_discipulado = 1;

-- TABLA MATRICULACION --
-- INSERT
INSERT INTO matriculacion (id_discípulo, id_discipulado, fecha_inscripcion)
VALUES (1, 1, '2024-06-13');

-- SELECT
SELECT * FROM matriculacion;

-- UPDATE
UPDATE matriculacion
SET fecha_inscripcion = '2024-06-15'
WHERE id_matriculacion = 1;

-- DELETE
DELETE FROM matriculacion
WHERE id_matriculacion = 1;

-- TABLA CLASE --
-- INSERT
INSERT INTO clase (id_discipulado, fecha, tema)
VALUES (1, '2024-06-20', 'Fe y obediencia');

-- SELECT
SELECT * FROM clase;

-- UPDATE
UPDATE clase
SET tema = 'Fe verdadera', fecha = '2024-06-21'
WHERE id_clase = 1;

-- DELETE
DELETE FROM clase
WHERE id_clase = 1;

-- TABLA ASISTENCIA --
-- INSERT
INSERT INTO asistencia (id_matriculacion, id_clase, presente)
VALUES (1, 1, true);

-- SELECT
SELECT * FROM asistencia;

-- UPDATE
UPDATE asistencia
SET presente = false
WHERE id_asistencia = 1;

-- DELETE
DELETE FROM asistencia
WHERE id_asistencia = 1;

-- TABLA CELULA --
-- INSERT
INSERT INTO celula (localidad, caracteristica_especial, estado)
VALUES ('Barrio Norte', 'Célula juvenil', 'Activa');

-- SELECT
SELECT * FROM celula;

-- UPDATE
UPDATE celula
SET estado = 'Inactiva', caracteristica_especial = 'Suspendida temporalmente'
WHERE id_celula = 1;

-- DELETE
DELETE FROM celula
WHERE id_celula = 1;