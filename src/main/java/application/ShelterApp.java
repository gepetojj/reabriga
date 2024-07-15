package application;

import application.interfaces.LoggedInApp;
import application.interfaces.UI;
import entities.DistributionCenter;
import entities.Item;
import entities.Shelter;
import entities.enums.OrderStatus;
import jakarta.validation.*;
import services.ShelterService;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

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
            ui.println("O abrigo não criou ordens de pedido ainda.");
            ui.hold();
            return;
        }

        ui.println("Ordens de pedido feitas pelo abrigo '" + shelter.getName() + "':");
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

    private void createItemOrder() {
        ui.clear();

        var items = service.getAvailableItems();
        displayItems(ui, items);
        ui.println("");

        var options = new ArrayList<String>(items.size());
        for (var item : items) {
            options.add(item.getDescription());
        }
        options.add("Finalizar");

        var selectedItems = new ArrayList<Item>();
        while (true) {
            ui.clear();
            ui.println("Selecione o item para adicionar à ordem de pedido:");
            var selected = ui.userChoice(options);
            if (selected == options.size()) {
                break;
            }

            var description = options.get(selected - 1);
            items.stream().filter(i -> Objects.equals(i.getDescription(), description)).findFirst().ifPresent((item) -> {
                selectedItems.add(item);
                options.remove(item.getDescription());
                ui.println(item.getDescription() + " adicionado.");
            });
            ui.hold();
        }

        var selectedItemsCenters = new ArrayList<DistributionCenter>();
        for (var selectedItem : selectedItems) {
            var center = selectedItem.getInventory().getDistributionCenter();
            if (!selectedItemsCenters.contains(center)) {
                selectedItemsCenters.add(center);
            }
        }

        for (var center : selectedItemsCenters) {
            var centerItems = selectedItems.stream().filter(i -> i.getInventory().getDistributionCenter().equals(center)).toList();
            service.createItemOrder(center, shelter, centerItems);
        }

        ui.println("Pedidos feitos com sucesso.");
        ui.hold();
    }

    private void cancelItemOrder() {
        ui.clear();

        var itemOrders = shelter.getItemOrders();
        if (itemOrders.isEmpty()) {
            ui.println("O abrigo não criou ordens de pedido ainda.");
            ui.hold();
            return;
        }

        ui.println("Ordens de pedido feitas pelo abrigo '" + shelter.getName() + "':");
        ui.println("");

        var pendingItemOrders = itemOrders.stream().filter(itemOrder -> itemOrder.getStatus() == OrderStatus.PENDING).toList();
        var options = new ArrayList<String>(pendingItemOrders.size());

        for (var itemOrder : pendingItemOrders) {
            options.add("Pedido feito ao centro '" + itemOrder.getToDistributionCenter().getName() + "' -> " + itemOrder.getItems().size() + " itens");
        }

        ui.println("Escolha qual ordem deseja cancelar:");
        var selected = ui.userChoice(options);
        var index = selected - 1;

        service.cancelItemOrder(pendingItemOrders.get(index));
        ui.println("Ordem cancelada com sucesso.");
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
                    createItemOrder();
                    break;

                case 3:
                    cancelItemOrder();
                    break;
            }
        }
    }

    // INFO

    private void showShelterInfo() {
        ui.clear();
        ui.println("Dados do abrigo '" + shelter.getName() + "':");
        ui.println("");

        ui.println("Nome do abrigo: " + shelter.getName());
        ui.println("Endereço do abrigo: " + shelter.getAddress());
        ui.println("Responsável pelo abrigo: " + shelter.getChief());
        ui.println("Telefone do abrigo: " + shelter.getPhone());
        ui.println("Email do abrigo: " + shelter.getEmail());

        ui.println("");
        ui.hold();
    }

    private void editShelterInfo() {
        ui.clear();
        ui.println("Menu de edição dos dados do abrigo '" + shelter.getName() + "'");
        ui.println("Digite a nova informação ou deixe em branco para não alterar.");
        ui.println("");

        var name = ui.textInput("Insira o nome do abrigo (" + shelter.getName() + "): ");
        var address = ui.textInput("Insira o endereço (" + shelter.getAddress() + "): ");
        var chief = ui.textInput("Insira o nome do(a) responsável pelo abrigo (" + shelter.getChief() + "): ");
        var phone = ui.textInput("Insira o telefone para contato (" + shelter.getPhone() + "): ");
        var email = ui.textInput("Insira o email para contato (" + shelter.getEmail() + "): ");

        if (!name.isBlank()) shelter.setName(name);
        if (!address.isBlank()) shelter.setAddress(address);
        if (!chief.isBlank()) shelter.setChief(chief);
        if (!phone.isBlank()) shelter.setPhone(phone);
        if (!email.isBlank()) shelter.setEmail(email);
        service.update(shelter);

        ui.println("Dados atualizados com sucesso.");
        ui.hold();
    }

    public void run() {
        ui.println("Escolha um abrigo para entrar no painel de administração:");
        shelter = selectShelter();

        while (true) {
            try {
                ui.clear();
                ui.println("Administrando o abrigo " + shelter.getName());
                ui.println("Selecione a opção:");

                var options = new ArrayList<String>();
                options.add("Ver itens no inventário");
                options.add("Ordens de pedido");
                options.add("Ver dados do abrigo");
                options.add("Editar dados do abrigo");
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
                        showShelterInfo();
                        break;

                    case 4:
                        editShelterInfo();
                        break;
                }
            } catch (RuntimeException e) {
                ui.println("[ERRO] " + e.getMessage());
            }
        }
    }

    public void registerNew() {
        ui.clear();
        ui.println("Formulário para registrar novo abrigo");
        ui.println("");

        var name = ui.textInput("Insira o nome do abrigo: ");
        var address = ui.textInput("Insira o endereço: ");
        var chief = ui.textInput("Insira o nome do(a) responsável pelo abrigo: ");
        var phone = ui.textInput("Insira o telefone para contato: ");
        var email = ui.textInput("Insira o email para contato: ");

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            var validator = factory.getValidator();
            var newShelter = new Shelter(name, address, chief, phone, email);
            Set<ConstraintViolation<Shelter>> violations = validator.validate(newShelter);

            if (!violations.isEmpty()) {
                ui.clear();
                ui.println("Não foi possível registrar o abrigo por erros de validação:");
                ui.println("");
                for (ConstraintViolation<Shelter> violation : violations) {
                    ui.println(violation.getMessage());
                }
            } else {
                service.create(newShelter);
                ui.println("Abrigo registrado com sucesso.");
            }
        } catch (Exception e) {
            ui.println("[ERRO] " + e.getMessage());
        }
        ui.hold();
    }
}
