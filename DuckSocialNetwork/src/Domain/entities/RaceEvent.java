package Domain.entities;

import java.util.*;

public class RaceEvent extends Event {
    private List<Duck> participants;
    private List<Double> distances; // distanțele balizelor
    private Map<Duck, Double> raceResults; // rezultatele cursei (rață -> timp)
    private boolean raceCompleted;

    // Fixed constructor - removed User parameter, added eventType
    public RaceEvent(Long id, String name, String description, String eventType, User administrator) {
        super(id, name, description, eventType, administrator);
        this.participants = new ArrayList<>();
        this.distances = new ArrayList<>();
        this.raceResults = new HashMap<>();
        this.raceCompleted = false;
    }

    // Additional constructor for convenience
    public RaceEvent(Long id, String name, String description, String eventType, User administrator, List<Double> distances) {
        super(id, name, description, eventType, administrator);
        this.participants = new ArrayList<>();
        this.distances = new ArrayList<>(distances);
        this.raceResults = new HashMap<>();
        this.raceCompleted = false;
    }

    // === METODE SPECIFICE CURSEI ===
    public void addParticipant(Duck duck) {
        if (!participants.contains(duck)) {
            participants.add(duck);
            System.out.println("Duck " + duck.getUsername() + " added to race: " + getName());
        }
    }

    public void removeParticipant(Duck duck) {
        if (participants.remove(duck)) {
            System.out.println("Duck " + duck.getUsername() + " removed from race: " + getName());
        }
    }

    public List<Duck> selectRacers(int m) {
        if (m <= 0 || m > participants.size()) {
            System.out.println("Invalid number of racers requested: " + m);
            return new ArrayList<>();
        }

        // Sortăm rațele după rezistență (descrescător) și apoi după viteză (descrescător)
        List<Duck> sortedDucks = new ArrayList<>(participants);
        sortedDucks.sort((d1, d2) -> {
            int resistanceCompare = Double.compare(d2.getResistance(), d1.getResistance());
            if (resistanceCompare != 0) return resistanceCompare;
            return Double.compare(d2.getSpeed(), d1.getSpeed());
        });

        // Verificăm dacă ordinea rezistențelor este validă (r₁ ≥ r₂ ≥ ... ≥ rₘ)
        List<Duck> selectedRacers = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            selectedRacers.add(sortedDucks.get(i));
        }

        // Verificăm validitatea ordinii rezistențelor
        if (!isValidResistanceOrder(selectedRacers)) {
            System.out.println("Invalid resistance order for selected racers");
            return new ArrayList<>();
        }

        System.out.println("Selected " + m + " racers for race: " + getName());
        return selectedRacers;
    }

    private boolean isValidResistanceOrder(List<Duck> racers) {
        for (int i = 0; i < racers.size() - 1; i++) {
            if (racers.get(i).getResistance() < racers.get(i + 1).getResistance()) {
                return false;
            }
        }
        return true;
    }

    public boolean simulateRace() {
        if (raceCompleted) {
            System.out.println("Race already completed!");
            return false;
        }

        List<Duck> racers = selectRacers(Math.min(participants.size(), distances.size()));
        if (racers.isEmpty()) {
            System.out.println("No valid racers selected for the race");
            return false;
        }

        raceResults.clear();

        for (Duck duck : racers) {
            double maxTime = 0.0;
            for (Double distance : distances) {
                double time = (2 * distance) / duck.getSpeed();
                if (time > maxTime) {
                    maxTime = time;
                }
            }
            raceResults.put(duck, maxTime);
        }

        raceCompleted = true;

        notifySubscribers("Race completed! Results available.");
        printRaceResults();

        return true;
    }

    public void printRaceResults() {
        if (!raceCompleted) {
            System.out.println("Race not completed yet!");
            return;
        }

        System.out.println("=== RACE RESULTS: " + getName() + " ===");

        List<Map.Entry<Duck, Double>> sortedResults = new ArrayList<>(raceResults.entrySet());
        sortedResults.sort(Map.Entry.comparingByValue());

        for (int i = 0; i < sortedResults.size(); i++) {
            Map.Entry<Duck, Double> entry = sortedResults.get(i);
            System.out.printf("Duck %d on lane %d: t = %.3f s (Speed: %.1f km/h, Resistance: %.1f)%n",
                    entry.getKey().getId(), i + 1, entry.getValue(),
                    entry.getKey().getSpeed(), entry.getKey().getResistance());
        }
    }

    public List<Duck> getParticipants() { return new ArrayList<>(participants); }
    public List<Double> getDistances() { return new ArrayList<>(distances); }
    public Map<Duck, Double> getRaceResults() { return new HashMap<>(raceResults); }
    public boolean isRaceCompleted() { return raceCompleted; }
    public int getParticipantCount() { return participants.size(); }

    public void setDistances(List<Double> distances) {
        this.distances = new ArrayList<>(distances);
        notifySubscribers("Race distances updated");
    }

    @Override
    public String toString() {
        return "RaceEvent: " + getName() + " | Participants: " + participants.size() +
                " | Distances: " + distances + " | Completed: " + raceCompleted +
                " | Subscribers: " + getSubscriberCount();
    }
}