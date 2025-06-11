package servicios;

import entidades.Discipulado;
import entidades.Clase;
import entidades.Matricula;
import entidades.Discipulo;
import entidades.Asistencia;
import java.util.*;


public class DeterminarAsistencia {
    private List<Discipulado> discipulados;
    private List<Clase> clases;
    private List<Matricula> matriculas;
    private List<Discipulo> discipulos;
    private List<Asistencia> asistencias;

    public DeterminarAsistencia(List<Discipulado> discipulados, List<Clase> clases, List<Matricula> matriculas, List<Discipulo> discipulos, List<Asistencia> asistencias) {
        this.discipulados = discipulados;
        this.clases = clases;
        this.matriculas = matriculas;
        this.discipulos = discipulos;
        this.asistencias = asistencias;
    }

    public void listarPorcentajeAsistencia() {
        Scanner scanner = new Scanner(System.in);

        // Mostrar discipulados
        System.out.println("Seleccione un Discipulado:");
        for (Discipulado d : discipulados) {
            System.out.println(d.getId() + " - " + d.getNombre());
        }

        System.out.print("ID del Discipulado: ");
        int idDiscipulado = scanner.nextInt();
        scanner.nextLine();

        // Buscar discipulado seleccionado
        Discipulado discipuladoSeleccionado = null;
        for (Discipulado d : discipulados) {
            if (d.getId() == idDiscipulado) {
                discipuladoSeleccionado = d;
                break;
            }
        }

        if (discipuladoSeleccionado == null) {
            System.out.println("Discipulado no encontrado.");
            return;
        }

        // Filtrar clases de ese discipulado
        List<Clase> clasesDelDiscipulado = new ArrayList<>();
        for (Clase c : clases) {
            if (c.getDiscipulado().getId() == idDiscipulado) {
                clasesDelDiscipulado.add(c);
            }
        }

        if (clasesDelDiscipulado.isEmpty()) {
            System.out.println("No hay clases para este discipulado.");
            return;
        }

        // Filtrar matrículas de ese discipulado
        List<Matricula> matriculasDelDiscipulado = new ArrayList<>();
        for (Matricula m : matriculas) {
            if (m.getDiscipulado().getId() == idDiscipulado) {
                matriculasDelDiscipulado.add(m);
            }
        }

        if (matriculasDelDiscipulado.isEmpty()) {
            System.out.println("No hay discípulos matriculados en este discipulado.");
            return;
        }

        int totalClases = clasesDelDiscipulado.size();

        // Calcular asistencias por discípulo
        System.out.println("\nAsistencias registradas:");
        System.out.println("-------------------------------------");

        for (Matricula m : matriculasDelDiscipulado) {
            Discipulo discipulo = m.getDiscipulo();
            int asistenciasContadas = 0;

            for (Clase clase : clasesDelDiscipulado) {
                for (Asistencia asistencia : asistencias) {
                    if (asistencia.getClase().equals(clase) && asistencia.getDiscipulo().equals(discipulo)) {
                        asistenciasContadas++;
                        break;
                    }
                }
            }

            double porcentaje = totalClases == 0 ? 0 : (double) asistenciasContadas / totalClases * 100;

            System.out.printf("%s: %d asistencias de %d clases (%.2f%%)\n", 
                discipulo.getNombre(), asistenciasContadas, totalClases, porcentaje);
        }
    }
}
