package gestores;

import java.util.*;
import entidades.Discipulo;

public class GestorDiscipulo {
    private List<Discipulo> discipulos = new ArrayList<>();
    private int contadorId = 1;
    private Scanner scanner = new Scanner(System.in);

    public void menuDiscipulos() {
        int opcion;
        do {
            System.out.println("\n--- Gestor de Discípulos ---");
            System.out.println("1. Crear discípulo");
            System.out.println("2. Editar discípulo");
            System.out.println("3. Eliminar discípulo");
            System.out.println("4. Listar discípulos");
            System.out.println("5. Volver al menú principal");
            System.out.print("Opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número.");
                opcion = -1; // Evita ejecutar ninguna acción
                continue;
            }

            switch (opcion) {
                case 1 -> crearDiscipulo();
                case 2 -> editarDiscipulo();
                case 3 -> eliminarDiscipulo();
                case 4 -> listarDiscipulos();
                case 5 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    public void crearDiscipulo() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();

        Discipulo nuevo = new Discipulo(contadorId++, nombre, apellido);
        discipulos.add(nuevo);
        System.out.println("Discípulo registrado.");
    }

    public void editarDiscipulo() {
        listarDiscipulos();
        System.out.print("ID del discípulo a editar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Discipulo d = buscarPorId(id);

        if (d != null) {
            System.out.print("Nuevo nombre: ");
            d.setNombre(scanner.nextLine());
            System.out.print("Nuevo apellido: ");
            d.setApellido(scanner.nextLine());
            System.out.println("Discípulo actualizado.");
        } else {
            System.out.println("Discípulo no encontrado.");
        }
    }

    public void eliminarDiscipulo() {
        listarDiscipulos();
        System.out.print("ID del discípulo a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Discipulo d = buscarPorId(id);

        if (d != null) {
            discipulos.remove(d);
            System.out.println("Discípulo eliminado.");
        } else {
            System.out.println("Discípulo no encontrado.");
        }
    }

    public void listarDiscipulos() {
        if (discipulos.isEmpty()) {
            System.out.println("No hay discípulos registrados.");
        } else {
            System.out.println("--- Lista de Discípulos ---");
            for (Discipulo d : discipulos) {
                System.out.println("ID: " + d.getId() + ", Nombre: " + d.getNombreCompleto());
            }
        }
    }

    public Discipulo buscarPorId(int id) {
        for (Discipulo d : discipulos) {
            if (d.getId() == id) return d;
        }
        return null;
    }

    public List<Discipulo> getDiscipulos() {
        return discipulos;
    }
}
