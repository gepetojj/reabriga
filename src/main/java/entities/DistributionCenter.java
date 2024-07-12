package entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

@Entity
@Table(name = "distribution_center")
public class DistributionCenter implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String postalCode;

    @OneToOne
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    private Inventory inventory;
    @OneToMany
    private final List<ItemOrder> itemOrders = new ArrayList<>();

    public DistributionCenter() {
    }

    public DistributionCenter(String name, String address, String postalCode) {
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public List<ItemOrder> getItemOrders() {
        return itemOrders;
    }

    public void addItemOrder(ItemOrder itemOrder) {
        itemOrders.add(itemOrder);
    }

    public void removeItemOrder(ItemOrder itemOrder) {
        itemOrders.remove(itemOrder);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DistributionCenter that = (DistributionCenter) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DistributionCenter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", inventory=" + inventory +
                ", itemOrders=" + itemOrders +
                '}';
    }
}
