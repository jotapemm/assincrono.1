package model;

import javax.persistence.*;

@Entity
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "estado", nullable = false, length = 2)
    private String estado;

    @Column(name = "cidade", nullable = false, length = 100)
    private String cidade;

    @Column(name = "rua", nullable = false, length = 255)
    private String rua;

    @Column(name = "cep", nullable = false, length = 10)
    private String cep;

    @Column(name = "numero", nullable = false, length = 20)
    private String numero;

    // Construtores
    public Endereco() {}

    public Endereco(String estado, String cidade, String rua, String numero, String cep) {
        this.estado = estado;
        this.cidade = cidade;
        this.rua = rua;
        this.numero = numero;
        this.cep = cep;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @Override
    public String toString() {
        return rua + ", " + numero + " - " + cidade + "/" + estado + " - CEP: " + cep;
    }
}