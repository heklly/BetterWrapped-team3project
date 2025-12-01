package interface_adapter.leave_group;

import use_case.leave_group.LeaveGroupInputBoundary;

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
        leaveGroupUseCaseInteractor.execute();
    }
}