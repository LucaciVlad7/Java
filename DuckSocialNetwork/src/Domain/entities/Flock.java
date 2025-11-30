package Domain.entities;

import Domain.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Flock extends Entity<Long> {
    private Long id;
    private String name;
    private String description; // ✅ added
    private List<Duck> members;

    public Flock(Long id, String name, String description) { // ✅ new constructor
        this.id = id;
        this.name = name;
        this.description = description;
        this.members = new ArrayList<>();
    }

    // keep your existing 2-arg constructor for backward compatibility
    public Flock(Long id, String name) {
        this(id, name, ""); // default empty description
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFlockName() { return name; }
    public void setFlockName(String name) { this.name = name; }

    public String getDescription() { return description; } // ✅ getter
    public void setDescription(String description) { this.description = description; } // ✅ setter

    public List<Duck> getMembers() { return new ArrayList<>(members); }
    public int getMemberCount() { return members.size(); }

    public void addDuck(Duck duck) {
        if (!members.contains(duck)) {
            members.add(duck);
            if (duck.getFlock() != this) {
                duck.setFlock(this);
            }
        }
    }

    public void removeDuck(Duck duck) {
        members.remove(duck);
        if (duck.getFlock() != this) {
            duck.setFlock(null);
        }
    }

    public double getAveragePerformace() {
        if (members.isEmpty()) return 0;
        return members.stream().mapToDouble(Duck::getPerformance).average().orElse(0);
    }

    public double getAverageSpeed() {
        if (members.isEmpty()) return 0;
        return members.stream().mapToDouble(Duck::getSpeed).average().orElse(0);
    }

    public double getAverageResistance() {
        if (members.isEmpty()) return 0;
        return members.stream().mapToDouble(Duck::getResistance).average().orElse(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flock)) return false;
        Flock flock = (Flock) o;
        return Objects.equals(id, flock.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Card: " + name +
                " | Members: " + members.size() +
                " | Avg Performance: " + String.format("%.2f", getAveragePerformace()) +
                " | Avg Speed: " + String.format("%.1f", getAverageSpeed()) + " km/h" +
                " | Avg Resistance: " + String.format("%.1f", getAverageResistance()) + "/10" +
                " | Description: " + (description == null || description.isEmpty() ? "N/A" : description);
    }
}
