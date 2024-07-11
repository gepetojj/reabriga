package repositories;

import entities.DistributionCenter;
import jakarta.persistence.EntityManager;
import persistence.JPAUtil;

import java.util.List;
import java.util.Optional;

public class DistributionCenterRepository {
    private final EntityManager em;

    public DistributionCenterRepository() {
        em = JPAUtil.getEntityManagerFactory().createEntityManager();
    }

    public List<DistributionCenter> findAll() {
        return em.createQuery("SELECT c FROM DistributionCenter c", DistributionCenter.class).getResultList();
    }

    public Optional<DistributionCenter> findById(Long id) {
        return Optional.ofNullable(em.find(DistributionCenter.class, id));
    }

    public DistributionCenter save(DistributionCenter distributionCenter) {
        em.getTransaction().begin();
        em.persist(distributionCenter);
        em.getTransaction().commit();
        return distributionCenter;
    }

    public DistributionCenter update(DistributionCenter distributionCenter) {
        em.getTransaction().begin();
        em.merge(distributionCenter);
        em.getTransaction().commit();
        return distributionCenter;
    }

    public void delete(DistributionCenter distributionCenter) {
        em.getTransaction().begin();
        em.remove(distributionCenter);
        em.getTransaction().commit();
    }
}
