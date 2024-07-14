package services;

import entities.Shelter;
import repositories.ShelterRepository;

import java.util.List;
import java.util.Optional;

public class ShelterService {
    ShelterRepository repository;

    public ShelterService() {
        this.repository = new ShelterRepository();
    }

    public Optional<Shelter> getShelter(Long distributionCenterId) {
        return repository.findById(distributionCenterId);
    }

    public List<Shelter> getAllShelters() {
        return repository.findAll();
    }
}
