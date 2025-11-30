package Domain.entities;

import Domain.enums.DuckType;
import Domain.enums.EventType;
import Domain.enums.UserType;

import java.util.Objects;

public class Duck extends User {
    private DuckType duckType;
    private double speed;
    private double resistance;
    private Flock flock;

    public Duck(Long id, String username, String email, String password,
                DuckType tip, double speed, double resistance) {
        super(id, username, email, password);
        this.duckType = tip;
        this.speed = speed;
        this.resistance = resistance;
        this.flock = null;
    }

    @Override
    public String getDisplayName() {
        return "Duck" + username;
    }

    @Override
    public UserType getUserType() {
        return UserType.DUCK;
    }

    public void joinCard(Flock flock) {
        if (this.flock != null) {
            this.flock.removeDuck(this);
        }
        this.flock = flock;
        if (flock != null && !flock.getMembers().contains(this)) {
            flock.addDuck(this);
        }

        EventHistory eventHistory = new EventHistory(null, this, EventType.JOINED_FLOCK,
                "Joined card: " + (flock != null ? flock.getFlockName() : "none"));
        addEventToHistory(eventHistory);
    }

    public void leaveCard() {
        if (this.flock != null) {
            String cardName = this.flock.getFlockName();
            this.flock.removeDuck(this);
            this.flock = null;

            EventHistory eventHistory = new EventHistory(null, this, EventType.LEFT_FLOCK,
                    "Left card: " + cardName);
            addEventToHistory(eventHistory);
        }
    }

    public UserType getDuckType() {return UserType.DUCK;}
    public DuckType getType() {return duckType;}
    public double getSpeed() {return speed;}
    public double getResistance() {return resistance;}
    public Flock getFlock() {return flock;}

    public void setType(DuckType duckType) {this.duckType = duckType;}
    public void setSpeed(double speed) {this.speed = speed;}
    public void setResistance(double resistance) {
        if (resistance < 1 || resistance > 10) {
            throw new IllegalArgumentException("Resistance must be between 1 and 10");
        }
        this.resistance = resistance;
    }
    public void setFlock(Flock flock) {this.flock = flock;}


    public void participateInEvent(Event event,String role) {
        System.out.println(getDisplayName() + " participates in event '" +
                event.getName() + "' as " + role);

        EventHistory eventHistory = new EventHistory(null, this, EventType.RACE_PARTICIPATION,
                "Participates in '" + event.getName() + "' as " + role);
        addEventToHistory(eventHistory);
    }

    public void compleTraining(){
        String automaticMessage = "Quack! I finished training! 🦆";
        System.out.println(getDisplayName() + ": " + automaticMessage);

        // Trimite mesaj automat prietenilor
        for (User friend : getFriends()) {
            if (friend instanceof Person) {
                sendMessage(friend, automaticMessage);
            }
        }

        EventHistory eventHistory = new EventHistory(null, this, EventType.TRAINING_COMPLETED,
                "Completed training");
        addEventToHistory(eventHistory);
    }

    @Override
    public void sendMessage(User receiver, String content) {
        Message message = new Message(this, receiver, content);
        receiver.receiveMessage(message);

        EventHistory eventHistory = new EventHistory(null, this, EventType.MESSAGE_SENT,
                "Sent duck message to " + receiver.getDisplayName());
        addEventToHistory(eventHistory);
    }

    public double getPerformance() {
        return (speed * 0.6) + (resistance * 0.4);
    }

    public boolean canFly() {
        return duckType == DuckType.FLYING || duckType == DuckType.FLYING_AND_SWIMMING;
    }

    public boolean canSwim() {
        return duckType == DuckType.SWIMMING || duckType == DuckType.FLYING_AND_SWIMMING;
    }

    public String getSkills() {
        StringBuilder skills = new StringBuilder();
        if (canFly()) skills.append("Flying");
        if (canSwim()) {
            if (skills.length() > 0) skills.append(", ");
            skills.append("Swimming");
        }
        return skills.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Duck)) return false;
        if (!super.equals(o)) return false;
        Duck duck = (Duck) o;
        return Double.compare(duck.speed, speed) == 0 &&
                Double.compare(duck.resistance, resistance) == 0 &&
                duckType == duck.duckType &&
                Objects.equals(flock, duck.flock);
    }

    public Flock getCard() { return flock; }
    public void setCard(Flock flock) { this.flock = flock; }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), duckType, speed, resistance, flock);
    }

    @Override
    public String toString() {
        return "Duck: " + username +
                " | Type: " + duckType +
                " | Speed: " + String.format("%.1f", speed) + " km/h" +
                " | Resistance: " + String.format("%.1f", resistance) + "/10" +
                " | Performance: " + String.format("%.1f", getPerformance()) +
                " | Skills: " + getSkills() +
                " | Card: " + (flock != null ? flock.getFlockName() : "free");
    }
}
