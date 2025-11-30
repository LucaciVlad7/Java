package Domain.Validator;

import Domain.entities.Flock;

public class FlockValidator implements Validator<Flock> {
    @Override
    public void validate(Flock flock) throws ValidationException {
        if (flock == null) {
            throw new ValidationException("Flock cannot be null");
        }

        // Validate ID
        if (flock.getId() == null) {
            throw new ValidationException("Flock ID cannot be null");
        }
        if (flock.getId() <= 0) {
            throw new ValidationException("Flock ID must be a positive number");
        }

        // Validate name
        if (flock.getFlockName() == null || flock.getFlockName().trim().isEmpty()) {
            throw new ValidationException("Flock name cannot be empty");
        }
        if (flock.getFlockName().length() < 2 || flock.getFlockName().length() > 50) {
            throw new ValidationException("Flock name must be between 2 and 50 characters long");
        }

        // Validate members (can be empty but not null)
        if (flock.getMembers() == null) {
            throw new ValidationException("Flock members list cannot be null");
        }
    }
}