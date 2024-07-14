package application;

import cli.CLI;
import entities.Item;
import entities.Shelter;
import entities.enums.ItemType;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
            shelter = new Shelter("Abrigo 2", "Rua def", "Nome da responsável", "82999999999", "email@gmail.com");
        } else {
            shelter = new Shelter("Abrigo 1", "Rua abc", "Nome do responsável", "82999999999", "email@gmail.com");
        }
    }

    // TODO: Abstract this function
    private void displayItems(List<Item> items) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")
                .withZone(ZoneId.systemDefault());
        for (var item : items) {
            if (item.getType() == ItemType.HYGIENE) {
                cli.println(item.getDescription() + " - " + item.getQuantity());
            } else if (item.getType() == ItemType.CLOTHING) {
                cli.println(item.getDescription() + " - Tipo (M/F): " + item.getClothingType() + " | Tamanho: " + item.getClothingSize());
            } else {
                cli.println(item.getDescription() + " - " + item.getQuantity() + " " + item.getUnit() + " (" + formatter.format(item.getExpiration()) + ")");
            }
        }
    }

    // INVENTORY

    private void showInventory() {
        cli.clear();
        cli.println("Inventário do abrigo '" + shelter.getName() + "':");
        cli.println("");

        var inventory = shelter.getInventory();
        displayItems(inventory.getItems());

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
