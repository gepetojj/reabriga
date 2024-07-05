package application;

import cli.CLI;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CLI cli = new CLI(sc);

        System.out.println("Escolha uma opção:");
        var options = new ArrayList<String>();
        options.add("Opção um");
        options.add("Opção dois");
        var selected = cli.userChoice(options);
        System.out.println(selected);
    }
}