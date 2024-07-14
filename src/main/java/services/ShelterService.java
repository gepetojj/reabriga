package services;

import entities.Item;
import entities.Shelter;
import repositories.ShelterRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShelterService {
    ShelterRepository repository;
    DistributionCenterService distributionCenterService;

    public ShelterService() {
        this.repository = new ShelterRepository();
        this.distributionCenterService = new DistributionCenterService();
    }

    public Optional<Shelter> getShelter(Long distributionCenterId) {
        return repository.findById(distributionCenterId);
    }

    public List<Shelter> getAllShelters() {
        return repository.findAll();
    }

    public List<Item> getAvailableItems() {
        var centers = distributionCenterService.getAllDistributionCenters();
        var items = new ArrayList<Item>();
        for (var center : centers) {
            var centerItems = center.getInventory().getItems();
            items.addAll(centerItems);
        }
        return items;
    }
}
