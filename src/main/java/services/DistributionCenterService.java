package services;

import entities.DistributionCenter;
import repositories.DistributionCenterRepository;

import java.util.Optional;
import java.util.List;

public class DistributionCenterService {
    DistributionCenterRepository distributionCenterRepository;

    public DistributionCenterService(DistributionCenterRepository distributionCenterRepository) {
        this.distributionCenterRepository = distributionCenterRepository;
    }

    public Optional<DistributionCenter> getDistributionCenter(Long distributionCenterId) {
        return distributionCenterRepository.findById(distributionCenterId);
    }

    public List<DistributionCenter> getAllDistributionCenters() {
        return distributionCenterRepository.findAll();
    }
}
