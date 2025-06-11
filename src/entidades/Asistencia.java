package entidades;


public class Asistencia {
    private Clase clase;
    private Discipulo discipulo;

    public Asistencia(Clase clase, Discipulo discipulo) {
        this.clase = clase;
        this.discipulo = discipulo;
    }

    public Clase getClase() {
        return clase;
    }

    public Discipulo getDiscipulo() {
        return discipulo;
    }
}
