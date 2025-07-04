package entidades;

public class Clase {
    private int id;
    private String tema;
    private String fecha;
    private Discipulado discipulado;

    public Clase(int id, String tema, String fecha, Discipulado discipulado) {
        this.id = id;
        this.tema = tema;
        this.fecha = fecha;
        this.discipulado = discipulado;
    }

    public int getId() {
        return id;
    }

    public String getTema() {
        return tema;
    }

    public String getFecha() {
        return fecha;
    }

    public Discipulado getDiscipulado() {
        return discipulado;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setTema(String tema) {
        this.tema = tema;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
       
    public void setDiscipulado (Discipulado discipulado) {
        this.discipulado = discipulado;
    }
}
