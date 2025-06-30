package entidades;

public class Asistencia {
    private int id; // opcional, útil si después querés editar o borrar
    private Clase clase;
    private Matricula matricula;

    public Asistencia(int id, Clase clase, Matricula matricula) {
        this.id = id;
        this.clase = clase;
        this.matricula = matricula;
    }

    public Asistencia(Clase clase, Matricula matricula) {
        this(-1, clase, matricula); // Para cuando no usás ID
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public Clase getClase() {
        return clase;
    }

    public Matricula getMatricula() {
        return matricula;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setClase(Clase clase) {
        this.clase = clase;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }


    @Override
    public String toString() {
        return "Asistencia{" +
                "discípulo=" + matricula.getDiscipulo().getNombreCompleto() +
                ", clase=" + clase.getTema() +
                ", fecha=" + clase.getFecha() +
                '}';
    }
}
