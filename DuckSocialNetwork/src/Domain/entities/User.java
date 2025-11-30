package Domain.entities;

import Domain.Entity;
import Domain.enums.UserType;

import java.util.*;

public abstract class User extends Entity<Long> {
    protected Long id;
    protected String username;
    protected String email;
    protected String password;
    protected List<User> friends;
    protected List<EventHistory> eventHistory;

    public User(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.friends = new ArrayList<>();
        this.eventHistory = new ArrayList<>();
    }

    // Metode abstracte
    public abstract String getDisplayName();
    public abstract UserType getUserType();

    // Metode generale
    public void login() {
        System.out.println(username + " s-a logat.");
    }

    public void logout() {
        System.out.println(username + " s-a delogat.");
    }

    public void sendMessage(User receiver, String content) {
        Message message = new Message(this, receiver, content);
        receiver.receiveMessage(message);
    }

    public void receiveMessage(Message message) {
        System.out.println(username + " a primit mesaj: " + message.getContent());
    }

    public void addFriend(User friend) {
        if (!friends.contains(friend)) {
            friends.add(friend);
            friend.getFriends().add(this); // Prietenie bidirecțională
        }
    }

    public void removeFriend(User friend) {
        friends.remove(friend);
        friend.getFriends().remove(this);
    }

    public void addEventToHistory(EventHistory event) {
        eventHistory.add(event);
    }

    // Getters și Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<User> getFriends() { return friends; }
    public List<EventHistory> getEventHistory() { return eventHistory; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getDisplayName() + " (" + username + ")";
    }
}