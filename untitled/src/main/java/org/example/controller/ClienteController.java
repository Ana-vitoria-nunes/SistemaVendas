package org.example.controller;

import org.example.connection.Connect;
import org.example.controller.validacao.ValidarEmail;
import org.example.controller.validacao.ValidarUser;

import java.sql.*;
import java.util.Date;

public class ClienteController {
    private Statement statement;
    private final ValidarUser validarUser =new ValidarUser();
    private final ValidarEmail validarEmail =new ValidarEmail();

    private final Connection connection = Connect.fazerConexao();


    public ClienteController() {
        try {
            statement = Connect.fazerConexao().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void adicionarUsuario(String nomeCompleto, Date dataNascimento, String email, String senha, String cpf, String endereco, String telefone)  {

        if (!validarUser.validarCamposObrigatoriosUser(nomeCompleto,email,senha,cpf,endereco,telefone)){
            System.out.println("Todos os campos do cliente devem ser preenchidos!");
            return;
        }
      if (ValidarUser.validarDataNascimento(dataNascimento)){
          System.out.println("Você precisa ser maior de idade");
          return;
      }

        if (!validarEmail.validarEmail(email)) {
            System.out.println("O e-mail precisa conter @ e o (gmail.com)");
            return;
        }
        String sql = "INSERT INTO cliente (nomeCompleto,dataNascimento ,email, senha, cpf, endereco, telefone) " +
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
        String sql = "UPDATE cliente SET email = '" + email + "', senha = '" + senha + "', endereco = '" + endereco + "', telefone = '" + telefone + "' WHERE idCliente = " + id;
        try {
            statement.executeUpdate(sql);
            System.out.println("Usuário atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

