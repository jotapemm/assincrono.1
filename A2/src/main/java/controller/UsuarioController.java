package controller;

import dao.UsuarioDAO;
import model.Endereco;
import model.Usuario;

import java.util.List;

public class UsuarioController {

    private final UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    // Create
    public void criarUsuario(String nome, String cpf, int idade, String email, String telefone, Endereco endereco) {
        Usuario usuario = new Usuario(nome, cpf, idade, email, telefone, endereco);
        usuarioDAO.create(usuario);
    }

    // Read
    public List<Usuario> listarUsuarios() {
        return usuarioDAO.listAll();
    }

    public Usuario buscarUsuarioPorId(int id) {
        if (id <= 0) return null;
        return usuarioDAO.findById(id);
    }

    public Usuario buscarUsuarioPorCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) return null;
        return usuarioDAO.findByCpf(cpf);
    }

    // Update
    public boolean atualizarUsuario(int id, String nome, String cpf, int idade, String email, String telefone, Endereco endereco) {
        Usuario existente = buscarUsuarioPorId(id);
        if (existente == null) return false;
        existente.setNome(nome);
        existente.setCpf(cpf);
        existente.setIdade(idade);
        existente.setEmail(email);
        existente.setTelefone(telefone);
        existente.setEndereco(endereco);
        return usuarioDAO.update(existente);
    }

    // Delete
    public boolean deletarUsuario(int id) {
        if (id <= 0) return false;
        return usuarioDAO.delete(id);
    }

    public int getTotalUsuarios() {
        return usuarioDAO.count();
    }
}
