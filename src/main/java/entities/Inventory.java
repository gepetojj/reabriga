package entities;

import entities.item.Clothing;
import entities.item.Food;
import entities.item.Hygiene;

import java.util.List;
import java.util.ArrayList;

public class Inventory {
    List<Hygiene> hygiene = new ArrayList<>();
    List<Food> food = new ArrayList<>();
    List<Clothing> clothing = new ArrayList<>();

    public Inventory() {
    }

    public Inventory(List<Hygiene> hygiene, List<Food> food, List<Clothing> clothing) {
        this.hygiene = hygiene;
        this.food = food;
        this.clothing = clothing;
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
        return "Inventory{" +
                "hygiene=" + hygiene +
                ", food=" + food +
                ", clothing=" + clothing +
                '}';
    }
}
