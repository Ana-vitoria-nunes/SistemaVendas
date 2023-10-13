package org.example.controller.validação;

import org.example.connection.Connect;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ValidarCartao {
    private final Connection connection = Connect.fazerConexao();

    // review do métoo validar numero cartão
    public boolean validarNumeroCartao(String numeroCartao) {
    // Remover espaços em branco e traços (caso presentes) do número do cartão.
    numeroCartao = numeroCartao.replaceAll("[ -]", "");

    if (numeroCartao.length() != 16) {
        return false;  // O número do cartão deve ter exatamente 16 dígitos.
    }

    int sum = 0;
    boolean doubleDigit = false;

    for (int i = numeroCartao.length() - 1; i >= 0; i--) {
        int digit = Character.getNumericValue(numeroCartao.charAt(i));

        if (doubleDigit) {
            digit *= 2;
            if (digit > 9) {
                digit -= 9;
            }
        }

        sum += digit;
        doubleDigit = !doubleDigit;
    }

    return sum % 10 == 0;
}
    public boolean validarCartao(Long id) {
        String sql = "SELECT COUNT(*) FROM cartao WHERE id=?";

        try {
            assert connection != null;
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
    public boolean validarCamposObrigatorios(String nomeRemetente, String numeroCartao, String cvv, Date dataValidade, BigDecimal limiteCartao) {
        return nomeRemetente != null && !nomeRemetente.isEmpty() &&
                numeroCartao != null && !numeroCartao.isEmpty() &&
                cvv != null && !cvv.isEmpty() &&
                dataValidade != null && limiteCartao != null;
    }
    public boolean validarLimitesValores(BigDecimal limiteCartao, BigDecimal valorTotalParcela) {
        if (limiteCartao != null && valorTotalParcela != null) {
            return limiteCartao.compareTo(valorTotalParcela) >= 0;
        } else {
            return false;
        }
    }




}
