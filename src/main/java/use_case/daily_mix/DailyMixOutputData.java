package use_case.daily_mix;

import java.util.List;

public class DailyMixOutputData {
    private final List<String> tracks;  // song + artist

    public DailyMixOutputData(List<String> tracks) {
        this.tracks = tracks;
    }

    public List<String> getTracks() {
        return tracks;
    }
}

