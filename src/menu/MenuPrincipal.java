package menu;

import conexion.ConexionBD;
import dao.*;
import entidades.*;
import gestores.*;
import servicios.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class MenuPrincipal {
    private Scanner scanner = new Scanner(System.in);

    // Listas compartidas
    private List<Disciplina> disciplinas;
    private List<Discipulo> discipulos;
    private List<Discipulado> discipulados;
    private List<Clase> clases;
    private List<Matricula> matriculas;
    private List<Asistencia> asistencias;
    private List<Celula> celulas;

    // Gestores y servicios
    private GestorDisciplina gestorDisciplina;
    private GestorDiscipulo gestorDiscipulo;
    private GestorDiscipulado gestorDiscipulado;
    private GestorClase gestorClase;
    private GestorMatricula gestorMatricula;
    private GestorAsistencia gestorAsistencia;
    private GestorCelula gestorCelula;

    private DeterminarAsistencia determinarAsistencia;
    private GeneradorDeAlertas generadorDeAlertas;

    public MenuPrincipal() {
        try {
            Connection conexion = ConexionBD.getConexion();

            // DAOs
            DisciplinaDAO disciplinaDAO = new DisciplinaDAO(conexion);
            DiscipuloDAO discipuloDAO = new DiscipuloDAO(conexion);
            DiscipuladoDAO discipuladoDAO = new DiscipuladoDAO(conexion);
            ClaseDAO claseDAO = new ClaseDAO(conexion);
            MatriculaDAO matriculaDAO = new MatriculaDAO(conexion);
            AsistenciaDAO asistenciaDAO = new AsistenciaDAO(conexion);
            CelulaDAO celulaDAO = new CelulaDAO(conexion);

            // Cargar listas desde BD
            disciplinas = disciplinaDAO.listarTodas();
            discipulos = discipuloDAO.listarTodos();
            discipulados = discipuladoDAO.listarTodos(disciplinas);
            clases = claseDAO.listarTodas(discipulados);
            matriculas = matriculaDAO.listarTodas(discipulos, discipulados);
            asistencias = asistenciaDAO.listarTodas(matriculas, clases);
            celulas = celulaDAO.listarTodas(disciplinas, discipulos);

            // Crear gestores
            gestorDisciplina = new GestorDisciplina(disciplinas, disciplinaDAO);
            gestorDiscipulo = new GestorDiscipulo(discipulos, discipuloDAO);
            gestorDiscipulado = new GestorDiscipulado(disciplinas, discipulados, discipuladoDAO);
            gestorClase = new GestorClase(discipulados, clases, claseDAO);
            gestorMatricula = new GestorMatricula(discipulos, discipulados, matriculas, matriculaDAO);
            gestorAsistencia = new GestorAsistencia(clases, discipulos, matriculas, asistencias, asistenciaDAO);
            gestorCelula = new GestorCelula(disciplinas, discipulos, celulas, celulaDAO);

            // Crear servicios
            determinarAsistencia = new DeterminarAsistencia(discipulados, clases, matriculas, discipulos, asistencias);
            generadorDeAlertas = new GeneradorDeAlertas(celulas, discipulados, clases, matriculas, asistencias);

        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
            System.exit(1);
        }
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Gestor de Discípulos");
            System.out.println("2. Gestor de Disciplinas");
            System.out.println("3. Gestor de Discipulados");
            System.out.println("4. Gestor de Clases");
            System.out.println("5. Gestor de Matrículas");
            System.out.println("6. Gestor de Asistencias");
            System.out.println("7. Gestor de Células");
            System.out.println("8. Control de Porcentaje de Asistencias");
            System.out.println("9. Alertas de incumplimiento");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida.");
                opcion = -1;
            }

            switch (opcion) {
                case 1 -> gestorDiscipulo.menuDiscipulos();
                case 2 -> gestorDisciplina.menuDisciplinas();
                case 3 -> gestorDiscipulado.menuDiscipulados();
                case 4 -> gestorClase.menuClases();
                case 5 -> gestorMatricula.menuMatriculas();
                case 6 -> gestorAsistencia.menuAsistencia();
                case 7 -> gestorCelula.menuCelulas();
                case 8 -> determinarAsistencia.listarPorcentajeAsistencia();
                case 9 -> generadorDeAlertas.generarAlertas();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }
}
