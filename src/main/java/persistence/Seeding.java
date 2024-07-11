package persistence;

import entities.DistributionCenter;

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

        em.getTransaction().commit();
        em.close();
    }
}
