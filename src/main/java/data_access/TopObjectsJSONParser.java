package data_access;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TopObjectsJSONParser {

    /**
     * @param responseJSON the response JSONObject from Get User's Top Items
     * @return the names of top tracks/artists, in order.
     */

    public static List<String> getTopObjects(JSONObject responseJSON) {
        JSONArray items = responseJSON.getJSONArray("items");
        List<String> topObjects = new ArrayList<>();

        for (int i = 0; i < items.length(); i++) {
            JSONObject object_info = items.getJSONObject(i);
            String name = object_info.get("name").toString();
            topObjects.add(name);
        }
        return topObjects;
    }

}
