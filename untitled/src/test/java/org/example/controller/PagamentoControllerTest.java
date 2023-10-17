package org.example.controller;

import org.example.controller.validacao.ValidarCartao;
import org.example.controller.validacao.ValidarPagameto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagamentoControllerTest {

    @InjectMocks
    private PagamentoController pagamentoController;
    private final ValidarPagameto validarPagameto = Mockito.mock(ValidarPagameto.class);
    private final ValidarCartao validarCartao = Mockito.mock(ValidarCartao.class);
    @Test
    void adicionarPagamentoComSucesso() {

        when(validarPagameto.validarCamposObrigatoriosPagamento(anyLong(),anyInt(),any(),any(),anyString())).thenReturn(true);
        when(validarCartao.validarCartao(anyLong())).thenReturn(true);

        pagamentoController.adicionarPagamento(2L,4, BigDecimal.valueOf(53.5),BigDecimal.valueOf(214),"Pago");
    }
    @Test
    void adicionarPagamentoComCredenciaisVazia() {

        when(validarPagameto.validarCamposObrigatoriosPagamento(anyLong(),anyInt(),any(),any(),anyString())).thenReturn(false);
        when(validarCartao.validarCartao(anyLong())).thenReturn(true);

        pagamentoController.adicionarPagamento(2L,4, BigDecimal.valueOf(53.5),BigDecimal.valueOf(214),"Pago");
    }
    @Test
    void adicionarPagamentoComIdDoCartaoInvalido() {

        when(validarPagameto.validarCamposObrigatoriosPagamento(anyLong(),anyInt(),any(),any(),anyString())).thenReturn(true);
        when(validarCartao.validarCartao(anyLong())).thenReturn(false);

        pagamentoController.adicionarPagamento(2L,4, BigDecimal.valueOf(53.5),BigDecimal.valueOf(214),"Pago");
    }
}