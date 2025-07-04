package dao;

import entidades.Disciplina;
import entidades.Discipulado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiscipuladoDAO {
    private Connection conexion;

    public DiscipuladoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void guardar(Discipulado d) throws SQLException {
        String sql = "INSERT INTO discipulado (id_disciplina, nombre, anio) VALUES (?, ?, ?)";
        PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, d.getDisciplina().getId());
        stmt.setString(2, d.getNombre());
        stmt.setInt(3, d.getAnio());
        stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            d.setId(rs.getInt(1));
    }
    }

    public void actualizar(Discipulado d) throws SQLException {
        String sql = "UPDATE discipulado SET id_disciplina = ?, nombre = ?, anio = ? WHERE id_discipulado = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, d.getDisciplina().getId());
        stmt.setString(2, d.getNombre());
        stmt.setInt(3, d.getAnio());
        stmt.setInt(4, d.getId());
        stmt.executeUpdate();
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM discipulado WHERE id_discipulado = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    public List<Discipulado> listarTodos(List<Disciplina> disciplinas) throws SQLException {
        List<Discipulado> lista = new ArrayList<>();
        String sql = "SELECT * FROM discipulado";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id_discipulado");
            int idDisciplina = rs.getInt("id_disciplina");
            String nombre = rs.getString("nombre");
            int anio = rs.getInt("anio");

            Disciplina disciplina = buscarDisciplinaPorId(disciplinas, idDisciplina);
            if (disciplina != null) {
                lista.add(new Discipulado(id, nombre, anio, disciplina));
            }
        }

        return lista;
    }

    public Discipulado buscarPorId(int id, List<Disciplina> disciplinas) throws SQLException {
        String sql = "SELECT * FROM discipulado WHERE id_discipulado = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int idDisciplina = rs.getInt("id_disciplina");
            String nombre = rs.getString("nombre");
            int anio = rs.getInt("anio");

            Disciplina disciplina = buscarDisciplinaPorId(disciplinas, idDisciplina);
            if (disciplina != null) {
                return new Discipulado(id, nombre, anio, disciplina);
            }
        }
        return null;
    }

    private Disciplina buscarDisciplinaPorId(List<Disciplina> lista, int id) {
        for (Disciplina d : lista) {
            if (d.getId() == id) return d;
        }
        return null;
    }
}
