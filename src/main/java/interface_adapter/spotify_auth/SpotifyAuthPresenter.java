package interface_adapter.spotify_auth;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.spotify_auth.SpotifyAuthOutputBoundary;
import use_case.spotify_auth.SpotifyAuthOutputData;
import view.LoggedInView;

public class SpotifyAuthPresenter implements SpotifyAuthOutputBoundary {
    private final SpotifyAuthViewModel spotifyAuthViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;
    private LoggedInView loggedInView;

    public SpotifyAuthPresenter(ViewManagerModel viewManagerModel,
                                SpotifyAuthViewModel spotifyAuthViewModel,
                                LoggedInViewModel loggedInViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.spotifyAuthViewModel = spotifyAuthViewModel;
        this.loggedInViewModel = loggedInViewModel;
    }

    public void setLoggedInView(LoggedInView view) {
        this.loggedInView = view;
    }

    @Override
    public void prepareSuccessView(SpotifyAuthOutputData response) {
        // Update logged in state with Spotify authentication info
        LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setSpotifyAuthenticated(true);
        loggedInState.setSpotifyUserId(response.getSpotifyUserId());
        loggedInViewModel.setState(loggedInState);
        loggedInViewModel.firePropertyChange();

        // Pass the SpotifyUser to the LoggedInView
        if (loggedInView != null && response.getSpotifyUser() != null) {
            loggedInView.setCurrentSpotifyUser(response.getSpotifyUser());
        }

        // Switch to logged in view
        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        SpotifyAuthState currentState = spotifyAuthViewModel.getState();
        currentState.setAuthError(error);
        spotifyAuthViewModel.setState(currentState);
        spotifyAuthViewModel.firePropertyChange();
    }
}