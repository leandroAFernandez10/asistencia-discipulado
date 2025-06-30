package dao;

import entidades.Clase;
import entidades.Discipulado;
import conexion.ConexionBD;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClaseDAO {
    private Connection conexion;

    public ClaseDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void guardar(Clase clase) throws SQLException {
        String sql = "INSERT INTO clase (id_discipulado, fecha, tema) VALUES (?, ?, ?)";
        PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, clase.getDiscipulado().getId());
        stmt.setDate(2, Date.valueOf(clase.getFecha()));
        stmt.setString(3, clase.getTema());
        stmt.executeUpdate();

        ResultSet keys = stmt.getGeneratedKeys();
        if (keys.next()) {
            clase.setId(keys.getInt(1));
        }
    }


    public void actualizar(Clase clase) throws SQLException {
        String sql = "UPDATE clase SET id_discipulado = ?, fecha = ?, tema = ? WHERE id_clase = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, clase.getDiscipulado().getId());
        stmt.setDate(2, Date.valueOf(clase.getFecha()));
        stmt.setString(3, clase.getTema());
        stmt.setInt(4, clase.getId());
        stmt.executeUpdate();
    }

    public void eliminar(int idClase) throws SQLException {
        String sql = "DELETE FROM clase WHERE id_clase = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, idClase);
        stmt.executeUpdate();
    }

    public List<Clase> listarTodas(List<Discipulado> discipulados) throws SQLException {
        List<Clase> lista = new ArrayList<>();
        String sql = "SELECT * FROM clase";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id_clase");
            int idDiscipulado = rs.getInt("id_discipulado");
            LocalDate fecha = rs.getDate("fecha").toLocalDate();
            String tema = rs.getString("tema");

            Discipulado discipulado = buscarDiscipuladoPorId(discipulados, idDiscipulado);
            if (discipulado != null) {
                Clase clase = new Clase(id, tema, fecha.toString(), discipulado);
                lista.add(clase);
            }
        }

        return lista;
    }

    private Discipulado buscarDiscipuladoPorId(List<Discipulado> lista, int id) {
        for (Discipulado d : lista) {
            if (d.getId() == id) return d;
        }
        return null;
    }
}
