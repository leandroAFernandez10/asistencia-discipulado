package dao;

import conexion.ConexionBD;
import entidades.Disciplina;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDAO {
    private Connection conexion;

    public DisciplinaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void guardar(Disciplina disciplina) throws SQLException {
        String sql = "INSERT INTO disciplina (nombre) VALUES (?)";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setString(1, disciplina.getNombre());
        stmt.executeUpdate();
    }

    public void actualizar(Disciplina disciplina) throws SQLException {
        String sql = "UPDATE disciplina SET nombre = ? WHERE id_disciplina = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setString(1, disciplina.getNombre());
        stmt.setInt(2, disciplina.getId());
        stmt.executeUpdate();
    }

    public void eliminar(int idDisciplina) throws SQLException {
        String sql = "DELETE FROM disciplina WHERE id_disciplina = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, idDisciplina);
        stmt.executeUpdate();
    }

    public List<Disciplina> listarTodas() throws SQLException {
        List<Disciplina> lista = new ArrayList<>();
        String sql = "SELECT * FROM disciplina";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id_disciplina");
            String nombre = rs.getString("nombre");
            Disciplina d = new Disciplina(id, nombre);
            lista.add(d);
        }

        return lista;
    }

    public Disciplina buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM disciplina WHERE id_disciplina = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new Disciplina(rs.getInt("id_disciplina"), rs.getString("nombre"));
        }
        return null;
    }
}
