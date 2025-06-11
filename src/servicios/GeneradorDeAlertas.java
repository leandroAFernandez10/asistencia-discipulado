package servicios;

import entidades.*;
import java.util.*;

public class GeneradorDeAlertas {

    private List<Celula> celulas;
    private List<Discipulado> discipulados;
    private List<Clase> clases;
    private List<Matricula> matriculas;
    private List<Asistencia> asistencias;

    public GeneradorDeAlertas(
        List<Celula> celulas,
        List<Discipulado> discipulados,
        List<Clase> clases,
        List<Matricula> matriculas,
        List<Asistencia> asistencias
    ) {
        this.celulas = celulas;
        this.discipulados = discipulados;
        this.clases = clases;
        this.matriculas = matriculas;
        this.asistencias = asistencias;
    }

    public void generarAlertas() {
        for (Celula celula : celulas) {
            Disciplina disciplina = celula.getDisciplina();

            // Obtener todos los discipulados que corresponden a esa disciplina
            List<Discipulado> discipuladosDeLaDisciplina = new ArrayList<>();
            for (Discipulado d : discipulados) {
                if (d.getDisciplina().getId() == disciplina.getId()) {
                    discipuladosDeLaDisciplina.add(d);
                }
            }

            // Verificar cada rol de la célula
            verificarAlertaParaRol("Anfitrión", celula.getAnfitrion(), discipuladosDeLaDisciplina, celula);
            verificarAlertaParaRol("Timonel", celula.getTimonel(), discipuladosDeLaDisciplina, celula);
            verificarAlertaParaRol("Colaborador", celula.getColaborador(), discipuladosDeLaDisciplina, celula);
        }
    }

    private void verificarAlertaParaRol(String rol, Discipulo discipulo, List<Discipulado> discipulados, Celula celula) {
        boolean encontrado = false;

        for (Discipulado discipulado : discipulados) {
            
            for (Matricula matricula : matriculas) {
                if (
                    matricula.getDiscipulo().getId() == discipulo.getId() &&
                    matricula.getDiscipulado().getId() == discipulado.getId()
                ) {
                    encontrado = true;

                    // Buscar clases del discipulado
                    List<Clase> clasesDelDiscipulado = new ArrayList<>();
                    for (Clase clase : clases) {
                        if (clase.getDiscipulado().getId() == discipulado.getId()) {
                            clasesDelDiscipulado.add(clase);
                        }
                    }

                    if (clasesDelDiscipulado.isEmpty()) return;

                    int totalClases = clasesDelDiscipulado.size();
                    int asistenciasDelDiscipulo = 0;

                    for (Clase clase : clasesDelDiscipulado) {
                        for (Asistencia asistencia : asistencias) {
                            if (
                                asistencia.getClase().getId() == clase.getId() &&
                                asistencia.getDiscipulo().getId() == discipulo.getId()
                            ) {
                                asistenciasDelDiscipulo++;
                            }
                        }
                    }

                    double porcentaje = (asistenciasDelDiscipulo * 100.0) / totalClases;

                    if (porcentaje < 70.0) {
                        System.out.println("⚠️ Alerta: El " + rol + " '" + discipulo.getNombreCompleto() + "' de la célula ID "
                            + celula.getId() + " en la localidad '" + celula.getLocalidad()
                            + "' tiene un porcentaje de asistencia del " + String.format("%.2f", porcentaje)
                            + "% al discipulado '" + discipulado.getNombre()
                            + "' (requiere mínimo 70%).");
                    }

                    return; // Ya se encontró una matrícula válida
                }
            }
        }

        if (!encontrado) {
            System.out.println("⚠️ Alerta: El " + rol + " '" + discipulo.getNombreCompleto()
                + "' de la célula ID " + celula.getId() + " no está matriculado en ningún discipulado de la disciplina '"
                + celula.getDisciplina().getNombre() + "'.");
        }
    }
}
