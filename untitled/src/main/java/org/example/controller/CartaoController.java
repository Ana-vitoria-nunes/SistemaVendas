package org.example.controller;

import org.example.controller.validacao.ValidarCartao;
import org.example.controller.validacao.ValidarData;
import org.example.controller.validacao.ValidarUser;

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

        String numeroCartaoFormatado = numeroCartao.replaceAll("[^0-9]", "");
        if (!validarCartao.validarCamposObrigatorios(nomeRemetente,numeroCartaoFormatado,cvvCartao,dataDevalidade,limiteCartao)){
            System.out.println("Todos os campos do cartão deve estar preenchidoa!");
            return;
        }
        if (!validarUser.validarUser(idCliente)){
            System.out.println("Esse cliente não existe no banco!");
            return;
        }

        if (!validarCartao.validarNumeroCartaoCredito(numeroCartaoFormatado)) {
            System.out.println("Está faltando dígitos no numero do cartão");
            return;
        }

        if (validarCartao.validarNumeroCartaoLuhn(numeroCartaoFormatado)) {
            System.out.println("Número do cartão é invalido");
            return;
        }
        if (!validarCartao.verificarDataVencimentoCartao(dataDevalidade)) {
            System.out.println("Data de expiração do cartão é invalido");
            return;
        }
        if (!validarCartao.verificarCodigoSegurancaCartao(cvvCartao)){
            System.out.println("CVV inválido");
            return;

        }

        String sql = "INSERT INTO cartao (idcliente, nomeRemetente, NumeroCartao, cvv, dataValidade, limiteCartao) " +
                "VALUES (" + idCliente+ ", '" + nomeRemetente + "', '" + numeroCartaoFormatado + "', '" + cvvCartao + "', '" + dataDevalidade + "', " + limiteCartao + ")";
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
