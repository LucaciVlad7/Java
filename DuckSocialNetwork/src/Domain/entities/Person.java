package Domain.entities;

import Domain.enums.EventType;
import Domain.enums.UserType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Person extends User {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String job;
    private int empatyLevel;

    public Person(Long id, String username, String email, String password,
                  String firstName, String lastName, LocalDate birthDate,
                  String job, int empatyLevel){
        super(id, username, email, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.job = job;
        setEmpatyLevel(empatyLevel);//for validation
    }

    @Override
    public String getDisplayName() {
        return firstName + " " + lastName;
    }
    @Override
    public UserType getUserType() {
        return UserType.PERSON;
    }

    public Event createEvent(String eventName, String eventDescription, String eventType) {
        Event event = new Event(null,eventName, eventDescription, eventType,this);
        System.out.println(getDisplayName() + " created: " + eventName);

        EventHistory eventHistory = new EventHistory(null,this, EventType.EVENT_CREATED,
                "Created: " + eventName);
        addEventToHistory(eventHistory);

        return event;
    }

    @Override
    public void sendMessage(User receiver, String content) {
        super.sendMessage(receiver, content);

        EventHistory eventHistory = new EventHistory(null, this,EventType.MESSAGE_SENT,
                "Sent message to " + receiver.getDisplayName() + ":" + content);
        addEventToHistory(eventHistory);
    }

    public void sendMessage(Duck duck, String content){
        sendMessage(duck, content);
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getJob() { return job; }
    public void setJob(String job) { this.job = job; }

    public int getEmpatyLevel() { return empatyLevel; }

    public void setEmpatyLevel(int empatyLevel) {
        if (empatyLevel < 1 || empatyLevel > 10) {
            throw new IllegalArgumentException("Empathy level must be between 1 and 10");
        }
        this.empatyLevel = empatyLevel;
    }

    public int getAge() {
        return LocalDate.now().getYear() - birthDate.getYear();
    }

    public String getEmpathyDescription() {
        if (empatyLevel <= 3) return "Low empathy";
        if (empatyLevel <= 6) return "Medium empathy";
        return "High empathy";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        if (!super.equals(o)) return false;
        Person person = (Person) o;
        return empatyLevel == person.empatyLevel &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(birthDate, person.birthDate) &&
                Objects.equals(job, person.job);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, birthDate, job, empatyLevel);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return "Person: " + getDisplayName() +
                " | Age: " + getAge() +
                " | Job: " + job +
                " | Empathy: " + empatyLevel + "/10 (" + getEmpathyDescription() + ")" +
                " | Username: " + username;
    }

}
