package org.example.controller;

import org.example.connection.Connect;
import org.example.controller.validação.ValidarEmail;
import org.example.controller.validação.ValidarUser;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.Date;

public class ClienteController {
    private Statement statement;
    private ValidarUser validarUser =new ValidarUser();
    private ValidarEmail validarEmail =new ValidarEmail();

    private final Connection connection = Connect.fazerConexao();


    public ClienteController() {
        try {
            statement = Connect.fazerConexao().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void adicionarUsuario(String nomeCompleto, LocalDate dataNascimento, String email, String senha, String cpf, String endereco, String telefone) {

        if (!validarUser.validarCamposObrigatoriosUser(nomeCompleto,email,senha,cpf,endereco,telefone)){
            System.out.println("Todos os campos do cliente devem ser preenchidos!");
            return;
        }
        validarUser.validarDataNascimento(dataNascimento);

        if (!validarEmail.validarEmail(email)) {
            System.out.println("O e-mail precisa conter @ e o (gmail.com)");
            return;
        }
        String sql = "INSERT INTO \"user\" (nomeCompleto,dataNascimento ,email, senha, cpf, endereco, telefone) " +
                "VALUES ('" + nomeCompleto + "', '" + dataNascimento+"', '" + email + "', '" + senha +
                "', '" + cpf + "', '" + endereco + "', '" + telefone + "')";
        try {
            statement.executeUpdate(sql);
            System.out.println("Usuário adicionado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarUsuario(Long id, String email, String senha, String endereco, String telefone) {
        if (validarEmail.validarEmail(email)) {
            System.out.println("O e-mail precisa conter @ e o (gmail.com)");
        }
        String sql = "UPDATE user SET email = '" + email + "', senha = '" + senha + "', endereco = '" + endereco + "', telefone = '" + telefone + "' WHERE id = " + id;
        try {
            statement.executeUpdate(sql);
            System.out.println("Usuário atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String cartaoInfoByEmail(Long idCliente) {
        String sql = "SELECT id, nomeremetente, numerocartao, cvv, dataValidade, limitecartao FROM cartao WHERE idcliente = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idCliente);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String nome = resultSet.getString("nomeremetente");
                String numero = resultSet.getString("numerocartao");
                String cvv = resultSet.getString("cvv");
                Date data = resultSet.getDate("datavalidade");
                BigDecimal limite = resultSet.getBigDecimal("limitecartao");
                System.out.println("\nSeus cartões:\n ID: " + id + " |Nome Remetente: " + nome + " |Nº cartão: " + numero + " |CVV: " + cvv + " |Limite: " + limite);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public Long getClienteId(String email) {
        try {
            String sql = "SELECT id FROM \"user\" WHERE email =?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("id");
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Long.valueOf("Erro ao buscar id do cliente");
        }
    }


    public String userInfoByAlias(String email) {
        String sql = "SELECT id, nomecompleto, cpf FROM \"user\" WHERE email=?";

        String nome = "";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                nome = resultSet.getString("nomecompleto");
                String cpf = resultSet.getString("cpf");
                System.out.println("Informações da Conta:\n ID: " + id + " | CPF: " + cpf + " | Nome: " + nome + " | Email: " + email);
            } else {
                System.out.println("Usuário com o nome " + nome + " não encontrado.");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }





}

