package data_access;

import entity.SpotifyUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton DAO to store SpotifyUser objects across the app.
 */
public class SpotifyUserDataAccessObject {

    private static SpotifyUserDataAccessObject instance;

    // This map lives inside the DAO, not the entity
    private final Map<String, SpotifyUser> userIdToUser;

    private SpotifyUserDataAccessObject() {
        this.userIdToUser = new HashMap<>();
    }

    public static SpotifyUserDataAccessObject getInstance() {
        if (instance == null) {
            instance = new SpotifyUserDataAccessObject();
        }
        return instance;
    }

    // Add or update a SpotifyUser
    public void addUser(SpotifyUser user) {
        if (user != null) {
            userIdToUser.put(user.getSpotifyUserId(), user);
        }
    }

    // Lookup a SpotifyUser by their Spotify User ID
    public SpotifyUser getUserById(String userId) {
        return userIdToUser.get(userId);
    }

    // Optional: get all users
    public List<SpotifyUser> getAllUsers() {
        return new ArrayList<>(userIdToUser.values());
    }

    // Optional: remove a user
    public void removeUser(String userId) {
        userIdToUser.remove(userId);
    }
}
