package entity;

/**
 * A simple entity representing a user. Users have a username, userid, and datejoined
 */
public class User {

    private final String username;
    private final String userid;
    private final String date_created;

    /**
     * Creates a new user with the given non-empty name and non-empty password.
     * @param name the username
     * @param userid the unique userid for each user
     * @param date the date the user was created, formatted yyyy-mm-dd
     */
    public User(String name, String userid, String date ) {
        this.username = name;
        this.userid = userid;
        this.date_created = date;
    }

    public String getName() {
        return this.username;
    }
    public String getUserid() { return this.userid; }
    public String getDate() { return this.date_created; }

    // dummy method so code compiles...
    public String getPassword() {return "password";}

}
