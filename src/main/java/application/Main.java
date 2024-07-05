package application;

import cli.CLI;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CLI cli = new CLI(sc);

        System.out.println("Bem-vindo(a) ao Reabriga!\nFaça login:");
        var options = new ArrayList<String>();
        options.add("Entrar como abrigo");
        options.add("Entrar como centro de distribuição");

        var selected = cli.userChoice(options);
        if (selected == 1) {
            new ShelterApp(sc);
        } else {
            new DistributionCenterApp(sc);
        }
    }
}