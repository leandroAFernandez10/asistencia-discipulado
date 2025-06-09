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
}
