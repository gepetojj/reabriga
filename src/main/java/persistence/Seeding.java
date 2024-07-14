package persistence;

import entities.*;
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

        var st1 = new Shelter("Abrigo Dois Irmãos", "Rua ABC, Maceió, Alagoas", "Nome do responsável", "82999999999", "email@gmail.com");
        var st2 = new Shelter("Abrigo Santa Cruz", "Rua DEF, Maceió, Alagoas", "Nome da responsável", "82999999999", "email@gmail.com");
        em.persist(st1);
        em.persist(st2);

        var inv1 = new Inventory(dc1);
        dc1.setInventory(inv1);
        em.persist(inv1);
        em.persist(dc1);

        var inv2 = new Inventory(st1);
        st1.setInventory(inv2);
        em.persist(inv2);
        em.persist(st1);

        var inv3 = new Inventory(dc2);
        dc2.setInventory(inv3);
        em.persist(inv3);
        em.persist(dc2);

        var item1 = new Item("pasta-de-dente", "Pasta de dente colgate");
        var item2 = new Item("camisa", "Camisa verde feminina", ClothingType.M, ClothingSize.L);
        var item3 = new Item("pao-de-queijo", "Pão de queijo de Minas", 1.0, "kg", Instant.now());
        var item4 = new Item("escova-de-dentes", "Escova de dentes");
        em.persist(item1);
        em.persist(item2);
        em.persist(item3);
        em.persist(item4);
        inv1.addItem(item1);
        inv1.addItem(item2);
        inv1.addItem(item3);
        inv2.addItem(item4);
        em.persist(inv1);
        em.persist(inv2);

        var io1 = new ItemOrder();
        io1.setFromShelter(st1);
        io1.setToDistributionCenter(dc1);
        io1.addItem(item1);
        io1.addItem(item2);
        io1.addItem(item3);
        em.persist(io1);
        dc1.addItemOrder(io1);
        st1.addItemOrder(io1);
        em.persist(dc1);
        em.persist(st1);

        em.getTransaction().commit();
        em.close();
    }
}
