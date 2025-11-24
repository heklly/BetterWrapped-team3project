package entity;

/**
 * Dummy kept in so code compiles...
 */
public class UserFactory {

    public User create(String name, String password) {
        return new User(name, "id", "hi");
    }
}