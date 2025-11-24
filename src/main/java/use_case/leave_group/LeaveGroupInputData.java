package use_case.leave_group;

import entity.Group;
import entity.SpotifyUser;

public class LeaveGroupInputData {
    private final SpotifyUserUser user;
    private final Group group;

    public LeaveGroupInputData(final SpotifyUser user, final Group group) {
        this.user = user;
        this.group = this.group;
    }

    public String getUser() { return user; }
    public String getGroup() { return group; }

}
