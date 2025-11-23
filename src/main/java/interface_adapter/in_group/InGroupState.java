package interface_adapter.in_group;

import java.util.ArrayList;
import java.util.List;

public class InGroupState {
    private String username = "";
    private String groupName = "";
    private String ownerName = "";
    private List<String> groupUsers = new ArrayList<>();

    public InGroupState() {};

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username;}

    public String getGroupName() { return groupName; }

    public void setGroupName(String groupName) { this.groupName = groupName; }

    public List<String> getGroupUsers() { return groupUsers; }

    public void setGroupUsers(List<String> groupUsers) { this.groupUsers = groupUsers; }
}
