package cli;

import java.util.Scanner;
import java.util.List;

public class CLI {
    protected Scanner sc;

    public CLI(Scanner sc) {
        this.sc = sc;
    }

    public int userChoice(List<String> options) {
        for (int i = 0; i < options.size(); i++) {
            var option = options.get(i);
            System.out.printf("%d. %s\n", i + 1, option);
        }
        int selected = sc.nextInt();
        return selected;
    }
}
