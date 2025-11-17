package entity;

/**
 * A simple entity representing a user.
 * Users have a username, password, userid, and date joined.
 */
public class User {

    private final String username;
    private String password;
    private final int userid;
    private final String date_created;

    /**
     * Constructor mainly used by login/signup examples:
     * username + password.
     * userid / date
     */
    public User(String username, String password) {
        this(username, password, -1, "");
    }

    /**
     * Constructor for when you also want userid & date.
     */
    public User(String username, String password, int userid, String date) {
        this.username = username;
        this.password = password;
        this.userid = userid;
        this.date_created = date;
    }

    /**
     * keep original User Class
     * set password as empty string.
     */
    public User(String name, int userid, String date) {
        this(name, "", userid, date);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
}

