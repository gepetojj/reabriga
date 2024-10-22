package services;

import entities.ItemOrder;
import repositories.ItemOrderRepository;

public class ItemOrderService {
    private final ItemOrderRepository repository;

    public ItemOrderService() {
        this.repository = new ItemOrderRepository();
    }

    public void create(ItemOrder itemOrder) {
        repository.save(itemOrder);
    }

    public void update(ItemOrder itemOrder) {
        repository.update(itemOrder);
    }

    public void delete(ItemOrder itemOrder) {
        repository.delete(itemOrder);
    }
}
