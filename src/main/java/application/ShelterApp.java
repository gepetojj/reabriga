package application;

import application.interfaces.LoggedInApp;
import application.interfaces.UI;
import entities.Shelter;

import java.util.ArrayList;

public class ShelterApp implements LoggedInApp {
    private final UI ui;
    private Shelter shelter;

    public ShelterApp(UI ui) {
        this.ui = ui;
    }

    private void selectShelter() {
        ui.println("Escolha um abrigo para entrar no painel de administração:");

        var options = new ArrayList<String>();
        options.add("Abrigo 1");
        options.add("Abrigo 2");

        var selected = ui.userChoice(options);
        if (selected == 2) {
            shelter = new Shelter("Abrigo 2", "Rua def", "Nome da responsável", "82999999999", "email@gmail.com");
        } else {
            shelter = new Shelter("Abrigo 1", "Rua abc", "Nome do responsável", "82999999999", "email@gmail.com");
        }
    }

    // INVENTORY

    private void showInventory() {
        ui.clear();
        ui.println("Inventário do abrigo '" + shelter.getName() + "':");
        ui.println("");

        var inventory = shelter.getInventory();
        displayItems(ui, inventory.getItems());

        ui.hold();
    }

    public void run() {
        selectShelter();

        while (true) {
            ui.clear();
            ui.println("Administrando o abrigo " + shelter.getName());
            ui.println("Selecione a opção:");

            var options = new ArrayList<String>();
            options.add("Ver itens no inventário");
            options.add("Ordens de pedido");
            options.add("Sair");

            var selected = ui.userChoice(options);
            switch (selected){
                default:
                    return;

                case 1:
                    showInventory();
                    break;

                case 2:
                    ui.println("" + selected);
                    break;
            }
        }
    }
}
