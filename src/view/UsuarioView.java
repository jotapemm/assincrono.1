package view;

import java.util.List;
import java.util.Scanner;

import model.Endereco;
import model.Usuario;

public class UsuarioView {
    private Scanner scanner;

    public UsuarioView() {
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenuUsuario() {
        System.out.println("\n=== MENU PACIENTES ===");
        System.out.println("1. Cadastrar Paciente");
        System.out.println("2. Listar Pacientes");
        System.out.println("3. Buscar Paciente por ID");
        System.out.println("4. Buscar Paciente por CPF");
        System.out.println("5. Atualizar Paciente");
        System.out.println("6. Deletar Paciente");
        System.out.println("0. Voltar ao Menu Principal");
        System.out.print("Escolha uma opcao: ");
    }

    public Usuario lerDadosUsuario() {
        System.out.println("\n=== CADASTRAR PACIENTE ===");
        
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        
        System.out.println("\n--- Dados do Endereco ---");
        System.out.print("Estado: ");
        String estado = scanner.nextLine();
        
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        
        System.out.print("Rua: ");
        String rua = scanner.nextLine();
        
        System.out.print("Numero: ");
        String numero = scanner.nextLine();
        
        System.out.print("CEP: ");
        String cep = scanner.nextLine();
        
        Endereco endereco = new Endereco(estado, cidade, rua, numero, cep);
        return new Usuario(nome, cpf, email, telefone, endereco);
    }

    public void exibirUsuarios(List<Usuario> usuarios) {
        System.out.println("\n=== LISTA DE PACIENTES ===");
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
        } else {
            for (Usuario usuario : usuarios) {
                System.out.println("----------------------------------------");
                System.out.println(usuario.toString());
                System.out.println("----------------------------------------");
            }
        }
    }

    public void exibirUsuario(Usuario usuario) {
        if (usuario != null) {
            System.out.println("\n=== DADOS DO PACIENTE ===");
            System.out.println(usuario.toString());
        } else {
            System.out.println("Paciente nao encontrado.");
        }
    }

    public int lerIdUsuario() {
        System.out.print("Digite o ID do paciente: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID invalido!");
            return -1;
        }
    }

    public String lerCpfUsuario() {
        System.out.print("Digite o CPF do paciente: ");
        return scanner.nextLine();
    }

    public Usuario lerDadosAtualizacaoUsuario() {
        System.out.println("\n=== ATUALIZAR PACIENTE ===");
        
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        
        System.out.println("\n--- Dados do Endereco ---");
        System.out.print("Estado: ");
        String estado = scanner.nextLine();
        
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        
        System.out.print("Rua: ");
        String rua = scanner.nextLine();
        
        System.out.print("Numero: ");
        String numero = scanner.nextLine();
        
        System.out.print("CEP: ");
        String cep = scanner.nextLine();
        
        Endereco endereco = new Endereco(estado, cidade, rua, numero, cep);
        return new Usuario(nome, cpf, email, telefone, endereco);
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
}
