package entity;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple entity representing a group. Groups have a group_name, group_playlists, users,
 * and date_created
 */
public class Group {

    private String group_name;
    private final List<User> users;
    private final List<Playlist> group_playlists;
    private final String date_created;


    /**
     * Creates a new group given these parameters:
     * @param group_name the group's name
     * @param date the date the group was created
     */
    public Group(String group_name, String date ) {
        this.group_name = group_name;
        this.date_created = date;
        this.users = new ArrayList<User>();
        this.group_playlists = new ArrayList<Playlist>();
    }

    public String getName() {
        return this.group_name;
    }
    public String getDate() { return this.date_created; }
    public void addUser(User user) { this.users.add(user); }
    public void addPlaylist(Playlist playlist) { this.group_playlists.add(playlist); }
    public void changeName(String group_name) { this.group_name = group_name; }
    public List<User> getUsers() { return new ArrayList<User>(this.users); }
}
