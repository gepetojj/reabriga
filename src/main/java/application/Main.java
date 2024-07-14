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

            mainLoop: while (true) {
                try {
                    cli.clear();
                    cli.println("Bem-vindo(a) ao Reabriga!\nFaça login:");
                    var options = new ArrayList<String>();
                    options.add("Entrar como abrigo");
                    options.add("Entrar como centro de distribuição");
                    options.add("Registrar novo abrigo");
                    options.add("Fechar app");

                    var selected = cli.userChoice(options);
                    switch (selected) {
                        default:
                            break mainLoop;

                        case 1:
                            new ShelterApp(cli).run();
                            break;

                        case 2:
                            new DistributionCenterApp(cli).run();
                            break;

                        case 3:
                            new ShelterApp(cli).registerNew();
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