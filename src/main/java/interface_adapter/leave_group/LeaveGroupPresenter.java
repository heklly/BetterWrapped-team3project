package interface_adapter.leave_group;

import entity.SpotifyUser;
import interface_adapter.ViewManagerModel;
import use_case.leave_group.LeaveGroupOutputBoundary;
import use_case.leave_group.LeaveGroupOutputData;

/**
 * Presenter for the Leave Group Use Case.
 */
public class LeaveGroupPresenter implements LeaveGroupOutputBoundary {

    private InGroupViewModel inGroupViewModel;
    private ViewManagerModel viewManagerModel;
    private NoGroupViewModel noGroupViewModel;

    public LeaveGroupPresenter(ViewManagerModel viewManagerModel,
                               InGroupViewModel inGroupVIewModel,
                               NoGroupViewModel noGroupViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.inGroupViewModel = inGroupVIewModel;
        this.noGroupViewModel = noGroupViewModel;
    }

    @Override
    public void present(LeaveGroupOutputData outputData);
    // Update UserGroupState - set in group to false
    final UserGroupState userGroupState = inGroupViewModel.getState();

    userGroupState.setInGroup(false);
    inGroupViewModel.setState(userGroupState);
    inGroupViewModel.firePropertyChange();

    // Switch to NoGroupView
    this.viewManagerModel.setState(noGroupViewModel.getViewName());
    this.viewManagerModel.firePropetyChange();
}