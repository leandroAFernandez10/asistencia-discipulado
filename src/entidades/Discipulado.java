package entidades;

public class Discipulado {
    private int id;
    private String nombre;
    private int anio;
    private Disciplina disciplina;

    public Discipulado(int id, String nombre, int anio, Disciplina disciplina) {
        this.id = id;
        this.nombre = nombre;
        this.anio = anio;
        this.disciplina = disciplina;
    }
    
    public Discipulado(String nombre, int anio, Disciplina disciplina) {
        this(-1, nombre, anio, disciplina);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getAnio() {
        return anio;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setNombre(String nombre) {
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }
}
