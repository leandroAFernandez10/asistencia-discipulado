package enumeracion;

public enum EstadoCelula {
    ACTIVA,
    INACTIVA;

    public static EstadoCelula desdeTexto(String entrada) {
        String valor = entrada.trim().toLowerCase();

        switch (valor) {
            case "activa":
                return ACTIVA;
            case "inactiva":
                return INACTIVA;
            default:
                throw new IllegalArgumentException("Estado inválido. Debe ser activa o inactiva.");
        }
    }
    
    @Override
    public String toString() {
        switch (this) {
            case ACTIVA:
                return "Activa";
            case INACTIVA:
                return "Inactiva";
            default:
                return "";
        }
    }
}


