package entidades;

import entidades.Disciplina;
import entidades.Discipulo;
import enumeracion.EstadoCelula;  

public class Celula {
    private int id;
    private String localidad;
    private Disciplina disciplina;
    private EstadoCelula estado;
    private Discipulo anfitrion;
    private Discipulo timonel;
    private Discipulo colaborador;

    public Celula(int id, String localidad, Disciplina disciplina, EstadoCelula estado, Discipulo anfitrion, Discipulo timonel, Discipulo colaborador) {
        this.id = id;
        this.localidad = localidad;
        this.disciplina = disciplina;
        this.estado = estado;
        this.anfitrion = anfitrion;
        this.timonel = timonel;        
        this.colaborador = colaborador;
    }

    public int getId() {
        return id;
    }

    public String getLocalidad() {
        return localidad;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }
    
    public Discipulo getAnfitrion() {
        return anfitrion;
    }
    
    public Discipulo getTimonel() {
        return timonel;
    }
    
    public Discipulo getColaborador() {
        return colaborador;
    }
    
    public String getEstadoTexto() {
        return estado.toString();
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public void setEstado(EstadoCelula estado) {
        this.estado = estado;
    }

    public void setAnfitrion (Discipulo anfitrion) {
        this.anfitrion = anfitrion;
    }    
    
    public void setTimonel(Discipulo timonel) {
        this.timonel = timonel;
    }    
    
    public void setColaborador (Discipulo colaborador) {
        this.colaborador = colaborador;
    }    
    
    
    @Override
    public String toString() {
        return "Celula [id=" + id + ", localidad= " + localidad +
               ", característica= " + disciplina.getNombre() +
               ", estado= " + estado.toString() + 
               ", anfitrion= " + anfitrion.getNombreCompleto() + 
               ", timonel= " + timonel.getNombreCompleto() + 
               ", colaborador= " + colaborador.getNombreCompleto() + "]";
    }
}
