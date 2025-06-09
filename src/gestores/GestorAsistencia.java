package gestores;

import entidades.Asistencia;
import entidades.Clase;
import entidades.Discipulo;
import java.util.*;

public class GestorAsistencia {
    private List<Asistencia> asistencias = new ArrayList<>();
    private int contadorId = 1;
    private Scanner scanner = new Scanner(System.in);

    private List<Clase> clasesDisponibles;
    private List<Discipulo> discipulosDisponibles;

    public GestorAsistencia(List<Clase> clases, List<Discipulo> discipulos) {
        this.clasesDisponibles = clases;
        this.discipulosDisponibles = discipulos;
    }

    public void menuAsistencias() {
        int opcion;
        do {
            System.out.println("\n--- Gestor de Asistencias ---");
            System.out.println("1. Registrar asistencia");
            System.out.println("2. Editar asistencia");
            System.out.println("3. Eliminar asistencia");
            System.out.println("4. Listar asistencias");
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
                case 1 -> registrarAsistencia();
                case 2 -> editarAsistencia();
                case 3 -> eliminarAsistencia();
                case 4 -> listarAsistencias();
                case 5 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    public void registrarAsistencia() {
        Clase clase = seleccionarClase();
        Discipulo discipulo = seleccionarDiscipulo();

        if (clase != null && discipulo != null) {
            System.out.print("¿Estuvo presente? (s/n): ");
            String r = scanner.nextLine().toLowerCase();
            boolean presente = r.equals("s");

            Asistencia a = new Asistencia(clase, discipulo, presente);
            asistencias.add(a);
            System.out.println("Asistencia registrada.");
        }
    }

    public void editarAsistencia() {
        listarAsistencias();
        System.out.print("Seleccione el número de asistencia a editar: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;

        if (index >= 0 && index < asistencias.size()) {
            Asistencia a = asistencias.get(index);
            System.out.print("¿Nuevo valor? Presente (s/n): ");
            boolean nuevoValor = scanner.nextLine().toLowerCase().equals("s");
            a.setPresente(nuevoValor);
            System.out.println("Asistencia actualizada.");
        } else {
            System.out.println("Índice inválido.");
        }
    }

    public void eliminarAsistencia() {
        listarAsistencias();
        System.out.print("Seleccione el número de asistencia a eliminar: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;

        if (index >= 0 && index < asistencias.size()) {
            asistencias.remove(index);
            System.out.println("Asistencia eliminada.");
        } else {
            System.out.println("Índice inválido.");
        }
    }

    public void listarAsistencias() {
        if (asistencias.isEmpty()) {
            System.out.println("No hay asistencias registradas.");
        } else {
            System.out.println("--- Lista de Asistencias ---");
            int i = 1;
            for (Asistencia a : asistencias) {
                System.out.println(i++ + ". " + a.getDiscipulo().getNombreCompleto()
                    + " | Clase: " + a.getClase().getTema()
                    + " (" + a.getClase().getFecha() + ")"
                    + " => " + (a.isPresente() ? "Presente" : "Ausente"));
            }
        }
    }

    private Clase seleccionarClase() {
        if (clasesDisponibles.isEmpty()) {
            System.out.println("No hay clases disponibles.");
            return null;
        }

        System.out.println("--- Seleccionar Clase ---");
        for (Clase c : clasesDisponibles) {
            System.out.println(c.getId() + ". " + c.getTema() + " - " + c.getFecha());
        }
        System.out.print("ID de clase: ");
        int id = Integer.parseInt(scanner.nextLine());

        for (Clase c : clasesDisponibles) {
            if (c.getId() == id) return c;
        }
        System.out.println("Clase no encontrada.");
        return null;
    }

    private Discipulo seleccionarDiscipulo() {
        if (discipulosDisponibles.isEmpty()) {
            System.out.println("No hay discípulos disponibles.");
            return null;
        }

        System.out.println("--- Seleccionar Discípulo ---");
        for (Discipulo d : discipulosDisponibles) {
            System.out.println(d.getId() + ". " + d.getNombreCompleto());
        }
        System.out.print("ID de discípulo: ");
        int id = Integer.parseInt(scanner.nextLine());

        for (Discipulo d : discipulosDisponibles) {
            if (d.getId() == id) return d;
        }
        System.out.println("Discípulo no encontrado.");
        return null;
    }

    public List<Asistencia> getAsistencias() {
        return asistencias;
    }
}
