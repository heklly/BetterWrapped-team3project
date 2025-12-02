package use_case.create_group;

import entity.Group;
import entity.SpotifyUser;

import java.util.List;

/**
 * Interactor for the Create Group use case.
 * Implements the input boundary.
 */
public class CreateGroupInteractor implements CreateGroupInputBoundary {

    private final CreateGroupOutputBoundary presenter;  // ADD THIS FIELD

    // ADD THIS CONSTRUCTOR
    public CreateGroupInteractor(CreateGroupOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public CreateGroupOutputData execute(CreateGroupInputData inputData) {

        Group group = new Group(inputData.getGroup_name(), inputData.getOwner());

        List<SpotifyUser> initialMembers = inputData.getInitialMembers();
        if (initialMembers != null) {
            for (SpotifyUser user : initialMembers) {
                group.addUser(user);
            }
        }

        // Create output data WITH the group object
        CreateGroupOutputData outputData = new CreateGroupOutputData(
                group.getGroup_name(),
                group.getOwner(),
                group.getUsers(),
                group
        );

        // CALL THE PRESENTER
        presenter.present(outputData);

        // Return output data (some architectures return it, others don't)
        return outputData;
    }
}