package gestores;

import entidades.Disciplina;
import entidades.Discipulado;
import dao.DiscipuladoDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class GestorDiscipulado {
    private List<Disciplina> disciplinasDisponibles;
    private List<Discipulado> discipulados;
    private DiscipuladoDAO discipuladoDAO;
    private Scanner scanner = new Scanner(System.in);

    public GestorDiscipulado(List<Disciplina> disciplinas, List<Discipulado> discipulados, DiscipuladoDAO dao) {
        this.disciplinasDisponibles = disciplinas;
        this.discipulados = discipulados;
        this.discipuladoDAO = dao;
    }

    public void menuDiscipulados() {
        int opcion;
        do {
            System.out.println("\n--- Gestor de Discipulados ---");
            System.out.println("1. Crear discipulado");
            System.out.println("2. Editar discipulado");
            System.out.println("3. Eliminar discipulado");
            System.out.println("4. Listar discipulados");
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
                case 1 -> {
                    Discipulado nuevo = crearDiscipulado();
                    if (nuevo != null) discipulados.add(nuevo);
                }
                case 2 -> editarDiscipulado();
                case 3 -> eliminarDiscipulado();
                case 4 -> listarDiscipulados();
                case 5 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    public Discipulado crearDiscipulado() {
        if (disciplinasDisponibles.isEmpty()) {
            System.out.println("No hay disciplinas disponibles. Cree una primero.");
            return null;
        }

        System.out.print("Nombre del discipulado: ");
        String nombre = scanner.nextLine();
        System.out.print("Año: ");
        int anio = Integer.parseInt(scanner.nextLine());

        System.out.println("Seleccione la disciplina asociada:");
        for (Disciplina d : disciplinasDisponibles) {
            System.out.println(d.getId() + ". " + d.getNombre());
        }
        System.out.print("ID de disciplina: ");
        int idDisciplina = Integer.parseInt(scanner.nextLine());
        Disciplina disciplina = buscarDisciplinaPorId(idDisciplina);

        if (disciplina != null) {
            Discipulado d = new Discipulado(nombre, anio, disciplina); // ID se asigna luego
            try {
                discipuladoDAO.guardar(d);  // ✅ Guardar en BD
                System.out.println("✔ Discipulado guardado en la base de datos.");
                return d;
            } catch (SQLException e) {
                System.out.println("❌ Error al guardar el discipulado: " + e.getMessage());
                return null;
            }
        } else {
            System.out.println("Disciplina no encontrada.");
            return null;
        }
    }

    public Discipulado editarDiscipulado() {
        listarDiscipulados();
        System.out.print("ID del discipulado a editar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Discipulado d = buscarPorId(id);

        if (d != null) {
            System.out.print("Nuevo nombre [" + d.getNombre() + "]: ");
            String nombre = scanner.nextLine();
            if (!nombre.isBlank()) d.setNombre(nombre);

            System.out.print("Nuevo año [" + d.getAnio() + "]: ");
            String anioTexto = scanner.nextLine();
            if (!anioTexto.isBlank()) {
                try {
                    d.setAnio(Integer.parseInt(anioTexto));
                } catch (NumberFormatException e) {
                    System.out.println("Año inválido. Se mantuvo el valor anterior.");
                }
            }

            try {
                discipuladoDAO.actualizar(d);  // ✅ Actualizar en BD
                System.out.println("✔ Discipulado actualizado en la base de datos.");
            } catch (SQLException e) {
                System.out.println("❌ Error al actualizar el discipulado: " + e.getMessage());
            }

            return d;
        } else {
            System.out.println("Discipulado no encontrado.");
            return null;
        }
    }

    public Discipulado eliminarDiscipulado() {
        listarDiscipulados();
        System.out.print("ID del discipulado a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Discipulado d = buscarPorId(id);

        if (d != null) {
            try {
                discipuladoDAO.eliminar(d.getId());  // ✅ Eliminar de la BD
                discipulados.remove(d);              // Eliminar de la lista local
                System.out.println("Discipulado eliminado de la base de datos.");
            } catch (SQLException e) {
                System.out.println("Error al eliminar el discipulado: " + e.getMessage());
            }
            return d;
        } else {
            System.out.println("Discipulado no encontrado.");
            return null;
        }
    }

    public void listarDiscipulados() {
        try {
            discipulados = discipuladoDAO.listarTodos(disciplinasDisponibles);; // Recargar desde BD
        } catch (SQLException e) {
            System.out.println("Error al listar discipulados: " + e.getMessage());
            return;
        }

        if (discipulados.isEmpty()) {
            System.out.println("No hay discipulados registrados.");
        } else {
            System.out.println("--- Lista de Discipulados ---");
            for (Discipulado d : discipulados) {
                System.out.println("ID: " + d.getId() +
                        ", Nombre: " + d.getNombre() +
                        ", Año: " + d.getAnio() +
                        ", Disciplina: " + d.getDisciplina().getNombre());
            }
        }
    }

    public Discipulado buscarPorId(int id) {
        for (Discipulado d : discipulados) {
            if (d.getId() == id) return d;
        }
        return null;
    }

    private Disciplina buscarDisciplinaPorId(int id) {
        for (Disciplina d : disciplinasDisponibles) {
            if (d.getId() == id) return d;
        }
        return null;
    }

    public List<Discipulado> getDiscipulados() {
        return discipulados;
    }
}
