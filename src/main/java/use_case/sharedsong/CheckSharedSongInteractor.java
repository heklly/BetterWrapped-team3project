package use_case.sharedsong;

import entity.User;
import se.michaelthelin.spotify.requests.data.library.CheckUsersSavedTracksRequest;
import se.michaelthelin.spotify.requests.data.player.GetUsersCurrentlyPlayingTrackRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Use Case Interactor for the Check Shared Song Use Case.
 */
public class CheckSharedSongInteractor implements CheckSharedSongInputBoundary{
    private final CheckSharedSongGroupDataAccessInterface sharedSongDataAccessObject;
    private final CheckSharedSongOutputBoundary sharedSongPresenter;

    public CheckSharedSongInteractor(CheckSharedSongGroupDataAccessInterface sharedSongDataAccessInterface,
                                     CheckSharedSongOutputBoundary sharedSongPresenter) {
        this.sharedSongDataAccessObject = sharedSongDataAccessInterface;
        this.sharedSongPresenter = sharedSongPresenter;
    }

    public void execute(CheckSharedSongInputData inputData) {
        final User user = inputData.getUser();
        //get current user's currently playing state
        // ...
        //parse JSON response
        // get "currently_playing_type" and fail if not currently playing a track

        // get "is_playing"

        // boolean isPlaying =

        // go into string key "item"

        // get "id"
        // String trackId =

        // if not currently playing a track (is playing is false or not currently playing a track)
        if (isPlaying == false || !playingType.equals("track")) {
            sharedSongPresenter.prepareFailureView("Try playing a song");
        }

        //go into group and check saved songs
        final List<User> group = inputData.getListOfMembers();
        final Map<String, String> usernameToShared = new HashMap<String, String>();
        // tracking if at least one person shares your song
        boolean noneShare = false;
        for (final User u : group) {
//            CheckUsersSavedTracksRequest request =
            if (u == user)
                continue;
            else if (...) {
                usernameToShared.put(u.getName(), "Yes");
                noneShare = true;
            } else (...)
                usernameToShared.put(u.getName(), "No");

        }
        // if no users in group have saved songs or none share your song then fail
        if (noneShare) {
            sharedSongPresenter.prepareFailureView("No one shares your song :(");
        }
        else {
            final CheckSharedSongOutputData sharedSongOutputData = new CheckSharedSongOutputData(usernameToShared);
            sharedSongPresenter.prepareSuccessView(sharedSongOutputData);
        }
    }
}
