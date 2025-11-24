package use_case.leave_group;

public interface LeaveGroupInputBoundary {
    LeaveGroupOutputData execute(LeaveGroupInputData inputData);

    /**
     * executes leave group use case
     * @param inputData the input data for leaving a group
     */
}
