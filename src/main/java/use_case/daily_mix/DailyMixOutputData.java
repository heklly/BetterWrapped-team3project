package use_case.daily_mix;

import java.util.List;

/**
 * Output Data for the Daily Mix Use Case.
 */
public class DailyMixOutputData {

    private final String playlistName;
    private final List<String> trackIds;
    private final String message; // can be empty if no special message

    public DailyMixOutputData(String playlistName, List<String> trackIds, String message) {
        this.playlistName = playlistName;
        this.trackIds = trackIds;
        this.message = message;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public List<String> getTrackIds() {
        return trackIds;
    }

    public String getMessage() {
        return message;
    }
}

