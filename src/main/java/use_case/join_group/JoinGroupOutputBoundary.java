package use_case.join_group;


import use_case.join_group.JoinGroupOutputData;

public interface JoinGroupOutputBoundary {
    void prepareSuccessView(JoinGroupOutputData data);
    void prepareFailView(String error);
}


