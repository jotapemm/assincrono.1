package controller;

import java.util.ArrayList;
import java.util.List;

import model.Endereco;
import model.Usuario;

public class UsuarioController {
    private List<Usuario> usuarios;
    private int proximoId;

    public UsuarioController() {
        this.usuarios = new ArrayList<>();
        this.proximoId = 1;
    }

    // Create
    public void criarUsuario(String nome, String cpf, String email, String telefone, Endereco endereco) {
        Usuario usuario = new Usuario(nome, cpf, email, telefone, endereco);
        usuario.setId(proximoId++);
        usuarios.add(usuario);
    }

    // Read
    public List<Usuario> listarUsuarios() {
        return new ArrayList<>(usuarios);
    }

    public Usuario buscarUsuarioPorId(int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null;
    }

    public Usuario buscarUsuarioPorCpf(String cpf) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(cpf)) {
                return usuario;
            }
        }
        return null;
    }

    // Update
    public boolean atualizarUsuario(int id, String nome, String cpf, String email, String telefone, Endereco endereco) {
        Usuario usuario = buscarUsuarioPorId(id);
        if (usuario != null) {
            usuario.setNome(nome);
            usuario.setCpf(cpf);
            usuario.setEmail(email);
            usuario.setTelefone(telefone);
            usuario.setEndereco(endereco);
            return true;
        }
        return false;
    }

    // Delete
    public boolean deletarUsuario(int id) {
        Usuario usuario = buscarUsuarioPorId(id);
        if (usuario != null) {
            usuarios.remove(usuario);
            return true;
        }
        return false;
    }

    public int getTotalUsuarios() {
        return usuarios.size();
    }
}
