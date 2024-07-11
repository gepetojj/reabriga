package application;

import cli.CLI;
import entities.DistributionCenter;
import services.DistributionCenterService;

import java.util.ArrayList;

public class DistributionCenterApp {
    private final CLI cli;
    private final DistributionCenterService service;

    private DistributionCenter distributionCenter;

    public DistributionCenterApp(CLI cli) {
        this.cli = cli;
        this.service = new DistributionCenterService();
    }

    private void selectDistributionCenter() {
        cli.println("Escolha um centro de distribuição para entrar no painel de administração:");

        var centers = service.getAllDistributionCenters();
        if (centers.isEmpty()) {
            throw new RuntimeException("Não há centros de distribuição cadastrados.");
        }

        var options = new ArrayList<String>(centers.size());
        for (var center : centers) {
            options.add(center.getName());
        }

        var selected = cli.userChoice(options);
        var index = selected - 1;
        distributionCenter = centers.get(index);
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
