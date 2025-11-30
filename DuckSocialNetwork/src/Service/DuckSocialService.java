package Service;

import Domain.entities.*;
import Domain.enums.DuckType;
import Repository.DuckSocialRepository;

import java.util.*;

public class DuckSocialService {
    private final UserService userService;
    private final FriendshipService friendshipService;
    private final FlockService flockService;
    private final EventService eventService;
    private final RaceService raceService;
    private final DuckSocialRepository repository;

    public DuckSocialService() {
        this.repository = DuckSocialRepositoryFactory.getInstance();
        this.userService = new UserService(repository);
        this.friendshipService = new FriendshipService(repository);
        this.flockService = new FlockService(repository);
        this.eventService = new EventService(repository);
        this.raceService = new RaceService(repository);
    }

    public DuckSocialService(DuckSocialRepository repository) {
        this.repository = repository;
        this.userService = new UserService(repository);
        this.friendshipService = new FriendshipService(repository);
        this.flockService = new FlockService(repository);
        this.eventService = new EventService(repository);
        this.raceService = new RaceService(repository);
    }

    // === DELEGATE METHODS ===

    // UserService delegates
    public User addUser(String username, String email, String password, String userType,
                        Map<String, Object> additionalData) {
        return userService.addUser(username, email, password, userType, additionalData);
    }

    public User updateUser(Long id, String username, String email, String password,
                           Map<String, Object> additionalData) {
        return userService.updateUser(id, username, email, password, additionalData);
    }

    public User deleteUser(Long id) {
        return userService.deleteUser(id);
    }

    public User findUserById(Long id) {
        return userService.findUserById(id);
    }

    public User findUserByUsername(String username) {
        return userService.findUserByUsername(username);
    }

    public User findUserByEmail(String email) {
        return userService.findUserByEmail(email);
    }

    public Iterable<User> findAllUsers() {
        return userService.findAllUsers();
    }

    public Iterable<Duck> findAllDucks() {
        return userService.findAllDucks();
    }

    public Iterable<Person> findAllPersons() {
        return userService.findAllPersons();
    }

    public Iterable<Duck> findDucksByType(DuckType type) {
        return userService.findDucksByType(type);
    }

    public Iterable<Duck> findDucksWithoutFlock() {
        return userService.findDucksWithoutFlock();
    }

    public Iterable<Duck> findFlyingDucks() {
        return userService.findFlyingDucks();
    }

    // FriendshipService delegates
    public void addFriendship(Long userId1, Long userId2) {
        friendshipService.addFriendship(userId1, userId2);
    }

    public void removeFriendship(Long userId1, Long userId2) {
        friendshipService.removeFriendship(userId1, userId2);
    }

    public boolean areFriends(Long userId1, Long userId2) {
        return friendshipService.areFriends(userId1, userId2);
    }

    public Iterable<User> getFriends(Long userId) {
        return friendshipService.getFriends(userId);
    }

    public int getFriendCount(Long userId) {
        return friendshipService.getFriendCount(userId);
    }

    public int getNumberOfCommunities() {
        return friendshipService.getNumberOfCommunities();
    }

    public List<User> getMostSociableCommunity() {
        return friendshipService.getMostSociableCommunity();
    }

    // FlockService delegates
    public Flock addFlock(String name) {
        return flockService.addFlock(name);
    }

    public Flock updateFlock(Long id, String name) {
        return flockService.updateFlock(id, name);
    }

    public Flock deleteFlock(Long id) {
        return flockService.deleteFlock(id);
    }

    public Flock findFlockById(Long id) {
        return flockService.findFlockById(id);
    }

    public Iterable<Flock> findAllFlocks() {
        return flockService.findAllFlocks();
    }

    // EventService delegates
    public Event addEvent(String name, String description, String eventType, User administrator) {
        return eventService.addEvent(name, description, eventType, administrator);
    }

    public Event findEventById(Long id) {
        return eventService.findEventById(id);
    }

    public Iterable<Event> findAllEvents() {
        return eventService.findAllEvents();
    }

    // RaceService delegates
    public RaceEvent createRaceEvent(String name, String description, User administrator, List<Double> distances) {
        return raceService.createRaceEvent(name, description, administrator, distances);
    }

    public void addParticipantToRace(Long raceEventId, Long duckId) {
        raceService.addParticipantToRace(raceEventId, duckId);
    }

