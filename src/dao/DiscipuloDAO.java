package dao;

import conexion.ConexionBD;
import entidades.Discipulo;
import enumeracion.Genero;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiscipuloDAO {
    private Connection conexion;

    public DiscipuloDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void guardar(Discipulo d) throws SQLException {
        String sql = "INSERT INTO discipulo (nombre, apellido, dni, email, telefono, genero) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setString(1, d.getNombre());
        stmt.setString(2, d.getApellido());
        stmt.setString(3, d.getDni());
        stmt.setString(4, d.getEmail());
        stmt.setString(5, d.getTelefono());
        stmt.setString(6, d.getGenero().toString());
        stmt.executeUpdate();
    }

    public void actualizar(Discipulo d) throws SQLException {
        String sql = "UPDATE discipulo SET nombre = ?, apellido = ?, dni = ?, email = ?, telefono = ?, genero = ? WHERE id_discipulo = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setString(1, d.getNombre());
        stmt.setString(2, d.getApellido());
        stmt.setString(3, d.getDni());
        stmt.setString(4, d.getEmail());
        stmt.setString(5, d.getTelefono());
        stmt.setString(6, d.getGenero().toString());
        stmt.setInt(7, d.getId());
        stmt.executeUpdate();
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM discipulo WHERE id_discipulo = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    public List<Discipulo> listarTodos() throws SQLException {
        List<Discipulo> lista = new ArrayList<>();
        String sql = "SELECT * FROM discipulo";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id_discipulo");
            String nombre = rs.getString("nombre");
            String apellido = rs.getString("apellido");
            String dni = rs.getString("dni");
            String email = rs.getString("email");
            String telefono = rs.getString("telefono");
            String generoTexto = rs.getString("genero");

            Genero genero = Genero.desdeTexto(generoTexto);

            Discipulo d = new Discipulo(id, nombre, apellido, dni, email, telefono, genero);
            lista.add(d);
        }

        return lista;
    }

    public Discipulo buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM discipulo WHERE id_discipulo = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String nombre = rs.getString("nombre");
            String apellido = rs.getString("apellido");
            String dni = rs.getString("dni");
            String email = rs.getString("email");
            String telefono = rs.getString("telefono");
            String generoTexto = rs.getString("genero");
            Genero genero = Genero.desdeTexto(generoTexto);

            return new Discipulo(id, nombre, apellido, dni, email, telefono, genero);
        }
        return null;
    }
}
