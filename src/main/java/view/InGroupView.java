package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_group.InGroupViewModel;
import interface_adapter.leave_group.LeaveGroupController;
//import interface_adapter.sharedsong.SharedSongController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

public class InGroupView extends JPanel implements ActionListener {

    String viewName = "in group";
    private final ViewManagerModel viewManagerModel;
    private final InGroupViewModel inGroupViewModel;

    private final JButton leaveGroup;
//    private final JButton sharedSong;

//    private SharedSongController sharedSongController = null;
    private LeaveGroupController leaveGroupController = null;


    public InGroupView(ViewManagerModel viewManagerModel, InGroupViewModel inGroupViewModel) {
         this.viewManagerModel = viewManagerModel;
         this.inGroupViewModel = inGroupViewModel;

         final JLabel title = new JLabel(String.format("Group Name %s", inGroupViewModel.getState().getGroupName()));
         title.setAlignmentX(JComponent.CENTER_ALIGNMENT);

         final JPanel buttons = new JPanel();

         leaveGroup = new JButton("Leave Group");
         buttons.add(leaveGroup);

//         sharedSong = new JButton("Shared Song");
//         buttons.add(sharedSong);

        leaveGroup.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(leaveGroup)) {
                            leaveGroupController.execute();
                        }
                    }
                }
        );

//        sharedSong.addActionListener(
//                new ActionListener() {
//                    public void actionPerformed(ActionEvent evt) {
//                        if (evt.getSource().equals(sharedSong)) {
//                            sharedSongController.execute();
//                        }
//                    }
//                }
//        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        for (String username : inGroupViewModel.getState().getGroupUsernames()) {
            JLabel usernameLabel = new JLabel(username);
            usernameLabel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
            this.add(usernameLabel);
        }
        this.add(buttons);
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    public void setLeaveGroupController(LeaveGroupController leaveGroupController) {
        this.leaveGroupController = leaveGroupController;
    }
//    public void setSharedSongController(SharedSongController sharedSongController) {
//        this.sharedSongController = sharedSongController;
//    }
    public String getViewName() {
    return viewName;
}

}
