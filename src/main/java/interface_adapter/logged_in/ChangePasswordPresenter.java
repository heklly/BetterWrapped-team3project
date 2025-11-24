package interface_adapter.logged_in;

import interface_adapter.ViewManagerModel;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.change_password.ChangePasswordOutputData;

/**
 * The Presenter for the Change Password Use Case.
 */
public class ChangePasswordPresenter implements ChangePasswordOutputBoundary {

    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;

    public ChangePasswordPresenter(ViewManagerModel viewManagerModel,
                             LoggedInViewModel loggedInViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
    }

    @Override
    public void prepareSuccessView(ChangePasswordOutputData outputData) {
        final LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setPassword("");  // ← ADD THIS LINE
        loggedInState.setPasswordError(null);

        loggedInViewModel.setState(loggedInState);
        loggedInViewModel.firePropertyChange("password");
    }
    @Override
    public void prepareFailView(String error) {
        final LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setPasswordError(error);
        loggedInViewModel.firePropertyChange("password");
    }
}
