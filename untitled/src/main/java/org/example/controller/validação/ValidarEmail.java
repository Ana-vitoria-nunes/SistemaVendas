package org.example.controller.validação;

public class ValidarEmail {
    public boolean validarEmail(String email) {
        return email != null && email.contains("@") && email.endsWith("gmail.com"); // O email atende aos critérios.
    }

}
