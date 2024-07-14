package services;

import entities.*;
import entities.enums.OrderStatus;
import repositories.ShelterRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShelterService {
    private final ShelterRepository repository;
    private final DistributionCenterService distributionCenterService;
    private final ItemOrderService itemOrderService;
    private final InventoryService inventoryService;

    public ShelterService() {
        this.repository = new ShelterRepository();
        this.distributionCenterService = new DistributionCenterService();
        this.itemOrderService = new ItemOrderService();
        this.inventoryService = new InventoryService();
    }

    public void create(Shelter shelter) {
        Inventory inventory = new Inventory(shelter);
        repository.save(shelter);
        inventoryService.createInventory(inventory);
        shelter.setInventory(inventory);
        repository.update(shelter);
    }

    public Optional<Shelter> getShelter(Long distributionCenterId) {
        return repository.findById(distributionCenterId);
    }

    public List<Shelter> getAllShelters() {
        return repository.findAll();
    }

    public List<Item> getAvailableItems() {
        var centers = distributionCenterService.getAllDistributionCenters();
        var items = new ArrayList<Item>();
        for (var center : centers) {
            var centerItems = center.getInventory().getItems();
            items.addAll(centerItems);
        }
        return items;
    }

    public void createItemOrder(DistributionCenter to, Shelter from, List<Item> items) {
        ItemOrder order = new ItemOrder();
        order.setFromShelter(from);
        order.setToDistributionCenter(to);
        for (var item : items) {
            order.addItem(item);
        }
        itemOrderService.create(order);
        from.addItemOrder(order);
        repository.save(from);
        distributionCenterService.addItemOrder(to, order);
    }

    public void cancelItemOrder(ItemOrder order) {
        distributionCenterService.updateItemOrderStatus(order, OrderStatus.CANCELLED, "Cancelada pelo abrigo.");
    }
}
