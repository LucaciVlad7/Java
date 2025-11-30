package Domain.entities;

import Domain.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Event extends Entity<Long> {
    private Long id;
    private String name;
    private String description;
    private String eventType;
    private User administrator;
    private LocalDateTime creationDate;
    private List<User> subscribers;

    public Event(Long id, String name, String description, String eventType, User administrator) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.eventType = eventType;
        this.administrator = administrator;
        this.creationDate = LocalDateTime.now();
        this.subscribers = new ArrayList<>();
    }

    public void subscribe(User user) {
        if (!subscribers.contains(user)) {
            subscribers.add(user);
            System.out.println(user.getDisplayName() + " subscribed to event: " + name);
        }
    }

    public void unsubscribe(User user) {
        if (subscribers.remove(user)) {
            System.out.println(user.getDisplayName() + " unsubscribed from event: " + name);
        }
    }

    public void notifySubscribers(String message) {
        for (User subscriber : subscribers) {
            subscriber.receiveMessage(new Message(this.administrator, subscriber,
                    "Event Notification [" + name + "]: " + message));
        }
        System.out.println("Notified " + subscribers.size() + " subscribers about: " + message);
    }

    public void notifySubscribers() {
        notifySubscribers("Event '" + name + "' has been updated.");
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public User getAdministrator() { return administrator; }
    public void setAdministrator(User administrator) { this.administrator = administrator; }

    public LocalDateTime getCreationDate() { return creationDate; }
    public List<User> getSubscribers() { return new ArrayList<>(subscribers); }
    public int getSubscriberCount() { return subscribers.size(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Event: " + name + " | Type: " + eventType + " | Administrator: " + administrator.getDisplayName();
    }
}