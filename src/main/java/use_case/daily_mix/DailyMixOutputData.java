package use_case.daily_mix;

import entity.Playlist;

/**
 * Output Data for the Daily Mix Use Case.
 *
 * The output includes the Playlist entity that was generated,
 * and an optional message for the presenter (e.g., small library warning).
 */
public class DailyMixOutputData {

    private final Playlist playlist;
    private final String message;

    public DailyMixOutputData(Playlist playlist, String message) {
        this.playlist = playlist;
        this.message = message;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public String getMessage() {
        return message;
    }
}


