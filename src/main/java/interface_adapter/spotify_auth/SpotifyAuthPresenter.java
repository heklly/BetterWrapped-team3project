package interface_adapter.spotify_auth;

import interface_adapter.ViewManagerModel;
import interface_adapter.daily_mix.DailyMixState;
import interface_adapter.daily_mix.DailyMixViewModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.spotify_auth.SpotifyAuthOutputBoundary;
import use_case.spotify_auth.SpotifyAuthOutputData;
import view.LoggedInView;
import view.ProfileView;

public class SpotifyAuthPresenter implements SpotifyAuthOutputBoundary {
    private final SpotifyAuthViewModel spotifyAuthViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;
    private final DailyMixViewModel dailyMixViewModel;
    private LoggedInView loggedInView;

    public SpotifyAuthPresenter(ViewManagerModel viewManagerModel,
                                SpotifyAuthViewModel spotifyAuthViewModel,
                                LoggedInViewModel loggedInViewModel,
                                DailyMixViewModel dailyMixViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.spotifyAuthViewModel = spotifyAuthViewModel;
        this.loggedInViewModel = loggedInViewModel;
        this.dailyMixViewModel = dailyMixViewModel;
    }

    public void setLoggedInView(LoggedInView view) {
        this.loggedInView = view;
    }

    @Override
    public void prepareSuccessView(SpotifyAuthOutputData response) {
        // Get current state (preserves existing username!)
        LoggedInState loggedInState = loggedInViewModel.getState();

        // If username is somehow empty, set it from response
        if (loggedInState.getUsername() == null || loggedInState.getUsername().isEmpty()) {
            loggedInState.setUsername(response.getUsername());
        }

        // Add Spotify authentication info
        loggedInState.setSpotifyAuthenticated(true);
        loggedInState.setSpotifyUserId(response.getSpotifyUserId());

        // Update the view model
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