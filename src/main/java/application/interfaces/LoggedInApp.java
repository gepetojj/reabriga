package application.interfaces;

import cli.CLI;
import entities.Item;
import entities.enums.ItemType;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public interface LoggedInApp {
    default void displayItems(CLI cli, List<Item> items) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")
                .withZone(ZoneId.systemDefault());
        for (var item : items) {
            if (item.getType() == ItemType.HYGIENE) {
                cli.println(item.getDescription() + " - " + item.getQuantity());
            } else if (item.getType() == ItemType.CLOTHING) {
                cli.println(item.getDescription() + " - Tipo (M/F): " + item.getClothingType() + " | Tamanho: " + item.getClothingSize());
            } else {
                cli.println(item.getDescription() + " - " + item.getQuantity() + " " + item.getUnit() + " (" + formatter.format(item.getExpiration()) + ")");
            }
        }
    }

    void run();
}
