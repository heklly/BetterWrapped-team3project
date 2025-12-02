package interface_adapter.leave_group;

import entity.Group;
import entity.SpotifyUser;
import use_case.leave_group.LeaveGroupInputBoundary;
import use_case.leave_group.LeaveGroupInputData;

/**
 * The controller for the Leave Group Use Case.
 */
public class LeaveGroupController {

    private LeaveGroupInputBoundary leaveGroupUseCaseInteractor;

    public LeaveGroupController(LeaveGroupInputBoundary leaveGroupUseCaseInteractor) {
        this.leaveGroupUseCaseInteractor = leaveGroupUseCaseInteractor;  // Also fixed this bug!
    }

    /**
     * Executes the Leave Group Use Case.
     * @param user the user leaving the group
     * @param group the group to leave
     */
    public void execute(SpotifyUser user, Group group) {
        LeaveGroupInputData inputData = new LeaveGroupInputData(user, group);
        leaveGroupUseCaseInteractor.execute(inputData);
    }
}