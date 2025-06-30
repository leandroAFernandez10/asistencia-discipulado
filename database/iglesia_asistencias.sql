CREATE DATABASE iglesia_asistencias;
USE iglesia_asistencias;

-- Tabla Discípulo
CREATE TABLE discipulo (
  id_discípulo INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(50),
  apellido VARCHAR(50),
  dni VARCHAR(20) UNIQUE,
  email VARCHAR(100),
  telefono VARCHAR(20),
  genero ENUM('Masculino', 'Femenino'),
);

-- Tabla Disciplina
CREATE TABLE disciplina (
  id_disciplina INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100)
);

-- Tabla Discipulado
CREATE TABLE discipulado (
  id_discipulado INT AUTO_INCREMENT PRIMARY KEY,
  id_disciplina INT,
  nombre VARCHAR(100),
  anio YEAR,
  FOREIGN KEY (id_disciplina) REFERENCES disciplina(id_disciplina)
);

-- Tabla Matriculación
CREATE TABLE matriculacion (
  id_matriculacion INT AUTO_INCREMENT PRIMARY KEY,
  id_discípulo INT,
  id_discipulado INT,
  fecha_inscripcion DATE,
  FOREIGN KEY (id_discípulo) REFERENCES discipulo(id_discípulo),
  FOREIGN KEY (id_discipulado) REFERENCES discipulado(id_discipulado)
);

-- Tabla Clase
CREATE TABLE clase (
  id_clase INT AUTO_INCREMENT PRIMARY KEY,
  id_discipulado INT,
  fecha DATE,
  tema VARCHAR(100),
  FOREIGN KEY (id_discipulado) REFERENCES discipulado(id_discipulado)
);

-- Tabla Asistencia
CREATE TABLE asistencia (
  id_asistencia INT AUTO_INCREMENT PRIMARY KEY,
  id_matriculacion INT,
  id_clase INT,
  presente BOOLEAN,
  FOREIGN KEY (id_matriculacion) REFERENCES matriculacion(id_matriculacion),
  FOREIGN KEY (id_clase) REFERENCES clase(id_clase)
);

 -- Tabla Celula
CREATE TABLE celula (
  id_celula INT AUTO_INCREMENT PRIMARY KEY,
  localidad VARCHAR(100),
  timonel INT,
  anfitrion INT,
  colaborador INT,
  anio YEAR,
  estado ENUM('Activa', 'Inactiva'),
  FOREIGN KEY (timonel) REFERENCES discipulo(id_discípulo),
  FOREIGN KEY (anfitrion) REFERENCES discipulo(id_discípulo),
  FOREIGN KEY (colaborador) REFERENCES discipulo(id_discípulo)
);

