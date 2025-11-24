package use_case.create_group;

import entity.Group;
import entity.User;

import java.util.List;

/**
 * Interactor for the Create Group use case.
 * Implements the input boundary.
 */
public abstract class CreateGroupInteractor implements use_case.create_group.CreateGroupInputBoundary {

    @Override
    public use_case.create_group.CreateGroupOutputData execute(use_case.create_group.CreateGroupInputData inputData) {

        Group group = new Group(inputData.getGroup_name(), inputData.getOwner());

        List<User> initialMembers = inputData.getInitialMembers();
        if (initialMembers != null) {
            for (User user : initialMembers) {
                group.addUser(user);
            }
        }

        // Return output data
        return new use_case.create_group.CreateGroupOutputData(
                group.getGroup_name(),
                group.getOwner(),
                group.getUsers()
        );
    }

    public use_case.create_group.CreateGroupOutputData execute() {
        return null;
    }
}