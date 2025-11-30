package Service;

import Domain.entities.*;
import Domain.Validator.ValidationException;
import Repository.DuckSocialRepository;

import java.util.*;

public class RaceService {
    private final DuckSocialRepository repository;

    public RaceService(DuckSocialRepository repository) {
        this.repository = repository;
    }

    // === RACE-SPECIFIC METHODS ===
    public RaceEvent createRaceEvent(String name, String description, User administrator, List<Double> distances) {
        Long nextId = 1L;
        for (Event event : repository.findAllEvents()) {
            if (event.getId() >= nextId) {
                nextId = event.getId() + 1;
            }
        }

        RaceEvent raceEvent = new RaceEvent(nextId, name, description, "RACE", administrator);
        raceEvent.setDistances(distances);

        Event savedEvent = repository.saveEvent(raceEvent);
        return (RaceEvent) savedEvent;
    }

    public void addParticipantToRace(Long raceEventId, Long duckId) {
        Event event = repository.findEventById(raceEventId);
        User user = repository.findOne(duckId);

        if (!(event instanceof RaceEvent)) {
            throw new ValidationException("Event with ID " + raceEventId + " is not a race event");
        }
        if (!(user instanceof Duck)) {
            throw new ValidationException("User with ID " + duckId + " is not a duck");
        }

        RaceEvent raceEvent = (RaceEvent) event;
        Duck duck = (Duck) user;
        raceEvent.addParticipant(duck);

        repository.saveEvent(raceEvent);
    }

    public void removeParticipantFromRace(Long raceEventId, Long duckId) {
        Event event = repository.findEventById(raceEventId);
        User user = repository.findOne(duckId);

        if (!(event instanceof RaceEvent)) {
            throw new ValidationException("Event with ID " + raceEventId + " is not a race event");
        }
        if (!(user instanceof Duck)) {
            throw new ValidationException("User with ID " + duckId + " is not a duck");
        }

        RaceEvent raceEvent = (RaceEvent) event;
        Duck duck = (Duck) user;
        raceEvent.removeParticipant(duck);

        repository.saveEvent(raceEvent);
    }

    public Map<Duck, Double> calculateRaceResults(Long raceEventId) {
        Event event = repository.findEventById(raceEventId);
        if (!(event instanceof RaceEvent)) {
            throw new ValidationException("Event with ID " + raceEventId + " is not a race event");
        }

        RaceEvent raceEvent = (RaceEvent) event;
        Map<Duck, Double> results = new HashMap<>();

        for (Duck duck : raceEvent.getParticipants()) {
            double totalDistance = raceEvent.getDistances().stream().mapToDouble(Double::doubleValue).sum();
            double speedFactor = duck.getSpeed();
            double resistanceFactor = duck.getResistance();

            // Scoring formula: (speed * resistance * totalDistance) / 100
            double score = (speedFactor * resistanceFactor * totalDistance) / 100.0;
            results.put(duck, score);
        }

        // Sort by score descending
        return results.entrySet().stream()
                .sorted(Map.Entry.<Duck, Double>comparingByValue().reversed())
                .collect(LinkedHashMap::new,
                        (map, entry) -> map.put(entry.getKey(), entry.getValue()),
                        Map::putAll);
    }

    public List<Duck> getRaceWinners(Long raceEventId) {
        Map<Duck, Double> results = calculateRaceResults(raceEventId);
        return new ArrayList<>(results.keySet());
    }

    public List<RaceEvent> findAllRaceEvents() {
        List<RaceEvent> raceEvents = new ArrayList<>();
        for (Event event : repository.findAllEvents()) {
            if (event instanceof RaceEvent) {
                raceEvents.add((RaceEvent) event);
            }
        }
        return raceEvents;
    }

    public boolean simulateRace(Long raceEventId) {
        Event event = repository.findEventById(raceEventId);
        if (!(event instanceof RaceEvent)) {
            throw new ValidationException("Event with ID " + raceEventId + " is not a race event");
        }

        RaceEvent raceEvent = (RaceEvent) event;
        return raceEvent.simulateRace();
    }
}