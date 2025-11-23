package view;

import interface_adapter.sharedsong.SharedSongController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SharedSongView implements ActionListener {

    private final String viewName = "check shared song";
//    private final SharedSongViewModel sharedSongViewModel;

    private final JButton checksharedsong;
    private SharedSongController sharedSongController;

    public SharedSongView() {
        final JLabel title = new JLabel ("Who shares your currently playing song?");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        checksharedsong = new JButton("Check Who Has Saved Your Currently Playing Song");
        checksharedsong.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(checksharedsong)) {
                            final userGroupState currentState = inGroupViewModel.getState();

                            sharedSongController.execute(
                                    currentState.getUser(),
                                    currentState.getGroup()
                            );
                        }

                    }
                }
        );
    }
    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {

        System.out.println("Click " + evt.getActionCommand());
    }

    public String getViewName() { return viewName; }

    public void setSharedSongController(SharedSongController sharedSongController) {
        this.sharedSongController = sharedSongController;
    }

}
