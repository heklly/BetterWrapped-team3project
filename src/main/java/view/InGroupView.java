package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_group.InGroupViewModel;
import interface_adapter.leave_group.LeaveGroupController;
import interface_adapter.sharedsong.SharedSongViewModel;
//import interface_adapter.group_analytics.GroupAnalyticsViewModel;
//import interface_adapter.sharedsong.SharedSongController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InGroupView extends JPanel implements ActionListener {

    String viewName = "in group";
    private final ViewManagerModel viewManagerModel;
    private final InGroupViewModel inGroupViewModel;
    private final SharedSongViewModel sharedSongViewModel;
//    private final GroupAnalyticsViewModel groupAnalyticsViewModel;

    private final JButton leaveGroup;
//    private final JButton sharedSong;
//    private final JButton groupAnalytics;

//    private SharedSongController sharedSongController = null;
//    private GroupAnalyticsController groupAnalyticsController = null;
    private LeaveGroupController leaveGroupController = null;


    public InGroupView(ViewManagerModel viewManagerModel, InGroupViewModel inGroupViewModel, SharedSongViewModel sharedSongViewModel) {
         this.viewManagerModel = viewManagerModel;
         this.inGroupViewModel = inGroupViewModel;
         this.sharedSongViewModel = sharedSongViewModel;
//         this.groupAnalyticsViewModel = groupAnalyticsViewModel


         final JLabel title = new JLabel(String.format("Group Name %s", inGroupViewModel.getState().getGroupName()));
         title.setAlignmentX(JComponent.CENTER_ALIGNMENT);

         final JPanel buttons = new JPanel();

//        sharedSong = new JButton("Shared Song");
//         buttons.add(sharedSong);

//        groupAnalytics = new JButton("Group Analytics");
//        buttons.add(groupAnalytics);

         leaveGroup = new JButton("Leave Group");
         buttons.add(leaveGroup);

//

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

//        groupAnalytics.addActionListener(
//                new ActionListener() {
//                    public void actionPerformed(ActionEvent evt) {
//                        if (evt.getSource().equals(groupAnalytics) {
//                            TODO
//                        }
//                    }
//                }
//        );
//TODO change to gridbag or flow layout
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

//    public void setGroupAnalyticsController(GroupAnalyticsController groupAnalyticsController) {
//        this.groupAnalyticsController() = groupAnalyticsController;
//    }

    public String getViewName() {
    return viewName;
}

}
