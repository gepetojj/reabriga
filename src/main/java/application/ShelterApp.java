package application;

import application.interfaces.LoggedInApp;
import cli.CLI;
import entities.Item;
import entities.Shelter;
import entities.enums.ItemType;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ShelterApp implements LoggedInApp {
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
            shelter = new Shelter("Abrigo 2", "Rua def", "Nome da responsável", "82999999999", "email@gmail.com");
        } else {
            shelter = new Shelter("Abrigo 1", "Rua abc", "Nome do responsável", "82999999999", "email@gmail.com");
        }
    }

    // INVENTORY

    private void showInventory() {
        cli.clear();
        cli.println("Inventário do abrigo '" + shelter.getName() + "':");
        cli.println("");

        var inventory = shelter.getInventory();
        displayItems(cli, inventory.getItems());

        cli.hold();
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
                    showInventory();
                    break;

                case 2:
                    cli.println("" + selected);
                    break;
            }
        }
    }
}
