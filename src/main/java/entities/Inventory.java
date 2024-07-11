package entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "inventory")
public class Inventory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany
    List<Item> items = new ArrayList<>();

    @OneToOne(mappedBy = "inventory")
    DistributionCenter distributionCenter;
    @OneToOne(mappedBy = "inventory")
    Shelter shelter;

    public Inventory() {
    }

    public Inventory(DistributionCenter distributionCenter) {
        this.distributionCenter = distributionCenter;
    }

    public Inventory(Shelter shelter) {
        this.shelter = shelter;
    }

    public Long getId() {
        return id;
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

    public DistributionCenter getDistributionCenter() {
        return distributionCenter;
    }

    public Shelter getShelter() {
        return shelter;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", items=" + items +
                ", distributionCenter=" + distributionCenter +
                ", shelter=" + shelter +
                '}';
    }
}
