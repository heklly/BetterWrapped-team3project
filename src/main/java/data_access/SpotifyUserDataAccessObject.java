package data_access;

import entity.SpotifyUser;
import java.util.*;

public class SpotifyUserDataAccessObject {

    private static SpotifyUserDataAccessObject instance; // singleton instance

    private final Map<String, SpotifyUser> usernameToUser = new HashMap<>();

    private SpotifyUserDataAccessObject() {
        // private constructor to prevent external instantiation
    }

    // Accessor for singleton
    public static SpotifyUserDataAccessObject getInstance() {
        if (instance == null) {
            instance = new SpotifyUserDataAccessObject();
        }
        return instance;
    }

    // Add a user
    public void addUser(SpotifyUser user) {
        usernameToUser.put(user.getUsername(), user);
    }

    // Get a user by username
    public SpotifyUser getUserByUsername(String username) {
        return usernameToUser.get(username);
    }

    public Collection<SpotifyUser> getAllUsers() {
        return usernameToUser.values();
    }
}