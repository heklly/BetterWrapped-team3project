package use_case.loyalty_score;

import entity.TopItem;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.util.List;

/**
 * An interface for communicating with spotify's api.
 * Currently conflicting with Jennifer's implementation.
 */

public interface SpotifyGatewayInterface {
    /**
     * @return the Top artists, in order
     */
    List<TopItem> getTopArtists(String userId) throws IOException, ParseException, SpotifyWebApiException;

    /**
     * @return the Top tracks, in order
     */
    List<TopItem> getTopTracks(String userId) throws IOException, ParseException, SpotifyWebApiException;

    /**
     * @return recently played tracks, in order
     */

    List<TopItem> getRecentlyPlayedTracks(String userId) throws IOException, ParseException, SpotifyWebApiException;
}
