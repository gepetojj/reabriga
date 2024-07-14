package services;

import entities.ItemOrder;
import repositories.ItemOrderRepository;

public class ItemOrderService {
    ItemOrderRepository repository;

    public ItemOrderService() {
        this.repository = new ItemOrderRepository();
    }

    public void create(ItemOrder itemOrder) {
        repository.save(itemOrder);
    }

    public void update(ItemOrder itemOrder) {
        repository.update(itemOrder);
    }
}
