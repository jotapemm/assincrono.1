package controller;

import dao.EnderecoDAO;
import model.Endereco;

import java.util.List;

public class EnderecoController {

    private final EnderecoDAO enderecoDAO;

    public EnderecoController() {
        this.enderecoDAO = new EnderecoDAO();
    }

    // Create
    public void criarEndereco(String estado, String cidade, String rua, String numero, String cep) {
        Endereco endereco = new Endereco(estado, cidade, rua, numero, cep);
        enderecoDAO.create(endereco);
    }

    // Read
    public List<Endereco> listarEnderecos() {
        return enderecoDAO.listAll();
    }

    public Endereco buscarEnderecoPorId(int id) {
        if (id <= 0) return null;
        return enderecoDAO.findById(id);
    }

    public List<Endereco> buscarEnderecosPorCidade(String cidade) {
        return enderecoDAO.findByCidade(cidade);
    }

    public List<Endereco> buscarEnderecosPorEstado(String estado) {
        return enderecoDAO.findByEstado(estado);
    }

    // Update
    public boolean atualizarEndereco(int id, String estado, String cidade, String rua, String numero, String cep) {
        Endereco existente = buscarEnderecoPorId(id);
        if (existente == null) return false;
        existente.setEstado(estado);
        existente.setCidade(cidade);
        existente.setRua(rua);
        existente.setNumero(numero);
        existente.setCep(cep);
        return enderecoDAO.update(existente);
    }

    // Delete
    public boolean deletarEndereco(int id) {
        if (id <= 0) return false;
        return enderecoDAO.delete(id);
    }

    public int getTotalEnderecos() {
        return enderecoDAO.count();
    }
}
