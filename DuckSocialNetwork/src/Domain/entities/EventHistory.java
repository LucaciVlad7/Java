package Domain.entities;

import Domain.Entity;
import Domain.enums.EventType;
import java.time.LocalDateTime;
import java.util.Objects;

public class EventHistory extends Entity<Long> {
    private Long id;
    private User user;
    private EventType eventType;
    private String description;
    private LocalDateTime timestamp;

    public EventHistory(Long id, User user, EventType eventType, String description) {
        this.id = id;
        this.user = user;
        this.eventType = eventType;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    // Getters și Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public EventType getEventType() { return eventType; }
    public void setEventType(EventType eventType) { this.eventType = eventType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventHistory)) return false;
        EventHistory that = (EventHistory) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + eventType + ": " + description;
    }
}