package application;

import cli.CLI;
import jakarta.persistence.EntityManager;
import persistence.JPAUtil;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CLI cli = new CLI(sc);

        EntityManager manager = JPAUtil.getEntityManagerFactory().createEntityManager();
        manager.getTransaction().begin();
        manager.getTransaction().commit();
        manager.close();
        JPAUtil.shutdown();

        cli.println("Bem-vindo(a) ao Reabriga!\nFaça login:");
        var options = new ArrayList<String>();
        options.add("Entrar como abrigo");
        options.add("Entrar como centro de distribuição");

        var selected = cli.userChoice(options);
        if (selected == 1) {
            new ShelterApp(cli).run();
        } else {
            new DistributionCenterApp(cli).run();
        }
    }
}