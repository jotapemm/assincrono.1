package model;

import java.util.ArrayList;
import java.util.List;

public class Usuario extends Endereco {
    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private Endereco endereco;
    private List<Consulta> consultas;

    public Usuario() {
        this.consultas = new ArrayList<>();
    }

    public Usuario(String nome, String cpf, String email, String telefone, Endereco endereco) {
        this();
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    public void adicionarConsulta(Consulta consulta) {
        this.consultas.add(consulta);
    }

    public void removerConsulta(Consulta consulta) {
        this.consultas.remove(consulta);
    }

    @Override
    public String toString() {
        return "ID: " + id + 
               "\nNome: " + nome + 
               "\nCPF: " + cpf + 
               "\nEmail: " + email + 
               "\nTelefone: " + telefone + 
               "\nEndereco: " + (endereco != null ? endereco.toString() : "Nao informado");
    }
}
