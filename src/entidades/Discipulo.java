package entidades;

import enumeracion.Genero;

public class Discipulo {
    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private String telefono;
    private Genero genero;

    // Constructor completo (usado al cargar desde base de datos)
    public Discipulo(int id, String nombre, String apellido, String dni, String email, String telefono, Genero genero) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.genero = genero;
    }

    // Constructor sin ID (usado al crear nuevo discípulo)
    public Discipulo(String nombre, String apellido, String dni, String email, String telefono, Genero genero) {
        this(-1, nombre, apellido, dni, email, telefono, genero);
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDni() {
        return dni;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public Genero getGenero() {
        return genero;
    }

    public String getGeneroTexto() {
        return genero.toString();
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public void setId(int id) {
        this.id = id;
    }
}
