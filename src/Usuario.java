public class Usuario {
    private String username;
    private String password;

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean validar(String usuario, String clave) {
        return this.username.equals(usuario) && this.password.equals(clave);
    }
}
