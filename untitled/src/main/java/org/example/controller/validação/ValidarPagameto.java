package org.example.controller.validação;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ValidarPagameto {
    public boolean validarCamposObrigatoriosPagamento(Long idCartao, Integer parcelas, BigDecimal valorTotalParcela, BigDecimal valorTotalEmprestimo, String status) {
        return idCartao != null && parcelas != null && valorTotalParcela != null && valorTotalEmprestimo != null && status != null;
    }
}
