import java.util.*;

public class Sistema {
    private Scanner scanner = new Scanner(System.in);
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Disciplina> disciplinas = new ArrayList<>();
    private List<Discipulo> discipulos = new ArrayList<>();
    private List<Discipulado> discipulados = new ArrayList<>();
    private List<Clase> clases = new ArrayList<>();
    private List<Asistencia> asistencias = new ArrayList<>();

    public Sistema() {
        // Usuario por defecto
        usuarios.add(new Usuario("admin", "1234"));
    }

    public void iniciar() {
        System.out.println("=== Sistema de Asistencia ===");
        if (login()) {
            menuPrincipal();
        } else {
            System.out.println("Demasiados intentos. Cerrando programa.");
        }
    }

    private boolean login() {
        int intentos = 3;
        while (intentos > 0) {
            System.out.print("Usuario: ");
            String username = scanner.nextLine();
            System.out.print("Contraseña: ");
            String password = scanner.nextLine();

            for (Usuario u : usuarios) {
                if (u.validar(username, password)) {
                    System.out.println("¡Bienvenido, " + username + "!");
                    return true;
                }
            }
            System.out.println("Credenciales incorrectas. Intenta nuevamente.");
            intentos--;
        }
        return false;
    }

    private void menuPrincipal() {
        int opcion;
        do {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Registrar disciplina");
            System.out.println("2. Registrar discípulo");
            System.out.println("3. Registrar discipulado");
            System.out.println("4. Ver disciplinas");
            System.out.println("5. Salir");
            System.out.print("Elija una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> registrarDisciplina();
                case 2 -> registrarDiscipulo();
                case 3 -> registrarDiscipulado();
                case 4 -> mostrarDisciplinas();
                case 5 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    private void registrarDisciplina() {
        System.out.print("Nombre de la disciplina: ");
        String nombre = scanner.nextLine();
        Disciplina d = new Disciplina(disciplinas.size() + 1, nombre);
        disciplinas.add(d);
        System.out.println("Disciplina registrada.");
    }

    private void registrarDiscipulo() {
        System.out.print("Nombre del discípulo: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        discipulos.add(new Discipulo(discipulos.size() + 1, nombre, apellido));
        System.out.println("Discípulo registrado.");
    }

    private void registrarDiscipulado() {
        System.out.print("Nombre del discipulado: ");
        String nombre = scanner.nextLine();
        System.out.print("Año: ");
        int anio = Integer.parseInt(scanner.nextLine());

        mostrarDisciplinas();
        System.out.print("ID de la disciplina asociada: ");
        int idDisciplina = Integer.parseInt(scanner.nextLine());

        Disciplina d = buscarDisciplinaPorId(idDisciplina);
        if (d != null) {
            Discipulado dis = new Discipulado(discipulados.size() + 1, nombre, anio, d);
            discipulados.add(dis);
            System.out.println("Discipulado registrado.");
        } else {
            System.out.println("Disciplina no encontrada.");
        }
    }

    private void mostrarDisciplinas() {
        System.out.println("--- Disciplinas ---");
        for (Disciplina d : disciplinas) {
            System.out.println(d.getId() + ". " + d.getNombre());
        }
    }

    private Disciplina buscarDisciplinaPorId(int id) {
        for (Disciplina d : disciplinas) {
            if (d.getId() == id) return d;
        }
        return null;
    }
}
