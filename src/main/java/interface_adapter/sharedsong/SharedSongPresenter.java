package interface_adapter.sharedsong;


import use_case.sharedsong.SharedSongOutputBoundary;
import use_case.sharedsong.SharedSongOutputData;

/**
 * Presenter for the Shared Song Use Case.
 */
public class SharedSongPresenter implements SharedSongOutputBoundary {

    private final SharedSongViewModel sharedSongViewModel;

    public SharedSongPresenter(SharedSongViewModel sharedSongViewModel) {
        this.sharedSongViewModel = sharedSongViewModel;
    }

    public void prepareSuccessView(SharedSongOutputData response) {
        // on success, display song and who has saved it
        SharedSongState currentState = sharedSongViewModel.getState();
        currentState.setUserToShared(response.getSharedSongOutputData());

        sharedSongViewModel.setState(currentState);
        sharedSongViewModel.firePropertyChange();

    }
    public void prepareFailureView(String errorMessage) {
        // on failure, display error message
        SharedSongState currentState = sharedSongViewModel.getState();
        currentState.setError(errorMessage);

        sharedSongViewModel.setState(currentState);
        sharedSongViewModel.firePropertyChange();
    }
}
