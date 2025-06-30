package dao;

import entidades.Discipulado;
import entidades.Discipulo;
import entidades.Matricula;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MatriculaDAO {
    private Connection conexion;

    public MatriculaDAO(Connection conexion) {
        this.conexion = conexion;
    }

public void guardar(Matricula m) throws SQLException {
    String sql = "INSERT INTO matriculacion (id_discipulo, id_discipulado, fecha_inscripcion) VALUES (?, ?, ?)";
    PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // 👈

    stmt.setInt(1, m.getDiscipulo().getId());
    stmt.setInt(2, m.getDiscipulado().getId());
    stmt.setDate(3, Date.valueOf(m.getFechaInscripcion()));
    stmt.executeUpdate();

    // Obtener el ID generado
    ResultSet keys = stmt.getGeneratedKeys();
    if (keys.next()) {
        m.setId(keys.getInt(1)); // 👈 asigna el ID real
    }
}


    public void actualizar(Matricula m) throws SQLException {
        String sql = "UPDATE matriculacion SET id_discipulo = ?, id_discipulado = ?, fecha_inscripcion = ? WHERE id_matriculacion = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, m.getDiscipulo().getId());
        stmt.setInt(2, m.getDiscipulado().getId());
        stmt.setDate(3, Date.valueOf(m.getFechaInscripcion()));
        stmt.setInt(4, m.getId());
        stmt.executeUpdate();
    }

    public void eliminar(int idMatricula) throws SQLException {
        String sql = "DELETE FROM matriculacion WHERE id_matriculacion = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, idMatricula);
        stmt.executeUpdate();
    }

    public List<Matricula> listarTodas(List<Discipulo> discipulos, List<Discipulado> discipulados) throws SQLException {
        List<Matricula> lista = new ArrayList<>();
        String sql = "SELECT * FROM matriculacion";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id_matriculacion");
            int idDiscipulo = rs.getInt("id_discipulo");
            int idDiscipulado = rs.getInt("id_discipulado");
            LocalDate fecha = rs.getDate("fecha_inscripcion").toLocalDate();

            Discipulo discipulo = buscarDiscipuloPorId(discipulos, idDiscipulo);
            Discipulado discipulado = buscarDiscipuladoPorId(discipulados, idDiscipulado);

            if (discipulo != null && discipulado != null) {
                Matricula m = new Matricula(id, discipulo, discipulado, fecha.toString());
                lista.add(m);
            }
        }

        return lista;
    }

    public Matricula buscarPorId(int id, List<Discipulo> discipulos, List<Discipulado> discipulados) throws SQLException {
        String sql = "SELECT * FROM matriculacion WHERE id_matriculacion = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int idDiscipulo = rs.getInt("id_discipulo");
            int idDiscipulado = rs.getInt("id_discipulado");
            LocalDate fecha = rs.getDate("fecha_inscripcion").toLocalDate();

            Discipulo discipulo = buscarDiscipuloPorId(discipulos, idDiscipulo);
            Discipulado discipulado = buscarDiscipuladoPorId(discipulados, idDiscipulado);

            if (discipulo != null && discipulado != null) {
                return new Matricula(id, discipulo, discipulado, fecha.toString());
            }
        }
        return null;
    }

    // Métodos auxiliares
    private Discipulo buscarDiscipuloPorId(List<Discipulo> lista, int id) {
        for (Discipulo d : lista) {
            if (d.getId() == id) return d;
        }
        return null;
    }

    private Discipulado buscarDiscipuladoPorId(List<Discipulado> lista, int id) {
        for (Discipulado d : lista) {
            if (d.getId() == id) return d;
        }
        return null;
    }
}
