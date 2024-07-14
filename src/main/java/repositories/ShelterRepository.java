package repositories;

import entities.Shelter;
import jakarta.persistence.EntityManager;
import persistence.JPAUtil;

import java.util.List;
import java.util.Optional;

public class ShelterRepository {
    private final EntityManager em;

    public ShelterRepository() {
        em = JPAUtil.getEntityManagerFactory().createEntityManager();
    }

    public List<Shelter> findAll() {
        return em.createQuery("SELECT c FROM Shelter c", Shelter.class).getResultList();
    }

    public Optional<Shelter> findById(Long id) {
        return Optional.ofNullable(em.find(Shelter.class, id));
    }

    public Shelter save(Shelter shelter) {
        em.getTransaction().begin();
        em.persist(shelter);
        em.getTransaction().commit();
        return shelter;
    }

    public Shelter update(Shelter shelter) {
        em.getTransaction().begin();
        em.merge(shelter);
        em.getTransaction().commit();
        return shelter;
    }

    public void delete(Shelter shelter) {
        em.getTransaction().begin();
        em.remove(shelter);
        em.getTransaction().commit();
    }
}
