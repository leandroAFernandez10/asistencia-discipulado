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

    public void setNombre(String nextLine) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setAnio(int parseInt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
