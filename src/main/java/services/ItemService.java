package services;

import entities.Item;
import repositories.ItemRepository;

import java.util.Optional;

public class ItemService {
    private final ItemRepository repository;

    public ItemService() {
        this.repository = new ItemRepository();
    }

    public Optional<Item> getItem(Long itemId) {
        return repository.findById(itemId);
    }

    public void createItem(Item item) {
        repository.save(item);
    }

    public void updateItem(Item item) {
        repository.update(item);
    }
}
