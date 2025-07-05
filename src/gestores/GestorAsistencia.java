package gestores;

import dao.AsistenciaDAO;
import entidades.Asistencia;
import entidades.Clase;
import entidades.Matricula;
import entidades.Discipulo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestorAsistencia {
    private List<Clase> clases;
    private List<Discipulo> discipulos;
    private List<Matricula> matriculas;
    private List<Asistencia> asistencias;
    private AsistenciaDAO asistenciaDAO;
    private Scanner scanner = new Scanner(System.in);

    public GestorAsistencia(List<Clase> clases, List<Discipulo> discipulos, List<Matricula> matriculas, List<Asistencia> asistencias, AsistenciaDAO dao) {
        this.clases = clases;
        this.discipulos = discipulos;
        this.matriculas = matriculas;
        this.asistencias = asistencias;
        this.asistenciaDAO = dao;
        cargarDesdeBD();
    }

    private void cargarDesdeBD() {
        try {
            this.asistencias = asistenciaDAO.listarTodas(matriculas, clases);
        } catch (SQLException e) {
            System.out.println("Error al cargar asistencias desde la base de datos: " + e.getMessage());
            this.asistencias = new ArrayList<>();
        }
    }

    public void menuAsistencia() {
        int opcion;
        do {
            System.out.println("\n--- Gestor de Asistencias ---");
            System.out.println("1. Registrar asistencia");
            System.out.println("2. Listar asistencias");
            System.out.println("3. Eliminar asistencia");
            System.out.println("4. Volver al menú principal");
            System.out.print("Opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida.");
                opcion = -1;
            }

            switch (opcion) {
                case 1 -> registrarAsistencia();
                case 2 -> listarAsistencias();
                case 3 -> eliminarAsistencia();
                case 4 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 4);
    }

    private void registrarAsistencia() {
        Clase clase = seleccionarClase();
        if (clase == null) return;

        System.out.println("--- Registro de asistencia para la clase: " + clase.getTema() + " ---");

        for (Matricula m : matriculas) {
            if (m.getDiscipulado().getId() == clase.getDiscipulado().getId()) {
                System.out.print("¿Asistió " + m.getDiscipulo().getNombreCompleto() + "? (s/n): ");
                String respuesta = scanner.nextLine().trim();

                if (respuesta.equalsIgnoreCase("s")) {
                    try {
                        if (!asistenciaDAO.asistenciaYaRegistrada(clase.getId(), m.getId())) {
                            Asistencia nueva = new Asistencia(clase, m);
                            asistenciaDAO.guardar(nueva);
                            asistencias.add(nueva);
                            System.out.println("Asistencia registrada.");
                        } else {
                            System.out.println("Ya se había registrado asistencia para este discípulo en esta clase.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error al registrar asistencia: " + e.getMessage());
                    }
                }
            }
        }
    }

    private Clase seleccionarClase() {
        if (clases.isEmpty()) {
            System.out.println("No hay clases disponibles.");
            return null;
        }

        System.out.println("--- Clases disponibles ---");
        for (Clase c : clases) {
            System.out.println("ID: " + c.getId() + " - " + c.getTema() + " (" + c.getFecha() + ")");
        }

        System.out.print("Ingrese el ID de la clase: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            for (Clase c : clases) {
                if (c.getId() == id) return c;
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }

        System.out.println("Clase no encontrada.");
        return null;
    }

    private void listarAsistencias() {
        if (asistencias.isEmpty()) {
            System.out.println("No hay asistencias registradas.");
            return;
        }

        System.out.println("--- Lista de Asistencias ---");
        for (Asistencia a : asistencias) {
            System.out.println("ID: " + a.getId()
                    + ", Discípulo: " + a.getMatricula().getDiscipulo().getNombreCompleto()
                    + ", Discipulado: " + a.getMatricula().getDiscipulado().getNombre()
                    + ", Clase: " + a.getClase().getTema()
                    + ", Fecha: " + a.getClase().getFecha());
        }
    }

    private void eliminarAsistencia() {
        listarAsistencias();
        if (asistencias.isEmpty()) return;

        System.out.print("Ingrese el ID de la asistencia a eliminar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Asistencia aEliminar = buscarAsistenciaPorId(id);
            if (aEliminar != null) {
                asistenciaDAO.eliminarPorId(id);
                asistencias.remove(aEliminar);
                System.out.println("Asistencia eliminada.");
            } else {
                System.out.println("No se encontró una asistencia con ese ID.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar asistencia: " + e.getMessage());
        }
    }

    private Asistencia buscarAsistenciaPorId(int id) {
        for (Asistencia a : asistencias) {
            if (a.getId() == id) return a;
        }
        return null;
    }

    public List<Asistencia> getAsistencias() {
        return asistencias;
    }
}
