package services;

import entities.DistributionCenter;
import entities.Item;
import entities.ItemOrder;
import entities.Shelter;
import entities.enums.OrderStatus;
import repositories.DistributionCenterRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

public class DistributionCenterService {
    private final DistributionCenterRepository distributionCenterRepository;
    private final ItemOrderService itemOrderService;
    private final InventoryService inventoryService;

    public DistributionCenterService() {
        this.distributionCenterRepository = new DistributionCenterRepository();
        this.itemOrderService = new ItemOrderService();
        this.inventoryService = new InventoryService();
    }

    public Optional<DistributionCenter> getDistributionCenter(Long distributionCenterId) {
        return distributionCenterRepository.findById(distributionCenterId);
    }

    public List<DistributionCenter> getAllDistributionCenters() {
        return distributionCenterRepository.findAll();
    }

    public ItemOrder updateItemOrderStatus(ItemOrder order, OrderStatus newStatus, String refusedMotive) {
        order.setStatus(newStatus);
        order.setRefusedMotive(refusedMotive);
        itemOrderService.update(order);
        return order;
    }

    public List<Item> executeItemOrder(ItemOrder order) {
        var items = order.getItems();
        var dc = order.getToDistributionCenter();
        var shelter = order.getFromShelter();

        var failedItemTransfer = new ArrayList<Item>();
        for (var item : items) {
            var shelterItems = shelter.getInventory().getItems();
            var shelterItemTypeCapacity = shelterItems.stream().filter(i -> i.getType().equals(item.getType())).toList();
            if (shelterItemTypeCapacity.size() >= 200) {
                failedItemTransfer.add(item);
            } else {
                dc.getInventory().removeItem(item);
                shelter.getInventory().addItem(item);

                inventoryService.updateInventory(dc.getInventory());
                inventoryService.updateInventory(shelter.getInventory());
            }
        }
        return failedItemTransfer;
    }

    public void addItemOrder(DistributionCenter center, ItemOrder order) {
        center.addItemOrder(order);
        distributionCenterRepository.update(center);
    }

    public void transferItem(DistributionCenter from, DistributionCenter to, Item item) {
        from.getInventory().removeItem(item);
        distributionCenterRepository.update(from);
        to.getInventory().addItem(item);
        distributionCenterRepository.update(to);
    }

    public void addItems(DistributionCenter to, List<Item> items) {
        for (var item : items) {
            to.getInventory().addItem(item);
            item.setInventory(to.getInventory());
            inventoryService.updateInventory(to.getInventory());
        }
    }
}
