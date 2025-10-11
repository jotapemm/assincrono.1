package dao;

import model.Consulta;
import model.Usuario;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {

    public Consulta create(Consulta consulta) {
        final String sql = "INSERT INTO consulta (usuario_id, data_consulta, hora_consulta, status, observacoes, created_at, updated_at) VALUES (?,?,?, CAST(? AS consulta_status), ?, now(), now())";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, consulta.getPaciente().getId());
            ps.setDate(2, Date.valueOf(consulta.getDataConsulta()));
            ps.setTime(3, Time.valueOf(consulta.getHoraConsulta()));
            ps.setString(4, consulta.getStatus().name());
            ps.setString(5, consulta.getObservacoes());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) consulta.setId(rs.getLong(1));
            }
            return consulta;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar consulta", e);
        }
    }

    public boolean update(Consulta consulta) {
        final String sql = "UPDATE consulta SET usuario_id=?, data_consulta=?, hora_consulta=?, status=CAST(? AS consulta_status), observacoes=?, updated_at=now() WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, consulta.getPaciente().getId());
            ps.setDate(2, Date.valueOf(consulta.getDataConsulta()));
            ps.setTime(3, Time.valueOf(consulta.getHoraConsulta()));
            ps.setString(4, consulta.getStatus().name());
            ps.setString(5, consulta.getObservacoes());
            ps.setLong(6, consulta.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar consulta", e);
        }
    }

    public boolean delete(long id) {
        final String sql = "DELETE FROM consulta WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public Consulta findById(long id) {
        final String sql = "SELECT c.id, c.usuario_id, c.data_consulta, c.hora_consulta, c.status, c.observacoes, " +
                "u.nome as u_nome, u.cpf as u_cpf " +
                "FROM consulta c JOIN usuario u ON c.usuario_id = u.id WHERE c.id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar consulta por id", e);
        }
    }

    public List<Consulta> listAll() {
        final String sql = "SELECT c.id, c.usuario_id, c.data_consulta, c.hora_consulta, c.status, c.observacoes, u.nome as u_nome, u.cpf as u_cpf FROM consulta c JOIN usuario u ON c.usuario_id = u.id ORDER BY c.data_consulta, c.hora_consulta";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Consulta> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar consultas", e);
        }
    }

    public List<Consulta> findByPaciente(long usuarioId) {
        final String sql = "SELECT c.id, c.usuario_id, c.data_consulta, c.hora_consulta, c.status, c.observacoes, u.nome as u_nome, u.cpf as u_cpf FROM consulta c JOIN usuario u ON c.usuario_id = u.id WHERE c.usuario_id=? ORDER BY c.data_consulta, c.hora_consulta";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Consulta> list = new ArrayList<>();
                while (rs.next()) list.add(map(rs));
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar consultas por paciente", e);
        }
    }

    public List<Consulta> findByDate(LocalDate date) {
        final String sql = "SELECT c.id, c.usuario_id, c.data_consulta, c.hora_consulta, c.status, c.observacoes, u.nome as u_nome, u.cpf as u_cpf FROM consulta c JOIN usuario u ON c.usuario_id = u.id WHERE c.data_consulta=? ORDER BY c.hora_consulta";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                List<Consulta> list = new ArrayList<>();
                while (rs.next()) list.add(map(rs));
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar consultas por data", e);
        }
    }

    public boolean updateStatus(long id, Consulta.StatusConsulta status) {
        final String sql = "UPDATE consulta SET status=CAST(? AS consulta_status), updated_at=now() WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status.name());
            ps.setLong(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar status da consulta", e);
        }
    }

    public int count() {
        final String sql = "SELECT COUNT(*) FROM consulta";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar consultas", e);
        }
    }

    private Consulta map(ResultSet rs) throws SQLException {
        Consulta c = new Consulta();
        c.setId(rs.getLong("id"));
        c.setDataConsulta(rs.getDate("data_consulta").toLocalDate());
        Time time = rs.getTime("hora_consulta");
        c.setHoraConsulta(time != null ? time.toLocalTime() : LocalTime.MIDNIGHT);
        c.setStatus(Consulta.StatusConsulta.valueOf(rs.getString("status")));
        c.setObservacoes(rs.getString("observacoes"));
        Usuario u = new Usuario();
        u.setId(rs.getLong("usuario_id"));
        u.setNome(rs.getString("u_nome"));
        u.setCpf(rs.getString("u_cpf"));
        c.setPaciente(u);
        return c;
    }
}
