package org.example.controller.validação;

import org.example.connection.Connect;
import org.example.controller.excecao.MaioDeIdade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

public class ValidarUser {

    private final Connection connection = Connect.fazerConexao();
    public boolean validarUserCredenciais(String email, String senha) {
        String sql = "SELECT COUNT(*) FROM \"user\" WHERE email = ? AND senha = ?";

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
        String sql = "SELECT COUNT(*) FROM \"user\" WHERE id=?";

        try {
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Math.toIntExact(id)); // caso der algum bug pode ser erro
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
    public boolean validarCamposObrigatoriosUser(String nomeCompleto, String email, String senha, String cpf, String endereco, String telefone) {
        return nomeCompleto != null && !nomeCompleto.isEmpty() &&

                email != null && !email.isEmpty() &&
                senha != null && !senha.isEmpty() &&
                cpf != null && !cpf.isEmpty() &&
                endereco != null && !endereco.isEmpty() &&
                telefone != null && !telefone.isEmpty();
        // lembrar que talvez a excessão ja esteja validando
         }

         //método não testado
         public static void validarDataNascimento (LocalDate dataNascimento) throws MaioDeIdade {
            LocalDate dataHoje = LocalDate.now();

             Period period = Period.between(dataNascimento, dataHoje);
             if (period.getYears() <=18) {
                 throw new MaioDeIdade("Você precisa ser maior de idade");
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
}
