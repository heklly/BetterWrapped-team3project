package use_case.daily_mix;

import entity.Playlist;

import java.util.List;
import java.util.Map;

/**
 * DAO interface for the Daily Mix Use Case.
 */
public interface DailyMixUserDataAccessInterface {

    /**
     * Returns the user's library with preference weights for each track.
     *
     * The concrete implementation may derive these weights from listening history,
     * top tracks, or any other available data source (e.g., Spotify Web API),
     * but the use case only relies on the relative weights.
     *
     * @param userId the unique id of the user
     * @return a Map from track identifier to a non-negative weight
     */
    Map<String, Integer> getLibraryWithFrequencies(int userId);

    /**
     * Creates (or replaces) the "Daily Mix" playlist for this user.
     * The implementation is responsible for constructing and persisting
     * the Playlist entity (including its creation date), and returning it.
     *
     * @param userId       the unique id of the user
     * @param playlistName the playlist name (e.g., "Daily Mix")
     * @param trackIds     the ordered list of track ids for the playlist
     * @return the created/updated Playlist entity
     */
    Playlist saveDailyMixPlaylist(int userId, String playlistName, List<String> trackIds);

    /**
     * Returns the previous Daily Mix playlist for this user, if any.
     * Used for simple anti-repetition / cooldown logic.
     *
     * @param userId the unique id of the user
     * @return the previous Daily Mix playlist, or null if none
     */
    Playlist getPreviousDailyMix(int userId);
}

