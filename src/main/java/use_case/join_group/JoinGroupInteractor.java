package use_case.join_group;

import entity.Group;
import entity.SpotifyUser;
import use_case.GroupDataAccessInterface;

import java.util.List;

public class JoinGroupInteractor implements JoinGroupInputBoundary {

    private final GroupDataAccessInterface groupDAO;
    private final JoinGroupOutputBoundary outputBoundary;

    public JoinGroupInteractor(GroupDataAccessInterface groupDAO,
                               JoinGroupOutputBoundary outputBoundary) {
        this.groupDAO = groupDAO;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void joinGroup(JoinGroupInputData data) {

        // 1. lookup group
        Group group = groupDAO.getGroupByCode(data.getGroupCode());
        if (group == null) {
            outputBoundary.prepareFailView("Group code does not exist.");
            return;
        }

        SpotifyUser user = data.getUser();

        // 2. check if already in group
        if (group.getUsers().contains(user)) {
            outputBoundary.prepareFailView("You are already a member of this group.");
            return;
        }

        // 3. try adding the user
        try {
            group.addUser(user);
        } catch (Exception e) {
            outputBoundary.prepareFailView(e.getMessage());
            return;
        }

        // 4. persist update
        groupDAO.updateGroup(group);

        // 5. return success
        JoinGroupOutputData outputData = new JoinGroupOutputData(
                group.getGroup_name(),
                true,
                "Successfully joined group!"
        );

        outputBoundary.prepareSuccessView(outputData);
    }
}