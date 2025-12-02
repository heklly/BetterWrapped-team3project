package view;

import entity.Group;
import interface_adapter.ViewManagerModel;
import interface_adapter.create_group.InGroupViewModel;
import interface_adapter.create_group.UserGroupState;
import interface_adapter.group_analytics.GroupAnalyticsController;
import interface_adapter.group_analytics.GroupAnalyticsViewModel;
import interface_adapter.leave_group.LeaveGroupController;
import interface_adapter.sharedsong.SharedSongController;
import interface_adapter.sharedsong.SharedSongState;
import interface_adapter.sharedsong.SharedSongViewModel;
import se.michaelthelin.spotify.model_objects.specification.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class InGroupView extends JPanel implements ActionListener, PropertyChangeListener {

    String viewName = "in group";
    private final ViewManagerModel viewManagerModel;
    private final InGroupViewModel inGroupViewModel;
    private final SharedSongViewModel sharedSongViewModel;
    private final GroupAnalyticsViewModel groupAnalyticsViewModel;

    private final JButton loggedIn;
    private final JLabel groupName;
    private final JPanel groupPanel;
    private final JButton leaveGroup;
    private final JButton sharedSong;
    private final JButton groupAnalytics;

    private SharedSongController sharedSongController = null;
    private GroupAnalyticsController groupAnalyticsController = null;
    private LeaveGroupController leaveGroupController = null;


    public InGroupView(ViewManagerModel viewManagerModel,
                       InGroupViewModel inGroupViewModel,
                       SharedSongViewModel sharedSongViewModel,
                       GroupAnalyticsViewModel groupAnalyticsViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.inGroupViewModel = inGroupViewModel;
        this.sharedSongViewModel = sharedSongViewModel;
        this.groupAnalyticsViewModel = groupAnalyticsViewModel;

        final UserGroupState currentState = inGroupViewModel.getState();

        groupName = new JLabel();
        setGroupName(currentState.getGroupName());
        groupName.setFont(new Font("Century Schoolbook", Font.PLAIN, 18));
        groupName.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        groupPanel = new JPanel();
        setGroupPanel(currentState);
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(groupName);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(groupPanel);

        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        loggedIn = new JButton("Main Page");
        buttons.add(loggedIn);
        sharedSong = new JButton("Shared Song");
        buttons.add(sharedSong);
        groupAnalytics = new JButton("Group Analytics");
        buttons.add(groupAnalytics);
        leaveGroup = new JButton("Leave Group");
        buttons.add(leaveGroup);

        loggedIn.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(loggedIn)) {
                            viewManagerModel.setState("logged in");
                            viewManagerModel.firePropertyChange();
                        }
                    }
                }
        );

        leaveGroup.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(leaveGroup)) {
                            final UserGroupState currentState = inGroupViewModel.getState();
                            leaveGroupController.execute(currentState.getSpotifyUser(),
                                    new Group("compiles", currentState.getSpotifyUser()));
                        }
                    }
                }
        );

        sharedSong.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(sharedSong)) {
                            if (sharedSongViewModel.getState() != null) {
                                final SharedSongState currentState = sharedSongViewModel.getState();
                                sharedSongController.execute(
                                        currentState.getSpotifyUser(),
                                        currentState.getGroupUsers()
                                );
                            } else {
                                inGroupViewModel.firePropertyChange("shared song error");
                            }

                        }
                    }
                }
        );

        groupAnalytics.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(groupAnalytics)) {
//                            final GroupAnalyticsState currentState = groupAnalyticsViewModel.getState();
//                            groupAnalyticsController.analyzeGroup()

                            // TODO: implement this button
                            //  Issue is that there is no List<UserTasteProfiles> to call controller with
                            //  For now, only calling view manager to show group analytics view
                            viewManagerModel.setState(groupAnalyticsViewModel.getViewName());
                        }
                    }
                }
        );

        this.setLayout(new BorderLayout());
        this.add(buttons, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("shared song error")) {
            JOptionPane.showMessageDialog(this,
                    sharedSongViewModel.getState().getErrorMessage(),
                    "Shared Song Error",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            final UserGroupState state = (UserGroupState) evt.getNewValue();
            setGroupName(state.getGroupName());
            setGroupPanel(state);
        }
    }

    public void setLeaveGroupController(LeaveGroupController leaveGroupController) {
        this.leaveGroupController = leaveGroupController;
    }
    public void setSharedSongController(SharedSongController sharedSongController) {
        this.sharedSongController = sharedSongController;
    }

    public void setGroupAnalyticsController(GroupAnalyticsController groupAnalyticsController) {
        this.groupAnalyticsController = groupAnalyticsController;
    }

    public void setGroupName(String groupName) {
        this.groupName.setText("Group Name: " + groupName);
    }

    public void setGroupPanel(UserGroupState state) {
        // default logic to do nothing if group state is nothing
        if (state.getGroupUsernames() == null  || state.getGroupUsernames().isEmpty()) { return; }

        groupPanel.removeAll();
        if (state.getGroupUsernames() != null) {
            groupPanel.setLayout(new BoxLayout(groupPanel, BoxLayout.Y_AXIS));
            for (String username : state.getGroupUsernames()) {
                JLabel usernameLabel = new JLabel(username);
                usernameLabel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
                usernameLabel.setFont(new Font("Century Schoolbook", Font.PLAIN, 18));
                groupPanel.add(usernameLabel);
            }
        }
    }
    public String getViewName() { return viewName; }
}
