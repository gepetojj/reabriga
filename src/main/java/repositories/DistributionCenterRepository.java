package repositories;

import entities.DistributionCenter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class DistributionCenterRepository {
    @PersistenceContext
    private EntityManager em;

    public List<DistributionCenter> findAll() {
        return em.createQuery("SELECT c FROM DistributionCenter c", DistributionCenter.class).getResultList();
    }
}
