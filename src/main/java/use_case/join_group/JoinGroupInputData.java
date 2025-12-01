package use_case.join_group;

import entity.SpotifyUser;

import java.util.List;

public class JoinGroupInputData {

    private final String groupCode;
    private final SpotifyUser user;

    public JoinGroupInputData(String groupCode, SpotifyUser user) {
        this.groupCode = groupCode;
        this.user = user;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public SpotifyUser getUser() {
        return user;
    }

    public List<SpotifyUser> getUsers(List<SpotifyUser> users) {
    return users;
    }
}