package application;

import application.interfaces.UI;
import cli.CLI;
import persistence.JPAUtil;
import persistence.Seeding;

import javax.management.RuntimeErrorException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UI cli = new CLI(sc);

        try {
            Seeding.seed();

            while (true) {
                try {
                    cli.clear();
                    cli.println("Bem-vindo(a) ao Reabriga!\nFaça login:");
                    var options = new ArrayList<String>();
                    options.add("Entrar como abrigo");
                    options.add("Entrar como centro de distribuição");
                    options.add("Sair");

                    var selected = cli.userChoice(options);
                    if (selected == 1) {
                        new ShelterApp(cli).run();
                    } else if (selected == 2) {

                        new DistributionCenterApp(cli).run();
                    } else {
                        break;
                    }
                } catch (RuntimeException e) {
                    cli.println("[ERRO] " + e.getMessage());
                }
            }
        } finally {
            JPAUtil.shutdown();
        }

        sc.close();
    }
}