package use_case.sharedsong;

import entity.User;
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

    public SharedSongInteractor(SharedSongOutputBoundary sharedSongPresenter) {
        this.sharedSongPresenter = sharedSongPresenter;
    }

    private CurrentlyPlaying getCurrentlyPlaying(User user) {
        // make api request
        GetUsersCurrentlyPlayingTrackRequest request = spotifyApi
                .getUsersCurrentlyPlayingTrack()
//                    .additionalTypes("track")
                .build();
        try {
            final CurrentlyPlaying response = request.execute();
            return response;

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void execute(SharedSongInputData inputData) {
        final User user = inputData.getUser();

        final GetUsersCurrentlyPlayingTrackRequest request = spotifyApi
                .getUsersCurrentlyPlayingTrack()
//                    .additionalTypes("track")
                .build();
        try {
            final CurrentlyPlaying response = request.execute();

            // check to make sure something is playing
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
            final List<User> group = inputData.getListOfMembers();
            final Map<String, String> usernameToShared = new HashMap<String, String>();

            // tracking if at least one person shares your song
            boolean noneShare = false;
            for (final User u : group) {
                boolean saved = checkUserSavedTrack(u, trackId);
                if (u == user)
                    continue;
                else if (saved) {
                    usernameToShared.put(u.getName(), "Yes");
                    noneShare = true;
                }
                // user has not saved song
                else {
                    usernameToShared.put(u.getName(), "No");
                }
            }
            // if no users in group have saved songs or none share your song then fail
            if (noneShare) {
                sharedSongPresenter.prepareFailureView("No one shares your song :(");
            } else {
                final SharedSongOutputData sharedSongOutputData = new SharedSongOutputData(usernameToShared);
                sharedSongPresenter.prepareSuccessView(sharedSongOutputData);
            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkUserSavedTrack(User user, String trackId) {
        try {
            final CheckUsersSavedTracksRequest request = spotifyApi.checkUserSavedTracks(new String[]{trackId}).build();
            final Boolean[] response = request.execute();
            return response[0];
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
