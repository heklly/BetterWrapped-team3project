package use_case.create_group;

import entity.Group;
import entity.SpotifyUser;
import interface_adapter.create_group.CreateGroupPresenter;

import java.util.List;

/**
 * Interactor for the Create Group use case.
 * Implements the input boundary.
 */
public class CreateGroupInteractor implements CreateGroupInputBoundary {

    private final CreateGroupOutputBoundary createGroupPresenter;
//    private GroupDataAccessInterface groupDAO;

    public CreateGroupInteractor(CreateGroupOutputBoundary createGroupPresenter
//                                 ,GroupDataAccessInterface groupDAO
    ) {
        this.createGroupPresenter = createGroupPresenter;
    }

    public CreateGroupOutputData execute(CreateGroupInputData inputData) {

        Group group = new Group(inputData.getGroup_name(), inputData.getOwner());

        List<SpotifyUser> initialMembers = inputData.getInitialMembers();
        if (initialMembers != null) {
            for (SpotifyUser user : initialMembers) {
                group.addUser(user);
            }
        } else {
            return null;
        }

        // Create output data
        CreateGroupOutputData outputData = new CreateGroupOutputData(
                group.getGroup_name(),
                group.getOwner(),
                group.getUsers()
        );

        //call presenter
        createGroupPresenter.present(outputData);

        return outputData;
    }
}