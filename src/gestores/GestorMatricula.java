package gestores;

import java.util.*;
import java.sql.SQLException;
import entidades.Matricula;
import entidades.Discipulo;
import entidades.Discipulado;
import dao.MatriculaDAO;

public class GestorMatricula {
    private List<Matricula> matriculas = new ArrayList<>();
    private List<Discipulo> discipulosDisponibles;
    private List<Discipulado> discipuladosDisponibles;
    private MatriculaDAO matriculaDAO;
    private Scanner scanner = new Scanner(System.in);

    public GestorMatricula(List<Discipulo> discipulos, List<Discipulado> discipulados, List<Matricula> matriculas, MatriculaDAO dao) {
        this.discipulosDisponibles = discipulos;
        this.discipuladosDisponibles = discipulados;
        this.matriculas = matriculas;
        this.matriculaDAO = dao;
        cargarDesdeBD();
    }

    private void cargarDesdeBD() {
        try {
            this.matriculas = matriculaDAO.listarTodas(discipulosDisponibles, discipuladosDisponibles);
        } catch (SQLException e) {
            System.out.println("Error al cargar matrículas desde la base de datos: " + e.getMessage());
        }
    }

    public void menuMatriculas() {
        int opcion;
        do {
            System.out.println("\n--- Gestor de Matrículas ---");
            System.out.println("1. Registrar matrícula");
            System.out.println("2. Editar matrícula");
            System.out.println("3. Eliminar matrícula");
            System.out.println("4. Listar matrículas");
            System.out.println("5. Volver al menú principal");
            System.out.print("Opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número.");
                opcion = -1;
                continue;
            }

            switch (opcion) {
                case 1 -> registrarMatricula();
                case 2 -> editarMatricula();
                case 3 -> eliminarMatricula();
                case 4 -> listarMatriculas();
                case 5 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    public void registrarMatricula() {
        Discipulo discipulo = seleccionarDiscipulo();
        Discipulado discipulado = seleccionarDiscipulado();

        if (discipulo != null && discipulado != null) {
            System.out.print("Fecha de inscripción (AAAA-MM-DD): ");
            String fecha = scanner.nextLine();

            Matricula m = new Matricula(discipulo, discipulado, fecha);
            try {
                matriculaDAO.guardar(m);
                matriculas.add(m);
                System.out.println("Matrícula registrada.");
            } catch (SQLException e) {
                System.out.println("Error al registrar la matrícula: " + e.getMessage());
            }
        }
    }

    public void editarMatricula() {
        listarMatriculas();
        System.out.print("ID de matrícula a editar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Matricula m = buscarPorId(id);

        if (m != null) {
            System.out.print("Nueva fecha de inscripción: ");
            m.setFechaInscripcion(scanner.nextLine());

            try {
                matriculaDAO.actualizar(m);
                System.out.println("Matrícula actualizada.");
            } catch (SQLException e) {
                System.out.println("Error al actualizar la matrícula: " + e.getMessage());
            }
        } else {
            System.out.println("Matrícula no encontrada.");
        }
    }

    public void eliminarMatricula() {
        listarMatriculas();
        System.out.print("ID de matrícula a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Matricula m = buscarPorId(id);

        if (m != null) {
            try {
                matriculaDAO.eliminar(m.getId());
                matriculas.remove(m);
                System.out.println("Matrícula eliminada.");
            } catch (SQLException e) {
                System.out.println("Error al eliminar la matrícula: " + e.getMessage());
            }
        } else {
            System.out.println("Matrícula no encontrada.");
        }
    }

    public void listarMatriculas() {
        if (matriculas.isEmpty()) {
            System.out.println("No hay matrículas registradas.");
        } else {
            System.out.println("--- Lista de Matrículas ---");
            for (Matricula m : matriculas) {
                System.out.println("ID: " + m.getId() +
                        ", Discípulo: " + m.getDiscipulo().getNombreCompleto() +
                        ", Discipulado: " + m.getDiscipulado().getNombre() +
                        ", Fecha: " + m.getFechaInscripcion());
            }
        }
    }

    public Matricula buscarPorId(int id) {
        for (Matricula m : matriculas) {
            if (m.getId() == id) return m;
        }
        return null;
    }

    private Discipulo seleccionarDiscipulo() {
        if (discipulosDisponibles.isEmpty()) {
            System.out.println("No hay discípulos disponibles.");
            return null;
        }

        System.out.println("--- Seleccione un discípulo ---");
        for (Discipulo d : discipulosDisponibles) {
            System.out.println(d.getId() + ". " + d.getNombreCompleto());
        }
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        for (Discipulo d : discipulosDisponibles) {
            if (d.getId() == id) return d;
        }
        System.out.println("Discípulo no encontrado.");
        return null;
    }

    private Discipulado seleccionarDiscipulado() {
        if (discipuladosDisponibles.isEmpty()) {
            System.out.println("No hay discipulados disponibles.");
            return null;
        }

        System.out.println("--- Seleccione un discipulado ---");
        for (Discipulado d : discipuladosDisponibles) {
            System.out.println(d.getId() + ". " + d.getNombre());
        }
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        for (Discipulado d : discipuladosDisponibles) {
            if (d.getId() == id) return d;
        }
        System.out.println("Discipulado no encontrado.");
        return null;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }
}
