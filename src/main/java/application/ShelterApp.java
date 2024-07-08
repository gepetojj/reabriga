package application;

import cli.CLI;
import entities.Shelter;

import java.util.ArrayList;

public class ShelterApp {
    private final CLI cli;
    private Shelter shelter;

    public ShelterApp(CLI cli) {
        this.cli = cli;
    }

    private void selectShelter() {
        cli.println("Escolha um abrigo para entrar no painel de administração:");

        var options = new ArrayList<String>();
        options.add("Abrigo 1");
        options.add("Abrigo 2");

        var selected = cli.userChoice(options);
        if (selected == 2) {
            shelter = new Shelter("Abrigo 2", "Rua def", "Nome da responsável", "82999999999", "email@gmail.com", 3000, 300);
        } else {
            shelter = new Shelter("Abrigo 1", "Rua abc", "Nome do responsável", "82999999999", "email@gmail.com", 1000, 100);
        }
    }

    public void run() {
        selectShelter();

        while (true) {
            cli.clear();
            cli.println("Administrando o abrigo " + shelter.getName());
            cli.println("Selecione a opção:");

            var options = new ArrayList<String>();
            options.add("Ver itens no inventário");
            options.add("Ordens de pedido");
            options.add("Sair");

            var selected = cli.userChoice(options);
            switch (selected){
                default:
                    return;

                case 1:
                    cli.println("" + selected);
                    break;

                case 2:
                    cli.println("" + selected);
                    break;
            }
        }
    }
}
