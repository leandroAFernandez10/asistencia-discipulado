package dao;

import entidades.Asistencia;
import entidades.Clase;
import entidades.Matricula;
import conexion.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsistenciaDAO {
    private Connection conexion;

    public AsistenciaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void guardar(Asistencia asistencia) throws SQLException {
        String sql = "INSERT INTO asistencia (id_matriculacion, id_clase) VALUES (?, ?)";
        PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, asistencia.getMatricula().getId());
        stmt.setInt(2, asistencia.getClase().getId());
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            asistencia.setId(rs.getInt(1));
        }
    }

    public void eliminarPorId(int idAsistencia) throws SQLException {
        String sql = "DELETE FROM asistencia WHERE id_asistencia = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, idAsistencia);
        stmt.executeUpdate();
    }

    public List<Asistencia> listarTodas(List<Matricula> matriculas, List<Clase> clases) throws SQLException {
        List<Asistencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM asistencia";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id_asistencia");
            int idClase = rs.getInt("id_clase");
            int idMatricula = rs.getInt("id_matriculacion");

            Clase clase = buscarClasePorId(clases, idClase);
            Matricula matricula = buscarMatriculaPorId(matriculas, idMatricula);

            if (clase != null && matricula != null) {
                Asistencia a = new Asistencia(id, clase, matricula);
                lista.add(a);
            }
        }

        return lista;
    }

    public boolean asistenciaYaRegistrada(int idClase, int idMatricula) throws SQLException {
        String sql = "SELECT COUNT(*) FROM asistencia WHERE id_clase = ? AND id_matriculacion = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, idClase);
        stmt.setInt(2, idMatricula);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }

    private Clase buscarClasePorId(List<Clase> clases, int id) {
        for (Clase c : clases) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    private Matricula buscarMatriculaPorId(List<Matricula> matriculas, int id) {
        for (Matricula m : matriculas) {
            if (m.getId() == id) return m;
        }
        return null;
    }
}
