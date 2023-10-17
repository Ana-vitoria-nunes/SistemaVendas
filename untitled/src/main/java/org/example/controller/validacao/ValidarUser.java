package org.example.controller.validacao;

import org.example.connection.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ValidarUser {

    private final Connection connection = Connect.fazerConexao();
    public boolean validarUserCredenciais(String email, String senha) {
        String sql = "SELECT COUNT(*) FROM cliente WHERE email = ? AND senha = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, senha);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            resultSet.close();
            preparedStatement.close();

            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean validarUser(Long id) {
        String sql = "SELECT COUNT(*) FROM cliente WHERE idCliente=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Math.toIntExact(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            resultSet.close();
            preparedStatement.close();

            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean validarCamposObrigatoriosUser(String nomeCompleto, String email, String senha, String cpf, String endereco,String telefone) {
        return nomeCompleto != null && !nomeCompleto.isEmpty() &&
                email != null && !email.isEmpty() &&
                senha != null && !senha.isEmpty() &&
                cpf != null && !cpf.isEmpty() &&
                endereco != null && !endereco.isEmpty() &&
                telefone != null && !telefone.isEmpty();
    }

         //método não testado
         public boolean validarDataNascimento(Date dataNascimento){
             Date dataHoje = new Date(); // Data atual

             // Calculando a idade
             long diff = dataHoje.getTime() - dataNascimento.getTime();
             long idadeEmMillis = diff / (1000L * 60 * 60 * 24 * 365); // Aproximação simples da idade em anos
             return idadeEmMillis < 18;
         }
    public String userInfoByAlias(String email) {
        String sql = "SELECT idCliente, nomecompleto, cpf FROM cliente WHERE email=?";

        String nome = "";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("idCliente");
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
    public Long getClienteId(String email) {
        try {
            String sql = "SELECT idCliente FROM cliente WHERE email =?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("idCliente");
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Long.valueOf("Erro ao buscar id do cliente");
        }
    }
}
