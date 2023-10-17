package org.example.controller;

import org.example.controller.validacao.ValidarCartao;
import org.example.controller.validacao.ValidarPagameto;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;

import static org.example.connection.Connect.fazerConexao;

public class PagamentoController {
    private Statement statement;
    private ValidarPagameto validarPagameto = new ValidarPagameto();
    private ValidarCartao validarCartao = new ValidarCartao();


    public PagamentoController() {
        try {
            statement = fazerConexao().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void adicionarPagamento(Long id_cartao, int parcelas, BigDecimal valorTotalParcela, BigDecimal valorTotalEmprestimo, String status) {
        if (!validarPagameto.validarCamposObrigatoriosPagamento(id_cartao,parcelas,valorTotalParcela,valorTotalEmprestimo,status)){
            System.out.println("Todos os campos de pagamento devem ser preenchidos!");
            return;
        }
        if (!validarCartao.validarCartao(id_cartao)){
            System.out.println("Id do cartão inválido!");
            return;
        }
        String sql = "INSERT INTO pagamento (idcartao, parcelas, valorTotalParcela, valorTotalEmprestimo, status) " +
                "VALUES (" + id_cartao + ", " + parcelas + ", " +
                valorTotalParcela + ", " + valorTotalEmprestimo + ", '" +
                status + "')";
        try {
            statement.executeUpdate(sql);
            System.out.println("Pagamento adicionado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

