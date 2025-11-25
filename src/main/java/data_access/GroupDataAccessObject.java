package data_access;

import entity.Group;
import entity.SpotifyUser;
import org.json.JSONObject;
import use_case.create_group.GroupDataAccessInterface;
import use_case.leave_group.LeaveGroupDataAccessInterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class GroupDataAccessObject implements GroupDataAccessInterface, LeaveGroupDataAccessInterface {

//    private final static String
    private final File jsonFile;
    private final String jsonPath;
    private final JSONObject jsonObject;
    private
    private final

    /**
     * Construct this DAO for saving to and reading from a local file.
     * @param csvPath the path of the file to save to
     * @param userFactory factory for creating user objects
     * @throws RuntimeException if there is an IOException when accessing the file
     */

    public GroupDataAccessObject(String jsonPath) {

        jsonFile = new File(jsonPath);

        if (jsonFile.length() == 0) {
            save();
        }
        else {
            try (BufferedReader reader = new BufferedReader(new FileReader(jsonFile))) {
                final String header = reader.readLine();
            }
        }
    }

    @Override
    public Group getGroupByName(String group_name) {
        return null;
    }

    @Override
    public void updateGroup(Group group) {

    }

    public void addUserToGroup(SpotifyUser user, String groupName) {
        idk.get(groupName).add(user.getSpotifyUserId());
    }
    public void removeUserFromGroup(SpotifyUser user, String groupName) {
        idk.get(groupName).remove(user.getSpotifyUserId());
    }

    @Override
    public boolean existsByName(String groupName) {
        return false;
    }

    @Override
    public void saveGroup(Group group) {

    }

}
