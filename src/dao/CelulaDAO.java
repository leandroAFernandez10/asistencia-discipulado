package dao;

import entidades.Celula;
import entidades.Disciplina;
import entidades.Discipulo;
import enumeracion.EstadoCelula;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CelulaDAO {
    private Connection conexion;

    public CelulaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void guardar(Celula celula) throws SQLException {
        String sql = "INSERT INTO celula (localidad, estado, id_disciplina, id_anfitrion, id_timonel, id_colaborador) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, celula.getLocalidad());
        stmt.setString(2, celula.getEstadoTexto());
        stmt.setInt(3, celula.getDisciplina().getId());
        stmt.setInt(4, celula.getAnfitrion().getId());
        stmt.setInt(5, celula.getTimonel().getId());
        stmt.setInt(6, celula.getColaborador().getId());
        stmt.executeUpdate();

        // Obtener el ID generado y asignarlo
        ResultSet keys = stmt.getGeneratedKeys();
        if (keys.next()) {
            celula.setId(keys.getInt(1));
        }
    }


    public List<Celula> listarTodas(List<Disciplina> disciplinas, List<Discipulo> discipulos) throws SQLException {
        List<Celula> lista = new ArrayList<>();
        String sql = "SELECT * FROM celula";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id_celula");
            String localidad = rs.getString("localidad");
            String estadoTexto = rs.getString("estado");

            int idDisciplina = rs.getInt("id_disciplina");
            int idAnfitrion = rs.getInt("id_anfitrion");
            int idTimonel = rs.getInt("id_timonel");
            int idColaborador = rs.getInt("id_colaborador");

            Disciplina disciplina = buscarDisciplinaPorId(disciplinas, idDisciplina);
            Discipulo anfitrion = buscarDiscipuloPorId(discipulos, idAnfitrion);
            Discipulo timonel = buscarDiscipuloPorId(discipulos, idTimonel);
            Discipulo colaborador = buscarDiscipuloPorId(discipulos, idColaborador);
            EstadoCelula estado = EstadoCelula.desdeTexto(estadoTexto);

            if (disciplina != null && anfitrion != null && timonel != null && colaborador != null) {
                Celula celula = new Celula(id, localidad, disciplina, estado, anfitrion, timonel, colaborador);
                lista.add(celula);
            }
        }

        return lista;
    }

    public void actualizar(Celula celula) throws SQLException {
        String sql = "UPDATE celula SET localidad = ?, estado = ?, id_disciplina = ?, id_anfitrion = ?, id_timonel = ?, id_colaborador = ? " +
                     "WHERE id_celula = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setString(1, celula.getLocalidad());
        stmt.setString(2, celula.getEstadoTexto());
        stmt.setInt(3, celula.getDisciplina().getId());
        stmt.setInt(4, celula.getAnfitrion().getId());
        stmt.setInt(5, celula.getTimonel().getId());
        stmt.setInt(6, celula.getColaborador().getId());
        stmt.setInt(7, celula.getId());
        stmt.executeUpdate();
    }

    public void eliminar(int idCelula) throws SQLException {
        String sql = "DELETE FROM celula WHERE id_celula = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, idCelula);
        stmt.executeUpdate();
    }

    // Métodos auxiliares
    private Disciplina buscarDisciplinaPorId(List<Disciplina> lista, int id) {
        for (Disciplina d : lista) {
            if (d.getId() == id) return d;
        }
        return null;
    }

    private Discipulo buscarDiscipuloPorId(List<Discipulo> lista, int id) {
        for (Discipulo d : lista) {
            if (d.getId() == id) return d;
        }
        return null;
    }
}
