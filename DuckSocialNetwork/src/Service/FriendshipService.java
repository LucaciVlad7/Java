package Service;

import Domain.entities.User;
import Domain.Validator.ValidationException;
import Repository.DuckSocialRepository;

public class FriendshipService {
    private final DuckSocialRepository repository;

    public FriendshipService(DuckSocialRepository repository) {
        this.repository = repository;
    }

    // === FRIENDSHIP MANAGEMENT ===
    public void addFriendship(Long userId1, Long userId2) {
        // Validate users exist
        User user1 = repository.findOne(userId1);
        User user2 = repository.findOne(userId2);

        if (user1 == null || user2 == null) {
            throw new ValidationException("One or both users not found");
        }

        repository.addFriendship(userId1, userId2);
    }

    public void removeFriendship(Long userId1, Long userId2) {
        repository.removeFriendship(userId1, userId2);
    }

    public boolean areFriends(Long userId1, Long userId2) {
        return repository.areFriends(userId1, userId2);
    }

    public Iterable<User> getFriends(Long userId) {
        return repository.getFriends(userId);
    }

    public int getFriendCount(Long userId) {
        return repository.getFriendCount(userId);
    }

    // === COMMUNITY ANALYSIS ===
    public int getNumberOfCommunities() {
        return repository.getNumberOfCommunities();
    }

    public java.util.List<User> getMostSociableCommunity() {
        return repository.getMostSociableCommunity();
    }

    public int getTotalFriendships() {
        return repository.getTotalFriendships();
    }
}