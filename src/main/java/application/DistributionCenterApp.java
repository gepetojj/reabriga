package application;

import cli.CLI;
import entities.DistributionCenter;

import java.util.ArrayList;

public class DistributionCenterApp {
    private final CLI cli;
    private DistributionCenter distributionCenter;

    public DistributionCenterApp(CLI cli) {
        this.cli = cli;
    }

    private void selectDistributionCenter() {
        cli.println("Escolha um centro de distribuição para entrar no painel de administração:");

        var options = new ArrayList<String>();
        options.add("Centro de distribuição 1");
        options.add("Centro de distribuição 2");

        var selected = cli.userChoice(options);
        if (selected == 2) {
            distributionCenter = new DistributionCenter("Centro de distribuição 1", "Rua abc", "57000000");
        } else {
            distributionCenter = new DistributionCenter("Centro de distribuição 2", "Rua def", "57000000");
        }
    }

    public void run() {
        selectDistributionCenter();

        while (true) {
            cli.clear();
            cli.println("Administrando o centro " + distributionCenter.getName());
            cli.println("Selecione a opção:");

            var options = new ArrayList<String>();
            options.add("Ver itens no inventário");
            options.add("Ordens de pedido");
            options.add("Transferência de doações");
            options.add("Sair");

            var selected = cli.userChoice(options);
            switch (selected){
                default:
                    return;

                case 1:
                    cli.println("" + selected);
                    break;

                case 2:
                    cli.println("" + selected);
                    break;

                case 3:
                    cli.println("" + selected);
                    break;
            }
        }
    }
}
