package use_case.sharedsong;

import entity.Group;
import entity.User;
import java.util.List;

/**
 * The input data for the Check Shared Song Use Case.
 */
public class CheckSharedSongInputData {

    private final Group group;
    private final User user;

    public CheckSharedSongInputData(Group group, User user) {
        this.group = group;
        this.user = user;
    }
    public List<User> getListOfMembers() {return group.getMembers();}
    public User getUser() {return user;}
}

