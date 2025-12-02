package use_case.create_group;

import entity.Group;
import entity.SpotifyUser;

import java.util.List;

/**
 * Output Data for the Create Group Use Case.
 */
public class CreateGroupOutputData {

    private final String group_name;
    private boolean success;
    private final SpotifyUser owner;
    private final List<SpotifyUser> users;
    private final Group group;  // ADD THIS

    /**
     * Constructs CreateGroupOutputData object
     *
     * @param group_name name of newly created group
     * @param owner the group owner
     * @param users list of users in the group
     * @param group the Group entity
     */
    public CreateGroupOutputData(String group_name, SpotifyUser owner, List<SpotifyUser> users, Group group) {
        this.group_name = group_name;
        this.success = true;  // Fixed: was assigning success to itself
        this.owner = owner;
        this.users = users;
        this.group = group;  // ADD THIS
    }

    public String getGroup_name() {
        return group_name;
    }

    public boolean isSuccess() {
        return success;
    }

    public SpotifyUser getOwner() {
        return owner;
    }

    public List<SpotifyUser> getUsers() {
        return users;
    }

    public Group getGroup() {  // ADD THIS GETTER
        return group;
    }
}

