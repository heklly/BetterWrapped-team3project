package data_access;

import entity.Group;
import entity.SpotifyUser;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.GroupDataAccessInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDataAccessObject implements GroupDataAccessInterface {

    private final File groupFile = new File("groups.json");

    @Override
    public boolean existsByName(String groupName) {
        JSONArray groups = readGroupsFile();
        for (Object obj : groups) {
            JSONObject g = (JSONObject) obj;
            if (g.getString("groupName").equalsIgnoreCase(groupName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existsByCode(String groupCode) {
        JSONArray groups = readGroupsFile();
        for (Object obj : groups) {
            JSONObject g = (JSONObject) obj;
            if (g.getString("groupCode").equals(groupCode)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void saveGroup(Group group) {
        JSONArray groups = readGroupsFile();
        groups.put(groupToJson(group));
        writeGroupsFile(groups);
    }

    @Override
    public Group getGroupByCode(String groupCode) {
        JSONArray groups = readGroupsFile();
        for (Object obj : groups) {
            JSONObject g = (JSONObject) obj;
            if (g.getString("groupCode").equals(groupCode)) {
                return jsonToGroup(g);
            }
        }
        return null;
    }

    @Override
    public void updateGroup(Group group) {
        JSONArray groups = readGroupsFile();
        for (int i = 0; i < groups.length(); i++) {
            JSONObject g = groups.getJSONObject(i);
            if (g.getString("groupCode").equals(group.getGroupCode())) {
                groups.put(i, groupToJson(group));
                writeGroupsFile(groups);
                return;
            }
        }
    }

    // ------------------- Helpers -------------------

    private JSONArray readGroupsFile() {
        if (!groupFile.exists()) return new JSONArray();
        try (BufferedReader br = new BufferedReader(new FileReader(groupFile))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) content.append(line);
            if (content.isEmpty()) return new JSONArray();
            return new JSONArray(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    private void writeGroupsFile(JSONArray groups) {
        try (FileWriter writer = new FileWriter(groupFile)) {
            writer.write(groups.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONObject groupToJson(Group group) {
        JSONObject json = new JSONObject();
        json.put("groupName", group.getGroup_name());
        json.put("groupCode", group.getGroupCode());

        JSONArray usersArray = new JSONArray();
        for (SpotifyUser user : group.getUsers()) {
            usersArray.put(user.getUsername());
        }
        json.put("users", usersArray);

        return json;
    }

    private Group jsonToGroup(JSONObject json) {
        JSONArray usersArray = json.optJSONArray("users");
        List<SpotifyUser> users = new ArrayList<>();

        if (usersArray != null) {
            for (Object u : usersArray) {
                String username = u.toString();
                users.add(new SpotifyUser(username)); // minimal reconstruction
            }
        }

        return new Group(json.getString("groupName"), users);
    }
}