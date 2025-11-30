package Repository;

import Domain.entities.*;
import Domain.enums.*;
import Domain.Friend;
import Domain.Tuple;
import Domain.validators.Validator;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private String dataDirectory;
    private Validator<Object> validator;

    public DuckNetworkRepository(String dataDirectory, Validator<Object> validator) {
        this.users = new HashMap<>();
        this.flocks = new HashMap<>();
        this.events = new HashMap<>();
        this.messages = new HashMap<>();
        this.eventHistories = new HashMap<>();
        this.friendships = new HashMap<>();
        this.dataDirectory = dataDirectory;
        this.validator = validator;

        loadAllData();
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
            saveUsersToFile();
            return null;
        } else if (entity instanceof Flock) {
            Flock flock = (Flock) entity;
            if (flocks.containsKey(flock.getId())) return entity;
            flocks.put(flock.getId(), flock);
            saveFlocksToFile();
            return null;
        } else if (entity instanceof Event) {
            Event event = (Event) entity;
            if (events.containsKey(event.getId())) return entity;
            events.put(event.getId(), event);
            saveEventsToFile();
            return null;
        } else if (entity instanceof Message) {
            Message message = (Message) entity;
            if (messages.containsKey(message.getId())) return entity;
            messages.put(message.getId(), message);
            saveMessagesToFile();
            return null;
        } else if (entity instanceof EventHistory) {
            EventHistory history = (EventHistory) entity;
            if (eventHistories.containsKey(history.getId())) return entity;
            eventHistories.put(history.getId(), history);
            saveEventHistoryToFile();
            return null;
        } else if (entity instanceof Friend) {
            Friend friend = (Friend) entity;
            if (friendships.containsKey(friend.getId())) return entity;
            friendships.put(friend.getId(), friend);
            saveFriendshipsToFile();
            return null;
        }

        return entity;
    }

    @Override
    public Object delete(Long id) {
        // Search and remove from all entity maps
        if (users.containsKey(id)) {
            User removed = users.remove(id);
            saveUsersToFile();
            return removed;
        }
        if (flocks.containsKey(id)) {
            Flock removed = flocks.remove(id);
            saveFlocksToFile();
            return removed;
        }
        if (events.containsKey(id)) {
            Event removed = events.remove(id);
            saveEventsToFile();
            return removed;
        }
        if (messages.containsKey(id)) {
            Message removed = messages.remove(id);
            saveMessagesToFile();
            return removed;
        }
        if (eventHistories.containsKey(id)) {
            EventHistory removed = eventHistories.remove(id);
            saveEventHistoryToFile();
            return removed;
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
            saveUsersToFile();
            return null;
        } else if (entity instanceof Flock) {
            Flock flock = (Flock) entity;
            if (!flocks.containsKey(flock.getId())) return entity;
            flocks.put(flock.getId(), flock);
            saveFlocksToFile();
            return null;
        } else if (entity instanceof Event) {
            Event event = (Event) entity;
            if (!events.containsKey(event.getId())) return entity;
            events.put(event.getId(), event);
            saveEventsToFile();
            return null;
        } else if (entity instanceof Message) {
            Message message = (Message) entity;
            if (!messages.containsKey(message.getId())) return entity;
            messages.put(message.getId(), message);
            saveMessagesToFile();
            return null;
        } else if (entity instanceof EventHistory) {
            EventHistory history = (EventHistory) entity;
            if (!eventHistories.containsKey(history.getId())) return entity;
            eventHistories.put(history.getId(), history);
            saveEventHistoryToFile();
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
            saveFriendshipsToFile();

            addEventHistory(user1, EventType.FRIEND_ADDED, "Became friends with " + user2.getDisplayName());
            addEventHistory(user2, EventType.FRIEND_ADDED, "Became friends with " + user1.getDisplayName());

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
            saveEventsToFile();
            return true;
        }
        return false;
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

    // === DATA LOADING AND SAVING ===

    private void loadAllData() {
        loadUsersFromFile();
        loadFlocksFromFile();
        loadEventsFromFile();
        loadMessagesFromFile();
        loadEventHistoryFromFile();
        loadFriendshipsFromFile();
        loadFlockMembersFromFile();
        loadEventSubscribersFromFile();
        loadRaceParticipantsFromFile();

        System.out.println("Data loading completed:");
        System.out.println("- Users: " + users.size());
        System.out.println("- Flocks: " + flocks.size());
        System.out.println("- Events: " + events.size());
        System.out.println("- Messages: " + messages.size());
        System.out.println("- Friendships: " + friendships.size());
        System.out.println("- Event History: " + eventHistories.size());
    }

    private void loadUsersFromFile() {
        File file = new File(dataDirectory + "/users.csv");
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) continue;

                String[] data = line.split(";");
                if (data.length >= 4) {
                    try {
                        UserType userType = UserType.valueOf(data[0].toUpperCase());
                        Long id = Long.parseLong(data[1]);
                        String username = data[2];
                        String email = data[3];
                        String password = data[4];

                        if (userType == UserType.PERSON && data.length >= 9) {
                            String firstName = data[5];
                            String lastName = data[6];
                            LocalDate birthDate = LocalDate.parse(data[7], dateFormatter);
                            String job = data[8];
                            int empathyLevel = Integer.parseInt(data[9]);

                            Person person = new Person(id, username, email, password,
                                    firstName, lastName, birthDate, job, empathyLevel);
                            users.put(id, person);
                        }
                        else if (userType == UserType.DUCK && data.length >= 8) {
                            DuckType duckType = DuckType.valueOf(data[5].toUpperCase());
                            double speed = Double.parseDouble(data[6]);
                            double resistance = Double.parseDouble(data[7]);

                            Duck duck = new Duck(id, username, email, password, duckType, speed, resistance);
                            users.put(id, duck);
                        }

                        nextUserId = Math.max(nextUserId, id + 1);
                    } catch (Exception e) {
                        System.err.println("Error parsing user line: " + line + " - " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users from CSV: " + e.getMessage());
        }
    }

    private void loadFlocksFromFile() {
        File file = new File(dataDirectory + "/flocks.csv");
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) continue;

                String[] data = line.split(";");
                if (data.length >= 2) {
                    try {
                        Long id = Long.parseLong(data[0]);
                        String name = data[1];
                        String description = data.length > 2 ? data[2] : "";

                        Flock flock = new Flock(id, name, description);
                        flocks.put(id, flock);
                        nextFlockId = Math.max(nextFlockId, id + 1);
                    } catch (Exception e) {
                        System.err.println("Error parsing flock line: " + line + " - " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading flocks from CSV: " + e.getMessage());
        }
    }

    private void loadEventsFromFile() {
        File file = new File(dataDirectory + "/events.csv");
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) continue;

                String[] data = line.split(";");
                if (data.length >= 5) {
                    try {
                        Long id = Long.parseLong(data[0]);
                        String name = data[1];
                        String description = data[2];
                        String eventType = data[3];
                        Long adminId = Long.parseLong(data[4]);
                        User admin = users.get(adminId);

                        if (admin != null) {
                            Event event;

                            if (data.length > 5 && !data[5].isEmpty()) {
                                List<Double> distances = Arrays.stream(data[5].split(","))
                                        .map(Double::parseDouble)
                                        .collect(Collectors.toList());
                                event = new RaceEvent(id, name, description, eventType, admin, distances);
                            } else {
                                event = new Event(id, name, description, eventType, admin);
                            }

                            events.put(id, event);
                            nextEventId = Math.max(nextEventId, id + 1);
                        }
                    } catch (Exception e) {
                        System.err.println("Error parsing event line: " + line + " - " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading events from CSV: " + e.getMessage());
        }
    }

    // Similar methods for loading messages, event history, friendships, etc.
    // (Implementation follows the same pattern as above)

    private void saveUsersToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(dataDirectory + "/users.csv"))) {
            for (User user : users.values()) {
                if (user instanceof Person) {
                    Person person = (Person) user;
                    pw.printf("%s;%d;%s;%s;%s;%s;%s;%s;%s;%d%n",
                            "PERSON", person.getId(), person.getUsername(), person.getEmail(), person.getPassword(),
                            person.getFirstName(), person.getLastName(),
                            person.getBirthDate(), person.getJob(), person.getEmpatyLevel());
                } else if (user instanceof Duck) {
                    Duck duck = (Duck) user;
                    pw.printf("%s;%d;%s;%s;%s;%s;%.1f;%.1f%n",
                            "DUCK", duck.getId(), duck.getUsername(), duck.getEmail(), duck.getPassword(),
                            duck.getType(), duck.getSpeed(), duck.getResistance());
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving users to CSV: " + e.getMessage());
        }
    }

    private void saveFlocksToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(dataDirectory + "/flocks.csv"))) {
            for (Flock flock : flocks.values()) {
                pw.printf("%d;%s;%s%n", flock.getId(), flock.getFlockName(), flock.getDescription());
            }
        } catch (IOException e) {
            System.err.println("Error saving flocks to CSV: " + e.getMessage());
        }
    }

    private void saveEventsToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(dataDirectory + "/events.csv"))) {
            for (Event event : events.values()) {
                if (event instanceof RaceEvent) {
                    RaceEvent race = (RaceEvent) event;
                    String distances = race.getDistances().stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(","));
                    pw.printf("%d;%s;%s;%s;%d;%s%n",
                            event.getId(), event.getName(), event.getDescription(),
                            event.getEventType(), event.getAdministrator().getId(), distances);
                } else {
                    pw.printf("%d;%s;%s;%s;%d%n",
                            event.getId(), event.getName(), event.getDescription(),
                            event.getEventType(), event.getAdministrator().getId());
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving events to CSV: " + e.getMessage());
        }
    }

    // Similar save methods for other entities...

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

    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", users.size());
        stats.put("totalDucks", findAllDucks().size());
        stats.put("totalPersons", findAllPersons().size());
        stats.put("totalFlocks", flocks.size());
        stats.put("totalEvents", events.size());
        stats.put("totalMessages", messages.size());
        stats.put("totalFriendships", friendships.size());
        return stats;
    }

    // Stub methods for other file operations (to be implemented similarly)
    private void loadMessagesFromFile() { /* implementation */ }
    private void loadEventHistoryFromFile() { /* implementation */ }
    private void loadFriendshipsFromFile() { /* implementation */ }
    private void loadFlockMembersFromFile() { /* implementation */ }
    private void loadEventSubscribersFromFile() { /* implementation */ }
    private void loadRaceParticipantsFromFile() { /* implementation */ }
    private void saveMessagesToFile() { /* implementation */ }
    private void saveEventHistoryToFile() { /* implementation */ }
    private void saveFriendshipsToFile() { /* implementation */ }
}