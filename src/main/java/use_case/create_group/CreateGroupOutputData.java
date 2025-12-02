package use_case.create_group;

import entity.SpotifyUser;

import java.util.List;

/**
 * Output Data for the Create Group Use Case.
 */
public class CreateGroupOutputData {

    private final String group_name;
    private final List<SpotifyUser> users;


    /**
     * Constructs CreateGroupOutputData object
     *
     * @param group_name name of newly created group
     * @param groupCode the code used by users to join a group
     */

    public CreateGroupOutputData(String group_name, List<SpotifyUser> users, String groupCode) {
        this.group_name = group_name;
        this.users = users;
    }

    public String getGroup_name() {
        return group_name;
    }

    public List<SpotifyUser> getUsers() { return users; }
}

