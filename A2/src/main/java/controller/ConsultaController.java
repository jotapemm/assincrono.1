package controller;
import java.util.ArrayList;
import java.util.List;

import model.Consulta;
import model.Usuario;

public class ConsultaController {
    private List<Consulta> consultas;
    private int proximoId;

    public ConsultaController() {
        this.consultas = new ArrayList<>();
        this.proximoId = 1;
    }

    // Create
    public void criarConsulta(String data, String hora, Usuario paciente) {
        Consulta consulta = new Consulta(data, hora, paciente);
        consulta.setId(proximoId++);
        consultas.add(consulta);
        paciente.adicionarConsulta(consulta);
    }

    // Read
    public List<Consulta> listarConsultas() {
        return new ArrayList<>(consultas);
    }

    public Consulta buscarConsultaPorId(int id) {
        for (Consulta consulta : consultas) {
            if (consulta.getId() == id) {
                return consulta;
            }
        }
        return null;
    }

    public List<Consulta> buscarConsultasPorPaciente(Usuario paciente) {
        List<Consulta> resultado = new ArrayList<>();
        for (Consulta consulta : consultas) {
            if (consulta.getPaciente() != null && consulta.getPaciente().getId() == paciente.getId()) {
                resultado.add(consulta);
            }
        }
        return resultado;
    }

    public List<Consulta> buscarConsultasPorData(String data) {
        List<Consulta> resultado = new ArrayList<>();
        for (Consulta consulta : consultas) {
            if (consulta.getData().equals(data)) {
                resultado.add(consulta);
            }
        }
        return resultado;
    }

    // Update
    public boolean atualizarConsulta(int id, String data, String hora, Usuario paciente) {
        Consulta consulta = buscarConsultaPorId(id);
        if (consulta != null) {
            // Remove a consulta da lista do paciente antigo
            if (consulta.getPaciente() != null) {
                consulta.getPaciente().removerConsulta(consulta);
            }
            
            consulta.setData(data);
            consulta.setHora(hora);
            consulta.setPaciente(paciente);
            
            // Adiciona a consulta à lista do novo paciente
            if (paciente != null) {
                paciente.adicionarConsulta(consulta);
            }
            
            return true;
        }
        return false;
    }

    // Delete
    public boolean deletarConsulta(int id) {
        Consulta consulta = buscarConsultaPorId(id);
        if (consulta != null) {
            // Remove a consulta da lista do paciente
            if (consulta.getPaciente() != null) {
                consulta.getPaciente().removerConsulta(consulta);
            }
            consultas.remove(consulta);
            return true;
        }
        return false;
    }

    // Métodos específicos para consulta
    public boolean cancelarConsulta(int id) {
        Consulta consulta = buscarConsultaPorId(id);
        if (consulta != null) {
            consulta.cancelar();
            return true;
        }
        return false;
    }

    public boolean confirmarConsulta(int id) {
        Consulta consulta = buscarConsultaPorId(id);
        if (consulta != null) {
            consulta.confirmar();
            return true;
        }
        return false;
    }

    public boolean reagendarConsulta(int id, String novaData, String novaHora) {
        Consulta consulta = buscarConsultaPorId(id);
        if (consulta != null) {
            consulta.setData(novaData);
            consulta.setHora(novaHora);
            consulta.reagendar();
            return true;
        }
        return false;
    }

    public int getTotalConsultas() {
        return consultas.size();
    }
}
