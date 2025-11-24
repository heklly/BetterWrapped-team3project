package use_case.leave_group;

public class LeaveGroupInteractor implements LeaveGroupInputBoundary {

    private final LeaveGroupDataAccessInterface leaveGroupDAO;
    private final LeaveGroupOutputBoundary groupPresenter;
    public LeaveGroupInteractor(LeaveGroupDataAccessInterface leaveGroupDAO, LeaveGroupOutputBoundary presenter) {
        this.leaveGroupDAO = leaveGroupDAO;
        this.groupPresenter = presenter;
    }

    @Override
    public void execute(LeaveGroupInputData inputData) {
        final String userid = inputData.getUserid();
        final String groupName = inputData.getGroup();

        //TODO: go into storage for group to users and remove use from group
        final leaveGroupDAO.remove(userid);
        final LeaveGroupOutputData outputData = new LeaveGroupOutputData();
        groupPresenter.prepareView(outputData);
    }
}
