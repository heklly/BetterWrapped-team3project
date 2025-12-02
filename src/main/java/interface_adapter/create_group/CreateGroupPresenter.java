package interface_adapter.create_group;

import interface_adapter.ViewManagerModel;
import use_case.create_group.CreateGroupOutputBoundary;
import use_case.create_group.CreateGroupOutputData;

/**
 * Presenter for the Create Group use case.
 * Converts output data into a form suitable for the UI and updates the ViewModel.
 */
public class CreateGroupPresenter implements CreateGroupOutputBoundary {

    private final InGroupViewModel inGroupViewModel;
    private final NoGroupViewModel noGroupViewModel;
    private final ViewManagerModel viewManagerModel;

    /**
     * Constructs a CreateGroupPresenter.
     *
     * @param noGroupViewModel the NoGroupViewModel associated with the Create Group screen.
     * @param inGroupViewModel the InGroupViewModel to be updated
     * @param viewManagerModel changes create/join group view to in group view
     */
    public CreateGroupPresenter(InGroupViewModel inGroupViewModel,
                                NoGroupViewModel noGroupViewModel,
                                ViewManagerModel viewManagerModel) {
        this.inGroupViewModel = inGroupViewModel;
        this.noGroupViewModel = noGroupViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Updates the ViewModel based on output data from the interactor.
     *
     * @param outputData the output data containing the created group info
     */
    @Override
    public void present(CreateGroupOutputData outputData) {
        final UserGroupState currentState = inGroupViewModel.getState();
        if (outputData != null) {
            currentState.setGroupName(outputData.getGroup_name());
            currentState.setInGroup(true);
//            currentState.setGroupUsernames(outputData.getUsers());
//            currentState.setGroup(outputData.getGroup());
//            currentState.setSpotifyUser(outputData.);
            inGroupViewModel.firePropertyChange();
            noGroupViewModel.setState(new UserGroupState());

            viewManagerModel.setState(inGroupViewModel.getViewName());
            viewManagerModel.firePropertyChange();
        } else {
            currentState.setNameError("Failed to create group");
            noGroupViewModel.firePropertyChange("createError");
        }
    }
}
