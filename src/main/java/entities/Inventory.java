package entities;

import entities.item.Clothing;
import entities.item.Food;
import entities.item.Hygiene;
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
    List<Hygiene> hygiene = new ArrayList<>();
    @OneToMany
    List<Food> food = new ArrayList<>();
    @OneToMany
    List<Clothing> clothing = new ArrayList<>();

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

    public DistributionCenter getDistributionCenter() {
        return distributionCenter;
    }

    public Shelter getShelter() {
        return shelter;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "hygiene=" + hygiene +
                ", food=" + food +
                ", clothing=" + clothing +
                '}';
    }
}
