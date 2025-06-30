package gestores;

import dao.CelulaDAO;
import entidades.Celula;
import entidades.Disciplina;
import entidades.Discipulo;
import enumeracion.EstadoCelula;

import java.sql.SQLException;
import java.util.*;

public class GestorCelula {
    private List<Celula> celulas = new ArrayList<>();
    private List<Disciplina> disciplinasDisponibles;
    private List<Discipulo> discipulosDisponibles;
    private CelulaDAO celulaDAO;
    private Scanner scanner = new Scanner(System.in);

    public GestorCelula(List<Disciplina> disciplinas, List<Discipulo> discipulos, List<Celula> celulas, CelulaDAO dao) {
        this.disciplinasDisponibles = disciplinas;
        this.discipulosDisponibles = discipulos;
        this.celulas = celulas;
        this.celulaDAO = dao;
        cargarDesdeBD();
    }

    private void cargarDesdeBD() {
        try {
            this.celulas = celulaDAO.listarTodas(disciplinasDisponibles, discipulosDisponibles);
        } catch (SQLException e) {
            System.out.println("Error al cargar células desde la base de datos: " + e.getMessage());
        }
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
                System.out.println("Entrada inválida.");
                opcion = -1;
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

        Disciplina disciplina = seleccionarDisciplina();
        Discipulo anfitrion = seleccionarDiscipulo("anfitrión");
        Discipulo timonel = seleccionarDiscipulo("timonel");
        Discipulo colaborador = seleccionarDiscipulo("colaborador");

        if (disciplina == null || anfitrion == null || timonel == null || colaborador == null) {
            System.out.println("Faltó seleccionar alguna entidad. Cancelando operación.");
            return;
        }

        System.out.print("Estado (Activa/Inactiva): ");
        String estadoTexto = scanner.nextLine();

        try {
            EstadoCelula estado = EstadoCelula.desdeTexto(estadoTexto);
            Celula nueva = new Celula(0, localidad, disciplina, estado, anfitrion, timonel, colaborador);
            celulaDAO.guardar(nueva);
            celulas.add(nueva);
            System.out.println("Célula registrada.");
        } catch (IllegalArgumentException e) {
            System.out.println("Estado inválido. " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error al guardar célula: " + e.getMessage());
        }
    }

    public void editarCelula() {
        listarCelulas();
        System.out.print("ID de célula a editar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Celula c = buscarPorId(id);

        if (c == null) {
            System.out.println("Célula no encontrada.");
            return;
        }

        System.out.print("Nueva localidad: ");
        c.setLocalidad(scanner.nextLine());

        Disciplina nuevaDisciplina = seleccionarDisciplina();
        if (nuevaDisciplina != null) c.setDisciplina(nuevaDisciplina);

        Discipulo nuevoAnfitrion = seleccionarDiscipulo("anfitrión");
        if (nuevoAnfitrion != null) c.setAnfitrion(nuevoAnfitrion);

        Discipulo nuevoTimonel = seleccionarDiscipulo("timonel");
        if (nuevoTimonel != null) c.setTimonel(nuevoTimonel);

        Discipulo nuevoColaborador = seleccionarDiscipulo("colaborador");
        if (nuevoColaborador != null) c.setColaborador(nuevoColaborador);

        System.out.print("Nuevo estado (Activa/Inactiva): ");
        String estadoTexto = scanner.nextLine();
        try {
            EstadoCelula nuevoEstado = EstadoCelula.desdeTexto(estadoTexto);
            c.setEstado(nuevoEstado);
        } catch (IllegalArgumentException e) {
            System.out.println("Estado inválido. Se mantiene el anterior.");
        }

        try {
            celulaDAO.actualizar(c);
            System.out.println("Célula actualizada.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar célula: " + e.getMessage());
        }
    }

    public void eliminarCelula() {
        listarCelulas();
        System.out.print("ID de célula a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Celula c = buscarPorId(id);

        if (c != null) {
            try {
                celulaDAO.eliminar(c.getId());
                celulas.remove(c);
                System.out.println("Célula eliminada.");
            } catch (SQLException e) {
                System.out.println("Error al eliminar célula: " + e.getMessage());
            }
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
                        ", Estado: " + c.getEstadoTexto() +
                        ", Anfitrión: " + c.getAnfitrion().getNombreCompleto());
            }
        }
    }

    private Disciplina seleccionarDisciplina() {
        if (disciplinasDisponibles.isEmpty()) {
            System.out.println("No hay disciplinas disponibles.");
            return null;
        }

        System.out.println("--- Seleccione una disciplina ---");
        for (Disciplina d : disciplinasDisponibles) {
            System.out.println("ID: " + d.getId() + " - " + d.getNombre());
        }

        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        for (Disciplina d : disciplinasDisponibles) {
            if (d.getId() == id) return d;
        }

        System.out.println("Disciplina no encontrada.");
        return null;
    }

    private Discipulo seleccionarDiscipulo(String rol) {
        if (discipulosDisponibles.isEmpty()) {
            System.out.println("No hay discípulos disponibles.");
            return null;
        }

        System.out.println("--- Seleccione un " + rol + " ---");
        for (Discipulo d : discipulosDisponibles) {
            System.out.println("ID: " + d.getId() + " - " + d.getNombreCompleto());
        }

        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        for (Discipulo d : discipulosDisponibles) {
            if (d.getId() == id) return d;
        }

        System.out.println("Discípulo no encontrado.");
        return null;
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
