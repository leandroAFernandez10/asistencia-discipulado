import menu.MenuPrincipal;
import entidades.Usuario;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Usuario usuarioValido = new Usuario("admin", "1234");

        boolean accesoConcedido = false;

        System.out.println("=== SISTEMA DE GESTIÓN DE DISCIPULADO ===");
        System.out.println("PARA ESTA VERSIÓN DE PRUEBA SE UTILIZARÁN LAS CREDENCIALES");
        System.out.println("Usuario: admin --- Contraseña: 1234");

        while (!accesoConcedido) {
            System.out.print("Usuario: ");
            String user = scanner.nextLine();
            System.out.print("Contraseña: ");
            String pass = scanner.nextLine();

            if (usuarioValido.validar(user, pass)) {
                accesoConcedido = true;
            } else {
                System.out.println("Credenciales incorrectas");
            }
        }
        
        MenuPrincipal menu = new MenuPrincipal();
        menu.mostrarMenu();
         
        scanner.close();
    }
}
