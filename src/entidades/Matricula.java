package entidades;

public class Matricula {
    private int id;
    private Discipulo discipulo;
    private Discipulado discipulado;
    private String fechaInscripcion;
    
    public Matricula(int id, Discipulo discipulo, Discipulado discipulado, String fechaInscripcion) {
    this.id = id;
    this.discipulo = discipulo;
    this.discipulado = discipulado;
    this.fechaInscripcion = fechaInscripcion;
}


    public Matricula(Discipulo discipulo, Discipulado discipulado, String fechaInscripcion) {
        this.discipulo = discipulo;
        this.discipulado = discipulado;
        this.fechaInscripcion = fechaInscripcion;
    }

    // Getters
    public int getId() {
        return id;
    }

    public Discipulo getDiscipulo() {
        return discipulo;
    }

    public Discipulado getDiscipulado() {
        return discipulado;
    }

    public String getFechaInscripcion() {
        return fechaInscripcion;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setFechaInscripcion(String fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public void setDiscipulo(Discipulo discipulo) {
        this.discipulo = discipulo;
    }

    public void setDiscipulado(Discipulado discipulado) {
        this.discipulado = discipulado;
    }

    @Override
    public String toString() {
        return "Matricula [id=" + id +
               ", discipulo=" + discipulo.getNombreCompleto() +
               ", discipulado=" + discipulado.getNombre() +
               ", fecha=" + fechaInscripcion + "]";
    }
}

