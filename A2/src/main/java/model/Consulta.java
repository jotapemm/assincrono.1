package model;

public class Consulta {
    private int id;
    private String data;
    private String hora;
    private Usuario paciente;
    private StatusConsulta status;

    public enum StatusConsulta {
        AGENDADA, CONFIRMADA, CANCELADA, REAGENDADA
    }

    public Consulta() {
        this.status = StatusConsulta.AGENDADA;
    }

    public Consulta(String data, String hora, Usuario paciente) {
        this();
        this.data = data;
        this.hora = hora;
        this.paciente = paciente;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Usuario getPaciente() {
        return paciente;
    }

    public void setPaciente(Usuario paciente) {
        this.paciente = paciente;
    }

    public StatusConsulta getStatus() {
        return status;
    }

    public void setStatus(StatusConsulta status) {
        this.status = status;
    }

    public void cancelar() {
        this.status = StatusConsulta.CANCELADA;
    }

    public void confirmar() {
        this.status = StatusConsulta.CONFIRMADA;
    }

    public void reagendar() {
        this.status = StatusConsulta.REAGENDADA;
    }

    @Override
    public String toString() {
        return "ID: " + id + 
               "\nData: " + data + 
               "\nHora: " + hora + 
               "\nPaciente: " + (paciente != null ? paciente.getNome() : "Nao informado") + 
               "\nStatus: " + status;
    }
}
