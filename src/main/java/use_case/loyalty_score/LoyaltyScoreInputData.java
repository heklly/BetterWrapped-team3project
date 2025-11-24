package use_case.loyalty_score;

import entity.SpotifyUser;
import entity.User;
import org.json.JSONArray;

/**
 * The Input Data for the Login Use Case.
 * artist_name is the artist of interest.
 */
public class LoyaltyScoreInputData {

    private final User currentUser;
    private final SpotifyUser spotifyUser;
    private final String artist_name;
    private JSONArray loyalty_scores;


    public LoyaltyScoreInputData(User currentUser, SpotifyUser currentSpotifyUser, String artistName) {
        this.currentUser = currentUser;
        this.spotifyUser = currentSpotifyUser;
        this.artist_name = artistName;
    }

    public User getCurrentUser() {
        return currentUser;
    }
    public SpotifyUser getSpotifyUser() {
        return spotifyUser;
    }

    public String getArtist_name() {
        return artist_name;
    }
}