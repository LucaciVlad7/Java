package Repository;

import Domain.entities.*;
import Domain.enums.*;
import Domain.Friend;
import Domain.Tuple;
import Domain.Validator.Validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class DuckNetworkRepository implements Repository<Long, Object> {
    private Map<Long, User> users;
    private Map<Long, Flock> flocks;
    private Map<Long, Event> events;
    private Map<Long, Message> messages;
    private Map<Long, EventHistory> eventHistories;
    private Map<Tuple<Long, Long>, Friend> friendships;

    private Long nextUserId = 1L;
    private Long nextFlockId = 1L;
    private Long nextEventId = 1L;
    private Long nextMessageId = 1L;
    private Long nextHistoryId = 1L;

    private Validator<Object> validator;

    public DuckNetworkRepository(Validator<Object> validator) {
        this.users = new HashMap<>();
        this.flocks = new HashMap<>();
        this.events = new HashMap<>();
        this.messages = new HashMap<>();
        this.eventHistories = new HashMap<>();
        this.friendships = new HashMap<>();
        this.validator = validator;
    }

    // === CORE REPOSITORY INTERFACE IMPLEMENTATION ===

    @Override
    public Object findOne(Long id) {
        // Search in all entity maps
        if (users.containsKey(id)) return users.get(id);
        if (flocks.containsKey(id)) return flocks.get(id);
        if (events.containsKey(id)) return events.get(id);
        if (messages.containsKey(id)) return messages.get(id);
        if (eventHistories.containsKey(id)) return eventHistories.get(id);
        return null;
    }

    @Override
    public Iterable<Object> findAll() {
        List<Object> allEntities = new ArrayList<>();
        allEntities.addAll(users.values());
        allEntities.addAll(flocks.values());
        allEntities.addAll(events.values());
        allEntities.addAll(messages.values());
        allEntities.addAll(eventHistories.values());
        allEntities.addAll(friendships.values());
        return allEntities;
    }

    @Override
    public Object save(Object entity) {
        if (entity == null) throw new IllegalArgumentException("Entity must not be null");

        validator.validate(entity);

        if (entity instanceof User) {
            User user = (User) entity;
            if (users.containsKey(user.getId())) return entity;
            users.put(user.getId(), user);
            return null;
        } else if (entity instanceof Flock) {
            Flock flock = (Flock) entity;
            if (flocks.containsKey(flock.getId())) return entity;
            flocks.put(flock.getId(), flock);
            return null;
        } else if (entity instanceof Event) {
            Event event = (Event) entity;
            if (events.containsKey(event.getId())) return entity;
            events.put(event.getId(), event);
            return null;
        } else if (entity instanceof Message) {
            Message message = (Message) entity;
            if (messages.containsKey(message.getId())) return entity;
            messages.put(message.getId(), message);
            return null;
        } else if (entity instanceof EventHistory) {
            EventHistory history = (EventHistory) entity;
            if (eventHistories.containsKey(history.getId())) return entity;
            eventHistories.put(history.getId(), history);
            return null;
        } else if (entity instanceof Friend) {
            Friend friend = (Friend) entity;
            if (friendships.containsKey(friend.getId())) return entity;
            friendships.put(friend.getId(), friend);
            return null;
        }

        return entity;
    }

    @Override
    public Object delete(Long id) {
        // Search and remove from all entity maps
        if (users.containsKey(id)) {
            return users.remove(id);
        }
        if (flocks.containsKey(id)) {
            return flocks.remove(id);
        }
        if (events.containsKey(id)) {
            return events.remove(id);
        }
        if (messages.containsKey(id)) {
            return messages.remove(id);
        }
        if (eventHistories.containsKey(id)) {
            return eventHistories.remove(id);
        }
        return null;
    }

    @Override
    public Object update(Object entity) {
        if (entity == null) throw new IllegalArgumentException("Entity must not be null");

        validator.validate(entity);

        if (entity instanceof User) {
            User user = (User) entity;
            if (!users.containsKey(user.getId())) return entity;
            users.put(user.getId(), user);
            return null;
        } else if (entity instanceof Flock) {
            Flock flock = (Flock) entity;
            if (!flocks.containsKey(flock.getId())) return entity;
            flocks.put(flock.getId(), flock);
            return null;
        } else if (entity instanceof Event) {
            Event event = (Event) entity;
            if (!events.containsKey(event.getId())) return entity;
            events.put(event.getId(), event);
            return null;
        } else if (entity instanceof Message) {
            Message message = (Message) entity;
            if (!messages.containsKey(message.getId())) return entity;
            messages.put(message.getId(), message);
            return null;
        } else if (entity instanceof EventHistory) {
            EventHistory history = (EventHistory) entity;
            if (!eventHistories.containsKey(history.getId())) return entity;
            eventHistories.put(history.getId(), history);
            return null;
        }

        return entity;
    }

    @Override
    public int size() {
        return users.size() + flocks.size() + events.size() + messages.size() +
                eventHistories.size() + friendships.size();
    }

    // === SPECIALIZED FIND METHODS ===

    public User findUserById(Long id) {
        return users.get(id);
    }

    public Flock findFlockById(Long id) {
        return flocks.get(id);
    }

    public Event findEventById(Long id) {
        return events.get(id);
    }

    public Message findMessageById(Long id) {
        return messages.get(id);
    }

    public List<User> findAllUsers() {
        return new ArrayList<>(users.values());
    }

    public List<Flock> findAllFlocks() {
        return new ArrayList<>(flocks.values());
    }

    public List<Event> findAllEvents() {
        return new ArrayList<>(events.values());
    }

    public List<Message> findAllMessages() {
        return new ArrayList<>(messages.values());
    }

    public List<Duck> findAllDucks() {
        return users.values().stream()
                .filter(user -> user instanceof Duck)
                .map(user -> (Duck) user)
                .collect(Collectors.toList());
    }

    public List<Person> findAllPersons() {
        return users.values().stream()
                .filter(user -> user instanceof Person)
                .map(user -> (Person) user)
                .collect(Collectors.toList());
    }

    // === BUSINESS OPERATIONS ===

    public User createUser(UserType userType, String username, String email, String password, Object... additionalParams) {
        User user = null;
        Long id = nextUserId++;

        switch (userType) {
            case PERSON:
                if (additionalParams.length >= 5) {
                    String firstName = (String) additionalParams[0];
                    String lastName = (String) additionalParams[1];
                    LocalDate birthDate = (LocalDate) additionalParams[2];
                    String job = (String) additionalParams[3];
                    int empathyLevel = (Integer) additionalParams[4];
                    user = new Person(id, username, email, password, firstName, lastName, birthDate, job, empathyLevel);
                }
                break;
            case DUCK:
                if (additionalParams.length >= 3) {
                    DuckType duckType = (DuckType) additionalParams[0];
                    double speed = (Double) additionalParams[1];
                    double resistance = (Double) additionalParams[2];
                    user = new Duck(id, username, email, password, duckType, speed, resistance);
                }
                break;
        }

        if (user != null) {
            save(user);
            addEventHistory(user, EventType.LOGIN, "User created: " + username);
        }

        return user;
    }

    public boolean addFriendship(Long user1Id, Long user2Id) {
        User user1 = users.get(user1Id);
        User user2 = users.get(user2Id);

        if (user1 != null && user2 != null && !user1.equals(user2)) {
            user1.addFriend(user2);

            Tuple<Long, Long> friendshipKey = new Tuple<>(
                    Math.min(user1Id, user2Id),
                    Math.max(user1Id, user2Id)
            );
            Friend friend = new Friend(user1, user2);
            friendships.put(friendshipKey, friend);

            addEventHistory(user1, EventType.FRIEND_ADDED, "Became friends with " + user2.getDisplayName());
            addEventHistory(user2, EventType.FRIEND_ADDED, "Became friends with " + user1.getDisplayName());

            return true;
        }
        return false;
    }

    public boolean removeFriendship(Long user1Id, Long user2Id) {
        User user1 = users.get(user1Id);
        User user2 = users.get(user2Id);

        if (user1 != null && user2 != null) {
            user1.removeFriend(user2);

            Tuple<Long, Long> friendshipKey = new Tuple<>(
                    Math.min(user1Id, user2Id),
                    Math.max(user1Id, user2Id)
            );
            friendships.remove(friendshipKey);

            addEventHistory(user1, EventType.FRIEND_REMOVED, "Removed friendship with " + user2.getDisplayName());

            return true;
        }
        return false;
    }

    public Message sendMessage(Long senderId, Long receiverId, String content) {
        User sender = users.get(senderId);
        User receiver = users.get(receiverId);

        if (sender != null && receiver != null) {
            Message message = new Message(sender, receiver, content);
            message.setId(nextMessageId++);
            save(message);

            sender.sendMessage(receiver, content);

            addEventHistory(sender, EventType.MESSAGE_SENT, "Sent message to " + receiver.getDisplayName());
            addEventHistory(receiver, EventType.MESSAGE_RECEIVED, "Received message from " + sender.getDisplayName());

            return message;
        }
        return null;
    }

    public boolean deleteMessage(Long messageId) {
        return messages.remove(messageId) != null;
    }

    public Event createEvent(String name, String description, String eventType, Long administratorId, List<Double> distances) {
        User admin = users.get(administratorId);

        if (admin != null) {
            Event event;
            Long id = nextEventId++;

            if (distances != null && !distances.isEmpty()) {
                event = new RaceEvent(id, name, description, eventType, admin, distances);
            } else {
                event = new Event(id, name, description, eventType, admin);
            }

            save(event);
            addEventHistory(admin, EventType.EVENT_CREATED, "Created event: " + name);

            return event;
        }
        return null;
    }

    public boolean subscribeToEvent(Long eventId, Long userId) {
        Event event = events.get(eventId);
        User user = users.get(userId);

        if (event != null && user != null) {
            event.subscribe(user);
            addEventHistory(user, EventType.EVENT_SUBSCRIBED, "Subscribed to event: " + event.getName());
            return true;
        }
        return false;
    }

    public boolean unsubscribeFromEvent(Long eventId, Long userId) {
        Event event = events.get(eventId);
        User user = users.get(userId);

        if (event != null && user != null) {
            event.unsubscribe(user);
            return true;
        }
        return false;
    }

    public void notifyEventSubscribers(Long eventId, String message) {
        Event event = events.get(eventId);
        if (event != null) {
            event.notifySubscribers(message);
        }
    }

    public Flock createFlock(String name, String description) {
        Flock flock = new Flock(nextFlockId++, name, description);
        save(flock);
        return flock;
    }

    public boolean addDuckToFlock(Long flockId, Long duckId) {
        Flock flock = flocks.get(flockId);
        User user = users.get(duckId);

        if (flock != null && user instanceof Duck) {
            Duck duck = (Duck) user;
            flock.addDuck(duck);
            duck.joinCard(flock);

            save(flock);
            save(duck);

            addEventHistory(duck, EventType.JOINED_FLOCK, "Joined flock: " + flock.getFlockName());

            return true;
        }
        return false;
    }

    public boolean removeDuckFromFlock(Long flockId, Long duckId) {
        Flock flock = flocks.get(flockId);
        User user = users.get(duckId);

        if (flock != null && user instanceof Duck) {
            Duck duck = (Duck) user;
            flock.removeDuck(duck);
            duck.leaveCard();

            save(flock);
            save(duck);

            addEventHistory(duck, EventType.LEFT_FLOCK, "Left flock: " + flock.getFlockName());

            return true;
        }
        return false;
    }

    public double getFlockAveragePerformance(Long flockId) {
        Flock flock = flocks.get(flockId);
        return flock != null ? flock.getAveragePerformace() : 0;
    }

    public RaceEvent createRace(String name, String description, Long administratorId, List<Double> distances) {
        User admin = users.get(administratorId);

        if (admin != null) {
            RaceEvent race = new RaceEvent(nextEventId++, name, description, "RACE", admin, distances);
            save(race);
            addEventHistory(admin, EventType.EVENT_CREATED, "Created race: " + name);
            return race;
        }
        return null;
    }

    public boolean addParticipantToRace(Long raceId, Long duckId) {
        Event event = events.get(raceId);
        User user = users.get(duckId);

        if (event instanceof RaceEvent && user instanceof Duck) {
            RaceEvent race = (RaceEvent) event;
            Duck duck = (Duck) user;
            race.addParticipant(duck);

            save(race);
            addEventHistory(duck, EventType.RACE_PARTICIPATION, "Joined race: " + race.getName());

            return true;
        }
        return false;
    }

    public boolean simulateRace(Long raceId) {
        Event event = events.get(raceId);

        if (event instanceof RaceEvent) {
            RaceEvent race = (RaceEvent) event;
            boolean result = race.simulateRace();

            if (result) {
                save(race);
                addEventHistory(null, EventType.RACE_COMPLETED, "Race completed: " + race.getName());
            }

            return result;
        }
        return false;
    }

    public User login(String username, String password) {
        Optional<User> userOpt = users.values().stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst();

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.login();
            addEventHistory(user, EventType.LOGIN, "User logged in");
            return user;
        }
        return null;
    }

    public void logout(Long userId) {
        User user = users.get(userId);
        if (user != null) {
            user.logout();
            addEventHistory(user, EventType.LOGOUT, "User logged out");
        }
    }

    // === UTILITY METHODS ===

    private void addEventHistory(User user, EventType eventType, String description) {
        EventHistory history = new EventHistory(nextHistoryId++, user, eventType, description);
        save(history);

        if (user != null) {
            user.addEventToHistory(history);
        }
    }

    public List<User> searchUsersByName(String name) {
        return users.values().stream()
                .filter(user -> user.getDisplayName().toLowerCase().contains(name.toLowerCase()) ||
                        user.getUsername().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Message> getMessageHistory(Long userId) {
        return messages.values().stream()
                .filter(message -> message.getSender().getId().equals(userId) ||
                        message.getReceiver().getId().equals(userId))
                .sorted((m1, m2) -> m2.getTimestamp().compareTo(m1.getTimestamp()))
                .collect(Collectors.toList());
    }

    public List<EventHistory> getUserEventHistory(Long userId) {
        User user = users.get(userId);
        return user != null ? user.getEventHistory() : new ArrayList<>();
    }

    public List<EventHistory> getAllEventHistory() {
        return new ArrayList<>(eventHistories.values());
    }

    public List<Friend> getAllFriendships() {
        return new ArrayList<>(friendships.values());
    }

    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", users.size());
        stats.put("totalDucks", findAllDucks().size());
        stats.put("totalPersons", findAllPersons().size());
        stats.put("totalFlocks", flocks.size());
        stats.put("totalEvents", events.size());
        stats.put("totalMessages", messages.size());
        stats.put("totalFriendships", friendships.size());
        stats.put("totalEventHistory", eventHistories.size());
        return stats;
    }

    // === BULK OPERATIONS ===

    public void clearAllData() {
        users.clear();
        flocks.clear();
        events.clear();
        messages.clear();
        eventHistories.clear();
        friendships.clear();

        nextUserId = 1L;
        nextFlockId = 1L;
        nextEventId = 1L;
        nextMessageId = 1L;
        nextHistoryId = 1L;
    }

    public void initializeSampleData() {
        // Create sample persons
        Person alice = (Person) createUser(UserType.PERSON, "alice", "alice@email.com", "pass123",
                "Alice", "Smith", LocalDate.of(1990, 5, 15), "Veterinarian", 9);

        Person bob = (Person) createUser(UserType.PERSON, "bob", "bob@email.com", "pass123",
                "Bob", "Johnson", LocalDate.of(1985, 8, 22), "Farmer", 7);

        // Create sample ducks
        Duck speedy = (Duck) createUser(UserType.DUCK, "speedy", "speedy@pond.com", "quack",
                DuckType.FLYING_AND_SWIMMING, 30.0, 8.5);

        Duck strong = (Duck) createUser(UserType.DUCK, "strong", "strong@pond.com", "quack",
                DuckType.SWIMMING, 18.0, 9.8);

        // Create friendships
        addFriendship(alice.getId(), speedy.getId());
        addFriendship(bob.getId(), strong.getId());

        // Create flock
        Flock champions = createFlock("Champions", "Elite racing ducks");
        addDuckToFlock(champions.getId(), speedy.getId());
        addDuckToFlock(champions.getId(), strong.getId());

        // Create race event
        RaceEvent race = createRace("Spring Championship", "Annual spring duck race", "RACE",
                alice.getId(), Arrays.asList(10.0, 20.0, 15.0));

        addParticipantToRace(race.getId(), speedy.getId());
        addParticipantToRace(race.getId(), strong.getId());

        // Send some messages
        sendMessage(alice.getId(), speedy.getId(), "Good luck in the race!");
        sendMessage(speedy.getId(), alice.getId(), "Quack! I'm ready!");
    }
}