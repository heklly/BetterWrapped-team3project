package interface_adapter.leave_group;

import entity.SpotifyUser;
import use_case.leave_group.LeaveGroupInputBoundary;
import use_case.leave_group.LeaveGroupInputData;

/**
 * The controller for the Leave Group Use Case.
 */

public class LeaveGroupController {

    private LeaveGroupInputBoundary leaveGroupUseCaseInteractor;

    public LeaveGroupController(LeaveGroupInputBoundary leaveGroupUseCaseInteractor) {
        this.leaveGroupUseCaseInteractor = this.leaveGroupUseCaseInteractor;
    }

    /**
     * Executes the Leave Group Use Case.
     */
    public void execute() {
        // COMMENTED OUT CURRENTLY SO CODE COMPILES:
        // leaveGroupUseCaseInteractor.execute();
    }
}