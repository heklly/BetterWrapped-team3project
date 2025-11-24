package interface_adapter.logged_in;

import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInputData;

/**
 * Controller for the Change Password Use Case.
 */
public class ChangePasswordController {
    private final ChangePasswordInputBoundary changePasswordInteractor;

    public ChangePasswordController(ChangePasswordInputBoundary changePasswordInteractor) {
        this.changePasswordInteractor = changePasswordInteractor;
    }

    /**
     * Executes the Change Password Use Case.
     * @param password the new password
     * @param username the user whose password to change
     */
    public void execute(String username, String password) {
        final ChangePasswordInputData changePasswordInputData = new ChangePasswordInputData(username, password);
        changePasswordInteractor.execute(changePasswordInputData);
    }
}
