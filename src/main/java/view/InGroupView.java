package view;

import interface_adapter.in_group.InGroupState;
import interface_adapter.in_group.InGroupViewModel;
import interface_adapter.sharedsong.SharedSongController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for when the user is in a group
 */
public class InGroupView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String groupViewName = "in group";
    private final InGroupViewModel inGroupViewModel;
    private final SharedSongController sharedSongController;
    private final LeaveGroupController leaveGroupController;

    final JLabel groupName;

//    final JPanel groupUsers;

    private final JButton sharedSong;

    private final JButton leaveButton;

    public InGroupView(InGroupViewModel inGroupViewModel) {
        this.inGroupViewModel = inGroupViewModel;
        this.inGroupViewModel.addPropertyChangeListener(this);

        groupName = new JLabel(String.format("Group name: %s",
                inGroupViewModel.getState().getGroupName()));

        final JPanel buttons = new JPanel();
        sharedSong = new JButton("shared song");
        buttons.add(sharedSong);

        leaveButton = new JButton("leave song");
        buttons.add(leaveButton);

        leaveButton.addActionListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        sharedSong.addActionListener(
                evt -> {
                    if (evt.getSource().equals(sharedSong)) {
                        final InGroupState currentState = inGroupViewModel.getState();

                        this.sharedSongController.execute(...);
                    }
                }
        );

        this.add(groupName);
        for (String u : inGroupViewModel.getState().getGroupUsers()) {
            this.add(new JLabel(u));
        }
        this.add(buttons);
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        // TODO: execute the leave group use case through the Controller
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //TODO implemennt group state change
    }

    public String getGroupViewName() {
        return groupViewName;
    }

    public void setSharedSongController(SharedSongController sharedSongController) {
        this.sharedSongController = sharedSongController;
    }

    public void setLeaveGroupController(LeaveGroupController leaveGroupController) {
        this.leaveGroupController = leaveGroupController;
    }
}
