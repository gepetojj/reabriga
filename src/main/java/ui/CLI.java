package ui;

import application.interfaces.CSV;
import application.interfaces.UI;
import entities.Item;
import entities.enums.ClothingSize;
import entities.enums.ClothingType;
import entities.enums.ItemType;
import exceptions.OptionOutOfBoundsException;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;

public class CLI implements UI {
    protected Scanner sc;

    public CLI(Scanner sc) {
        this.sc = sc;
    }

    public void print(String message) {
        System.out.print(message);
    }

    public void println(String message) {
        System.out.println(message);
    }

    public void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void hold() {
        System.out.print("Pressione ENTER para continuar");
        sc.nextLine();
    }

    public int userChoice(List<String> options) throws InputMismatchException, OptionOutOfBoundsException {
        for (int i = 0; i < options.size(); i++) {
            var option = options.get(i);
            System.out.printf("%d. %s\n", i + 1, option);
        }

        System.out.print("\nDigite aqui: ");
        int selected = sc.nextInt();

        if (selected < 0 || selected > options.size()) {
            throw new OptionOutOfBoundsException("A opção deve estar entre 1 e " + (options.size()));
        }
        sc.nextLine();

        return selected;
    }

    public String textInput(String placeholder) {
        System.out.print(placeholder);
        return sc.nextLine();
    }

    public List<Item> getItemsFromCSV(String placeholder) throws Exception {
        System.out.print(placeholder);
        var path = sc.nextLine();

        Path sysPath = Paths.get(ClassLoader.getSystemResource(path).toURI());
        CSV reader = new ui.CSV(sysPath);
        var lines = reader.read();

        var items = new ArrayList<Item>();
        for (var line : lines) {
            // FORMATO CSV: type,name,description,quantity,clothing_type,clothing_size,unit,expiration
            var item = new Item();
            item.setType(ItemType.valueOf(line[0]));
            item.setName(line[1]);
            item.setDescription(line[2]);
            item.setQuantity(Double.parseDouble(line[3]));
            item.setClothingType(ClothingType.valueOf(line[4]));
            item.setClothingSize(ClothingSize.valueOf(line[5]));
            item.setUnit(line[6]);
            item.setExpiration(Instant.parse(line[7]));
            items.add(item);
        }
        return items;
    }
}
