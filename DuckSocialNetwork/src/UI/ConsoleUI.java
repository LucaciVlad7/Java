package UI;

import Service.DuckSocialService;
import Service.DuckSocialServiceFactory;
import Domain.entities.*;
import Domain.enums.DuckType;
import Domain.enums.EventType;
import Domain.Validator.ValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class ConsoleUI extends AbstractUI {

    public ConsoleUI() {
        super(DuckSocialServiceFactory.getInstance());
    }

    public ConsoleUI(DuckSocialService service) {
        super(service);
    }

    @Override
    public void run() {
        clearScreen();
        printHeader("🐤 DUCK SOCIAL NETWORK 🦆");
        System.out.println("Welcome to Duck Social Network!");

        boolean running = true;
        while (running) {
            showMenu();
            handleUserInput();
        }

        scanner.close();
        System.out.println("Thank you for using Duck Social Network! Goodbye! 🦆");
    }

    @Override
    public void showMenu() {
        printSection("MAIN MENU");
        System.out.println("1.  User Management");
        System.out.println("2.  Friendship Management");
        System.out.println("3.  Flock Management");
        System.out.println("4.  Event Management");
        System.out.println("5.  Race Management");
        System.out.println("6.  Statistics & Reports");
        System.out.println("0.  Exit");
        System.out.println("-".repeat(30));
    }

    @Override
    public void handleUserInput() {
        int choice = readInt("Enter your choice: ");

        switch (choice) {
            case 1:
                userManagementMenu();
                break;
            case 2:
                friendshipManagementMenu();
                break;
            case 3:
                flockManagementMenu();
                break;
            case 4:
                eventManagementMenu();
                break;
            case 5:
                raceManagementMenu();
                break;
            case 6:
                statisticsMenu();
                break;
            case 0:
                exitApplication();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }

        pause();
    }

    private void exitApplication() {
        System.out.println("Exiting Duck Social Network...");
        scanner.close();
        System.exit(0);
    }

    // === USER MANAGEMENT ===
    private void userManagementMenu() {
        while (true) {
            clearScreen();
            printHeader("USER MANAGEMENT");
            System.out.println("1. Add User");
            System.out.println("2. Update User");
            System.out.println("3. Delete User");
            System.out.println("4. Find User by ID");
            System.out.println("5. Find User by Username");
            System.out.println("6. List All Users");
            System.out.println("7. List All Ducks");
            System.out.println("8. List All Persons");
            System.out.println("9. Find Ducks by Type");
            System.out.println("10. Find Ducks Without Flock");
            System.out.println("0. Back to Main Menu");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1: addUser(); break;
                case 2: updateUser(); break;
                case 3: deleteUser(); break;
                case 4: findUserById(); break;
                case 5: findUserByUsername(); break;
                case 6: listAllUsers(); break;
                case 7: listAllDucks(); break;
                case 8: listAllPersons(); break;
                case 9: findDucksByType(); break;
                case 10: findDucksWithoutFlock(); break;
                case 0: return;
                default: System.out.println("Invalid choice.");
            }
            pause();
        }
    }

    // === FRIENDSHIP MANAGEMENT ===
    private void friendshipManagementMenu() {
        while (true) {
            clearScreen();
            printHeader("FRIENDSHIP MANAGEMENT");
            System.out.println("1. Add Friendship");
            System.out.println("2. Remove Friendship");
            System.out.println("3. List User Friends");
            System.out.println("4. List All Friendships");
            System.out.println("0. Back to Main Menu");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1: addFriendship(); break;
                case 2: removeFriendship(); break;
                case 3: listUserFriends(); break;
                case 4: listAllFriendships(); break;
                case 0: return;
                default: System.out.println("Invalid choice.");
            }
            pause();
        }
    }

    // === FLOCK MANAGEMENT ===
    private void flockManagementMenu() {
        while (true) {
            clearScreen();
            printHeader("FLOCK MANAGEMENT");
            System.out.println("1. Create Flock");
            System.out.println("2. Update Flock");
            System.out.println("3. Delete Flock");
            System.out.println("4. Find Flock by ID");
            System.out.println("5. List All Flocks");
            System.out.println("6. Add Duck to Flock");
            System.out.println("7. Remove Duck from Flock");
            System.out.println("8. List Ducks in Flock");
            System.out.println("0. Back to Main Menu");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1: createFlock(); break;
                case 2: updateFlock(); break;
                case 3: deleteFlock(); break;
                case 4: findFlockById(); break;
                case 5: listAllFlocks(); break;
                case 6: addDuckToFlock(); break;
                case 7: removeDuckFromFlock(); break;
                case 8: listDucksInFlock(); break;
                case 0: return;
                default: System.out.println("Invalid choice.");
            }
            pause();
        }
    }

    // === EVENT MANAGEMENT ===
    private void eventManagementMenu() {
        while (true) {
            clearScreen();
            printHeader("EVENT MANAGEMENT");
            System.out.println("1. Create Event");
            System.out.println("2. Update Event");
            System.out.println("3. Delete Event");
            System.out.println("4. Find Event by ID");
            System.out.println("5. List All Events");
            System.out.println("6. Add Participant to Event");
            System.out.println("7. Remove Participant from Event");
            System.out.println("8. List Event Participants");
            System.out.println("0. Back to Main Menu");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1: createEvent(); break;
                case 2: updateEvent(); break;
                case 3: deleteEvent(); break;
                case 4: findEventById(); break;
                case 5: listAllEvents(); break;
                case 6: addParticipantToEvent(); break;
                case 7: removeParticipantFromEvent(); break;
                case 8: listEventParticipants(); break;
                case 0: return;
                default: System.out.println("Invalid choice.");
            }
            pause();
        }
    }

    // === RACE MANAGEMENT ===
    private void raceManagementMenu() {
        while (true) {
            clearScreen();
            printHeader("RACE MANAGEMENT");
            System.out.println("1. Start Race");
            System.out.println("2. Complete Race");
            System.out.println("3. Get Race Results");
            System.out.println("4. List Active Races");
            System.out.println("5. List Completed Races");
            System.out.println("0. Back to Main Menu");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1: startRace(); break;
                case 2: completeRace(); break;
                case 3: getRaceResults(); break;
                case 4: listActiveRaces(); break;
                case 5: listCompletedRaces(); break;
                case 0: return;
                default: System.out.println("Invalid choice.");
            }
            pause();
        }
    }

    // === STATISTICS & REPORTS ===
    private void statisticsMenu() {
        while (true) {
            clearScreen();
            printHeader("STATISTICS & REPORTS");
            System.out.println("1. User Statistics");
            System.out.println("2. Flock Statistics");
            System.out.println("3. Event Statistics");
            System.out.println("4. Race Statistics");
            System.out.println("5. Generate Report");
            System.out.println("0. Back to Main Menu");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1: showUserStatistics(); break;
                case 2: showFlockStatistics(); break;
                case 3: showEventStatistics(); break;
                case 4: showRaceStatistics(); break;
                case 5: generateReport(); break;
                case 0: return;
                default: System.out.println("Invalid choice.");
            }
            pause();
        }
    }

    // === USER MANAGEMENT METHODS ===
    private void updateUser() {
        printSection("UPDATE USER");
        Long userId = readLong("Enter user ID to update: ");

        try {
            User existingUser = service.findUserById(userId);
            if (existingUser == null) {
                System.out.println("User not found!");
                return;
            }

            printUserDetails(existingUser);
            System.out.println("\nEnter new values (press Enter to keep current):");

            String username = readString("New Username [" + existingUser.getUsername() + "]: ");
            String email = readString("New Email [" + existingUser.getEmail() + "]: ");
            String password = readString("New Password [***]: ");

            Map<String, Object> additionalData = new HashMap<>();

            if (existingUser instanceof Person) {
                Person person = (Person) existingUser;
                additionalData.put("firstName", readString("First Name [" + person.getFirstName() + "]: "));
                additionalData.put("lastName", readString("Last Name [" + person.getLastName() + "]: "));
                additionalData.put("birthDate", readDate("Birth Date [" + person.getBirthDate() + "]: "));
                additionalData.put("job", readString("Job [" + person.getJob() + "]: "));
                additionalData.put("empathyLevel", readInt("Empathy Level [" + person.getEmpatyLevel() + "]: "));
            } else if (existingUser instanceof Duck) {
                Duck duck = (Duck) existingUser;
                additionalData.put("speed", readDouble("Speed [" + duck.getSpeed() + "]: "));
                additionalData.put("resistance", readDouble("Resistance [" + duck.getResistance() + "]: "));
            }

            User updatedUser = service.updateUser(userId,
                    username.isEmpty() ? null : username,
                    email.isEmpty() ? null : email,
                    password.isEmpty() ? null : password,
                    additionalData);

            System.out.println("User updated successfully!");
            printUserDetails(updatedUser);
        } catch (ValidationException e) {
            System.out.println("Error updating user: " + e.getMessage());
        }
    }

    private void deleteUser() {
        printSection("DELETE USER");
        Long userId = readLong("Enter user ID to delete: ");

        try {
            service.deleteUser(userId);
            System.out.println("User deleted successfully!");
        } catch (Exception e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }

    private void findUserById() {
        printSection("FIND USER BY ID");
        Long userId = readLong("Enter user ID: ");

        User user = service.findUserById(userId);
        if (user != null) {
            printUserDetails(user);
        } else {
            System.out.println("User not found!");
        }
    }

    private void findUserByUsername() {
        printSection("FIND USER BY USERNAME");
        String username = readString("Enter username: ");

        User user = service.findUserByUsername(username);
        if (user != null) {
            printUserDetails(user);
        } else {
            System.out.println("User not found!");
        }
    }

    private void listAllUsers() {
        printSection("ALL USERS");
        List<User> users = service.listAllUsers();

        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            users.forEach(this::printUserDetails);
            System.out.println("Total users: " + users.size());
        }
    }

    private void listAllDucks() {
        printSection("ALL DUCKS");
        List<Duck> ducks = service.listAllDucks();

        if (ducks.isEmpty()) {
            System.out.println("No ducks found.");
        } else {
            ducks.forEach(duck -> {
                System.out.println("ID: " + duck.getId() +
                        ", Username: " + duck.getUsername() +
                        ", Type: " + duck.getType() +
                        ", Flock: " + (duck.getFlock() != null ? duck.getFlock().getFlockName() : "None"));
            });
            System.out.println("Total ducks: " + ducks.size());
        }
    }

    private void listAllPersons() {
        printSection("ALL PERSONS");
        List<Person> persons = service.listAllPersons();

        if (persons.isEmpty()) {
            System.out.println("No persons found.");
        } else {
            persons.forEach(person -> {
                System.out.println("ID: " + person.getId() +
                        ", Username: " + person.getUsername() +
                        ", Name: " + person.getFirstName() + " " + person.getLastName() +
                        ", Job: " + person.getJob());
            });
            System.out.println("Total persons: " + persons.size());
        }
    }

    private void findDucksByType() {
        printSection("FIND DUCKS BY TYPE");
        System.out.println("Available duck types: " + Arrays.toString(DuckType.values()));
        String duckTypeStr = readString("Enter duck type: ").toUpperCase();

        try {
            DuckType duckType = DuckType.valueOf(duckTypeStr);
            List<Duck> ducks = service.findDucksByType(duckType);

            if (ducks.isEmpty()) {
                System.out.println("No ducks found of type: " + duckType);
            } else {
                ducks.forEach(duck -> {
                    System.out.println("ID: " + duck.getId() +
                            ", Username: " + duck.getUsername() +
                            ", Speed: " + duck.getSpeed() +
                            ", Resistance: " + duck.getResistance());
                });
                System.out.println("Total ducks of type " + duckType + ": " + ducks.size());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid duck type!");
        }
    }

    private void findDucksWithoutFlock() {
        printSection("DUCKS WITHOUT FLOCK");
        List<Duck> ducks = service.findDucksWithoutFlock();

        if (ducks.isEmpty()) {
            System.out.println("No ducks without flock found.");
        } else {
            ducks.forEach(duck -> {
                System.out.println("ID: " + duck.getId() +
                        ", Username: " + duck.getUsername() +
                        ", Type: " + duck.getType() +
                        ", Speed: " + duck.getSpeed());
            });
            System.out.println("Total ducks without flock: " + ducks.size());
        }
    }

    // === FRIENDSHIP MANAGEMENT METHODS ===
    private void addFriendship() {
        printSection("ADD FRIENDSHIP");
        Long userId1 = readLong("Enter first user ID: ");
        Long userId2 = readLong("Enter second user ID: ");

        try {
            service.addFriendship(userId1, userId2);
            System.out.println("Friendship added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding friendship: " + e.getMessage());
        }
    }

    private void removeFriendship() {
        printSection("REMOVE FRIENDSHIP");
        Long userId1 = readLong("Enter first user ID: ");
        Long userId2 = readLong("Enter second user ID: ");

        try {
            service.removeFriendship(userId1, userId2);
            System.out.println("Friendship removed successfully!");
        } catch (Exception e) {
            System.out.println("Error removing friendship: " + e.getMessage());
        }
    }

    private void listUserFriends() {
        printSection("LIST USER FRIENDS");
        Long userId = readLong("Enter user ID: ");

        List<User> friends = service.listUserFriends(userId);
        if (friends.isEmpty()) {
            System.out.println("No friends found for this user.");
        } else {
            System.out.println("Friends of user ID " + userId + ":");
            friends.forEach(friend -> {
                System.out.println("  ID: " + friend.getId() +
                        ", Username: " + friend.getUsername() +
                        ", Type: " + (friend instanceof Person ? "Person" : "Duck"));
            });
            System.out.println("Total friends: " + friends.size());
        }
    }

    private void listAllFriendships() {
        printSection("ALL FRIENDSHIPS");
        // This would typically list all friendship relationships
        System.out.println("Feature not yet implemented.");
    }

    // === FLOCK MANAGEMENT METHODS ===
    private void createFlock() {
        printSection("CREATE FLOCK");
        String flockName = readString("Flock Name: ");
        String description = readString("Description: ");

        try {
            Flock flock = service.createFlock(flockName, description);
            System.out.println("Flock created successfully: " + flock.getFlockName());
        } catch (Exception e) {
            System.out.println("Error creating flock: " + e.getMessage());
        }
    }

    private void updateFlock() {
        printSection("UPDATE FLOCK");
        Long flockId = readLong("Enter flock ID to update: ");
        String flockName = readString("New Flock Name: ");
        String description = readString("New Description: ");

        try {
            Flock flock = service.updateFlock(flockId, flockName, description);
            System.out.println("Flock updated successfully: " + flock.getFlockName());
        } catch (Exception e) {
            System.out.println("Error updating flock: " + e.getMessage());
        }
    }

    private void deleteFlock() {
        printSection("DELETE FLOCK");
        Long flockId = readLong("Enter flock ID to delete: ");

        try {
            service.deleteFlock(flockId);
            System.out.println("Flock deleted successfully!");
        } catch (Exception e) {
            System.out.println("Error deleting flock: " + e.getMessage());
        }
    }

    private void findFlockById() {
        printSection("FIND FLOCK BY ID");
        Long flockId = readLong("Enter flock ID: ");

        Flock flock = service.findFlockById(flockId);
        if (flock != null) {
            System.out.println("Flock Details:");
            System.out.println("  ID: " + flock.getId());
            System.out.println("  Name: " + flock.getFlockName());
            System.out.println("  Description: " + flock.getDescription());
            System.out.println("  Member Count: " + (flock.getMembers() != null ? flock.getMembers().size() : 0));
        } else {
            System.out.println("Flock not found!");
        }
    }

    private void listAllFlocks() {
        printSection("ALL FLOCKS");
        List<Flock> flocks = service.listAllFlocks();

        if (flocks.isEmpty()) {
            System.out.println("No flocks found.");
        } else {
            flocks.forEach(flock -> {
                System.out.println("ID: " + flock.getId() +
                        ", Name: " + flock.getFlockName() +
                        ", Description: " + flock.getDescription() +
                        ", Members: " + (flock.getMembers() != null ? flock.getMembers().size() : 0));
            });
            System.out.println("Total flocks: " + flocks.size());
        }
    }

    private void addDuckToFlock() {
        printSection("ADD DUCK TO FLOCK");
        Long duckId = readLong("Enter duck ID: ");
        Long flockId = readLong("Enter flock ID: ");

        try {
            service.addDuckToFlock(duckId, flockId);
            System.out.println("Duck added to flock successfully!");
        } catch (Exception e) {
            System.out.println("Error adding duck to flock: " + e.getMessage());
        }
    }

    private void removeDuckFromFlock() {
        printSection("REMOVE DUCK FROM FLOCK");
        Long duckId = readLong("Enter duck ID: ");
        Long flockId = readLong("Enter flock ID: ");

        try {
            service.removeDuckFromFlock(duckId, flockId);
            System.out.println("Duck removed from flock successfully!");
        } catch (Exception e) {
            System.out.println("Error removing duck from flock: " + e.getMessage());
        }
    }

    private void listDucksInFlock() {
        printSection("LIST DUCKS IN FLOCK");
        Long flockId = readLong("Enter flock ID: ");

        List<Duck> ducks = service.listDucksInFlock(flockId);
        if (ducks.isEmpty()) {
            System.out.println("No ducks found in this flock.");
        } else {
            System.out.println("Ducks in flock ID " + flockId + ":");
            ducks.forEach(duck -> {
                System.out.println("  ID: " + duck.getId() +
                        ", Username: " + duck.getUsername() +
                        ", Type: " + duck.getType());
            });
            System.out.println("Total ducks: " + ducks.size());
        }
    }

    // === EVENT MANAGEMENT METHODS ===
    private void createEvent() {
        printSection("CREATE EVENT");
        String name = readString("Event Name: ");
        String description = readString("Description: ");
        System.out.println("Available event types: " + Arrays.toString(EventType.values()));
        String eventTypeStr = readString("Event Type: ").toUpperCase();
        Long adminId = readLong("Administrator User ID: ");

        try {
            EventType eventType = EventType.valueOf(eventTypeStr);
            Event event = service.createEvent(name, description, eventType, adminId);
            System.out.println("Event created successfully: " + event.getName());
        } catch (Exception e) {
            System.out.println("Error creating event: " + e.getMessage());
        }
    }

    private void updateEvent() {
        printSection("UPDATE EVENT");
        Long eventId = readLong("Enter event ID to update: ");
        String name = readString("New Event Name: ");
        String description = readString("New Description: ");

        try {
            Event event = service.updateEvent(eventId, name, description);
            System.out.println("Event updated successfully: " + event.getName());
        } catch (Exception e) {
            System.out.println("Error updating event: " + e.getMessage());
        }
    }

    private void deleteEvent() {
        printSection("DELETE EVENT");
        Long eventId = readLong("Enter event ID to delete: ");

        try {
            service.deleteEvent(eventId);
            System.out.println("Event deleted successfully!");
        } catch (Exception e) {
            System.out.println("Error deleting event: " + e.getMessage());
        }
    }

    private void findEventById() {
        printSection("FIND EVENT BY ID");
        Long eventId = readLong("Enter event ID: ");

        Event event = service.findEventById(eventId);
        if (event != null) {
            printEventDetails(event);
        } else {
            System.out.println("Event not found!");
        }
    }

    private void listAllEvents() {
        printSection("ALL EVENTS");
        List<Event> events = service.listAllEvents();

        if (events.isEmpty()) {
            System.out.println("No events found.");
        } else {
            events.forEach(event -> {
                System.out.println("ID: " + event.getId() +
                        ", Name: " + event.getName() +
                        ", Type: " + event.getEventType() +
                        ", Admin: " + (event.getAdministrator() != null ? event.getAdministrator().getUsername() : "None"));
            });
            System.out.println("Total events: " + events.size());
        }
    }

    private void addParticipantToEvent() {
        printSection("ADD PARTICIPANT TO EVENT");
        Long eventId = readLong("Enter event ID: ");
        Long userId = readLong("Enter user ID: ");

        try {
            service.addParticipantToEvent(eventId, userId);
            System.out.println("Participant added to event successfully!");
        } catch (Exception e) {
            System.out.println("Error adding participant to event: " + e.getMessage());
        }
    }

    private void removeParticipantFromEvent() {
        printSection("REMOVE PARTICIPANT FROM EVENT");
        Long eventId = readLong("Enter event ID: ");
        Long userId = readLong("Enter user ID: ");

        try {
            service.removeParticipantFromEvent(eventId, userId);
            System.out.println("Participant removed from event successfully!");
        } catch (Exception e) {
            System.out.println("Error removing participant from event: " + e.getMessage());
        }
    }

    private void listEventParticipants() {
        printSection("LIST EVENT PARTICIPANTS");
        Long eventId = readLong("Enter event ID: ");

        List<User> participants = service.listEventParticipants(eventId);
        if (participants.isEmpty()) {
            System.out.println("No participants found for this event.");
        } else {
            System.out.println("Participants in event ID " + eventId + ":");
            participants.forEach(participant -> {
                System.out.println("  ID: " + participant.getId() +
                        ", Username: " + participant.getUsername() +
                        ", Type: " + (participant instanceof Person ? "Person" : "Duck"));
            });
            System.out.println("Total participants: " + participants.size());
        }
    }

    // === RACE MANAGEMENT METHODS ===
    private void startRace() {
        printSection("START RACE");
        Long eventId = readLong("Enter race event ID: ");

        try {
            service.startRace(eventId);
            System.out.println("Race started successfully!");
        } catch (Exception e) {
            System.out.println("Error starting race: " + e.getMessage());
        }
    }

    private void completeRace() {
        printSection("COMPLETE RACE");
        Long eventId = readLong("Enter race event ID: ");

        try {
            service.completeRace(eventId);
            System.out.println("Race completed successfully!");
        } catch (Exception e) {
            System.out.println("Error completing race: " + e.getMessage());
        }
    }

    private void getRaceResults() {
        printSection("GET RACE RESULTS");
        Long eventId = readLong("Enter race event ID: ");

        try {
            Map<Duck, Double> results = service.getRaceResults(eventId);
            if (results.isEmpty()) {
                System.out.println("No results available for this race.");
            } else {
                System.out.println("Race Results:");
                results.entrySet().stream()
                        .sorted(Map.Entry.<Duck, Double>comparingByValue().reversed())
                        .forEach(entry -> {
                            System.out.println("  " + entry.getKey().getUsername() + ": " + entry.getValue());
                        });
            }
        } catch (Exception e) {
            System.out.println("Error getting race results: " + e.getMessage());
        }
    }

    private void listActiveRaces() {
        printSection("ACTIVE RACES");
        List<RaceEvent> races = service.listActiveRaces();

        if (races.isEmpty()) {
            System.out.println("No active races found.");
        } else {
            races.forEach(race -> {
                System.out.println("ID: " + race.getId() +
                        ", Name: " + race.getName() +
                        ", Participants: " + race.getParticipantCount());
            });
            System.out.println("Total active races: " + races.size());
        }
    }

    private void listCompletedRaces() {
        printSection("COMPLETED RACES");
        List<RaceEvent> races = service.listCompletedRaces();

        if (races.isEmpty()) {
            System.out.println("No completed races found.");
        } else {
            races.forEach(race -> {
                System.out.println("ID: " + race.getId() +
                        ", Name: " + race.getName() +
                        ", Participants: " + race.getParticipantCount());
            });
            System.out.println("Total completed races: " + races.size());
        }
    }

    // === STATISTICS METHODS ===
    private void showUserStatistics() {
        printSection("USER STATISTICS");
        System.out.println("Feature not yet implemented.");
    }

    private void showFlockStatistics() {
        printSection("FLOCK STATISTICS");
        System.out.println("Feature not yet implemented.");
    }

    private void showEventStatistics() {
        printSection("EVENT STATISTICS");
        System.out.println("Feature not yet implemented.");
    }

    private void showRaceStatistics() {
        printSection("RACE STATISTICS");
        System.out.println("Feature not yet implemented.");
    }

    private void generateReport() {
        printSection("GENERATE REPORT");
        System.out.println("Feature not yet implemented.");
    }

    // ... (rest of your existing methods like addUser, helper methods, etc.)

    private void addUser() {
        printSection("ADD USER");

        String userType = readString("Enter user type (PERSON/DUCK): ").toUpperCase();
        String username = readString("Username: ");
        String email = readString("Email: ");
        String password = readString("Password: ");

        Map<String, Object> additionalData = new HashMap<>();

        if ("PERSON".equals(userType)) {
            additionalData.put("firstName", readString("First Name: "));
            additionalData.put("lastName", readString("Last Name: "));
            additionalData.put("birthDate", readDate("Birth Date (YYYY-MM-DD): "));
            additionalData.put("job", readString("Job: "));
            additionalData.put("empathyLevel", readInt("Empathy Level (1-10): "));
        } else if ("DUCK".equals(userType)) {
            System.out.println("Available duck types: " + Arrays.toString(DuckType.values()));
            String duckTypeStr = readString("Duck Type: ").toUpperCase();
            try {
                DuckType duckType = DuckType.valueOf(duckTypeStr);
                additionalData.put("duckType", duckType);
                additionalData.put("speed", readDouble("Speed: "));
                additionalData.put("resistance", readDouble("Resistance: "));

                // Optional flock assignment
                if (readBoolean("Assign to flock?")) {
                    listAllFlocks();
                    Long flockId = readLong("Enter flock ID: ");
                    additionalData.put("flockId", flockId);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid duck type!");
                return;
            }
        } else {
            System.out.println("Invalid user type!");
            return;
        }

        try {
            User user = service.addUser(username, email, password, userType, additionalData);
            System.out.println("User added successfully: " + user.getUsername());
        } catch (ValidationException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

    // === HELPER METHODS ===
    private LocalDate readDate(String prompt) {
        while (true) {
            String dateStr = readString(prompt);
            try {
                return LocalDate.parse(dateStr);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
    }

    private void printUserDetails(User user) {
        System.out.println("User Details:");
        System.out.println("  ID: " + user.getId());
        System.out.println("  Username: " + user.getUsername());
        System.out.println("  Email: " + user.getEmail());

        if (user instanceof Person) {
            Person person = (Person) user;
            System.out.println("  Type: Person");
            System.out.println("  Name: " + person.getFirstName() + " " + person.getLastName());
            System.out.println("  Birth Date: " + person.getBirthDate());
            System.out.println("  Job: " + person.getJob());
            System.out.println("  Empathy Level: " + person.getEmpatyLevel());
        } else if (user instanceof Duck) {
            Duck duck = (Duck) user;
            System.out.println("  Type: Duck");
            System.out.println("  Duck Type: " + duck.getType());
            System.out.println("  Speed: " + duck.getSpeed());
            System.out.println("  Resistance: " + duck.getResistance());
            System.out.println("  Flock: " + (duck.getFlock() != null ? duck.getFlock().getFlockName() : "None"));
        }

        // Show friends count
        int friendCount = service.getFriendCount(user.getId());
        System.out.println("  Friends: " + friendCount);
    }

    private void printEventDetails(Event event) {
        System.out.println("Event Details:");
        System.out.println("  ID: " + event.getId());
        System.out.println("  Name: " + event.getName());
        System.out.println("  Description: " + event.getDescription());
        System.out.println("  Type: " + event.getEventType());
        System.out.println("  Administrator: " +
                (event.getAdministrator() != null ? event.getAdministrator().getUsername() : "None"));

        if (event instanceof RaceEvent) {
            RaceEvent race = (RaceEvent) event;
            System.out.println("  Participants: " + race.getParticipantCount());
            System.out.println("  Distances: " + race.getDistances());
            System.out.println("  Completed: " + race.isRaceCompleted());
        }
    }
}