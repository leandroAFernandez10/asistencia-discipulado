package gestores;

import dao.ClaseDAO;
import entidades.Clase;
import entidades.Discipulado;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestorClase {
    private List<Clase> clases = new ArrayList<>();
    private List<Discipulado> discipuladosDisponibles;
    private ClaseDAO claseDAO;
    private int contadorId = 1;
    private Scanner scanner = new Scanner(System.in);

    public GestorClase(List<Discipulado> discipulados, List<Clase> clases, ClaseDAO dao) {
        this.discipuladosDisponibles = discipulados;
        this.clases = clases;
        this.claseDAO = dao;
        cargarDesdeBD();
    }

    private void cargarDesdeBD() {
        try {
            this.clases = claseDAO.listarTodas(discipuladosDisponibles);
            for (Clase c : clases) {
                if (c.getId() >= contadorId) {
                    contadorId = c.getId() + 1;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar clases desde la base de datos: " + e.getMessage());
        }
    }

    public void menuClases() {
        int opcion;
        do {
            System.out.println("\n--- Gestor de Clases ---");
            System.out.println("1. Crear clase");
            System.out.println("2. Editar clase");
            System.out.println("3. Eliminar clase");
            System.out.println("4. Listar clases");
            System.out.println("5. Volver al menú principal");
            System.out.print("Opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida.");
                opcion = -1;
            }

            switch (opcion) {
                case 1 -> crearClase();
                case 2 -> editarClase();
                case 3 -> eliminarClase();
                case 4 -> listarClases();
                case 5 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    public void crearClase() {
        System.out.print("Tema: ");
        String tema = scanner.nextLine();

        System.out.print("Fecha (AAAA-MM-DD): ");
        String fecha = scanner.nextLine();

        Discipulado discipulado = seleccionarDiscipulado();
        if (discipulado == null) {
            System.out.println("No se pudo seleccionar un discipulado.");
            return;
        }

        Clase nueva = new Clase(0, tema, fecha, discipulado);
        try {
            claseDAO.guardar(nueva);
            clases.add(nueva);
            System.out.println("Clase registrada.");
        } catch (SQLException e) {
            System.out.println("Error al guardar clase: " + e.getMessage());
        }
    }

    public void editarClase() {
        listarClases();
        System.out.print("ID de clase a editar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Clase c = buscarPorId(id);

        if (c == null) {
            System.out.println("Clase no encontrada.");
            return;
        }

        System.out.print("Nuevo tema: ");
        c.setTema(scanner.nextLine());

        System.out.print("Nueva fecha (AAAA-MM-DD): ");
        c.setFecha(scanner.nextLine());

        Discipulado nuevoDiscipulado = seleccionarDiscipulado();
        if (nuevoDiscipulado != null) {
            c.setDiscipulado(nuevoDiscipulado);
        }

        try {
            claseDAO.actualizar(c);
            System.out.println("Clase actualizada.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar clase: " + e.getMessage());
        }
    }

    public void eliminarClase() {
        listarClases();
        System.out.print("ID de clase a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Clase c = buscarPorId(id);

        if (c != null) {
            try {
                claseDAO.eliminar(c.getId());
                clases.remove(c);
                System.out.println("Clase eliminada.");
            } catch (SQLException e) {
                System.out.println("Error al eliminar clase: " + e.getMessage());
            }
        } else {
            System.out.println("Clase no encontrada.");
        }
    }

    public void listarClases() {
        if (clases.isEmpty()) {
            System.out.println("No hay clases registradas.");
        } else {
            System.out.println("--- Lista de Clases ---");
            for (Clase c : clases) {
                System.out.println("ID: " + c.getId() +
                        ", Tema: " + c.getTema() +
                        ", Fecha: " + c.getFecha() +
                        ", Discipulado: " + c.getDiscipulado().getNombre());
            }
        }
    }

    private Discipulado seleccionarDiscipulado() {
        if (discipuladosDisponibles.isEmpty()) {
            System.out.println("No hay discipulados disponibles.");
            return null;
        }

        System.out.println("--- Seleccione un discipulado ---");
        for (Discipulado d : discipuladosDisponibles) {
            System.out.println("ID: " + d.getId() + " - " + d.getNombre());
        }

        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        for (Discipulado d : discipuladosDisponibles) {
            if (d.getId() == id) return d;
        }

        System.out.println("Discipulado no encontrado.");
        return null;
    }

    public Clase buscarPorId(int id) {
        for (Clase c : clases) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    public List<Clase> getClases() {
        return clases;
    }
}
