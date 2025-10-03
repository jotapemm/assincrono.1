package view;

import java.util.List;
import java.util.Scanner;

import controller.ConsultaController;
import controller.EnderecoController;
import controller.UsuarioController;
import model.Consulta;
import model.Endereco;
import model.Usuario;

public class VisaoPrincipal {
    private Scanner scanner;
    private UsuarioView usuarioView;
    private EnderecoView enderecoView;
    private ConsultaView consultaView;
    private UsuarioController usuarioController;
    private EnderecoController enderecoController;
    private ConsultaController consultaController;

    public VisaoPrincipal() {
        this.scanner = new Scanner(System.in);
        this.usuarioView = new UsuarioView();
        this.enderecoView = new EnderecoView();
        this.consultaView = new ConsultaView();
        this.usuarioController = new UsuarioController();
        this.enderecoController = new EnderecoController();
        this.consultaController = new ConsultaController();
    }

    public void exibirMenuPrincipal() {
        System.out.println("1. Pacientes");
        System.out.println("2. Enderecos");
        System.out.println("3. Consultas");
        System.out.println("4. Estatisticas");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opcao: ");
    }

    public void executar() {
        int opcao;
        do {
            exibirMenuPrincipal();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1:
                        executarMenuUsuarios();
                        break;
                    case 2:
                        executarMenuEnderecos();
                        break;
                    case 3:
                        executarMenuConsultas();
                        break;
                    case 4:
                        exibirEstatisticas();
                        break;
                    case 0:
                        System.out.println("Saindo do sistema...");
                        break;
                    default:
                        System.out.println("Opcao invalida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Opcao invalida! Digite um numero.");
                opcao = -1;
            }
        } while (opcao != 0);
    }

    private void executarMenuUsuarios() {
        int opcao;
        do {
            usuarioView.exibirMenuUsuario();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1:
                        cadastrarUsuario();
                        break;
                    case 2:
                        listarUsuarios();
                        break;
                    case 3:
                        buscarUsuarioPorId();
                        break;
                    case 4:
                        buscarUsuarioPorCpf();
                        break;
                    case 5:
                        atualizarUsuario();
                        break;
                    case 6:
                        deletarUsuario();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcao invalida!");
                }
                if (opcao != 0) {
                    usuarioView.aguardarEnter();
                }
            } catch (NumberFormatException e) {
                System.out.println("Opcao invalida! Digite um numero.");
                opcao = -1;
            }
        } while (opcao != 0);
    }

    private void executarMenuEnderecos() {
        int opcao;
        do {
            enderecoView.exibirMenuEndereco();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1:
                        cadastrarEndereco();
                        break;
                    case 2:
                        listarEnderecos();
                        break;
                    case 3:
                        buscarEnderecoPorId();
                        break;
                    case 4:
                        buscarEnderecosPorCidade();
                        break;
                    case 5:
                        buscarEnderecosPorEstado();
                        break;
                    case 6:
                        atualizarEndereco();
                        break;
                    case 7:
                        deletarEndereco();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcao invalida!");
                }
                if (opcao != 0) {
                    enderecoView.aguardarEnter();
                }
            } catch (NumberFormatException e) {
                System.out.println("Opcao invalida! Digite um numero.");
                opcao = -1;
            }
        } while (opcao != 0);
    }

    private void executarMenuConsultas() {
        int opcao;
        do {
            consultaView.exibirMenuConsulta();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1:
                        agendarConsulta();
                        break;
                    case 2:
                        listarConsultas();
                        break;
                    case 3:
                        buscarConsultaPorId();
                        break;
                    case 4:
                        buscarConsultasPorPaciente();
                        break;
                    case 5:
                        buscarConsultasPorData();
                        break;
                    case 6:
                        atualizarConsulta();
                        break;
                    case 7:
                        cancelarConsulta();
                        break;
                    case 8:
                        confirmarConsulta();
                        break;
                    case 9:
                        reagendarConsulta();
                        break;
                    case 10:
                        deletarConsulta();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcao invalida!");
                }
                if (opcao != 0) {
                    consultaView.aguardarEnter();
                }
            } catch (NumberFormatException e) {
                System.out.println("Opcao invalida! Digite um numero.");
                opcao = -1;
            }
        } while (opcao != 0);
    }

    private void cadastrarUsuario() {
        Usuario usuario = usuarioView.lerDadosUsuario();
        usuarioController.criarUsuario(
            usuario.getNome(), 
            usuario.getCpf(), 
            usuario.getEmail(), 
            usuario.getTelefone(), 
            usuario.getEndereco()
        );
        usuarioView.exibirMensagemSucesso();
    }

    private void listarUsuarios() {
        List<Usuario> usuarios = usuarioController.listarUsuarios();
        usuarioView.exibirUsuarios(usuarios);
    }

    private void buscarUsuarioPorId() {
        int id = usuarioView.lerIdUsuario();
        Usuario usuario = usuarioController.buscarUsuarioPorId(id);
        usuarioView.exibirUsuario(usuario);
    }

    private void buscarUsuarioPorCpf() {
        String cpf = usuarioView.lerCpfUsuario();
        Usuario usuario = usuarioController.buscarUsuarioPorCpf(cpf);
        usuarioView.exibirUsuario(usuario);
    }

    private void atualizarUsuario() {
        int id = usuarioView.lerIdUsuario();
        Usuario usuario = usuarioView.lerDadosAtualizacaoUsuario();
        boolean sucesso = usuarioController.atualizarUsuario(
            id, 
            usuario.getNome(), 
            usuario.getCpf(), 
            usuario.getEmail(), 
            usuario.getTelefone(), 
            usuario.getEndereco()
        );
        if (sucesso) {
            usuarioView.exibirMensagemSucesso();
        } else {
            usuarioView.exibirMensagemErro();
        }
    }

    private void deletarUsuario() {
        int id = usuarioView.lerIdUsuario();
        if (usuarioView.confirmarExclusao()) {
            boolean sucesso = usuarioController.deletarUsuario(id);
            if (sucesso) {
                usuarioView.exibirMensagemSucesso();
            } else {
                usuarioView.exibirMensagemErro();
            }
        }
    }

    private void cadastrarEndereco() {
        Endereco endereco = enderecoView.lerDadosEndereco();
        enderecoController.criarEndereco(
            endereco.getEstado(), 
            endereco.getCidade(), 
            endereco.getRua(), 
            endereco.getNumero(), 
            endereco.getCep()
        );
        enderecoView.exibirMensagemSucesso();
    }

    private void listarEnderecos() {
        List<Endereco> enderecos = enderecoController.listarEnderecos();
        enderecoView.exibirEnderecos(enderecos);
    }

    private void buscarEnderecoPorId() {
        int id = enderecoView.lerIdEndereco();
        Endereco endereco = enderecoController.buscarEnderecoPorId(id);
        enderecoView.exibirEndereco(endereco);
    }

    private void buscarEnderecosPorCidade() {
        String cidade = enderecoView.lerCidade();
        List<Endereco> enderecos = enderecoController.buscarEnderecosPorCidade(cidade);
        enderecoView.exibirEnderecos(enderecos);
    }

    private void buscarEnderecosPorEstado() {
        String estado = enderecoView.lerEstado();
        List<Endereco> enderecos = enderecoController.buscarEnderecosPorEstado(estado);
        enderecoView.exibirEnderecos(enderecos);
    }

    private void atualizarEndereco() {
        int id = enderecoView.lerIdEndereco();
        Endereco endereco = enderecoView.lerDadosAtualizacaoEndereco();
        boolean sucesso = enderecoController.atualizarEndereco(
            id, 
            endereco.getEstado(), 
            endereco.getCidade(), 
            endereco.getRua(), 
            endereco.getNumero(), 
            endereco.getCep()
        );
        if (sucesso) {
            enderecoView.exibirMensagemSucesso();
        } else {
            enderecoView.exibirMensagemErro();
        }
    }

    private void deletarEndereco() {
        int id = enderecoView.lerIdEndereco();
        if (enderecoView.confirmarExclusao()) {
            boolean sucesso = enderecoController.deletarEndereco(id);
            if (sucesso) {
                enderecoView.exibirMensagemSucesso();
            } else {
                enderecoView.exibirMensagemErro();
            }
        }
    }

    private void agendarConsulta() {
        consultaView.exibirListaPacientes(usuarioController.listarUsuarios());
        int idPaciente = consultaView.lerIdPaciente();
        Usuario paciente = usuarioController.buscarUsuarioPorId(idPaciente);
        
        if (paciente != null) {
            String data = consultaView.lerData();
            String hora = consultaView.lerHora();
            consultaController.criarConsulta(data, hora, paciente);
            consultaView.exibirMensagemSucesso();
        } else {
            consultaView.exibirMensagem("Paciente nao encontrado!");
        }
    }

    private void listarConsultas() {
        List<Consulta> consultas = consultaController.listarConsultas();
        consultaView.exibirConsultas(consultas);
    }

    private void buscarConsultaPorId() {
        int id = consultaView.lerIdConsulta();
        Consulta consulta = consultaController.buscarConsultaPorId(id);
        consultaView.exibirConsulta(consulta);
    }

    private void buscarConsultasPorPaciente() {
        int idPaciente = consultaView.lerIdPaciente();
        Usuario paciente = usuarioController.buscarUsuarioPorId(idPaciente);
        if (paciente != null) {
            List<Consulta> consultas = consultaController.buscarConsultasPorPaciente(paciente);
            consultaView.exibirConsultas(consultas);
        } else {
            consultaView.exibirMensagem("Paciente nao encontrado!");
        }
    }

    private void buscarConsultasPorData() {
        String data = consultaView.lerData();
        List<Consulta> consultas = consultaController.buscarConsultasPorData(data);
        consultaView.exibirConsultas(consultas);
    }

    private void atualizarConsulta() {
        int id = consultaView.lerIdConsulta();
        consultaView.exibirListaPacientes(usuarioController.listarUsuarios());
        int idPaciente = consultaView.lerIdPaciente();
        Usuario paciente = usuarioController.buscarUsuarioPorId(idPaciente);
        
        if (paciente != null) {
            String data = consultaView.lerData();
            String hora = consultaView.lerHora();
            boolean sucesso = consultaController.atualizarConsulta(id, data, hora, paciente);
            if (sucesso) {
                consultaView.exibirMensagemSucesso();
            } else {
                consultaView.exibirMensagemErro();
            }
        } else {
            consultaView.exibirMensagem("Paciente nao encontrado!");
        }
    }

    private void cancelarConsulta() {
        int id = consultaView.lerIdConsulta();
        boolean sucesso = consultaController.cancelarConsulta(id);
        if (sucesso) {
            consultaView.exibirMensagemSucesso();
        } else {
            consultaView.exibirMensagemErro();
        }
    }

    private void confirmarConsulta() {
        int id = consultaView.lerIdConsulta();
        boolean sucesso = consultaController.confirmarConsulta(id);
        if (sucesso) {
            consultaView.exibirMensagemSucesso();
        } else {
            consultaView.exibirMensagemErro();
        }
    }

    private void reagendarConsulta() {
        int id = consultaView.lerIdConsulta();
        String novaData = consultaView.lerNovaData();
        String novaHora = consultaView.lerNovaHora();
        boolean sucesso = consultaController.reagendarConsulta(id, novaData, novaHora);
        if (sucesso) {
            consultaView.exibirMensagemSucesso();
        } else {
            consultaView.exibirMensagemErro();
        }
    }

    private void deletarConsulta() {
        int id = consultaView.lerIdConsulta();
        if (consultaView.confirmarExclusao()) {
            boolean sucesso = consultaController.deletarConsulta(id);
            if (sucesso) {
                consultaView.exibirMensagemSucesso();
            } else {
                consultaView.exibirMensagemErro();
            }
        }
    }

    private void exibirEstatisticas() {
        System.out.println("\n=== ESTATISTICAS DO SISTEMA ===");
        System.out.println("Total de Pacientes: " + usuarioController.getTotalUsuarios());
        System.out.println("Total de Enderecos: " + enderecoController.getTotalEnderecos());
        System.out.println("Total de Consultas: " + consultaController.getTotalConsultas());
        
        List<Consulta> consultas = consultaController.listarConsultas();
        long agendadas = consultas.stream().filter(c -> c.getStatus() == Consulta.StatusConsulta.AGENDADA).count();
        long confirmadas = consultas.stream().filter(c -> c.getStatus() == Consulta.StatusConsulta.CONFIRMADA).count();
        long canceladas = consultas.stream().filter(c -> c.getStatus() == Consulta.StatusConsulta.CANCELADA).count();
        long reagendadas = consultas.stream().filter(c -> c.getStatus() == Consulta.StatusConsulta.REAGENDADA).count();
        
        System.out.println("\n--- Status das Consultas ---");
        System.out.println("Agendadas: " + agendadas);
        System.out.println("Confirmadas: " + confirmadas);
        System.out.println("Canceladas: " + canceladas);
        System.out.println("Reagendadas: " + reagendadas);
        
        consultaView.aguardarEnter();
    }
}
