package dao;

import model.Endereco;
import model.Usuario;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private final EnderecoDAO enderecoDAO = new EnderecoDAO();

    public Usuario create(Usuario usuario) {
        if (usuario.getEndereco() != null && usuario.getEndereco().getId() == 0) {
            usuario.setEndereco(enderecoDAO.create(usuario.getEndereco()));
        }
        final String sql = "INSERT INTO usuario (nome, cpf, email, idade, telefone, endereco_id, created_at, updated_at) VALUES (?,?,?,?,?,?, now(), now())";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getCpf());
            ps.setString(3, usuario.getEmail());
            ps.setInt(4, usuario.getIdade() != null ? usuario.getIdade() : 0);
            ps.setString(5, usuario.getTelefone());
            if (usuario.getEndereco() != null) {
                ps.setInt(6, usuario.getEndereco().getId());
            } else {
                ps.setNull(6, Types.INTEGER);
            }
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setId(rs.getLong(1));
                }
            }
            usuario.setCreatedAt(LocalDateTime.now());
            usuario.setUpdatedAt(LocalDateTime.now());
            return usuario;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar usuario", e);
        }
    }

    public boolean update(Usuario usuario) {
        if (usuario.getEndereco() != null) {
            if (usuario.getEndereco().getId() == 0) {
                enderecoDAO.create(usuario.getEndereco());
            } else {
                enderecoDAO.update(usuario.getEndereco());
            }
        }
        final String sql = "UPDATE usuario SET nome=?, cpf=?, email=?, idade=?, telefone=?, endereco_id=?, updated_at=now() WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getCpf());
            ps.setString(3, usuario.getEmail());
            ps.setInt(4, usuario.getIdade() != null ? usuario.getIdade() : 0);
            ps.setString(5, usuario.getTelefone());
            if (usuario.getEndereco() != null) {
                ps.setInt(6, usuario.getEndereco().getId());
            } else {
                ps.setNull(6, Types.INTEGER);
            }
            ps.setLong(7, usuario.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuario", e);
        }
    }

    public boolean delete(long id) {
        // Apaga o usuario e, se o endereco ficar orfao, remove-o
        Long enderecoId = findEnderecoIdByUsuario(id);
        final String sql = "DELETE FROM usuario WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            boolean deleted = ps.executeUpdate() > 0;
            if (deleted && enderecoId != null) {
                if (!isEnderecoInUse(enderecoId.intValue())) {
                    enderecoDAO.delete(enderecoId.intValue());
                }
            }
            return deleted;
        } catch (SQLException e) {
            // Pode falhar por FKs (ex.: consultas); deixe o chamador lidar
            return false;
        }
    }

    private Long findEnderecoIdByUsuario(long usuarioId) {
        final String sql = "SELECT endereco_id FROM usuario WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int val = rs.getInt(1);
                    return rs.wasNull() ? null : (long) val;
                }
            }
            return null;
        } catch (SQLException e) {
            return null;
        }
    }

    private boolean isEnderecoInUse(int enderecoId) {
        final String sql = "SELECT COUNT(*) FROM usuario WHERE endereco_id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, enderecoId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            return true;
        }
    }

    public Usuario findById(long id) {
        final String sql = "SELECT u.id, u.nome, u.cpf, u.email, u.idade, u.telefone, u.created_at, u.updated_at, " +
                "e.id as e_id, e.estado, e.cidade, e.rua, e.numero, e.cep " +
                "FROM usuario u LEFT JOIN endereco e ON u.endereco_id = e.id WHERE u.id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuario por id", e);
        }
    }

    public Usuario findByCpf(String cpf) {
        final String sql = "SELECT u.id, u.nome, u.cpf, u.email, u.idade, u.telefone, u.created_at, u.updated_at, " +
                "e.id as e_id, e.estado, e.cidade, e.rua, e.numero, e.cep " +
                "FROM usuario u LEFT JOIN endereco e ON u.endereco_id = e.id WHERE u.cpf=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuario por cpf", e);
        }
    }

    public List<Usuario> listAll() {
        final String sql = "SELECT u.id, u.nome, u.cpf, u.email, u.idade, u.telefone, u.created_at, u.updated_at, " +
                "e.id as e_id, e.estado, e.cidade, e.rua, e.numero, e.cep " +
                "FROM usuario u LEFT JOIN endereco e ON u.endereco_id = e.id ORDER BY u.id";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Usuario> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuarios", e);
        }
    }

    public int count() {
        final String sql = "SELECT COUNT(*) FROM usuario";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar usuarios", e);
        }
    }

    private Usuario map(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getLong("id"));
        u.setNome(rs.getString("nome"));
        u.setCpf(rs.getString("cpf"));
        u.setEmail(rs.getString("email"));
        u.setIdade(rs.getInt("idade"));
        u.setTelefone(rs.getString("telefone"));
        Timestamp cAt = rs.getTimestamp("created_at");
        Timestamp uAt = rs.getTimestamp("updated_at");
        if (cAt != null) u.setCreatedAt(cAt.toLocalDateTime());
        if (uAt != null) u.setUpdatedAt(uAt.toLocalDateTime());
        int eId = rs.getInt("e_id");
        if (!rs.wasNull()) {
            Endereco e = new Endereco();
            e.setId(eId);
            e.setEstado(rs.getString("estado"));
            e.setCidade(rs.getString("cidade"));
            e.setRua(rs.getString("rua"));
            e.setNumero(rs.getString("numero"));
            e.setCep(rs.getString("cep"));
            u.setEndereco(e);
        }
        return u;
    }
}
