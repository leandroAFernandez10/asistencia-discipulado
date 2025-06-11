package entidades;

import enumeracion.Genero;

public class Discipulo {
    private int id;
    private String nombre;
    private String apellido;
    private Genero genero; //Masculino o Femenino
    
    public Discipulo(int id, String nombre, String apellido, Genero genero) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.genero = genero;
    }

    public int getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }
    
    public String getGeneroTexto() {
        return genero.toString();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.nombre = nombre;
    }
    
    public void setGenero(Genero genero) {
        this.genero = genero;
    }
}
