package gestores;

import entidades.Disciplina;
import dao.DisciplinaDAO;
import java.util.List;
import java.util.Scanner;
import java.sql.SQLException;

public class GestorDisciplina {
    private List<Disciplina> disciplinas;
    private DisciplinaDAO disciplinaDAO;
    private Scanner scanner = new Scanner(System.in);

    public GestorDisciplina(List<Disciplina> disciplinas, DisciplinaDAO dao) {
        this.disciplinas = disciplinas;
        this.disciplinaDAO = dao;
    }

    public void menuDisciplinas() {
        int opcion;
        do {
            System.out.println("\n--- Gestor de Disciplinas ---");
            System.out.println("1. Crear disciplina");
            System.out.println("2. Editar disciplina");
            System.out.println("3. Eliminar disciplina");
            System.out.println("4. Listar disciplinas");
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
                case 1 -> crearDisciplina();
                case 2 -> editarDisciplina();
                case 3 -> eliminarDisciplina();
                case 4 -> listarDisciplinas();
                case 5 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    public Disciplina crearDisciplina() {
        System.out.print("Nombre de la disciplina: ");
        String nombre = scanner.nextLine();

        if (nombre.isBlank()) {
            System.out.println("El nombre no puede estar vacío.");
            return null;
        }

        Disciplina nueva = new Disciplina(nombre);
        try {
            disciplinaDAO.guardar(nueva);
            disciplinas.clear();
            disciplinas.addAll(disciplinaDAO.listarTodas());
            System.out.println("Disciplina guardada en la base de datos.");
            return buscarPorNombre(nombre);
        } catch (SQLException e) {
            System.out.println("Error al guardar en la BD: " + e.getMessage());
            return null;
        }
    }

    public Disciplina editarDisciplina() {
        listarDisciplinas();
        System.out.print("ID de disciplina a editar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Disciplina d = buscarPorId(id);

        if (d != null) {
            System.out.print("Nuevo nombre [" + d.getNombre() + "]: ");
            String nuevoNombre = scanner.nextLine();
            if (!nuevoNombre.isBlank()) d.setNombre(nuevoNombre);

            try {
                disciplinaDAO.actualizar(d); // actualizar en BD
                System.out.println("Disciplina actualizada en la base de datos.");
            } catch (SQLException e) {
                System.out.println("Error al actualizar en la BD: " + e.getMessage());
            }

            return d;
        } else {
            System.out.println("Disciplina no encontrada.");
            return null;
        }
    }

    public Disciplina eliminarDisciplina() {
        listarDisciplinas();
        System.out.print("ID de disciplina a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Disciplina d = buscarPorId(id);

        if (d != null) {
            try {
                disciplinaDAO.eliminar(d.getId()); // ✅ eliminar en BD
                disciplinas.remove(d);
                System.out.println("✔ Disciplina eliminada de la base de datos.");
            } catch (SQLException e) {
                System.out.println("❌ Error al eliminar de la BD: " + e.getMessage());
            }

            return d;
        } else {
            System.out.println("❌ Disciplina no encontrada.");
            return null;
        }
    }

    public void listarDisciplinas() {
        try {
            disciplinas = disciplinaDAO.listarTodas(); // ✅ recarga desde BD
        } catch (SQLException e) {
            System.out.println("❌ Error al listar disciplinas: " + e.getMessage());
            return;
        }

        if (disciplinas.isEmpty()) {
            System.out.println("No hay disciplinas registradas.");
        } else {
            System.out.println("--- Lista de Disciplinas ---");
            for (Disciplina d : disciplinas) {
                System.out.println("ID: " + d.getId() + ", Nombre: " + d.getNombre());
            }
        }
    }

    public Disciplina buscarPorId(int id) {
        for (Disciplina d : disciplinas) {
            if (d.getId() == id) return d;
        }
        return null;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }
    private Disciplina buscarPorNombre(String nombre) {
    for (Disciplina d : disciplinas) {
        if (d.getNombre().equalsIgnoreCase(nombre)) {
            return d;
        }
    }
    return null;
}

}
