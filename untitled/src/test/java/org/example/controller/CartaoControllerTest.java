package org.example.controller;

import org.example.controller.validacao.ValidarCartao;
import org.example.controller.validacao.ValidarUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartaoControllerTest {

    @InjectMocks
    private CartaoController cartaoController;
    @Mock
    private Statement statement;
    private final ValidarUser validarUser = Mockito.mock(ValidarUser.class);
    private final ValidarCartao validarCartao = Mockito.mock(ValidarCartao.class);

    @Test
    void adicionarCartaoComSucesso() throws ParseException, SQLException {

        when(validarCartao.validarCamposObrigatorios(anyString(), anyString(), anyString(), any(), any())).thenReturn(true);
        when(validarUser.validarUser(anyLong())).thenReturn(true);
        when(validarCartao.validarNumeroCartaoCredito(anyString())).thenReturn(true);
        when(validarCartao.validarNumeroCartaoLuhn(anyString())).thenReturn(true);
        when(validarCartao.verificarDataVencimentoCartao(any())).thenReturn(true);
        when(validarCartao.verificarCodigoSegurancaCartao(anyString())).thenReturn(true);
        String dataValidadeStr = "05/2031";
        SimpleDateFormat dateFormatInput = new SimpleDateFormat("MM/yyyy");
        SimpleDateFormat dateFormatOutput = new SimpleDateFormat("yyyy-MM");
        Date dataDeValidade;

        dataDeValidade = dateFormatOutput.parse(dateFormatOutput.format(dateFormatInput.parse(dataValidadeStr)));

        when(statement.executeUpdate(anyString())).thenReturn(1);
        cartaoController.adicionarCartao(1L, "Ana", "5227314759872931", "123", dataDeValidade, BigDecimal.valueOf(1500));

    }
    @Test
    void adicionarCartaoComCredenciaisVazia() throws ParseException{

        when(validarCartao.validarCamposObrigatorios(anyString(), anyString(), anyString(), any(), any())).thenReturn(false);
        when(validarUser.validarUser(anyLong())).thenReturn(true);
        when(validarCartao.validarNumeroCartaoCredito(anyString())).thenReturn(true);
        when(validarCartao.validarNumeroCartaoLuhn(anyString())).thenReturn(true);
        when(validarCartao.verificarDataVencimentoCartao(any())).thenReturn(true);
        when(validarCartao.verificarCodigoSegurancaCartao(anyString())).thenReturn(true);
        String dataValidadeStr = "05/2031";
        SimpleDateFormat dateFormatInput = new SimpleDateFormat("MM/yyyy");
        SimpleDateFormat dateFormatOutput = new SimpleDateFormat("yyyy-MM");
        Date dataDeValidade;
        dataDeValidade = dateFormatOutput.parse(dateFormatOutput.format(dateFormatInput.parse(dataValidadeStr)));

        cartaoController.adicionarCartao(1L, "Ana", "5227314759872931", "123", dataDeValidade, BigDecimal.valueOf(1500));

    }
    @Test
    void adicionarCartaoComIdClienteInvalido() throws ParseException {

        when(validarCartao.validarCamposObrigatorios(anyString(), anyString(), anyString(), any(), any())).thenReturn(true);
        when(validarUser.validarUser(anyLong())).thenReturn(false);
        when(validarCartao.validarNumeroCartaoCredito(anyString())).thenReturn(true);
        when(validarCartao.validarNumeroCartaoLuhn(anyString())).thenReturn(true);
        when(validarCartao.verificarDataVencimentoCartao(any())).thenReturn(true);
        when(validarCartao.verificarCodigoSegurancaCartao(anyString())).thenReturn(true);
        String dataValidadeStr = "05/2031";
        SimpleDateFormat dateFormatInput = new SimpleDateFormat("MM/yyyy");
        SimpleDateFormat dateFormatOutput = new SimpleDateFormat("yyyy-MM");
        Date dataDeValidade;

        dataDeValidade = dateFormatOutput.parse(dateFormatOutput.format(dateFormatInput.parse(dataValidadeStr)));

        cartaoController.adicionarCartao(1L, "Ana", "5227314759872931", "123", dataDeValidade, BigDecimal.valueOf(1500));

    }
    @Test
    void adicionarCartaoComNumeroInsuficiente() throws ParseException {

        when(validarCartao.validarCamposObrigatorios(anyString(), anyString(), anyString(), any(), any())).thenReturn(true);
        when(validarUser.validarUser(anyLong())).thenReturn(true);
        when(validarCartao.validarNumeroCartaoCredito(anyString())).thenReturn(false);
        when(validarCartao.validarNumeroCartaoLuhn(anyString())).thenReturn(true);
        when(validarCartao.verificarDataVencimentoCartao(any())).thenReturn(true);
        when(validarCartao.verificarCodigoSegurancaCartao(anyString())).thenReturn(true);
        String dataValidadeStr = "05/2031";
        SimpleDateFormat dateFormatInput = new SimpleDateFormat("MM/yyyy");
        SimpleDateFormat dateFormatOutput = new SimpleDateFormat("yyyy-MM");
        Date dataDeValidade;

        dataDeValidade = dateFormatOutput.parse(dateFormatOutput.format(dateFormatInput.parse(dataValidadeStr)));

        cartaoController.adicionarCartao(1L, "Ana", "5227314759872931", "123", dataDeValidade, BigDecimal.valueOf(1500));

    }
    @Test
    void adicionarCartaoComNumeroInvalido() throws ParseException {

        when(validarCartao.validarCamposObrigatorios(anyString(), anyString(), anyString(), any(), any())).thenReturn(true);
        when(validarUser.validarUser(anyLong())).thenReturn(true);
        when(validarCartao.validarNumeroCartaoCredito(anyString())).thenReturn(true);
        when(validarCartao.validarNumeroCartaoLuhn(anyString())).thenReturn(false);
        when(validarCartao.verificarDataVencimentoCartao(any())).thenReturn(true);
        when(validarCartao.verificarCodigoSegurancaCartao(anyString())).thenReturn(true);
        String dataValidadeStr = "05/2031";
        SimpleDateFormat dateFormatInput = new SimpleDateFormat("MM/yyyy");
        SimpleDateFormat dateFormatOutput = new SimpleDateFormat("yyyy-MM");
        Date dataDeValidade;

        dataDeValidade = dateFormatOutput.parse(dateFormatOutput.format(dateFormatInput.parse(dataValidadeStr)));

        cartaoController.adicionarCartao(1L, "Ana", "5227314759872931", "123", dataDeValidade, BigDecimal.valueOf(1500));

    }
    @Test
    void adicionarCartaoComDataDeExpiracaoInvalida() throws ParseException {

        when(validarCartao.validarCamposObrigatorios(anyString(), anyString(), anyString(), any(), any())).thenReturn(true);
        when(validarUser.validarUser(anyLong())).thenReturn(true);
        when(validarCartao.validarNumeroCartaoCredito(anyString())).thenReturn(true);
        when(validarCartao.validarNumeroCartaoLuhn(anyString())).thenReturn(true);
        when(validarCartao.verificarDataVencimentoCartao(any())).thenReturn(false);
        when(validarCartao.verificarCodigoSegurancaCartao(anyString())).thenReturn(true);
        String dataValidadeStr = "05/2031";
        SimpleDateFormat dateFormatInput = new SimpleDateFormat("MM/yyyy");
        SimpleDateFormat dateFormatOutput = new SimpleDateFormat("yyyy-MM");
        Date dataDeValidade;

        dataDeValidade = dateFormatOutput.parse(dateFormatOutput.format(dateFormatInput.parse(dataValidadeStr)));

        cartaoController.adicionarCartao(1L, "Ana", "5227314759872931", "123", dataDeValidade, BigDecimal.valueOf(1500));

    }
    @Test
    void adicionarCartaoComCvvInalido() throws ParseException {

        when(validarCartao.validarCamposObrigatorios(anyString(), anyString(), anyString(), any(), any())).thenReturn(true);
        when(validarUser.validarUser(anyLong())).thenReturn(true);
        when(validarCartao.validarNumeroCartaoCredito(anyString())).thenReturn(true);
        when(validarCartao.validarNumeroCartaoLuhn(anyString())).thenReturn(true);
        when(validarCartao.verificarDataVencimentoCartao(any())).thenReturn(true);
        when(validarCartao.verificarCodigoSegurancaCartao(anyString())).thenReturn(false);
        String dataValidadeStr = "05/2031";
        SimpleDateFormat dateFormatInput = new SimpleDateFormat("MM/yyyy");
        SimpleDateFormat dateFormatOutput = new SimpleDateFormat("yyyy-MM");
        Date dataDeValidade;

        dataDeValidade = dateFormatOutput.parse(dateFormatOutput.format(dateFormatInput.parse(dataValidadeStr)));

        cartaoController.adicionarCartao(1L, "Ana", "5227314759872931", "123", dataDeValidade, BigDecimal.valueOf(1500));

    }
    @Test
    void deletarCartaoComSucesso() throws SQLException {
        when(validarCartao.validarCartao(anyLong())).thenReturn(true);

        when(statement.executeUpdate(anyString())).thenReturn(1);
        cartaoController.deletarCartao(1L);


    }
    @Test
    void deletarCartaoComIdInvalido() throws SQLException {
        when(validarCartao.validarCartao(anyLong())).thenReturn(false);

        when(statement.executeUpdate(anyString())).thenReturn(0);
        cartaoController.deletarCartao(1L);


    }
}