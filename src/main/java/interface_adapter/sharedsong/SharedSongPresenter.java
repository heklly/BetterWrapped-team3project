package interface_adapter.sharedsong;

import interface_adapter.ViewManagerModel;
import use_case.sharedsong.SharedSongOutputBoundary;
import use_case.sharedsong.SharedSongOutputData;
import view.SharedSongView;

/**
 * Presenter for the Shared Song Use Case.
 */
public class SharedSongPresenter implements SharedSongOutputBoundary {

    private final SharedSongViewModel sharedSongViewModel;
    private final ViewManagerModel viewManagerModel;


    public SharedSongPresenter(SharedSongViewModel sharedSongViewModel,
                               ViewManagerModel viewManagerModel) {
        this.sharedSongViewModel = sharedSongViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    public void prepareSuccessView(SharedSongOutputData response) {
        // on success, display song and who has saved it
        final SharedSongState sharedSongState = sharedSongViewModel.getState();
        sharedSongState.setUsernameToShared(response.getSharedSongOutputData());
        sharedSongState.setErrorMessage("");
        sharedSongViewModel.firePropertyChange("shared song");
        viewManagerModel.firePropertyChange(sharedSongViewModel.getViewName());
    }
    public void prepareFailureView(String errorMessage) {
        // on failure, display error message
        final SharedSongState sharedSongState = new SharedSongState();
        sharedSongState.setErrorMessage(errorMessage);
        sharedSongViewModel.setState(sharedSongState);
        sharedSongViewModel.firePropertyChange("error");
    }
}
