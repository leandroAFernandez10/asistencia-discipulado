package gestores;

import java.util.*;
import entidades.Celula;
import entidades.Disciplina;
import entidades.Discipulo;
import enumeracion.EstadoCelula;

public class GestorCelula {
    private List<Celula> celulas = new ArrayList<>();
    private int contadorId = 1;
    private Scanner scanner = new Scanner(System.in);
    private List<Disciplina> disciplinasDisponibles;
    private List<Discipulo> discipulosDisponibles;

    public GestorCelula(List<Disciplina> disciplinas, List<Discipulo> discipulos) {
        this.disciplinasDisponibles = disciplinas;
        this.discipulosDisponibles = discipulos;
    }

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
                opcion = -1;
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

        Disciplina disciplinaSeleccionada = seleccionarDisciplina();
        if (disciplinaSeleccionada == null) {
            System.out.println("No se pudo seleccionar una disciplina. Cancelando operación.");
            return;
        }
        Discipulo anfitrionSeleccionado = seleccionarDiscipulo();
        if (anfitrionSeleccionado == null) {
            System.out.println("No se pudo seleccionar una disciplina. Cancelando operación.");
            return;
        }
        Discipulo timonelSeleccionado = seleccionarDiscipulo();
        if (timonelSeleccionado == null) {
            System.out.println("No se pudo seleccionar una disciplina. Cancelando operación.");
            return;
        }
        Discipulo colaboradorSeleccionado = seleccionarDiscipulo();
        if (colaboradorSeleccionado == null) {
            System.out.println("No se pudo seleccionar una disciplina. Cancelando operación.");
            return;
        }
        System.out.print("Estado (Activa/Inactiva): ");
        String estadoTexto = scanner.nextLine();

        try {
            EstadoCelula estado = EstadoCelula.desdeTexto(estadoTexto);
            Celula nueva = new Celula(contadorId++, localidad, disciplinaSeleccionada, estado, anfitrionSeleccionado, timonelSeleccionado, colaboradorSeleccionado);
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

            Disciplina nuevaDisciplina = seleccionarDisciplina();
            if (nuevaDisciplina != null) {
                c.setDisciplina(nuevaDisciplina);
            } else {
                System.out.println("No se seleccionó una nueva disciplina. Se mantiene la anterior.");
            }

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

    private Disciplina seleccionarDisciplina() {
        if (disciplinasDisponibles.isEmpty()) {
            System.out.println("No hay disciplinas disponibles. Cargue una primero.");
            return null;
        }

        System.out.println("--- Selecciona un disciplina ---");
        for (Disciplina d : disciplinasDisponibles) {
            System.out.println("ID: " + d.getId() + " - " + d.getNombre());
        }
        System.out.print("Ingrese el ID de la disciplina: ");
        int idDiscipulo = Integer.parseInt(scanner.nextLine());
        for (Disciplina d : disciplinasDisponibles) {
            if (d.getId() == idDiscipulo) return d;
        }
        System.out.println("Disciplina no encontrada.");
        return null;
    }

    private Discipulo seleccionarDiscipulo() {
        if (discipulosDisponibles.isEmpty()) {
            System.out.println("No hay discipulos disponibles. Cargue uno primero.");
            return null;
        }

        System.out.println("--- Seleccione un discipulo ---");
        for (Discipulo d : discipulosDisponibles) {
            System.out.println("ID: " + d.getId() + " - " + d.getNombre());
        }
        System.out.print("Ingrese el ID de la discipulo: ");
        int idDiscipulo = Integer.parseInt(scanner.nextLine());
        for (Discipulo c : discipulosDisponibles) {
            if (c.getId() == idDiscipulo) return c;
        }
        System.out.println("Clase no encontrada.");
        return null;
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
                System.out.println("ID: " + c.getId() +
                    ", Localidad: " + c.getLocalidad() +
                    ", Disciplina: " + c.getDisciplina().getNombre() +
                    ", Estado: " + c.getEstadoTexto());
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
