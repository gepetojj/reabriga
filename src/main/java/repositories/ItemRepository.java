package repositories;

import entities.Item;
import jakarta.persistence.EntityManager;
import persistence.JPAUtil;

import java.util.List;
import java.util.Optional;

public class ItemRepository {
    private final EntityManager em;

    public ItemRepository() {
        em = JPAUtil.getEntityManagerFactory().createEntityManager();
    }

    public List<Item> findAll() {
        return em.createQuery("SELECT c FROM Item c", Item.class).getResultList();
    }

    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(em.find(Item.class, id));
    }

    public Item save(Item item) {
        em.getTransaction().begin();
        em.persist(item);
        em.getTransaction().commit();
        return item;
    }

    public Item update(Item item) {
        em.getTransaction().begin();
        em.merge(item);
        em.getTransaction().commit();
        return item;
    }

    public void delete(Item item) {
        em.getTransaction().begin();
        em.remove(item);
        em.getTransaction().commit();
    }
}
