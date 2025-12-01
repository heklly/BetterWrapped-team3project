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
    public void joinGroup(JoinGroupInputData data, List<SpotifyUser> users) {

        Group group = groupDAO.getGroupByCode(data.getGroupCode(), data.getUsers(users));
        if (group == null) {
            outputBoundary.prepareFailView("Group code does not exist.");
            return;
        }

        SpotifyUser user = data.getUser();

        if (group.getUsers().contains(user)) {
            outputBoundary.prepareFailView("You are already a member of this group.");
            return;
        }

        try {
            group.addUser(user);
        } catch (Exception e) {
            outputBoundary.prepareFailView(e.getMessage());
            return;
        }

        groupDAO.updateGroup(group);

        JoinGroupOutputData outputData = new JoinGroupOutputData(
                group.getGroup_name(),
                true,
                "Successfully joined group!"
        );

        outputBoundary.prepareSuccessView(outputData);
    }}
