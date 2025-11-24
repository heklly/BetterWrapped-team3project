package interface_adapter.get_top_songs;

import java.util.*;

public class GetTopSongsState {
    private List<String> topItems = new ArrayList<>();
    private boolean success = false;

    public List<String> getTopItems() {
        return topItems;
    }
    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    public void setTopItems(List<String> topItems) {
        this.topItems = topItems;
    }
}
