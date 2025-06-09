package gestores;

import java.util.*;
import entidades.Discipulado;
import entidades.Disciplina;

public class GestorDiscipulado {
    private List<Discipulado> discipulados = new ArrayList<>();
    private List<Disciplina> disciplinasDisponibles;
    private int contadorId = 1;
    private Scanner scanner = new Scanner(System.in);

    public GestorDiscipulado(List<Disciplina> disciplinas) {
        this.disciplinasDisponibles = disciplinas;
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
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> crearDiscipulado();
                case 2 -> editarDiscipulado();
                case 3 -> eliminarDiscipulado();
                case 4 -> listarDiscipulados();
                case 5 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    public void crearDiscipulado() {
        if (disciplinasDisponibles.isEmpty()) {
            System.out.println("No hay disciplinas disponibles. Debe crear una primero.");
            return;
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
            Discipulado d = new Discipulado(contadorId++, nombre, anio, disciplina);
            discipulados.add(d);
            System.out.println("Discipulado registrado.");
        } else {
            System.out.println("Disciplina no encontrada.");
        }
    }

    public void editarDiscipulado() {
        listarDiscipulados();
        System.out.print("ID del discipulado a editar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Discipulado d = buscarPorId(id);

        if (d != null) {
            System.out.print("Nuevo nombre: ");
            d.setNombre(scanner.nextLine());
            System.out.print("Nuevo año: ");
            d.setAnio(Integer.parseInt(scanner.nextLine()));
            System.out.println("Discipulado actualizado.");
        } else {
            System.out.println("Discipulado no encontrado.");
        }
    }

    public void eliminarDiscipulado() {
        listarDiscipulados();
        System.out.print("ID del discipulado a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Discipulado d = buscarPorId(id);

        if (d != null) {
            discipulados.remove(d);
            System.out.println("Discipulado eliminado.");
        } else {
            System.out.println("Discipulado no encontrado.");
        }
    }

    public void listarDiscipulados() {
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
