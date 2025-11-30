package Service;

import Domain.entities.Flock;
import Domain.Validator.ValidationException;
import Repository.DuckSocialRepository;

public class FlockService {
    private final DuckSocialRepository repository;

    public FlockService(DuckSocialRepository repository) {
        this.repository = repository;
    }

    // === FLOCK MANAGEMENT ===
    public Flock createFlock(String name, String description) {
        if (name == null || name.isEmpty()) {
            throw new ValidationException("Flock name cannot be empty!");
        }

        Long nextId = 1L;
        for (Flock flock : repository.findAllFlocks()) {
            if (flock.getId() >= nextId) {
                nextId = flock.getId() + 1;
            }
        }

        Flock flock = new Flock(nextId, name, description);
        return repository.saveFlock(flock);
    }

    public Flock updateFlock(Long id, String name, String description) {
        Flock existingFlock = repository.findFlockById(id);
        if (existingFlock == null) {
            throw new ValidationException("Flock not found with ID: " + id);
        }

        if (name != null && !name.isEmpty()) {
            existingFlock.setFlockName(name);
        }
        if (description != null && !description.isEmpty()) {
            existingFlock.setDescription(description);
        }

        // Simulate "update" via delete + save if repository lacks update method
        repository.deleteFlock(id);
        return repository.saveFlock(existingFlock);
    }

    public Flock deleteFlock(Long id) {
        Flock deletedFlock = repository.deleteFlock(id);
        if (deletedFlock == null) {
            throw new ValidationException("Flock not found with ID: " + id);
        }
        return deletedFlock;
    }

    public Flock findFlockById(Long id) {
        return repository.findFlockById(id);
    }

    public Iterable<Flock> findAllFlocks() {
        return repository.findAllFlocks();
    }
}
