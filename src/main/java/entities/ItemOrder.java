package entities;

import entities.enums.OrderStatus;
import entities.item.Clothing;
import entities.item.Food;
import entities.item.Hygiene;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "item_order")
public class ItemOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    OrderStatus status;
    String refusedMotive;

    @OneToMany
    List<Hygiene> hygiene = new ArrayList<>();
    @OneToMany
    List<Food> food = new ArrayList<>();
    @OneToMany
    List<Clothing> clothing = new ArrayList<>();

    public ItemOrder() {
    }

    public ItemOrder(OrderStatus status, String refusedMotive, List<Hygiene> hygiene, List<Food> food, List<Clothing> clothing) {
        this.status = status;
        this.refusedMotive = refusedMotive;
        this.hygiene = hygiene;
        this.food = food;
        this.clothing = clothing;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getRefusedMotive() {
        return refusedMotive;
    }

    public List<Hygiene> getHygiene() {
        return hygiene;
    }

    public List<Food> getFood() {
        return food;
    }

    public List<Clothing> getClothing() {
        return clothing;
    }

    public void addHygieneItem(Hygiene hygiene) {
        this.hygiene.add(hygiene);
    }

    public void addFoodItem(Food food) {
        this.food.add(food);
    }

    public void addClothingItem(Clothing clothing) {
        this.clothing.add(clothing);
    }

    public void removeHygieneItem(Hygiene hygiene) {
        this.hygiene.remove(hygiene);
    }

    public void removeFoodItem(Food food) {
        this.food.remove(food);
    }

    public void removeClothingItem(Clothing clothing) {
        this.clothing.remove(clothing);
    }

    @Override
    public String toString() {
        return "ItemOrder{" +
                "status=" + status +
                ", refusedMotive='" + refusedMotive + '\'' +
                ", hygiene=" + hygiene +
                ", food=" + food +
                ", clothing=" + clothing +
                '}';
    }
}
