package org.example.controller;
import org.example.controller.validacao.ValidarEmail;
import org.example.controller.validacao.ValidarUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {
    @InjectMocks
    private ClienteController clienteController ;
    @Mock
    private Statement statement;
    private final ValidarUser validarUser= Mockito.mock(ValidarUser.class);
    private final ValidarEmail validarEmail= Mockito.mock(ValidarEmail.class);
    @Test
    void adicionarUsuarioComSucesso() throws SQLException {
        when(validarUser.validarCamposObrigatoriosUser(anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(true);
        when(validarUser.validarDataNascimento(any())).thenReturn(true);
        when(validarEmail.validarEmail(anyString())).thenReturn(true);
        String dataNascimento="11/05/2004";
        LocalDate dataDeNasc = LocalDate.parse(dataNascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        java.util.Date dataDeNascComoDate = java.sql.Date.valueOf(dataDeNasc);
        when(statement.executeUpdate(anyString())).thenReturn(0);

        clienteController.adicionarUsuario("Lua", dataDeNascComoDate,"ana@gmail.com","ana123","123456","Rua do desafio","991768123");
    }
    @Test
    void adicionarUsuarioCredenciaisVazia()  {
        when(validarUser.validarCamposObrigatoriosUser(anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(false);
        when(validarUser.validarDataNascimento(any())).thenReturn(true);
        when(validarEmail.validarEmail(anyString())).thenReturn(true);
        String dataNascimento="11/05/2004";
        LocalDate dataDeNasc = LocalDate.parse(dataNascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        java.util.Date dataDeNascComoDate = java.sql.Date.valueOf(dataDeNasc);

        clienteController.adicionarUsuario("", dataDeNascComoDate,"ana@gmail.com","ana123","123456","Rua do desafio","991768123");
    }
    @Test
    void adicionarUsuarioMenorDeIdade(){
        when(validarUser.validarCamposObrigatoriosUser(anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(true);
        when(validarUser.validarDataNascimento(any())).thenReturn(false);
        when(validarEmail.validarEmail(anyString())).thenReturn(true);
        String dataNascimento="11/05/2009";
        LocalDate dataDeNasc = LocalDate.parse(dataNascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        java.util.Date dataDeNascComoDate = java.sql.Date.valueOf(dataDeNasc);

        clienteController.adicionarUsuario("Ana", dataDeNascComoDate,"ana@gmail.com","ana123","123456","Rua do desafio","991768123");
    }
    @Test
    void adicionarUsuarioComEmailInvalido() {
        when(validarUser.validarCamposObrigatoriosUser(anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(false);
        when(validarUser.validarDataNascimento(any())).thenReturn(true);
        when(validarEmail.validarEmail(anyString())).thenReturn(false);
        String dataNascimento="11/05/2004";
        LocalDate dataDeNasc = LocalDate.parse(dataNascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        java.util.Date dataDeNascComoDate = java.sql.Date.valueOf(dataDeNasc);

        clienteController.adicionarUsuario("Ana", dataDeNascComoDate,"anagmail.com","ana123","123456","Rua do desafio","991768123");
    }

    @Test
    void atualizarUsuarioComSucesso() throws SQLException {
        when(validarUser.validarUser(anyLong())).thenReturn(true);
        when(validarEmail.validarEmail(anyString())).thenReturn(true);

        when(statement.executeUpdate(anyString())).thenReturn(1);
        clienteController.atualizarUsuario(1L,"ana@gmail.com","ana123","Rua do desafio","991768123");
    }
    @Test
    void atualizarUsuarioComIdInvalido() {
        when(validarUser.validarUser(anyLong())).thenReturn(false);
        when(validarEmail.validarEmail(anyString())).thenReturn(true);

        clienteController.atualizarUsuario(0L,"ana@gmail.com","ana123","Rua do desafio","991768123");
    }
    @Test
    void atualizarUsuarioComEmailInvalido() {
        when(validarUser.validarUser(anyLong())).thenReturn(true);
        when(validarEmail.validarEmail(anyString())).thenReturn(false);

        clienteController.atualizarUsuario(1L,"anagmail.com","ana123","Rua do desafio","991768123");
    }
}