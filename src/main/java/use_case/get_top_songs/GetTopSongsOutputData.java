package use_case.get_top_songs;

import java.util.List;

public class GetTopSongsOutputData {

    private final List<String> topItems;

    public GetTopSongsOutputData(List<String> topItems) {
        this.topItems = topItems;
    }

    public List<String> getTopItems() {
        return topItems;
    }
}
