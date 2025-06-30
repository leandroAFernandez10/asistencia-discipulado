package gestores;

import entidades.Discipulo;
import dao.DiscipuloDAO;
import enumeracion.Genero;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class GestorDiscipulo {
    private List<Discipulo> discipulos;
    private DiscipuloDAO discipuloDAO;
    private Scanner scanner = new Scanner(System.in);

    public GestorDiscipulo(List<Discipulo> discipulos, DiscipuloDAO dao) {
        this.discipulos = discipulos;
        this.discipuloDAO = dao;
    }

    public void menuDiscipulos() {
        int opcion;
        do {
            System.out.println("\n--- Gestor de Discípulos ---");
            System.out.println("1. Crear discípulo");
            System.out.println("2. Editar discípulo");
            System.out.println("3. Eliminar discípulo");
            System.out.println("4. Listar discípulos");
            System.out.println("5. Volver al menú principal");
            System.out.print("Opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número.");
                opcion = -1;
                continue;
            }

            switch (opcion) {
                case 1 -> {
                    Discipulo nuevo = crearDiscipulo();
                    if (nuevo != null) discipulos.add(nuevo);
                }
                case 2 -> editarDiscipulo();
                case 3 -> eliminarDiscipulo();
                case 4 -> listarDiscipulos();
                case 5 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    public Discipulo crearDiscipulo() {
        try {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Apellido: ");
            String apellido = scanner.nextLine();
            System.out.print("DNI: ");
            String dni = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Teléfono: ");
            String telefono = scanner.nextLine();
            System.out.print("Género (Femenino/Masculino): ");
            String generoTexto = scanner.nextLine();

            Genero genero = Genero.desdeTexto(generoTexto);
            Discipulo nuevo = new Discipulo(nombre, apellido, dni, email, telefono, genero);

            discipuloDAO.guardar(nuevo);  // ✅ Insertar en la BD
            System.out.println("✔ Discípulo creado y guardado en la base de datos.");
            return nuevo;

        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("❌ Error al guardar en la base de datos: " + e.getMessage());
        }
        return null;
    }

    public Discipulo editarDiscipulo() {
        listarDiscipulos();
        System.out.print("ID del discípulo a editar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Discipulo d = buscarPorId(id);

        if (d != null) {
            System.out.print("Nuevo nombre [" + d.getNombre() + "]: ");
            String nombre = scanner.nextLine();
            if (!nombre.isBlank()) d.setNombre(nombre);

            System.out.print("Nuevo apellido [" + d.getApellido() + "]: ");
            String apellido = scanner.nextLine();
            if (!apellido.isBlank()) d.setApellido(apellido);

            System.out.print("Nuevo DNI [" + d.getDni() + "]: ");
            String dni = scanner.nextLine();
            if (!dni.isBlank()) d.setDni(dni);

            System.out.print("Nuevo Email [" + d.getEmail() + "]: ");
            String email = scanner.nextLine();
            if (!email.isBlank()) d.setEmail(email);

            System.out.print("Nuevo Teléfono [" + d.getTelefono() + "]: ");
            String telefono = scanner.nextLine();
            if (!telefono.isBlank()) d.setTelefono(telefono);

            System.out.print("Nuevo Género [" + d.getGeneroTexto() + "]: ");
            String generoTexto = scanner.nextLine();
            if (!generoTexto.isBlank()) {
                try {
                    d.setGenero(Genero.desdeTexto(generoTexto));
                } catch (IllegalArgumentException e) {
                    System.out.println("Género inválido. No se actualizó.");
                }
            }

            try {
                discipuloDAO.actualizar(d);  // ✅ Actualizar en la BD
                System.out.println("✔ Discípulo actualizado en la base de datos.");
            } catch (SQLException e) {
                System.out.println("❌ Error al actualizar en la base de datos: " + e.getMessage());
            }

            return d;
        } else {
            System.out.println("❌ Discípulo no encontrado.");
            return null;
        }
    }

    public Discipulo eliminarDiscipulo() {
        listarDiscipulos();
        System.out.print("ID del discípulo a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Discipulo d = buscarPorId(id);

        if (d != null) {
            try {
                discipuloDAO.eliminar(d.getId()); // ✅ Eliminar de la BD
                discipulos.remove(d);             // Eliminar localmente
                System.out.println("✔ Discípulo eliminado de la base de datos.");
            } catch (SQLException e) {
                System.out.println("❌ Error al eliminar en la base de datos: " + e.getMessage());
            }
            return d;
        } else {
            System.out.println("❌ Discípulo no encontrado.");
            return null;
        }
    }

    public void listarDiscipulos() {
        try {
            discipulos = discipuloDAO.listarTodos(); // ✅ Cargar desde BD
        } catch (SQLException e) {
            System.out.println("❌ Error al listar discípulos: " + e.getMessage());
            return;
        }

        if (discipulos.isEmpty()) {
            System.out.println("No hay discípulos registrados.");
        } else {
            System.out.println("--- Lista de Discípulos ---");
            for (Discipulo d : discipulos) {
                System.out.println("ID: " + d.getId() +
                        ", Nombre: " + d.getNombreCompleto() +
                        ", DNI: " + d.getDni() +
                        ", Email: " + d.getEmail() +
                        ", Tel: " + d.getTelefono() +
                        ", Género: " + d.getGeneroTexto());
            }
        }
    }

    public Discipulo buscarPorId(int id) {
        for (Discipulo d : discipulos) {
            if (d.getId() == id) return d;
        }
        return null;
    }

    public List<Discipulo> getDiscipulos() {
        return discipulos;
    }
}
