package org.example.controller;

import org.example.controller.validação.ValidarCartao;
import org.example.controller.validação.ValidarData;
import org.example.controller.validação.ValidarUser;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Objects;

import static org.example.connection.Connect.fazerConexao;

public class CartaoController {
    private Statement statement;
    ValidarCartao validarCartao=new ValidarCartao();
    ValidarUser validarUser=new ValidarUser();
    ValidarData validarData=new ValidarData();
    public CartaoController() {
        try {
            statement = Objects.requireNonNull(fazerConexao()).createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void adicionarCartao(Long idCliente, String nomeRemetente, String numeroCartao, String cvvCartao, Date dataDevalidade, BigDecimal limiteCartao) {

        if (!validarCartao.validarCamposObrigatorios(nomeRemetente,numeroCartao,cvvCartao,dataDevalidade,limiteCartao)){
            System.out.println("Todos os campos do cartão deve estar preenchidoa!");
            return;
        }

        if (!validarUser.validarUser(idCliente)){
            System.out.println("Esse cliente não existe no banco!");
            return;
        }

        if (validarCartao.validarNumeroCartao(numeroCartao)) {
            System.out.println("Número de cartão inválido!");
            return;
        }

        if (validarData.validarDataValidade(dataDevalidade)){
            System.out.println("Data do cartão é inválida!");
            return;

        }

        String sql = "INSERT INTO cartao (idcliente, nomeRemetente, NumeroCartao, cvv, dataValidade, limiteCartao) " +
                "VALUES (" + idCliente+ ", '" + nomeRemetente + "', '" + numeroCartao + "', '" + cvvCartao + "', '" + dataDevalidade + "', " + limiteCartao + ")";
        try {
            statement.executeUpdate(sql);
            System.out.println("Cartão adicionado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deletarCartao(Long cartaoId) {
        String sql = "DELETE FROM cartao WHERE id = " + cartaoId;
        try {
            int rowsAffected = statement.executeUpdate(sql);
            if (rowsAffected > 0) {
                System.out.println("Cartão excluído com sucesso!");
            } else {
                System.out.println("Nenhum cartão encontrado com o ID especificado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
