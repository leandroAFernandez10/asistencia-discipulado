public class Clase {
    private int id;
    private String tema;
    private String fecha; // puede mejorarse con LocalDate si se desea

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
}
