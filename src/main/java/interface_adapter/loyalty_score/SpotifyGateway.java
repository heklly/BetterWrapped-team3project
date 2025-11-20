package interface_adapter.loyalty_score;

import data_access.JSONParsers;
import data_access.JSONParsers;
import entity.TopItem;
import org.apache.hc.core5.http.ParseException;
import org.json.JSONObject;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.PlayHistory;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;
import se.michaelthelin.spotify.requests.data.player.GetCurrentUsersRecentlyPlayedTracksRequest;
import use_case.loyalty_score.SpotifyGatewayInterface;
import se.michaelthelin.spotify.SpotifyApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of SpotifyGatewayInterface.
 * Currently conflicting with Jennifer's implementation.
 */
public class SpotifyGateway implements SpotifyGatewayInterface {

    private final JSONParsers jsonParser;
    private final String ACCESS_TOKEN;
    private final SpotifyApi spotifyApi;
    private final GetUsersTopArtistsRequest usersTopArtistsRequest;
    private final GetUsersTopTracksRequest usersTopTracksRequest;
    private final GetCurrentUsersRecentlyPlayedTracksRequest recentlyPlayedTracksRequest;


    public SpotifyGateway(JSONParsers jsonParser, String accessToken) {
        this.jsonParser = jsonParser;
        this.ACCESS_TOKEN = accessToken;
        this.spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(accessToken)
                .build();
        this.usersTopArtistsRequest = spotifyApi.getUsersTopArtists()
                .limit(50)
                .time_range("short_term")
                .build();
        this.usersTopTracksRequest = spotifyApi.getUsersTopTracks()
                .limit(50)
                .time_range("short_term")
                .build();
        this.recentlyPlayedTracksRequest = spotifyApi.getCurrentUsersRecentlyPlayedTracks()
                .limit(50)
                .build();
    }

    @Override
    public List<TopItem> getTopArtists(String userId) throws IOException,
            ParseException, SpotifyWebApiException {
        try {
            Artist[] response = usersTopArtistsRequest.execute().getItems();
            List<TopItem> topItems = new ArrayList<>();
            for (int i = 0; i < response.length; i++) {
                Artist artist = response[i];
                TopItem topItem = new TopItem(artist.getName(), i);
                topItems.add(topItem);
            }
            return topItems;
        } catch (Exception e) {
            System.out.println("Something went wrong!\n" + e.getMessage());
            return List.of();
        }
    }

    @Override
    public List<TopItem> getTopTracks(String userId) throws IOException,
            ParseException, SpotifyWebApiException
    {
        try {
            Track[] response = usersTopTracksRequest.execute().getItems();
            List<TopItem> topItems = new ArrayList<>();
            for (int i = 0; i < response.length; i++) {
                Track track = response[i];
                TopItem topItem = new TopItem(track.getName(), i);
                topItems.add(topItem);
            }
            return topItems;
        } catch (Exception e) {
            System.out.println("Something went wrong!\n" + e.getMessage());
            return List.of();
        }
    }

    @Override
    public List<TopItem> getRecentlyPlayedTracks(String userId) throws IOException,
            ParseException, SpotifyWebApiException {
        try {
            PlayHistory[] response = recentlyPlayedTracksRequest.execute().getItems();
            List<TopItem> topItems = new ArrayList<>();
            for (int i = 0; i < response.length; i++) {
                PlayHistory playHistory = response[i];
                TopItem topItem = new TopItem(playHistory.getTrack().getName(), i);
                topItems.add(topItem);
            }
            return topItems;
        } catch (Exception e) {
            System.out.println("Something went wrong!\n" + e.getMessage());
            return List.of();
        }
    }
}