    public void removeParticipantFromRace(Long raceEventId, Long duckId) {
        raceService.removeParticipantFromRace(raceEventId, duckId);
    }

    public Map<Duck, Double> calculateRaceResults(Long raceEventId) {
        return raceService.calculateRaceResults(raceEventId);
    }

    public List<Duck> getRaceWinners(Long raceEventId) {
        return raceService.getRaceWinners(raceEventId);
    }

    public List<RaceEvent> findAllRaceEvents() {
        return raceService.findAllRaceEvents();
    }

    public boolean simulateRace(Long raceEventId) {
        return raceService.simulateRace(raceEventId);
    }

    // === STATISTICS METHODS (integrated in main service) ===
    public int getTotalUsers() {
        return repository.size();
    }

    public int getTotalFriendships() {
        return friendshipService.getTotalFriendships();
    }

    public Map<String, Integer> getUserStatistics() {
        Map<String, Integer> stats = new HashMap<>();
        int totalUsers = 0;
        int totalDucks = 0;
        int totalPersons = 0;

        for (User user : repository.findAll()) {
            totalUsers++;
            if (user instanceof Duck) {
                totalDucks++;
            } else if (user instanceof Person) {
                totalPersons++;
            }
        }

        stats.put("totalUsers", totalUsers);
        stats.put("totalDucks", totalDucks);
        stats.put("totalPersons", totalPersons);
        stats.put("totalFriendships", friendshipService.getTotalFriendships());
        stats.put("communities", friendshipService.getNumberOfCommunities());

        return stats;
    }

    public Map<String, Object> getDetailedStatistics() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("userStatistics", getUserStatistics());
        stats.put("flockCount", getFlockCount());
        stats.put("eventCount", getEventCount());
        stats.put("raceEventCount", getRaceEventCount());
        stats.put("averageFriendsPerUser", getAverageFriendsPerUser());
        stats.put("mostSociableCommunitySize", getMostSociableCommunitySize());

        return stats;
    }

    public Map<String, Double> getDuckPerformanceStats() {
        Map<String, Double> stats = new HashMap<>();
        List<Duck> ducks = new ArrayList<>();
        userService.findAllDucks().forEach(ducks::add);

        if (ducks.isEmpty()) {
            return stats;
        }

        double avgSpeed = ducks.stream().mapToDouble(Duck::getSpeed).average().orElse(0);
        double avgResistance = ducks.stream().mapToDouble(Duck::getResistance).average().orElse(0);
        double maxSpeed = ducks.stream().mapToDouble(Duck::getSpeed).max().orElse(0);
        double maxResistance = ducks.stream().mapToDouble(Duck::getResistance).max().orElse(0);

        stats.put("averageSpeed", avgSpeed);
        stats.put("averageResistance", avgResistance);
        stats.put("maxSpeed", maxSpeed);
        stats.put("maxResistance", maxResistance);

        return stats;
    }

    public Map<DuckType, Long> getDuckTypeDistribution() {
        Map<DuckType, Long> distribution = new HashMap<>();
        for (Duck duck : userService.findAllDucks()) {
            distribution.merge(duck.getType(), 1L, Long::sum);
        }
        return distribution;
    }

    private long getFlockCount() {
        return flockService.findAllFlocks().spliterator().getExactSizeIfKnown();
    }

    private long getEventCount() {
        return eventService.findAllEvents().spliterator().getExactSizeIfKnown();
    }

    private long getRaceEventCount() {
        return raceService.findAllRaceEvents().size();
    }

    private double getAverageFriendsPerUser() {
        int totalUsers = getTotalUsers();
        if (totalUsers == 0) return 0;

        int totalFriendships = getTotalFriendships();
        return (double) totalFriendships * 2 / totalUsers; // Each friendship involves 2 users
    }

    private int getMostSociableCommunitySize() {
        List<User> mostSociable = friendshipService.getMostSociableCommunity();
        return mostSociable != null ? mostSociable.size() : 0;
    }

    // === GETTERS FOR INDIVIDUAL SERVICES ===
    public UserService getUserService() {
        return userService;
    }

    public FriendshipService getFriendshipService() {
        return friendshipService;
    }

    public FlockService getFlockService() {
        return flockService;
    }

    public EventService getEventService() {
        return eventService;
    }

    public RaceService getRaceService() {
        return raceService;
    }
}