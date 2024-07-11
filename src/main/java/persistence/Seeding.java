package persistence;

import entities.DistributionCenter;
import entities.Inventory;
import entities.Item;
import entities.enums.ClothingSize;
import entities.enums.ClothingType;

import java.time.Instant;

public class Seeding {
    public static void seed() {
        var em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();

        var dc1 = new DistributionCenter("Distribuidora Toledo", "Rua ABC, Arapiraca, Alagoas", "57000000");
        var dc2 = new DistributionCenter("Centro de ajuda Marques", "Rua DEF, Arapiraca, Alagoas", "57000000");
        var dc3 = new DistributionCenter("Armazém Guimarães", "Rua HIJ, Arapiraca, Alagoas", "57000000");
        em.persist(dc1);
        em.persist(dc2);
        em.persist(dc3);

        var inv1 = new Inventory(dc1);
        dc1.setInventory(inv1);
        em.persist(inv1);
        em.persist(dc1);

        var item1 = new Item("pasta-de-dente", "Pasta de dente colgate");
        var item2 = new Item("camisa", "Camisa verde feminina", ClothingType.M, ClothingSize.L);
        var item3 = new Item("pao-de-queijo", "Pão de queijo de Minas", 1.0, "kg", Instant.now());
        em.persist(item1);
        em.persist(item2);
        em.persist(item3);
        inv1.addItem(item1);
        inv1.addItem(item2);
        inv1.addItem(item3);
        em.persist(inv1);

        em.getTransaction().commit();
        em.close();
    }
}
