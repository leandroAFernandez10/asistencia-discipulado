package gestores;

import java.util.*;
import entidades.Celula;
import enumeracion.EstadoCelula;
        
public class GestorCelula {
    private List<Celula> celulas = new ArrayList<>();
    private int contadorId = 1;
    private Scanner scanner = new Scanner(System.in);

    public void menuCelulas() {
        int opcion;
        do {
            System.out.println("\n--- Gestor de Células ---");
            System.out.println("1. Crear célula");
            System.out.println("2. Editar célula");
            System.out.println("3. Eliminar célula");
            System.out.println("4. Listar células");
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
                case 1 -> crearCelula();
                case 2 -> editarCelula();
                case 3 -> eliminarCelula();
                case 4 -> listarCelulas();
                case 5 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    public void crearCelula() {
        System.out.print("Localidad: ");
        String localidad = scanner.nextLine();
        System.out.print("Característica especial: ");
        String caracteristica = scanner.nextLine();
        System.out.print("Estado (Activa/Inactiva): ");
        String estadoTexto = scanner.nextLine();


        try {
            EstadoCelula estado = EstadoCelula.desdeTexto(estadoTexto);

            Celula nueva = new Celula(contadorId++, localidad, caracteristica, estado);
            celulas.add(nueva);
            System.out.println("Célula registrada.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void editarCelula() {
        listarCelulas();
        System.out.print("ID de célula a editar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Celula c = buscarPorId(id);

        if (c != null) {
            System.out.print("Nueva localidad: ");
            c.setLocalidad(scanner.nextLine());
            System.out.print("Nueva característica especial: ");
            c.setCaracteristicaEspecial(scanner.nextLine());
            System.out.print("Nuevo estado (Activa/Inactiva): ");
            String entradaEstado = scanner.nextLine();
            try {
                EstadoCelula nuevoEstado = EstadoCelula.desdeTexto(entradaEstado);
                c.setEstado(nuevoEstado);
                System.out.println("Célula actualizada.");
            } catch (IllegalArgumentException e) {
                System.out.println("Estado inválido. No se actualizó el estado.");
            }
        } else {
            System.out.println("Célula no encontrada.");
        }
    }

    public void eliminarCelula() {
        listarCelulas();
        System.out.print("ID de célula a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Celula c = buscarPorId(id);

        if (c != null) {
            celulas.remove(c);
            System.out.println("Célula eliminada.");
        } else {
            System.out.println("Célula no encontrada.");
        }
    }

    public void listarCelulas() {
        if (celulas.isEmpty()) {
            System.out.println("No hay células registradas.");
        } else {
            System.out.println("--- Lista de Células ---");
            for (Celula c : celulas) {
                System.out.println("ID: " + c.getId() + ", Localidad: " + c.getLocalidad()
                    + ", Característica: " + c.getCaracteristicaEspecial()
                    + ", Estado: " + c.getEstadoTexto());
            }
        }
    }

    public Celula buscarPorId(int id) {
        for (Celula c : celulas) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    public List<Celula> getCelulas() {
        return celulas;
    }
}
