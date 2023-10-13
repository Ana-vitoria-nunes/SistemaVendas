package org.example.controller.validacao;

import java.math.BigDecimal;

public class ValidarPagameto {
    public boolean validarCamposObrigatoriosPagamento(Long idCartao, Integer parcelas, BigDecimal valorTotalParcela, BigDecimal valorTotalEmprestimo, String status) {
        return idCartao != null && parcelas != null && valorTotalParcela != null && valorTotalEmprestimo != null && status != null;
    }
}
