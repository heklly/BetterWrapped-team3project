package use_case.join_group;

public class JoinGroupOutputData {
    private final String groupName;
    private final String message;

    public JoinGroupOutputData(String groupName, boolean success, String message) {
        this.groupName = groupName;
        this.message = message;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getMessage() {
        return message;
    }
}
