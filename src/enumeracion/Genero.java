package enumeracion;

public enum Genero {
    MASCULINO,
    FEMENINO;

    public static Genero desdeTexto(String entrada) {
        String valor = entrada.trim().toLowerCase();

        switch (valor) {
            case "masculino":
                return MASCULINO;
            case "femenino":
                return FEMENINO;
            default:
                throw new IllegalArgumentException("Género inválido. Debe ser masculino o femenino.");
        }
    }
}
