package entidades;

public class Celula {
    private int id;
    private String localidad;
    private String caracteristicaEspecial;
    private String estado; // Ejemplo: "Activa" o "Inactiva"

    public Celula(int id, String localidad, String caracteristicaEspecial, String estado) {
        this.id = id;
        this.localidad = localidad;
        this.caracteristicaEspecial = caracteristicaEspecial;
        this.estado = estado;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getLocalidad() {
        return localidad;
    }

    public String getCaracteristicaEspecial() {
        return caracteristicaEspecial;
    }

    public String getEstado() {
        return estado;
    }

    // Setters
    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public void setCaracteristicaEspecial(String caracteristicaEspecial) {
        this.caracteristicaEspecial = caracteristicaEspecial;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Celula [id=" + id + ", localidad=" + localidad +
               ", característica=" + caracteristicaEspecial +
               ", estado=" + estado + "]";
    }
}
