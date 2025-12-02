package view;

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
        groupName.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        groupPanel = new JPanel();
        setGroupPanel(currentState);

        loggedIn = new JButton("Main Page");
        sharedSong = new JButton("Shared Song");
        groupAnalytics = new JButton("Group Analytics");
        leaveGroup = new JButton("Leave Group");

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
                            leaveGroupController.execute();
                        }
                    }
                }
        );

        sharedSong.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(sharedSong)) {
                            final SharedSongState currentState = sharedSongViewModel.getState();
                            sharedSongController.execute(
                                    currentState.getSpotifyUser(),
                                    currentState.getGroupUsers()
                            );
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

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_END;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        this.add(groupName, c);

        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 2;
        c.gridy = 2;
        this.add(groupPanel, c);

        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 1;
        c.weighty = 0.5;
        c.weightx = 0.3;
        c.fill = GridBagConstraints.NONE;
        this.add(leaveGroup, c);

        c.weighty = 0.1;
        c.gridy = 2;
        this.add(sharedSong, c);

        c.gridy = 3;
        this.add(groupAnalytics, c);

        c.weightx = 0;
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 0;
        this.add(loggedIn, c);

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
        groupPanel.removeAll();
        groupPanel.setLayout(new BoxLayout(groupPanel, BoxLayout.Y_AXIS));
        for (String username : state.getGroupUsernames()) {
            JLabel usernameLabel = new JLabel(username);
            usernameLabel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
            groupPanel.add(usernameLabel);
        }
    }
    public String getViewName() { return viewName; }
}
