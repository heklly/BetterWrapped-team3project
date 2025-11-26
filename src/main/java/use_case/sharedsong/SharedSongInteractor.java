package use_case.sharedsong;

import data_access.SpotifyDataAccessObject;
import entity.SpotifyUser;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlaying;
import se.michaelthelin.spotify.requests.data.library.CheckUsersSavedTracksRequest;
import se.michaelthelin.spotify.requests.data.player.GetUsersCurrentlyPlayingTrackRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Use Case Interactor for the Check Shared Song Use Case.
 */
public class SharedSongInteractor implements SharedSongInputBoundary {
    private final SharedSongOutputBoundary sharedSongPresenter;
    private final SpotifyDataAccessObject spotifyDataAccessObject;

    public SharedSongInteractor(SharedSongOutputBoundary sharedSongPresenter,
                                SpotifyDataAccessObject spotifyDAO) {
        this.sharedSongPresenter = sharedSongPresenter;
        this.spotifyDataAccessObject = spotifyDAO;

    }

    public void execute(SharedSongInputData inputData,
                        SpotifyDataAccessObject spotifyDataAccessObject) {
        final SpotifyUser user = inputData.getUser();
        final GetUsersCurrentlyPlayingTrackRequest request = spotifyDataAccessObject
                .getSpotifyApiForUser(user)
                .getUsersCurrentlyPlayingTrack()
                .build();
        try {

            final CurrentlyPlaying response = request.execute();

            // check to make sure something is playing, i.e. playing type and id are not null
            if (!response.getIs_playing()) {
                sharedSongPresenter.prepareFailureView("Try playing a song");
                return;
            }
            // check if a song is playing
            String currentlyPlayingType = response.getCurrentlyPlayingType().getType();
            if (!currentlyPlayingType.equals("track")) {
                sharedSongPresenter.prepareFailureView("Try playing a song");
                return;
            }
            // get song id for api call
            String trackId = response.getItem().getId();

            // go into group and check if song saved
            final Map<String, String> usernameToShared = mapBuilder(inputData.getListOfMembers(), trackId);

            // if no users in group have saved songs or none share your song then fail
            final SharedSongOutputData sharedSongOutputData = new SharedSongOutputData(usernameToShared);
            sharedSongPresenter.prepareSuccessView(sharedSongOutputData);

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private boolean checkUserSavedTrack(SpotifyUser user, String trackId) throws IOException, SpotifyWebApiException, ParseException {
        final CheckUsersSavedTracksRequest request = spotifyDataAccessObject
                .getSpotifyApiForUser(user)
                .checkUsersSavedTracks(new String[]{trackId})
                .build();
        final Boolean[] response = request.execute();
        return response[0];
    }

    private Map<String, String> mapBuilder(List<SpotifyUser> group, String trackId)
            throws IOException, SpotifyWebApiException, ParseException {
        final Map<String, String> usernameToShared = new HashMap<>();

        for (final SpotifyUser u : group) {
            if (checkUserSavedTrack(u, trackId)) {
                usernameToShared.put(u.getUsername(), "Yes");
            }
            else {
                usernameToShared.put(u.getUsername(), "No");
            }
        } return usernameToShared;
    }
}
