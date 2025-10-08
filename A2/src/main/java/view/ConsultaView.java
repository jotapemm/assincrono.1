package view;

import java.util.List;
import java.util.Scanner;

import model.Consulta;
import model.Usuario;

public class ConsultaView {
    private Scanner scanner;

    public ConsultaView() {
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenuConsulta() {
        System.out.println("\n=== MENU CONSULTAS ===");
        System.out.println("1. Agendar Consulta");
        System.out.println("2. Listar Consultas");
        System.out.println("3. Buscar Consulta por ID");
        System.out.println("4. Buscar Consultas por Paciente");
        System.out.println("5. Buscar Consultas por Data");
        System.out.println("6. Atualizar Consulta");
        System.out.println("7. Cancelar Consulta");
        System.out.println("8. Confirmar Consulta");
        System.out.println("9. Reagendar Consulta");
        System.out.println("10. Deletar Consulta");
        System.out.println("0. Voltar ao Menu Principal");
        System.out.print("Escolha uma opcao: ");
    }

    public String lerData() {
        System.out.print("Digite a data (DD/MM/AAAA): ");
        return scanner.nextLine();
    }

    public String lerHora() {
        System.out.print("Digite a hora (HH:MM): ");
        return scanner.nextLine();
    }

    public int lerIdPaciente() {
        System.out.print("Digite o ID do paciente: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID invalido!");
            return -1;
        }
    }

    public int lerIdConsulta() {
        System.out.print("Digite o ID da consulta: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID invalido!");
            return -1;
        }
    }

    public void exibirConsultas(List<Consulta> consultas) {
        System.out.println("\n=== LISTA DE CONSULTAS ===");
        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta cadastrada.");
        } else {
            for (Consulta consulta : consultas) {
                System.out.println("----------------------------------------");
                System.out.println(consulta.toString());
                System.out.println("----------------------------------------");
            }
        }
    }

    public void exibirConsulta(Consulta consulta) {
        if (consulta != null) {
            System.out.println("\n=== DADOS DA CONSULTA ===");
            System.out.println(consulta.toString());
        } else {
            System.out.println("Consulta nao encontrada.");
        }
    }

    public void exibirMenuStatusConsulta() {
        System.out.println("\n=== OPCOES DE STATUS ===");
        System.out.println("1. Cancelar Consulta");
        System.out.println("2. Confirmar Consulta");
        System.out.println("3. Reagendar Consulta");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opcao: ");
    }

    public String lerNovaData() {
        System.out.print("Digite a nova data (DD/MM/AAAA): ");
        return scanner.nextLine();
    }

    public String lerNovaHora() {
        System.out.print("Digite a nova hora (HH:MM): ");
        return scanner.nextLine();
    }

    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public void exibirMensagemSucesso() {
        System.out.println("Operacao realizada com sucesso!");
    }

    public void exibirMensagemErro() {
        System.out.println("Erro ao realizar operacao!");
    }

    public boolean confirmarExclusao() {
        System.out.print("Tem certeza que deseja excluir? (s/n): ");
        String resposta = scanner.nextLine().toLowerCase();
        return resposta.equals("s") || resposta.equals("sim");
    }

    public void aguardarEnter() {
        System.out.print("\nPressione Enter para continuar...");
        scanner.nextLine();
    }

    public void exibirListaPacientes(List<Usuario> pacientes) {
        System.out.println("\n=== PACIENTES DISPONIVEIS ===");
        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
        } else {
            for (Usuario paciente : pacientes) {
                System.out.println("ID: " + paciente.getId() + " - " + paciente.getNome());
            }
        }
    }
}
