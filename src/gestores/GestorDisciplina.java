import java.util.*;

public class GestorDisciplina {
    private List<Disciplina> disciplinas = new ArrayList<>();
    private int contadorId = 1;
    private Scanner scanner = new Scanner(System.in);

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
            opcion = Integer.parseInt(scanner.nextLine());

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

    public void crearDisciplina() {
        System.out.print("Nombre de la disciplina: ");
        String nombre = scanner.nextLine();
        Disciplina nueva = new Disciplina(contadorId++, nombre);
        disciplinas.add(nueva);
        System.out.println("Disciplina registrada.");
    }

    public void editarDisciplina() {
        listarDisciplinas();
        System.out.print("ID de disciplina a editar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Disciplina d = buscarPorId(id);

        if (d != null) {
            System.out.print("Nuevo nombre: ");
            d.setNombre(scanner.nextLine());
            System.out.println("Disciplina actualizada.");
        } else {
            System.out.println("Disciplina no encontrada.");
        }
    }

    public void eliminarDisciplina() {
        listarDisciplinas();
        System.out.print("ID de disciplina a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Disciplina d = buscarPorId(id);

        if (d != null) {
            disciplinas.remove(d);
            System.out.println("Disciplina eliminada.");
        } else {
            System.out.println("Disciplina no encontrada.");
        }
    }

    public void listarDisciplinas() {
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
}
