package repositories;

import entities.Inventory;
import entities.ItemOrder;
import jakarta.persistence.EntityManager;
import persistence.JPAUtil;

import java.util.List;
import java.util.Optional;

public class ItemOrderRepository {
    private final EntityManager em;

    public ItemOrderRepository() {
        em = JPAUtil.getEntityManagerFactory().createEntityManager();
    }

    public List<ItemOrder> findAll() {
        return em.createQuery("SELECT c FROM ItemOrder c", ItemOrder.class).getResultList();
    }

    public Optional<ItemOrder> findById(Long id) {
        return Optional.ofNullable(em.find(ItemOrder.class, id));
    }

    public ItemOrder save(ItemOrder itemOrder) {
        em.getTransaction().begin();
        em.persist(itemOrder);
        em.getTransaction().commit();
        return itemOrder;
    }

    public ItemOrder update(ItemOrder itemOrder) {
        em.getTransaction().begin();
        em.merge(itemOrder);
        em.getTransaction().commit();
        return itemOrder;
    }

    public void delete(ItemOrder itemOrder) {
        em.getTransaction().begin();
        em.remove(itemOrder);
        em.getTransaction().commit();
    }
}
