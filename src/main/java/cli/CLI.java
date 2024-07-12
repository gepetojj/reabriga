package cli;

import exceptions.OptionOutOfBoundsException;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;

public class CLI {
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
}
