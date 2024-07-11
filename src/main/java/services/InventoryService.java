package services;

import entities.Inventory;
import repositories.InventoryRepository;

import java.util.List;
import java.util.Optional;

public class InventoryService {
    InventoryRepository inventoryRepository;

    public InventoryService() {
        this.inventoryRepository = new InventoryRepository();
    }

    public Optional<Inventory> getInventory(Long inventoryId) {
        return inventoryRepository.findById(inventoryId);
    }
}
