package use_case.sharedsong;

import entity.Group;
import entity.User;
import java.util.List;

/**
 * The input data for the Check Shared Song Use Case.
 */
public class SharedSongInputData {

    private final Group group;
    private final User user;

    public SharedSongInputData(User user, Group group) {
        this.group = group;
        this.user = user;
    }
    public List<User> getListOfMembers() {return group.getUsers();}
    public User getUser() {return user;}
}

