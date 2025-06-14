package gestores;

import java.util.*;
import entidades.Clase;
import entidades.Discipulado;

public class GestorClase {
    private List<Clase> clases = new ArrayList<>();
    private List<Discipulado> discipuladosDisponibles; 
    private int contadorId = 1;
    private Scanner scanner = new Scanner(System.in);
    
    public GestorClase(List<Discipulado> discipulados){
        this.discipuladosDisponibles = discipulados;
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
                System.out.println("Entrada inválida. Ingrese un número.");
                opcion = -1; // Evita ejecutar ninguna acción
                continue;
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
        if (discipuladosDisponibles.isEmpty()) {
            System.out.println("No hay disciplinas disponibles. Debe crear una primero.");
            return;
        }        
        System.out.print("Tema: ");
        String tema = scanner.nextLine();
        System.out.print("Fecha: ");
        String fecha = scanner.nextLine();
        
        System.out.println("Seleccione un discipulado asociada:");
        
        for (Discipulado d : discipuladosDisponibles) {
            System.out.println(d.getId() + ". " + d.getNombre());
        }
        System.out.print("ID de disciplina: ");
        int idDisciplina = Integer.parseInt(scanner.nextLine());
        Discipulado discipulado = buscarDiscipuladoPorId(idDisciplina);

        if (discipulado != null) {
            Clase nueva = new Clase(contadorId++, tema, fecha, discipulado);
            clases.add(nueva);
            System.out.println("Clase registrada.");
        } else {
            System.out.println("Discipulado no encontrada.");
        }
    }

    public void editarClase() {
        listarClases();
        System.out.print("ID de clase a editar: ");
        int id = Integer.parseInt(scanner.nextLine());

        Clase c = buscarPorId(id);
        if (c != null) {
            System.out.print("Nuevo tema: ");
            c.setTema(scanner.nextLine());
            System.out.print("Nueva fecha: ");
            c.setFecha(scanner.nextLine());
            System.out.println("Clase actualizada.");
        } else {
            System.out.println("Clase no encontrada.");
        }
    }

    public void eliminarClase() {
        listarClases();
        System.out.print("ID de clase a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Clase c = buscarPorId(id);
        if (c != null) {
            clases.remove(c);
            System.out.println("Clase eliminada.");
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
                System.out.println("ID: " + c.getId() + ", Tema: " + c.getTema() + ", Fecha: " + c.getFecha());
            }
        }
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

    
    private Discipulado buscarDiscipuladoPorId(int id) {
        for (Discipulado d : discipuladosDisponibles) {
            if (d.getId() == id) return d;
        }
        return null;
    }
}
