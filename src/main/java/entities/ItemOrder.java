package entities;

import entities.enums.OrderStatus;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "item_order")
public class ItemOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private OrderStatus status;
    private String refusedMotive;

    @ManyToOne
    private Shelter fromShelter;
    @ManyToOne
    private DistributionCenter toDistributionCenter;
    @OneToMany
    private final List<Item> items = new ArrayList<>();

    public ItemOrder() {
    }

    public ItemOrder(OrderStatus status, String refusedMotive) {
        this.status = status;
        this.refusedMotive = refusedMotive;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getRefusedMotive() {
        return refusedMotive;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void removeItem(Item item) {
        this.items.remove(item);
    }

    public Shelter getFromShelter() {
        return fromShelter;
    }

    public DistributionCenter getToDistributionCenter() {
        return toDistributionCenter;
    }

    @Override
    public String toString() {
        return "ItemOrder{" +
                "id=" + id +
                ", status=" + status +
                ", refusedMotive='" + refusedMotive + '\'' +
                ", items=" + items +
                '}';
    }
}
