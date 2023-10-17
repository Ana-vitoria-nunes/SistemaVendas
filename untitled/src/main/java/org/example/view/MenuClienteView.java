package org.example.view;

import org.example.controller.CartaoController;
import org.example.controller.ClienteController;
import org.example.controller.PagamentoController;
import org.example.controller.validacao.ValidarCartao;
import org.example.controller.validacao.ValidarEmail;
import org.example.controller.validacao.ValidarUser;
import org.example.model.CartaoModel;
import org.example.model.ClienteModel;
import org.example.model.InputUser;
import org.example.model.PagamentoModel;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class MenuClienteView {
    InputUser inputUser=new InputUser();
    CartaoController cartaoController=new CartaoController();
    CartaoModel cartaoModel=new CartaoModel();
    Scanner scanner=new Scanner(System.in);
    ValidarUser validarUser=new ValidarUser();
    ValidarCartao validarCartao=new ValidarCartao();
    PagamentoController pagamentoController =new PagamentoController();
    PagamentoModel pagamentoModel=new PagamentoModel();
    ClienteController clienteController=new ClienteController();
    ValidarEmail validarEmail=new ValidarEmail();
    ClienteModel clienteModel=new ClienteModel();
    Long id;

    public void menuLogar() {
        System.out.println("0 - Sair.");
        System.out.println("1 - Cadastra seu cartão.");
        System.out.println("2 - Efetuar um emprestimo.");
    }
    public void logar() {
        String email = inputUser.readStringFromUser("Digite seu e-mail:");
        String senha = inputUser.readStringFromUser("Digite sua senha:");

        if (validarUser.validarUserCredenciais(email, senha)) {
            System.out.println("========== Bem-Vindo(a) ao Deixa que eu pago ==========");
            System.out.println(validarUser.userInfoByAlias(email));
            id=validarUser.getClienteId(email);
            int option;
            do {
                menuLogar();
                option = inputUser.readIntFromUser("Qual opção você deseja: ");

                switch (option) {
                    case 0 -> System.out.println("Saindo do sistema...");
                    case 1 -> cadastrarCartao();
                    case 2 -> efetuarEmprestimo();
                    default -> System.out.println("Opção inválida, tente novamente!");
                }
            } while (option != 0);

        } else {
            System.out.println("E-mail ou senha inválidos!");
        }

    }
    public void cadastrarCartao(){
        System.out.println("Cadastrar Cartão");
        int idCliente =inputUser.readIntFromUser("ID do Cliente:");
        String nomeRemetente = inputUser.readStringFromUser("Nome do Remetente: ");
        String numeroCartao = inputUser.readStringFromUser("Número do Cartão: ");
        String cvvCartao = inputUser.readStringFromUser("CVV do Cartão: ");
        String dataValidadeStr =inputUser.readStringFromUser("Data de Validade (MM/yyyy): ");

        SimpleDateFormat dateFormatInput = new SimpleDateFormat("MM/yyyy");
        SimpleDateFormat dateFormatOutput = new SimpleDateFormat("yyyy-MM");
        Date dataDeValidade;

        try {
            dataDeValidade = dateFormatOutput.parse(dateFormatOutput.format(dateFormatInput.parse(dataValidadeStr)));
        } catch (ParseException e) {
            System.out.println("Formato de data de validade inválido. Use MM/yyyy.");
            return;
        }

        System.out.print("Limite do Cartão: ");
        BigDecimal limiteCartao = scanner.nextBigDecimal();


        cartaoModel.setIdCliente((long) idCliente);
        cartaoModel.setNomeRemetente(nomeRemetente);
        cartaoModel.setNumeroCartao(numeroCartao);
        cartaoModel.setCvv(cvvCartao);
        cartaoModel.setDataValidade(dataDeValidade);
        cartaoModel.setLimiteCartao(limiteCartao);
        cartaoController.adicionarCartao(cartaoModel.getIdCliente(),cartaoModel.getNomeRemetente(),
                cartaoModel.getNumeroCartao(),cartaoModel.getCvv(),cartaoModel.getDataValidade(),cartaoModel.getLimiteCartao());
    }
    public void  efetuarEmprestimo(){
        System.out.println("Qual o valor da sua divida:");
        BigDecimal valorDivida =scanner.nextBigDecimal();
        menuParcelas();
        double juros = 0;
        int parcela=0;

        int opc = inputUser.readIntFromUser("Qual o número de Parcelas");

        switch (opc) {
            case 1 -> {
                juros = 0.05;
                parcela=2;
            }
            case 2 -> {
                juros = 0.07;
                parcela=4;
            }
            case 3 ->{
                juros = 0.09;
                parcela=6;
            }
            case 4 -> {
                juros = 0.11;
                parcela=8;
            }
            case 5 -> {
                juros = 0.13;
                parcela=10;
            }
            case 6 -> {
                juros = 0.15;
                parcela=12;
            }
            default -> System.out.println("Opção inválida. Escolha uma opção válida.");
        }
        System.out.println("Você escolheu a opção " + opc + " e tera um acrecimo do juros de " + (juros * 100) + "%.");

        System.out.println("Fazer Pagamento");
        System.out.println(validarCartao.cartaoInfoByEmail(id));
        int idCartao =inputUser.readIntFromUser("ID do Cartão: ");
        BigDecimal valorTotalEmprestimo = valorDivida.multiply(BigDecimal.ONE.add(BigDecimal.valueOf(juros)));

        // Calcula o valor total da parcela
        BigDecimal valorTotalParcela = valorTotalEmprestimo.divide(BigDecimal.valueOf(parcela), 2, BigDecimal.ROUND_HALF_UP);


        pagamentoModel.setIdCartao((long) idCartao);
        pagamentoModel.setParcelas(parcela);
        pagamentoModel.setValorTotalParcela(valorTotalParcela);
        pagamentoModel.setValorTotalEmprestimo(valorTotalEmprestimo);
        pagamentoModel.setStatus("Pago");

        pagamentoController.adicionarPagamento(pagamentoModel.getIdCartao(),pagamentoModel.getParcelas(),pagamentoModel.getValorTotalParcela(),
                pagamentoModel.getValorTotalEmprestimo(),pagamentoModel.getStatus());

    }
    public void menuParcelas() {
        System.out.println("Escolha o número de parcelas:");
        System.out.println("1 - Dividir em 2x");
        System.out.println("2 - Dividir em 4x");
        System.out.println("3 - Dividir em 6x");
        System.out.println("4 - Dividir em 8x");
        System.out.println("5 - Dividir em 10x");
        System.out.println("6 - Dividir em 12x");
        System.out.println("0 - Sair");

    }
    public void cadastrarCliente() {
        String nome = inputUser.readStringFromUser("Qual seu nome completo:");
        String dataNascimento = inputUser.readStringFromUser("Qual sua data de nascimento (DD/MM/yyyy)");
        String email = inputUser.readStringFromUser("Qual seu e-mail:");
        String senha = inputUser.readStringFromUser("Qual sua senha:");
        String cpf = inputUser.readStringFromUser("Qual seu CPF:");
        String endereco = inputUser.readStringFromUser("Qual seu endereço:");
        String telefone = inputUser.readStringFromUser("Qual seu telefone para contato:");

        LocalDate dataDeNasc = LocalDate.parse(dataNascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Date dataDeNascComoDate = java.sql.Date.valueOf(dataDeNasc);

        while (true) {
            if (validarEmail.validarEmail(email)) {
                clienteModel.setNomeCompleto(nome);
                clienteModel.setEmail(email);
                clienteModel.setSenha(senha);
                clienteModel.setCpf(cpf);
                clienteModel.setEndereco(endereco);
                clienteModel.setTelefone(telefone);
                clienteModel.setDataNascimento(dataDeNascComoDate);

                clienteController.adicionarUsuario(clienteModel.getNomeCompleto(),clienteModel.getDataNascimento(), clienteModel.getEmail(),
                        clienteModel.getSenha(), clienteModel.getCpf(), clienteModel.getEndereco(), clienteModel.getTelefone());

                // Saia do loop quando a entrada for válida
                break;
            } else {
                System.out.println("E-mail inválido. Digite um e-mail válido.");
                // Solicite novamente a entrada do usuário
                email = inputUser.readStringFromUser("Qual seu e-mail:");
            }
        }
    }
}
