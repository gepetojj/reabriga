package application;

import application.interfaces.LoggedInApp;
import application.interfaces.UI;
import entities.DistributionCenter;
import entities.Item;
import entities.enums.ClothingSize;
import entities.enums.ClothingType;
import entities.enums.ItemType;
import entities.enums.OrderStatus;
import exceptions.TransferException;
import services.DistributionCenterService;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DistributionCenterApp implements LoggedInApp {
    private final UI ui;
    private final DistributionCenterService service;

    private DistributionCenter distributionCenter;

    public DistributionCenterApp(UI ui) {
        this.ui = ui;
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

        var selected = ui.userChoice(options);
        var index = selected - 1;
        return centers.get(index);
    }

    // INVENTORY

    private void showInventory() {
        ui.clear();
        ui.println("Inventário do centro '" + distributionCenter.getName() + "':");
        ui.println("");

        var inventory = distributionCenter.getInventory();
        displayItems(ui, inventory.getItems());

        ui.hold();
    }

    // ITEM_ORDER

    private void showItemOrders() {
        ui.clear();

        var itemOrders = distributionCenter.getItemOrders();
        if (itemOrders.isEmpty()) {
            ui.println("O centro não recebeu ordens de pedido ainda.");
            ui.hold();
            return;
        }

        ui.println("Ordens de pedido feitas ao centro '" + distributionCenter.getName() + "':");
        ui.println("");

        var pendingItemOrders = itemOrders.stream().filter(itemOrder -> itemOrder.getStatus() == OrderStatus.PENDING).toList();
        var settledItemOrders = itemOrders.stream().filter(itemOrder -> itemOrder.getStatus() != OrderStatus.PENDING).toList();

        for (var itemOrder : pendingItemOrders) {
            ui.println("Pedido do abrigo '" + itemOrder.getFromShelter().getName() + "' -> " + itemOrder.getItems().size() + " itens");
        }
        ui.println("-----------");
        for (var itemOrder : settledItemOrders) {
            String finalStatement = ")";
            if (itemOrder.getStatus() == OrderStatus.REFUSED) {
                finalStatement = ": " + itemOrder.getRefusedMotive() + ")";
            }

            ui.println("Pedido do abrigo '" +
                    itemOrder.getFromShelter().getName() +
                    "' -> " + itemOrder.getItems().size() +
                    " itens (" + itemOrder.getStatus() + finalStatement);
        }

        ui.hold();
    }

    private void respondItemOrders() {
        ui.clear();

        var itemOrders = distributionCenter.getItemOrders();
        var pendingItemOrders = itemOrders.stream().filter(itemOrder -> itemOrder.getStatus() == OrderStatus.PENDING).toList();
        if (pendingItemOrders.isEmpty()) {
            ui.println("O centro não tem ordens de pedido pendentes ainda.");
            ui.hold();
            return;
        }

        ui.println("Ordens de pedido pendentes do centro '" + distributionCenter.getName() + "':");
        ui.println("");

        var options = new ArrayList<String>();
        for (var itemOrder : pendingItemOrders) {
            options.add("Pedido do abrigo '" + itemOrder.getFromShelter().getName() + "' -> " + itemOrder.getItems().size() + " itens");
        }
        ui.println("Escolha uma ordem de pedido para responder:");
        var selected = ui.userChoice(options);
        var selectedItemOrder = pendingItemOrders.get(selected - 1);

        ui.clear();
        displayItems(ui, selectedItemOrder.getItems());
        ui.println("Escolha a ação para essa ordem de pedido:");

        options.clear();
        options.add("Aceitar");
        options.add("Recusar");
        selected = ui.userChoice(options);

        if (selected == 1) {
            selectedItemOrder = service.updateItemOrderStatus(selectedItemOrder, OrderStatus.ACCEPTED, null);
            var failedTransfers = service.executeItemOrder(selectedItemOrder);

            if (!failedTransfers.isEmpty()) {
                ui.println("Alguns itens não puderam ser transferidos e não foram retirados do inventário:");
                ui.println("");
                displayItems(ui, failedTransfers);
            }
        } else {
            // TODO: Validar input
            String motive = ui.textInput("Digite o motivo da recusa: ");
            service.updateItemOrderStatus(selectedItemOrder, OrderStatus.REFUSED, motive);
        }
    }

    private void itemOrderMenu() {
        while (true) {
            ui.clear();
            ui.println("Menu de ordens de pedido do centro '" + distributionCenter.getName() + "':");
            ui.println("Selecione a opção:");

            var options = new ArrayList<String>();
            options.add("Ver ordens de pedido");
            options.add("Responder ordem de pedido");
            options.add("Voltar");

            var selected = ui.userChoice(options);
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

        ui.clear();
        ui.println("Escolha o centro de distribuição destinatário:");
        var target = selectDistributionCenter();
        ui.println("");

        ui.println("Escolha qual item será doado:");
        var options = new ArrayList<String>();
        var items = distributionCenter.getInventory().getItems();
        for (var item : items) {
            if (item.getType() == ItemType.HYGIENE) {
                options.add(item.getDescription() + " - " + item.getQuantity());
            } else if (item.getType() == ItemType.CLOTHING) {
                options.add(item.getDescription() + " - Tipo (M/F): " + item.getClothingType() + " | Tamanho: " + item.getClothingSize());
            } else {
                options.add(item.getDescription() + " - " + item.getQuantity() + " " + item.getUnit() + " (" + formatter.format(item.getExpiration()) + ")");
            }
        }
        var selected = ui.userChoice(options);
        var targetItem = items.get(selected - 1);

        if (target.getInventory() == null) {
            throw new TransferException("O destinatário não possui um inventário. Transferência cancelada.");
        }
        var targetItemTypeCapacity = target.getInventory().getItems().stream().filter(i -> i.getType().equals(targetItem.getType())).toList();
        if (targetItemTypeCapacity.size() >= 1000) {
            throw new TransferException("O destinatário está com o armazenamento no limite. Transferência cancelada.");
        }
        service.transferItem(distributionCenter, target, targetItem);
    }

    // DONATIONS

    private void registerDonations() {
        ui.clear();
        ui.println("Formulário de registro de doação:");
        ui.println("");

        var options = new ArrayList<String>();
        options.add("Roupa");
        options.add("Comida");
        options.add("Item de higiene");
        options.add("Finalizar");

        var items = new ArrayList<Item>();

        while (true) {
            ui.println("Selecione o tipo do item sendo doado:");
            var selected = ui.userChoice(options);
            if (selected == options.size()) break;

            var item = new Item();
            var name = ui.textInput("Digite o nome do item no formato slug (ex.: pasta-de-dentes): ");
            var description = ui.textInput("Digite a descrição do item: ");
            item.setName(name);
            item.setDescription(description);

            switch (selected) {
                case 1:
                    var type = ui.textInput("Digite o tipo da roupa (M/F): ");
                    var size = ui.textInput("Digite o tamanho da roupa (XXS, XS, S, M, L, XL): ");
                    item.setClothingType(ClothingType.valueOf(type));
                    item.setClothingSize(ClothingSize.valueOf(size));
                    item.setType(ItemType.CLOTHING);
                    item.setQuantity(1.0);
                    break;

                case 2:
                    var quantity = ui.textInput("Digite o quantidade do item: ");
                    var quantityInDouble = Double.parseDouble(quantity);
                    var unit = ui.textInput("Digite a unidade de medida: ");
                    var expiration = ui.textInput("Digite a data de validade: ");
                    var expirationInInstant = Instant.parse(expiration);
                    item.setQuantity(quantityInDouble);
                    item.setUnit(unit);
                    item.setExpiration(expirationInInstant);
                    item.setType(ItemType.FOOD);
                    break;

                default:
                    item.setType(ItemType.HYGIENE);
                    item.setQuantity(1.0);
                    break;
            }

            items.add(item);
        }

        service.addItems(distributionCenter, items);
        ui.println("Itens adicionados com sucesso.");
        ui.hold();
    }

    private void registerDonationsFromCSV() {
        ui.clear();
        ui.println("Registro de doações via CSV");
        ui.println("");

        try {
            var items = ui.getItemsFromCSV("Digite o caminho do arquivo: ");
            displayItems(ui, items);
            ui.println("");
            ui.println("Verifique a lista acima e escolha uma opção:");

            var options = new ArrayList<String>();
            options.add("Registrar lote");
            options.add("Cancelar operação");

            var selected = ui.userChoice(options);
            if (selected == options.size()) return;

            service.addItems(distributionCenter, items);
            ui.println("Itens adicionados com sucesso.");
        } catch (Exception e) {
            ui.clear();
            ui.println("[ERRO] Não foi possível interpretar o arquivo CSV: " + e.getMessage());
        }

        ui.hold();
    }

    private void donationsMenu() {
        while (true) {
            ui.clear();
            ui.println("Menu de registro de doações para o centro '" + distributionCenter.getName() + "':");
            ui.println("Selecione a opção:");

            var options = new ArrayList<String>();
            options.add("Registrar lote manualmente");
            options.add("Registrar lote via arquivo CSV");
            options.add("Voltar");

            var selected = ui.userChoice(options);
            switch (selected) {
                default:
                    return;

                case 1:
                    registerDonations();
                    break;

                case 2:
                    registerDonationsFromCSV();
                    break;
            }
        }
    }

    public void run() {
        ui.println("Escolha um centro de distribuição para entrar no painel de administração:");
        distributionCenter = selectDistributionCenter();

        while (true) {
            try {
                ui.clear();
                ui.println("Administrando o centro '" + distributionCenter.getName() + "'");
                ui.println("Selecione a opção:");

                var options = new ArrayList<String>();
                options.add("Ver itens no inventário");
                options.add("Ordens de pedido");
                options.add("Transferência de doações");
                options.add("Registrar doações");
                options.add("Sair");

                var selected = ui.userChoice(options);
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

                    case 4:
                        donationsMenu();
                        break;
                }
            } catch (RuntimeException e) {
                ui.println("[ERRO] " + e.getMessage());
            }
        }
    }
}
