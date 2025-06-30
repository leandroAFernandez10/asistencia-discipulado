package entidades;

public class Disciplina {
    private int id;
    private String nombre;

    public Disciplina(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    
    public Disciplina(String nombre) {
        this(-1, nombre); 
    }    

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
