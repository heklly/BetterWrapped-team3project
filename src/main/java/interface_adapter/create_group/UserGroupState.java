package interface_adapter.create_group;

import java.util.List;

/**
 * The state for the Group View Model.
 */
public class UserGroupState {

    private String groupName = "";
    private List<String> groupUsernames = null;
    private boolean inGroup =  false;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<String> getGroupUsernames() {
        return groupUsernames;
    }

    public void setGroupUsernames(List<String> groupUsernames) {
        this.groupUsernames = groupUsernames;
    }

    public boolean isInGroup() {
        return inGroup;
    }

    public void setInGroup(boolean inGroup) {
        this.inGroup = inGroup;
    }
}
