package interface_adapter;

import java.util.List;

/**
 * The state for the Group View Model.
 */
public class CreateGroupState {

    // dont know if we would need all of these
    private String username;
    private String groupName;
    private String groupNameError;
    private List<String> groupUsernames;
    private String groupUsernamesError;
    private boolean inGroup;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupNameError() {
        return groupNameError;
    }

    public void setGroupNameError(String groupNameError) {
        this.groupNameError = groupNameError;
    }

    public List<String> getGroupUsernames() {
        return groupUsernames;
    }

    public void setGroupUsernames(List<String> groupUsernames) {
        this.groupUsernames = groupUsernames;
    }

    public String getGroupUsernamesError() {
        return groupUsernamesError;
    }

    public void setGroupUsernamesError(String groupUsernamesError) {
        this.groupUsernamesError = groupUsernamesError;
    }

    public boolean isInGroup() {
        return inGroup;
    }

    public void setInGroup(boolean inGroup) {
        this.inGroup = inGroup;
    }
}
