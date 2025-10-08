package controller;

import java.util.ArrayList;
import java.util.List;

import model.Endereco;

public class EnderecoController {
    private List<Endereco> enderecos;
    private int proximoId;

    public EnderecoController() {
        this.enderecos = new ArrayList<>();
        this.proximoId = 1;
    }

    // Create
    public void criarEndereco(String estado, String cidade, String rua, String numero, String cep) {
        Endereco endereco = new Endereco(estado, cidade, rua, numero, cep);
        endereco.setId(proximoId++);
        enderecos.add(endereco);
    }

    // Read
    public List<Endereco> listarEnderecos() {
        return new ArrayList<>(enderecos);
    }

    public Endereco buscarEnderecoPorId(int id) {
        for (Endereco endereco : enderecos) {
            if (endereco.getId() == id) {
                return endereco;
            }
        }
        return null;
    }

    public List<Endereco> buscarEnderecosPorCidade(String cidade) {
        List<Endereco> resultado = new ArrayList<>();
        for (Endereco endereco : enderecos) {
            if (endereco.getCidade().toLowerCase().contains(cidade.toLowerCase())) {
                resultado.add(endereco);
            }
        }
        return resultado;
    }

    public List<Endereco> buscarEnderecosPorEstado(String estado) {
        List<Endereco> resultado = new ArrayList<>();
        for (Endereco endereco : enderecos) {
            if (endereco.getEstado().toLowerCase().contains(estado.toLowerCase())) {
                resultado.add(endereco);
            }
        }
        return resultado;
    }

    // Update
    public boolean atualizarEndereco(int id, String estado, String cidade, String rua, String numero, String cep) {
        Endereco endereco = buscarEnderecoPorId(id);
        if (endereco != null) {
            endereco.setEstado(estado);
            endereco.setCidade(cidade);
            endereco.setRua(rua);
            endereco.setNumero(numero);
            endereco.setCep(cep);
            return true;
        }
        return false;
    }

    // Delete
    public boolean deletarEndereco(int id) {
        Endereco endereco = buscarEnderecoPorId(id);
        if (endereco != null) {
            enderecos.remove(endereco);
            return true;
        }
        return false;
    }

    public int getTotalEnderecos() {
        return enderecos.size();
    }
}
