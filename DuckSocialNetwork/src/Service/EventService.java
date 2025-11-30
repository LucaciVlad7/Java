package Service;

import Domain.entities.Event;
import Domain.entities.RaceEvent;
import Domain.entities.User;
import Domain.Validator.ValidationException;
import Repository.DuckSocialRepository;

import java.util.List;

public class EventService {
    private final DuckSocialRepository repository;

    public EventService(DuckSocialRepository repository) {
        this.repository = repository;
    }

    // === EVENT MANAGEMENT ===
    public Event addEvent(String name, String description, String eventType, User administrator) {
        Long nextId = 1L;
        for (Event event : repository.findAllEvents()) {
            if (event.getId() >= nextId) {
                nextId = event.getId() + 1;
            }
        }

        Event event = new Event(nextId, name, description, eventType, administrator);
        return repository.saveEvent(event);
    }

    public Event findEventById(Long id) {
        return repository.findEventById(id);
    }

    public Iterable<Event> findAllEvents() {
        return repository.findAllEvents();
    }
}