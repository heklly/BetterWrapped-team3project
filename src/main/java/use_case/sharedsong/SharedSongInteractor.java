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
    private final SpotifyDataAccessObject spotifyDAO;

    public SharedSongInteractor(SharedSongOutputBoundary sharedSongPresenter, SpotifyDataAccessObject spotifyDataAccessObject) {
        this.sharedSongPresenter = sharedSongPresenter;
        this.spotifyDAO = spotifyDataAccessObject;
    }

    public void execute(SharedSongInputData inputData) {
        final SpotifyUser user = inputData.getUser();

        final GetUsersCurrentlyPlayingTrackRequest request = spotifyDAO.getSpotifyApiForUser(user)
                .getUsersCurrentlyPlayingTrack()
//                    .additionalTypes("track")
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
            final Map<String, String> usernameToShared = new HashMap<>();
            boolean shared = mapBuilder(inputData.getListOfMembers(), usernameToShared, trackId);

            // if no users in group have saved songs or none share your song then fail
            if (shared) {
                final SharedSongOutputData sharedSongOutputData = new SharedSongOutputData(usernameToShared);
                sharedSongPresenter.prepareSuccessView(sharedSongOutputData);
            } else {
                sharedSongPresenter.prepareFailureView("No one shares your song :(");

            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private boolean checkUserSavedTrack(SpotifyUser user, String trackId) throws IOException, SpotifyWebApiException, ParseException {
        final CheckUsersSavedTracksRequest request = spotifyDAO.getSpotifyApiForUser(user).checkUsersSavedTracks(new String[]{trackId}).build();
        final Boolean[] response = request.execute();
        return response[0];
    }

    private boolean mapBuilder(List<SpotifyUser> group, Map<String, String> usernameToShared, String trackId)
            throws IOException, SpotifyWebApiException, ParseException {
        // tracking if at least one person shares your song
        boolean shared = false;
        for (final SpotifyUser u : group) {
            if (checkUserSavedTrack(u, trackId)) {
                usernameToShared.put(u.getUsername(), "Yes");
                shared = true;
            }
            else {
                usernameToShared.put(u.getUsername(), "No");
            }
        } return shared;
    }
}
