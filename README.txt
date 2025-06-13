CREDENCIALES DE ACCESO: 
  Usuario: admin
  Contraseña: 1234


SISTEMA DE ASISTENCIA A CLASES DE DISCIPULADO
Este sistema está diseñado para gestionar el proceso formativo de discípulos dentro de un contexto educativo estructurado en disciplinas, discipulados, clases, asistencias y células. Su objetivo es permitir un seguimiento claro y detallado del progreso individual de cada participante, asegurando que se cumplan los requisitos teóricos y prácticos para su formación integral.

ESTRUCTURA GENERAL DEL SISTEMA
El sistema trabaja con las siguientes entidades principales:
1. Disciplina: Una disciplina representa un área de formación. Cada disciplina puede tener uno o más discipulados teóricos asociados, así como varias células prácticas.
2. Discipulado: Un discipulado es un curso teórico que pertenece a una disciplina específica. Contiene un conjunto de clases que deben ser completadas por los discípulos. Cada discipulado tiene una relación directa con su disciplina.
3. Clase: Una clase es una instancia específica dentro de un discipulado. Incluye un tema, una fecha y se asocia al discipulado al que pertenece. Las asistencias se registran clase por clase.
4. Discípulo: Es la persona que participa del proceso de formación. Un discípulo puede estar matriculado a uno o más discipulados y puede participar en diferentes células si cumple los requisitos necesarios.
5. Matrícula: La matrícula es la relación formal que vincula a un discípulo con un discipulado. Permite controlar qué discípulos están habilitados para asistir a las clases de un determinado curso.
6. Asistencia: La asistencia se registra únicamente cuando un discípulo estuvo presente en una clase. No se registran asistencias "ausentes". La cantidad total de asistencias determina el porcentaje de cumplimiento del discipulado por parte del discípulo.
7. Célula: Una célula representa el espacio práctico dentro del sistema. Está asociada a una disciplina y formada por discípulos que hayan alcanzado un porcentaje mínimo de asistencia a las clases teóricas de un discipulado de la misma disciplina. Los discípulos pueden tener roles dentro de la célula, como anfitrión, timonel o colaborador.

FUNCIONALIDADES DEL SISTEMA
Registro, modificación y eliminación de disciplinas, discipulados, clases y discípulos.
Registro de matrículas y control de qué discipulado cursa cada discípulo.
Registro de asistencias solo para discípulos presentes.
Cálculo del porcentaje de asistencia por discipulado.
Generación de alertas cuando un discípulo con rol activo en una célula no cumple con el porcentaje mínimo requerido de asistencia teórica.
Listados generales de clases, asistencias, células, disciplinas, etc.

OBJETIVOS DEL SISTEMA
Facilitar la gestión de múltiples cursos y espacios prácticos dentro de una estructura educativa organizada.
Evaluar el compromiso y la participación de los discípulos tanto en la teoría como en la práctica.
Garantizar que los requisitos de formación se cumplan de forma clara y medible.
Detectar posibles falencias en la asistencia y generar alertas que permitan intervenir a tiempo.

USO ESPERADO
Este sistema está pensado para ejecutarse por consola y ser utilizado por una persona encargada de registrar y consultar la información. Las funcionalidades están organizadas por módulos, cada uno con sus respectivas opciones para facilitar su manejo.