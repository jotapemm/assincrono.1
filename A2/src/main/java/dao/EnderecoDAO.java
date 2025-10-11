package dao;

import model.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO {

    public Endereco create(Endereco endereco) {
        final String sql = "INSERT INTO endereco (estado, cidade, rua, numero, cep) VALUES (?,?,?,?,?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, endereco.getEstado());
            ps.setString(2, endereco.getCidade());
            ps.setString(3, endereco.getRua());
            ps.setString(4, endereco.getNumero());
            ps.setString(5, endereco.getCep());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    endereco.setId(rs.getInt(1));
                }
            }
            return endereco;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar endereco", e);
        }
    }

    public boolean update(Endereco endereco) {
        final String sql = "UPDATE endereco SET estado=?, cidade=?, rua=?, numero=?, cep=? WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, endereco.getEstado());
            ps.setString(2, endereco.getCidade());
            ps.setString(3, endereco.getRua());
            ps.setString(4, endereco.getNumero());
            ps.setString(5, endereco.getCep());
            ps.setInt(6, endereco.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar endereco", e);
        }
    }

    public boolean delete(int id) {
        final String sql = "DELETE FROM endereco WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            // Pode falhar por FK; deixe o chamador decidir como lidar
            return false;
        }
    }

    public Endereco findById(int id) {
        final String sql = "SELECT id, estado, cidade, rua, numero, cep FROM endereco WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar endereco por id", e);
        }
    }

    public List<Endereco> listAll() {
        final String sql = "SELECT id, estado, cidade, rua, numero, cep FROM endereco ORDER BY id";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Endereco> list = new ArrayList<>();
            while (rs.next()) {
                list.add(map(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar enderecos", e);
        }
    }

    public List<Endereco> findByCidade(String cidade) {
        final String sql = "SELECT id, estado, cidade, rua, numero, cep FROM endereco WHERE LOWER(cidade) LIKE LOWER(?) ORDER BY cidade";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + cidade + "%");
            try (ResultSet rs = ps.executeQuery()) {
                List<Endereco> list = new ArrayList<>();
                while (rs.next()) list.add(map(rs));
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar enderecos por cidade", e);
        }
    }

    public List<Endereco> findByEstado(String estado) {
        final String sql = "SELECT id, estado, cidade, rua, numero, cep FROM endereco WHERE LOWER(estado) LIKE LOWER(?) ORDER BY estado, cidade";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + estado + "%");
            try (ResultSet rs = ps.executeQuery()) {
                List<Endereco> list = new ArrayList<>();
                while (rs.next()) list.add(map(rs));
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar enderecos por estado", e);
        }
    }

    public int count() {
        final String sql = "SELECT COUNT(*) FROM endereco";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar enderecos", e);
        }
    }

    private Endereco map(ResultSet rs) throws SQLException {
        Endereco e = new Endereco();
        e.setId(rs.getInt("id"));
        e.setEstado(rs.getString("estado"));
        e.setCidade(rs.getString("cidade"));
        e.setRua(rs.getString("rua"));
        e.setNumero(rs.getString("numero"));
        e.setCep(rs.getString("cep"));
        return e;
    }
}
