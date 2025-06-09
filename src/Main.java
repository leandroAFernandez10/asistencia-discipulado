
import menu.MenuPrincipal;

public class Main {
    public static void main(String[] args) {

        MenuPrincipal menu = new MenuPrincipal();
                System.out.println("Versión de Java: " + System.getProperty("java.version"));

        menu.mostrarMenu(); 
    }
}
