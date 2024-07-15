package persistence;

import entities.*;
import entities.enums.ClothingSize;
import entities.enums.ClothingType;
import jakarta.persistence.EntityManager;

import java.time.Instant;
import java.util.ArrayList;

public class Seeding {
    private static Inventory persistDC(EntityManager em, DistributionCenter dc) {
        em.persist(dc);
        var inv = new Inventory(dc);
        em.persist(inv);
        dc.setInventory(inv);
        em.persist(dc);
        return inv;
    }

    public static void seed() {
        var em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();

        var dc1 = new DistributionCenter("Centro de Distribuição Esperança", "Av. Boqueirão, 2450 - Igara, Canoas - RS", "92032-420");
        var dc1_inv = persistDC(em, dc1);
        var dc2 = new DistributionCenter("Centro de Distribuição Prosperidade", "Av. Borges de Medeiros, 1501 – Cidade Baixa, Porto Alegre - RS", "90119-900");
        persistDC(em, dc2);
        var dc3 = new DistributionCenter("Centro de Distribuição Reconstrução", "R. Dr. Décio Martins Costa, 312 - Vila Eunice Nova, Cachoeirinha - RS", "94920-170");
        persistDC(em, dc3);

        var st1 = new Shelter("Abrigo Dois Irmãos", "Rua ABC, Maceió, Alagoas", "Nome do responsável", "82999999999", "email@gmail.com");
        var st2 = new Shelter("Abrigo Santa Cruz", "Rua DEF, Maceió, Alagoas", "Nome da responsável", "82999999999", "email@gmail.com");
        em.persist(st1);
        em.persist(st2);

        var inv2 = new Inventory(st1);
        st1.setInventory(inv2);
        em.persist(inv2);
        em.persist(st1);

        var item1 = new Item("pasta-de-dente", "Pasta de dente colgate");
        var item2 = new Item("camisa", "Camisa verde feminina", ClothingType.M, ClothingSize.L);
        var item3 = new Item("pao-de-queijo", "Pão de queijo de Minas", 1.0, "kg", Instant.now());
        var item4 = new Item("escova-de-dentes", "Escova de dentes");
        item1.setInventory(dc1_inv);
        item2.setInventory(dc1_inv);
        item3.setInventory(dc1_inv);
        item4.setInventory(inv2);
        em.persist(item1);
        em.persist(item2);
        em.persist(item3);
        em.persist(item4);
        dc1_inv.addItem(item1);
        dc1_inv.addItem(item2);
        dc1_inv.addItem(item3);
        inv2.addItem(item4);
        em.persist(dc1_inv);
        em.persist(inv2);

        em.getTransaction().commit();
        em.close();
    }
}
