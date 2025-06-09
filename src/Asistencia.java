public class Asistencia {
    private Clase clase;
    private Discipulo discipulo;
    private boolean presente;

    public Asistencia(Clase clase, Discipulo discipulo, boolean presente) {
        this.clase = clase;
        this.discipulo = discipulo;
        this.presente = presente;
    }

    public Clase getClase() {
        return clase;
    }

    public Discipulo getDiscipulo() {
        return discipulo;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }
}
