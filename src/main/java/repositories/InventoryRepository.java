package repositories;

import entities.Inventory;
import jakarta.persistence.EntityManager;
import persistence.JPAUtil;

import java.util.List;
import java.util.Optional;

public class InventoryRepository {
    private final EntityManager em;

    public InventoryRepository() {
        em = JPAUtil.getEntityManagerFactory().createEntityManager();
    }

    public List<Inventory> findAll() {
        return em.createQuery("SELECT c FROM Inventory c", Inventory.class).getResultList();
    }

    public Optional<Inventory> findById(Long id) {
        return Optional.ofNullable(em.find(Inventory.class, id));
    }

    public Inventory save(Inventory inventory) {
        em.getTransaction().begin();
        em.persist(inventory);
        em.getTransaction().commit();
        return inventory;
    }

    public Inventory update(Inventory inventory) {
        em.getTransaction().begin();
        em.merge(inventory);
        em.getTransaction().commit();
        return inventory;
    }

    public void delete(Inventory inventory) {
        em.getTransaction().begin();
        em.remove(inventory);
        em.getTransaction().commit();
    }
}
