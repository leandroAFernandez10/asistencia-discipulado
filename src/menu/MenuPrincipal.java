package menu;

// MenuPrincipal.java
import gestores.GestorAsistencia;
import gestores.GestorCelula;
import gestores.GestorClase;
import gestores.GestorDisciplina;
import gestores.GestorDiscipulado;
import gestores.GestorMatricula;
import gestores.GestorDiscipulo;
import java.util.Scanner;

public class MenuPrincipal {
    private Scanner scanner = new Scanner(System.in);

    // Gestores
    private GestorDisciplina gestorDisciplina = new GestorDisciplina();
    private GestorDiscipulo gestorDiscipulo = new GestorDiscipulo();
    private GestorDiscipulado gestorDiscipulado = new GestorDiscipulado(gestorDisciplina.getDisciplinas());
    private GestorClase gestorClase = new GestorClase();
    private GestorAsistencia gestorAsistencia = new GestorAsistencia(
        gestorClase.getClases(), gestorDiscipulo.getDiscipulos()
    );
    private GestorCelula gestorCelula = new GestorCelula();
    private GestorMatricula gestorMatricula = new GestorMatricula(
        gestorDiscipulo.getDiscipulos(), gestorDiscipulado.getDiscipulados()
    );

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Gestor de Disciplinas");
            System.out.println("2. Gestor de Discipulados");
            System.out.println("3. Gestor de Discípulos");
            System.out.println("4. Gestor de Clases");
            System.out.println("5. Gestor de Asistencias");
            System.out.println("6. Gestor de Células");
            System.out.println("7. Gestor de Matrículas");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número.");
                opcion = -1; // Evita ejecutar ninguna acción
                continue;
            }

            switch (opcion) {
                case 1 -> gestorDisciplina.menuDisciplinas();
                case 2 -> gestorDiscipulado.menuDiscipulados();
                case 3 -> gestorDiscipulo.menuDiscipulos();
                case 4 -> gestorClase.menuClases();
                case 5 -> gestorAsistencia.menuAsistencias();
                case 6 -> gestorCelula.menuCelulas();
                case 7 -> gestorMatricula.menuMatriculas();
                case 8 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }

        } while (opcion != 8);
    }
}
