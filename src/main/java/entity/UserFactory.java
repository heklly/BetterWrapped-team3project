package entity;

/**
 * Factory class for creating User entities.
 */
public class UserFactory {

    /**
     * Creates a new User with username and password.
     */
    public User create(String username, String password) {
        return new User(username, password);
    }
}


