package entities;

import entities.enums.ClothingSize;
import entities.enums.ClothingType;
import entities.enums.ItemType;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table("item")
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double quantity;
    private ItemType type;

    private ClothingType clothingType;
    private ClothingSize clothingSize;

    private String unit;
    private Instant expiration;

    @ManyToOne
    private Inventory inventory;

    public Item() {
    }

    public Item(String name, String description, ClothingType clothingType, ClothingSize clothingSize) {
        this.name = name;
        this.description = description;
        this.clothingType = clothingType;
        this.clothingSize = clothingSize;
        this.quantity = 1.0;
        this.type = ItemType.CLOTHING;
    }

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
        this.quantity = 1.0;
        this.type = ItemType.HYGIENE;
    }

    public Item(String name, String description, Double quantity, String unit, Instant expiration) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.unit = unit;
        this.expiration = expiration;
        this.type = ItemType.FOOD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public ClothingType getClothingType() {
        return clothingType;
    }

    public void setClothingType(ClothingType clothingType) {
        this.clothingType = clothingType;
    }

    public ClothingSize getClothingSize() {
        return clothingSize;
    }

    public void setClothingSize(ClothingSize clothingSize) {
        this.clothingSize = clothingSize;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Instant getExpiration() {
        return expiration;
    }

    public void setExpiration(Instant expiration) {
        this.expiration = expiration;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", type=" + type +
                ", clothingType=" + clothingType +
                ", clothingSize=" + clothingSize +
                ", unit='" + unit + '\'' +
                ", expiration=" + expiration +
                ", inventory=" + inventory +
                '}';
    }
}
