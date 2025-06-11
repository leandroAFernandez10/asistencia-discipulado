package gestores;

import entidades.Asistencia;
import entidades.Clase;
import entidades.Discipulo;
import entidades.Matricula;
import entidades.Discipulado;
import java.util.*;

public class GestorAsistencia {
    private List<Asistencia> asistencias = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    private List<Clase> clasesDisponibles;
    private List<Discipulo> discipulosDisponibles;
    private List<Matricula> matriculasDisponibles;


    public GestorAsistencia(List<Clase> clases, List<Discipulo> discipulos, List<Matricula> matriculas) {
        this.clasesDisponibles = clases;
        this.discipulosDisponibles = discipulos;
        this.matriculasDisponibles = matriculas;
    }

    public void menuAsistencias() {
        int opcion;
        do {
            System.out.println("\n--- Gestor de Asistencias ---");
            System.out.println("1. Registrar asistencia");
            System.out.println("2. Eliminar asistencia");
            System.out.println("3. Listar asistencias");
            System.out.println("4. Planilla de asistencia por clase");
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
                case 2 -> eliminarAsistencia();
                case 3 -> listarAsistencias();
                case 4 -> listarAsistenciasPorClase();
                case 5 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    public void registrarAsistencia() {
        Scanner scanner = new Scanner(System.in);

        if (clasesDisponibles.isEmpty()) {
            System.out.println("No hay clases registradas.");
            return;
        }

        System.out.println("Clases disponibles:");
        for (Clase clase : clasesDisponibles) {
            System.out.println("ID: " + clase.getId() + " | Tema: " + clase.getTema());
        }

        System.out.print("Ingrese el ID de la clase para registrar asistencia: ");
        int idClase = scanner.nextInt();
        Clase claseSeleccionada = buscarClasePorId(idClase);

        if (claseSeleccionada == null) {
            System.out.println("Clase no encontrada.");
            return;
        }

        Discipulado discipulado = claseSeleccionada.getDiscipulado();

        if (discipulado == null) {
            System.out.println("La clase no está asociada a ningún discipulado.");
            return;
        }

        List<Matricula> matriculas = new ArrayList<>();
        for (Matricula m : matriculasDisponibles) {
            if (m.getDiscipulado().equals(discipulado)) {
                matriculas.add(m);
            }
        }

        if (matriculas.isEmpty()) {
            System.out.println("No hay discípulos matriculados en este discipulado.");
            return;
        }

        // Paso 4: Obtener discípulos desde matrículas
        List<Discipulo> discipulos = new ArrayList<>();
        for (Matricula matricula : matriculas) {
            discipulos.add(matricula.getDiscipulo());
        }

        // Paso 5: Mostrar discípulos
        System.out.println("Discípulos matriculados en este discipulado:");
        for (Discipulo d : discipulos) {
            System.out.println("ID: " + d.getId() + " | Nombre: " + d.getNombre());
        }

        // Paso 6: Registrar asistencia
        System.out.println("Ingrese los ID de los discípulos que asistieron a la clase (0 para terminar):");

        while (true) {
            System.out.print("ID del discípulo presente: ");
            int idDiscipulo = scanner.nextInt();
            if (idDiscipulo == 0) break;

            Discipulo discipulo = buscarDiscipuloEnLista(idDiscipulo, discipulos);
            if (discipulo == null) {
                System.out.println("ID inválido. Intente nuevamente.");
            } else {
                // Verificar si ya se registró esa asistencia
                if (asistenciaYaRegistrada(claseSeleccionada, discipulo)) {
                    System.out.println("La asistencia ya fue registrada para este discípulo.");
                } else {
                    Asistencia nuevaAsistencia = new Asistencia(claseSeleccionada, discipulo);
                    asistencias.add(nuevaAsistencia);
                    System.out.println("✔ Asistencia registrada para " + discipulo.getNombre());
                }
            }
        }

        System.out.println("Registro de asistencia completado.");
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
                    + " (" + a.getClase().getFecha() + ")");
            }
        }
    }

    public void listarAsistenciasPorClase() {
        Clase claseSeleccionada = seleccionarClase();
        if (claseSeleccionada == null) {
            return;
        }

        Discipulado discipulado = claseSeleccionada.getDiscipulado();
        if (discipulado == null) {
            System.out.println("La clase no está asociada a ningún discipulado.");
            return;
        }

        // Obtener matrículas del discipulado
        List<Matricula> matriculasDiscipulado = new ArrayList<>();
        for (Matricula m : matriculasDisponibles) {
            if (m.getDiscipulado().equals(discipulado)) {
                matriculasDiscipulado.add(m);
            }
        }

        if (matriculasDiscipulado.isEmpty()) {
            System.out.println("No hay discípulos matriculados en este discipulado.");
            return;
        }

        // Verificar asistencia para cada discípulo matriculado
        System.out.println("\n--- Planilla de Asistencia para la clase: " + claseSeleccionada.getTema() + " (" + claseSeleccionada.getFecha() + ") ---");
        for (Matricula m : matriculasDiscipulado) {
            Discipulo d = m.getDiscipulo();
            boolean presente = false;

            for (Asistencia a : asistencias) {
                if (a.getClase().equals(claseSeleccionada) && a.getDiscipulo().equals(d)) {
                    presente = true;
                    break;
                }
            }

            String estado = presente ? "PRESENTE" : "AUSENTE";
            System.out.println(d.getNombreCompleto() + " - " + estado);
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
    
    private Clase buscarClasePorId(int id) {
        for (Clase c : clasesDisponibles) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    private Discipulo buscarDiscipuloEnLista(int id, List<Discipulo> discipulos) {
        for (Discipulo d : discipulos) {
            if (d.getId() == id) return d;
        }
        return null;
    }

    private boolean asistenciaYaRegistrada(Clase clase, Discipulo discipulo) {
        for (Asistencia a : asistencias) {
            if (a.getClase().equals(clase) && a.getDiscipulo().equals(discipulo)) {
                return true;
            }
        }
        return false;
    }

}
