package application;

import cli.CLI;
import entities.DistributionCenter;
import entities.Item;
import entities.enums.ItemType;
import entities.enums.OrderStatus;
import services.DistributionCenterService;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DistributionCenterApp {
    private final CLI cli;
    private final DistributionCenterService service;

    private DistributionCenter distributionCenter;

    public DistributionCenterApp(CLI cli) {
        this.cli = cli;
        this.service = new DistributionCenterService();
    }

    // UTILS

    private DistributionCenter selectDistributionCenter() {
        var centers = service.getAllDistributionCenters();
        if (centers.isEmpty()) {
            throw new RuntimeException("Não há centros de distribuição cadastrados.");
        }
        if (distributionCenter != null) {
            centers = centers.stream().filter(center -> !center.equals(distributionCenter)).toList();
        }

        var options = new ArrayList<String>(centers.size());
        for (var center : centers) {
            options.add(center.getName());
        }

        var selected = cli.userChoice(options);
        var index = selected - 1;
        return centers.get(index);
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
        cli.println("Inventário do centro '" + distributionCenter.getName() + "':");
        cli.println("");

        var inventory = distributionCenter.getInventory();
        displayItems(inventory.getItems());

        cli.hold();
    }

    // ITEM_ORDER

    private void showItemOrders() {
        cli.clear();

        var itemOrders = distributionCenter.getItemOrders();
        if (itemOrders.isEmpty()) {
            cli.println("O centro não recebeu ordens de pedido ainda.");
            cli.hold();
            return;
        }

        cli.println("Ordens de pedido feitas ao centro '" + distributionCenter.getName() + "':");
        cli.println("");

        var pendingItemOrders = itemOrders.stream().filter(itemOrder -> itemOrder.getStatus() == OrderStatus.PENDING).toList();
        var settledItemOrders = itemOrders.stream().filter(itemOrder -> itemOrder.getStatus() != OrderStatus.PENDING).toList();

        for (var itemOrder : pendingItemOrders) {
            cli.println("Pedido do abrigo '" + itemOrder.getFromShelter().getName() + "' -> " + itemOrder.getItems().size() + " itens");
        }
        cli.println("-----------");
        for (var itemOrder : settledItemOrders) {
            String finalStatement = ")";
            if (itemOrder.getStatus() == OrderStatus.REFUSED) {
                finalStatement = ": " + itemOrder.getRefusedMotive() + ")";
            }

            cli.println("Pedido do abrigo '" +
                    itemOrder.getFromShelter().getName() +
                    "' -> " + itemOrder.getItems().size() +
                    " itens (" + itemOrder.getStatus() + finalStatement);
        }

        cli.hold();
    }

    private void respondItemOrders() {
        cli.clear();

        var itemOrders = distributionCenter.getItemOrders();
        var pendingItemOrders = itemOrders.stream().filter(itemOrder -> itemOrder.getStatus() == OrderStatus.PENDING).toList();
        if (pendingItemOrders.isEmpty()) {
            cli.println("O centro não tem ordens de pedido pendentes ainda.");
            cli.hold();
            return;
        }

        cli.println("Ordens de pedido pendentes do centro '" + distributionCenter.getName() + "':");
        cli.println("");

        var options = new ArrayList<String>();
        for (var itemOrder : pendingItemOrders) {
            options.add("Pedido do abrigo '" + itemOrder.getFromShelter().getName() + "' -> " + itemOrder.getItems().size() + " itens");
        }
        cli.println("Escolha uma ordem de pedido para responder:");
        var selected = cli.userChoice(options);
        var selectedItemOrder = pendingItemOrders.get(selected - 1);

        cli.clear();
        displayItems(selectedItemOrder.getItems());
        cli.println("Escolha a ação para essa ordem de pedido:");

        options.clear();
        options.add("Aceitar");
        options.add("Recusar");
        selected = cli.userChoice(options);

        if (selected == 1) {
            service.updateItemOrderStatus(selectedItemOrder, OrderStatus.ACCEPTED, null);
        } else {
            // TODO: Validar input
            String motive = cli.textInput("Digite o motivo da recusa: ");
            service.updateItemOrderStatus(selectedItemOrder, OrderStatus.REFUSED, motive);
        }
    }

    private void itemOrderMenu() {
        while (true) {
            cli.clear();
            cli.println("Menu de ordens de pedido do centro '" + distributionCenter.getName() + "':");
            cli.println("Selecione a opção:");

            var options = new ArrayList<String>();
            options.add("Ver ordens de pedido");
            options.add("Responder ordem de pedido");
            options.add("Voltar");

            var selected = cli.userChoice(options);
            switch (selected) {
                default:
                    return;

                case 1:
                    showItemOrders();
                    break;

                case 2:
                    respondItemOrders();
                    break;
            }
        }
    }

    // TRANSFERS

    private void transferItems() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss").withZone(ZoneId.systemDefault());

        cli.clear();
        cli.println("Escolha o centro de distribuição destinatário:");
        var target = selectDistributionCenter();
        cli.println("");

        cli.println("Escolha qual item será doado:");
        var options = new ArrayList<String>();
        for (var item : distributionCenter.getInventory().getItems()) {
            if (item.getType() == ItemType.HYGIENE) {
                options.add(item.getDescription() + " - " + item.getQuantity());
            } else if (item.getType() == ItemType.CLOTHING) {
                options.add(item.getDescription() + " - Tipo (M/F): " + item.getClothingType() + " | Tamanho: " + item.getClothingSize());
            } else {
                options.add(item.getDescription() + " - " + item.getQuantity() + " " + item.getUnit() + " (" + formatter.format(item.getExpiration()) + ")");
            }
        }
        var selected = cli.userChoice(options);
        // Chama service para transferir itens
    }

    public void run() {
        cli.println("Escolha um centro de distribuição para entrar no painel de administração:");
        distributionCenter = selectDistributionCenter();

        while (true) {
            cli.clear();
            cli.println("Administrando o centro '" + distributionCenter.getName() + "'");
            cli.println("Selecione a opção:");

            var options = new ArrayList<String>();
            options.add("Ver itens no inventário");
            options.add("Ordens de pedido");
            options.add("Transferência de doações");
            options.add("Sair");

            var selected = cli.userChoice(options);
            switch (selected) {
                default:
                    return;

                case 1:
                    showInventory();
                    break;

                case 2:
                    itemOrderMenu();
                    break;

                case 3:
                    transferItems();
                    break;
            }
        }
    }
}
