package entities.item;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@DiscriminatorValue("food")
public class Food extends Item {
    String unit;
    Instant validity;

    public Food() {
    }

    public Food(String unit, Instant validity) {
        this.unit = unit;
        this.validity = validity;
    }

    public Food(String name, Double quantity, String unit, Instant validity) {
        super(name, quantity);
        this.unit = unit;
        this.validity = validity;
    }

    public String getUnit() {
        return unit;
    }

    public Instant getValidity() {
        return validity;
    }

    @Override
    public String toString() {
        return "Food{" +
                "quantity=" + quantity +
                ", name='" + name + '\'' +
                ", validity=" + validity +
                ", unit='" + unit + '\'' +
                '}';
    }
}
