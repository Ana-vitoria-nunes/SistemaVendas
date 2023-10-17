package org.example.controller.validacao;

import org.example.connection.Connect;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidarCartao {
    private final Connection connection = Connect.fazerConexao();

    // review do métoo validar numero cartão
    public boolean validarCartao(Long id) {
        String sql = "SELECT COUNT(*) FROM cartao WHERE idCartao=?";

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
    public String cartaoInfoByEmail(Long idCliente) {
        String sql = "SELECT idCartao, nomeremetente, numerocartao, cvv, dataValidade, limitecartao FROM cartao WHERE idcliente = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idCliente);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("idCartao");
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
    public boolean validarNumeroCartaoCredito(String numeroCartao) {
        numeroCartao = numeroCartao.replaceAll("[^0-9]", "");

        int numDigitos = numeroCartao.length();
        return numDigitos == 15 || numDigitos == 16;
    }
    public boolean validarNumeroCartaoLuhn(String numeroCartao) {
        String numeroCartaoFormatado = numeroCartao.replaceAll("[^0-9]", "");

        int sum = 0;
        boolean doubleDigit = false;

        for (int i = numeroCartaoFormatado.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(numeroCartaoFormatado.charAt(i));

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
    public boolean verificarDataVencimentoCartao(Date dataVencimento) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
            Date dataVencimentoDate = sdf.parse(sdf.format(dataVencimento));

            Date dataAtual = new Date();

            if (dataVencimentoDate.after(dataAtual)) {
                return true;
            }
        } catch (ParseException e) {
            e.getMessage();
        }

        return false;
    }
    public boolean verificarCodigoSegurancaCartao(String codigoSeguranca) {
        if (codigoSeguranca.length() == 3 || codigoSeguranca.length() == 4) {
            return codigoSeguranca.matches("\\d+");
        }
        return false;
    }

}
