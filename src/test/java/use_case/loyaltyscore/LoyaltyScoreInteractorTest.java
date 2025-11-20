package use_case.loyaltyscore;

import data_access.TopObjectsJSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import data_access.TopObjectsJSONParser;
import org.junit.jupiter.api.Test;



import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoyaltyScoreInteractorTest {

    @Test
    void testGetTopObjects() {
        // Create dummy JSON simulating Spotify's "top items" response
        JSONObject response = new JSONObject();
        JSONArray items = new JSONArray();

        JSONObject item1 = new JSONObject();
        item1.put("name", "Track One");
        item1.put("id", "123");

        JSONObject item2 = new JSONObject();
        item2.put("name", "Track Two");
        item2.put("id", "456");

        items.put(item1);
        items.put(item2);

        response.put("items", items);

        System.out.println(response.toString(2));

        TopObjectsJSONParser parser = new TopObjectsJSONParser();
        List<String> topObjects = parser.getTopObjects(response);



    }

}
