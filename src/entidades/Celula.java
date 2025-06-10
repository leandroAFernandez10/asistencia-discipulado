package entidades;

import entidades.Disciplina;
import enumeracion.EstadoCelula;  

public class Celula {
    private int id;
    private String localidad;
    private Disciplina disciplina;
    private EstadoCelula estado; //"Activa" o "Inactiva"

    public Celula(int id, String localidad, Disciplina disciplina, EstadoCelula estado) {
        this.id = id;
        this.localidad = localidad;
        this.disciplina = disciplina;
        this.estado = estado;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getLocalidad() {
        return localidad;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public String getEstadoTexto() {
        return estado.toString();
    }

    // Setters
    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public void setEstado(EstadoCelula estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Celula [id=" + id + ", localidad=" + localidad +
               ", característica=" + disciplina +
               ", estado=" + estado + "]";
    }
}
