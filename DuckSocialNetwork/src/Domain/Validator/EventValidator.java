package Domain.Validator;

import Domain.entities.Event;

public class EventValidator implements Validator<Event> {
    @Override
    public void validate(Event event) throws ValidationException {
        if (event == null) {
            throw new ValidationException("Event cannot be null");
        }

        // Validate ID
        if (event.getId() == null) {
            throw new ValidationException("Event ID cannot be null");
        }
        if (event.getId() <= 0) {
            throw new ValidationException("Event ID must be a positive number");
        }

        // Validate name
        if (event.getName() == null || event.getName().trim().isEmpty()) {
            throw new ValidationException("Event name cannot be empty");
        }
        if (event.getName().length() < 3 || event.getName().length() > 100) {
            throw new ValidationException("Event name must be between 3 and 100 characters long");
        }

        // Validate description
        if (event.getDescription() == null) {
            throw new ValidationException("Event description cannot be null");
        }

        // Validate event type
        if (event.getEventType() == null || event.getEventType().trim().isEmpty()) {
            throw new ValidationException("Event type cannot be empty");
        }

        // Validate creation date
        if (event.getCreationDate() == null) {
            throw new ValidationException("Event creation date cannot be null");
        }
        if (event.getCreationDate().isAfter(java.time.LocalDateTime.now())) {
            throw new ValidationException("Event creation date cannot be in the future");
        }
    }
}