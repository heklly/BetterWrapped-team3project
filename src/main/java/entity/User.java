package entity;

/**
 * A simple entity representing a user.
 * Users have a username, password (for authentication), a userid, and a date of creation.
 */
public class User {

    private final String username;
    private String password;
    private final int userid;
    private final String date_created;

    /**
     * Constructor for user creation during login/signup processes.
     * In this case, userid and date may not yet be assigned.
     *
     * @param name     the username
     * @param password the user's password
     */
    public User(String name, String password) {
        this(name, password, -1, "");
    }

    /**
     * Constructor used when userid and date are known,
     * while the password is not relevant for this creation path.
     *
     * @param name   the username
     * @param userid the unique user ID
     * @param date   the account creation date
     */
    public User(String name, int userid, String date) {
        this(name, "", userid, date);
    }

    /**
     * Full constructor specifying all fields.
     *
     * @param name     the username
     * @param password the user’s password
     * @param userid   the unique user ID
     * @param date     the account creation date
     */
    public User(String name, String password, int userid, String date) {
        this.username = name;
        this.password = password;
        this.userid = userid;
        this.date_created = date;
    }

    public String getName() {
        return this.username;
    }

    public int getUserid() {
        return this.userid;
    }

    public String getDate() {
        return this.date_created;
    }

    //The following are to ensure that existing DAO/use cases compile successfully:

    /** Password getter for login-related use cases. */
    public String getPassword() {
        return this.password;
    }

    /** Password setter for change-password use case. */
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
}



