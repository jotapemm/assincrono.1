package controller;

import dao.ConsultaDAO;
import model.Consulta;
import model.Usuario;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ConsultaController {

    private final ConsultaDAO consultaDAO;

    public ConsultaController() {
        this.consultaDAO = new ConsultaDAO();
    }

    // Create
    public void criarConsulta(LocalDate dataConsulta, LocalTime horaConsulta, Usuario paciente) {
        if (paciente == null || paciente.getId() == null || paciente.getId() <= 0) return;
        Consulta consulta = new Consulta(dataConsulta, horaConsulta, paciente);
        consultaDAO.create(consulta);
    }

    // Read
    public List<Consulta> listarConsultas() {
        return consultaDAO.listAll();
    }

    public Consulta buscarConsultaPorId(int id) {
        if (id <= 0) return null;
        return consultaDAO.findById(id);
    }

    public List<Consulta> buscarConsultasPorPaciente(Usuario paciente) {
        if (paciente == null || paciente.getId() == null) return List.of();
        return consultaDAO.findByPaciente(paciente.getId());
    }

    public List<Consulta> buscarConsultasPorData(LocalDate data) {
        return consultaDAO.findByDate(data);
    }

    // Update
    public boolean atualizarConsulta(int id, LocalDate dataConsulta, LocalTime horaConsulta, Usuario paciente) {
        Consulta existente = buscarConsultaPorId(id);
        if (existente == null) return false;
        existente.setDataConsulta(dataConsulta);
        existente.setHoraConsulta(horaConsulta);
        existente.setPaciente(paciente);
        return consultaDAO.update(existente);
    }

    // Delete
    public boolean deletarConsulta(int id) {
        if (id <= 0) return false;
        return consultaDAO.delete(id);
    }

    // Métodos específicos para consulta
    public boolean cancelarConsulta(int id) {
        Consulta existente = buscarConsultaPorId(id);
        if (existente == null) return false;
        return consultaDAO.updateStatus(id, Consulta.StatusConsulta.CANCELADA);
    }

    public boolean confirmarConsulta(int id) {
        Consulta existente = buscarConsultaPorId(id);
        if (existente == null) return false;
        return consultaDAO.updateStatus(id, Consulta.StatusConsulta.CONFIRMADA);
    }

    public boolean reagendarConsulta(int id, LocalDate novaData, LocalTime novaHora) {
        Consulta existente = buscarConsultaPorId(id);
        if (existente == null) return false;
        existente.setDataConsulta(novaData);
        existente.setHoraConsulta(novaHora);
        existente.reagendar();
        return consultaDAO.update(existente);
    }

    public int getTotalConsultas() {
        return consultaDAO.count();
    }
}
