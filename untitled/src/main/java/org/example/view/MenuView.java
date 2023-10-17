package org.example.view;
import org.example.model.InputUser;

public class MenuView {
    private final MenuClienteView menuClienteView=new MenuClienteView();
    private final InputUser inputUser=new InputUser();

    public void MenuPrincipal() {
        System.out.println("\nBem-vindo(a) ao menu Principal");
        System.out.println("0 - Sair.");
        System.out.println("1 - Logar.");
        System.out.println("2 - Cadastrar.");
    }
    public void casePrincipal()  {
        int option;
        do {
            MenuPrincipal();
            option = inputUser.readIntFromUser("Qual opção você deseja: ");

            switch (option) {
                case 0 -> System.out.println("Saindo do sistema...");
                case 1 -> menuClienteView.logar();
                case 2 -> menuClienteView.cadastrarCliente();
                default -> System.out.println("Opção inválida, tente novamente!");
            }
        } while (option != 0);
    }

}
