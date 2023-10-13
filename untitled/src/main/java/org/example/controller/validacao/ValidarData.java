package org.example.controller.validacao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidarData {
    public boolean validarDataValidade(Date dataValidade) {
        //Não testado

        // Defina o formato esperado para a data de validade (MM/AA ou MM/AAAA).
        SimpleDateFormat formato = new SimpleDateFormat("MM/yy"); // "MM/AA"
        // Ou, se a data de validade estiver no formato "MM/AAAA":
        // SimpleDateFormat formato = new SimpleDateFormat("MM/yyyy");

        // Certifique-se de que a data de validade esteja no formato correto.
        formato.setLenient(false); // Isso impedirá datas inválidas (por exemplo, 13/20) de serem aceitas.

        try {
            Date data = formato.parse(String.valueOf(dataValidade));

            // Verifique se a data de validade não está expirada.
            Date dataAtual = new Date();
            if (data.after(dataAtual)) {
                return true; // A data é válida e não expirada.
            } else {
                return false; // A data expirou.
            }
        } catch (ParseException e) {
            return false; // A data não está no formato correto.
        }
    }


}
