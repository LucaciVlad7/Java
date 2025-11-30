package Domain.entities;

import Domain.Entity;

import java.time.LocalDateTime;

public class Message extends Entity<Long> {
    private Long id;
    private User sender;
    private User receiver;
    private String content;
    private LocalDateTime timestamp;

    public Message(User sender, User receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getSender() { return sender; }
    public User getReceiver() { return receiver; }
    public String getContent() { return content; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
