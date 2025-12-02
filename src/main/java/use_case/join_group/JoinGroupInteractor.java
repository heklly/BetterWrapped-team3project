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

        // 1. Lookup group by code
        Group group = groupDAO.getGroupByCode(data.getGroupCode());
        if (group == null) {
            outputBoundary.prepareFailView("Group code does not exist.");
            return;
        }

        SpotifyUser user = data.getUser();

        // 2. Check if user is already in the group
        List<SpotifyUser> users = group.getUsers();
        boolean alreadyMember = users.stream()
                .anyMatch(u -> u.getUsername().equals(user.getUsername()));

        if (alreadyMember) {
            outputBoundary.prepareFailView("You are already a member of this group.");
            return;
        }

        // 3. Try adding the user
        try {
            group.addUser(user);
        } catch (IllegalStateException e) {
            outputBoundary.prepareFailView(e.getMessage()); // e.g., group full
            return;
        }

        // 4. Persist updated group
        groupDAO.updateGroup(group);

        // 5. Return success
        JoinGroupOutputData outputData = new JoinGroupOutputData(
                group.getGroup_name(),
                true,
                "Successfully joined group!"
        );
        outputBoundary.prepareSuccessView(outputData);
    }
}