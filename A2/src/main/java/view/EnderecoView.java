package view;

import java.util.List;
import java.util.Scanner;

import model.Endereco;

public class EnderecoView {
    private Scanner scanner;

    public EnderecoView() {
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenuEndereco() {
        System.out.println("\n=== MENU ENDERECOS ===");
        System.out.println("1. Cadastrar Endereco");
        System.out.println("2. Listar Enderecos");
        System.out.println("3. Buscar Endereco por ID");
        System.out.println("4. Buscar Enderecos por Cidade");
        System.out.println("5. Buscar Enderecos por Estado");
        System.out.println("6. Atualizar Endereco");
        System.out.println("7. Deletar Endereco");
        System.out.println("0. Voltar ao Menu Principal");
        System.out.print("Escolha uma opcao: ");
    }

    public Endereco lerDadosEndereco() {
        System.out.println("\n=== CADASTRAR ENDERECO ===");
        
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
        
        return new Endereco(estado, cidade, rua, numero, cep);
    }

    public void exibirEnderecos(List<Endereco> enderecos) {
        System.out.println("\n=== LISTA DE ENDERECOS ===");
        if (enderecos.isEmpty()) {
            System.out.println("Nenhum endereco cadastrado.");
        } else {
            for (Endereco endereco : enderecos) {
                System.out.println("----------------------------------------");
                System.out.println("ID: " + endereco.getId());
                System.out.println(endereco.toString());
                System.out.println("----------------------------------------");
            }
        }
    }

    public void exibirEndereco(Endereco endereco) {
        if (endereco != null) {
            System.out.println("\n=== DADOS DO ENDERECO ===");
            System.out.println("ID: " + endereco.getId());
            System.out.println(endereco.toString());
        } else {
            System.out.println("Endereco nao encontrado.");
        }
    }

    public int lerIdEndereco() {
        System.out.print("Digite o ID do endereco: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID invalido!");
            return -1;
        }
    }

    public String lerCidade() {
        System.out.print("Digite a cidade: ");
        return scanner.nextLine();
    }

    public String lerEstado() {
        System.out.print("Digite o estado: ");
        return scanner.nextLine();
    }

    public Endereco lerDadosAtualizacaoEndereco() {
        System.out.println("\n=== ATUALIZAR ENDERECO ===");
        
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
        
        return new Endereco(estado, cidade, rua, numero, cep);
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
