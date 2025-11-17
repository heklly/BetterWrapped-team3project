package use_case.daily_mix;

import java.util.List;
import java.util.Map;

/**
 * DAO interface for the Daily Mix Use Case.
 */
public interface DailyMixUserDataAccessInterface {

    /**
     * Returns the user's library and play frequencies.
     *
     * @param username the username
     * @return a Map from track id to play count
     */
    Map<String, Integer> getLibraryWithFrequencies(String username);

    /**
     * Creates (or replaces) the "Daily Mix" playlist for this user.
     *
     * @param username     the username
     * @param playlistName the playlist name (e.g., "Daily Mix")
     * @param trackIds     the ordered list of track ids for the playlist
     */
    void saveDailyMixPlaylist(String username, String playlistName, List<String> trackIds);

    /**
     * Returns the track ids from the previous Daily Mix, if any.
     * Used for simple anti-repetition / cooldown logic.
     *
     * @param username the username
     * @return list of track ids in the previous Daily Mix, or empty list if none
     */
    List<String> getPreviousDailyMix(String username);
}
