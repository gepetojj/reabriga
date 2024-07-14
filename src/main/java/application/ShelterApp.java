package application;

import application.interfaces.LoggedInApp;
import application.interfaces.UI;
import entities.Shelter;
import entities.enums.OrderStatus;
import services.ShelterService;

import java.util.ArrayList;

public class ShelterApp implements LoggedInApp {
    private final UI ui;
    private final ShelterService service;

    private Shelter shelter;

    public ShelterApp(UI ui) {
        this.ui = ui;
        this.service = new ShelterService();
    }

    private Shelter selectShelter() {
        var shelters = service.getAllShelters();
        if (shelters.isEmpty()) {
            throw new RuntimeException("Não há abrigos cadastrados.");
        }
        if (shelter != null) {
            shelters = shelters.stream().filter(center -> !center.equals(shelter)).toList();
        }

        var options = new ArrayList<String>(shelters.size());
        for (var center : shelters) {
            options.add(center.getName());
        }

        var selected = ui.userChoice(options);
        var index = selected - 1;
        return shelters.get(index);
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

    // ITEM_ORDER

    private void showItemOrders() {
        ui.clear();

        var itemOrders = shelter.getItemOrders();
        if (itemOrders.isEmpty()) {
            ui.println("O abrigo não recebeu ordens de pedido ainda.");
            ui.hold();
            return;
        }

        ui.println("Ordens de pedido feitas ao abrigo '" + shelter.getName() + "':");
        ui.println("");

        var pendingItemOrders = itemOrders.stream().filter(itemOrder -> itemOrder.getStatus() == OrderStatus.PENDING).toList();
        var settledItemOrders = itemOrders.stream().filter(itemOrder -> itemOrder.getStatus() != OrderStatus.PENDING).toList();

        for (var itemOrder : pendingItemOrders) {
            ui.println("Pedido feito ao centro '" + itemOrder.getToDistributionCenter().getName() + "' -> " + itemOrder.getItems().size() + " itens");
        }
        ui.println("-----------");
        for (var itemOrder : settledItemOrders) {
            String finalStatement = ")";
            if (itemOrder.getStatus() == OrderStatus.REFUSED) {
                finalStatement = ": " + itemOrder.getRefusedMotive() + ")";
            }

            ui.println("Pedido feito ao centro '" +
                    itemOrder.getToDistributionCenter().getName() +
                    "' -> " + itemOrder.getItems().size() +
                    " itens (" + itemOrder.getStatus() + finalStatement);
        }

        ui.hold();
    }

    private void itemOrderMenu() {
        while (true) {
            ui.clear();
            ui.println("Menu de ordens de pedido do abrigo '" + shelter.getName() + "':");
            ui.println("Selecione a opção:");

            var options = new ArrayList<String>();
            options.add("Ver ordens de pedido");
            options.add("Criar ordem de pedido");
            options.add("Cancelar ordem de pedido");
            options.add("Voltar");

            var selected = ui.userChoice(options);
            switch (selected) {
                default:
                    return;

                case 1:
                    showItemOrders();
                    break;

                case 2:
                    break;

                case 3:
                    break;
            }
        }
    }

    public void run() {
        ui.println("Escolha um abrigo para entrar no painel de administração:");
        shelter = selectShelter();

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
                    itemOrderMenu();
                    break;
            }
        }
    }
}
