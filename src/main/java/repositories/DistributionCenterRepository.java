package repositories;

import entities.DistributionCenter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

public class DistributionCenterRepository {
    @PersistenceContext
    private EntityManager em;

    public List<DistributionCenter> findAll() {
        return em.createQuery("SELECT c FROM DistributionCenter c", DistributionCenter.class).getResultList();
    }

    public Optional<DistributionCenter> findById(Long id) {
        return Optional.ofNullable(em.find(DistributionCenter.class, id));
    }

    public DistributionCenter save(DistributionCenter distributionCenter) {
        em.persist(distributionCenter);
        return distributionCenter;
    }

    public DistributionCenter update(DistributionCenter distributionCenter) {
        em.merge(distributionCenter);
        return distributionCenter;
    }

    public void delete(DistributionCenter distributionCenter) {
        em.remove(distributionCenter);
    }
}
