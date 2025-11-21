package use_case.loyaltyscore;
import data_access.JSONParsers;
import data_access.JSONParsers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JSONParserTest {

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

        List<String> topObjects = JSONParsers.getTopObjects(response);

        // Assertions
        assertEquals(2, topObjects.size());
        assertEquals("Track One", topObjects.get(0));
        assertEquals("Track Two", topObjects.get(1));
    }

}
