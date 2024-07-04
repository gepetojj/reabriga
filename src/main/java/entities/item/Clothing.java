package entities.item;

import entities.enums.ClothingSize;
import entities.enums.ClothingType;

public class Clothing extends Item {
    ClothingType type;
    ClothingSize size;

    public Clothing(ClothingType type, ClothingSize size) {
        this.type = type;
        this.size = size;
    }

    public Clothing(String name, Double quantity, ClothingType type, ClothingSize size) {
        super(name, quantity);
        this.type = type;
        this.size = size;
    }

    public ClothingType getType() {
        return type;
    }

    public ClothingSize getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "Clothing{" +
                "type=" + type +
                ", size=" + size +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
