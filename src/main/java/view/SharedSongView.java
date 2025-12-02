package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_group.InGroupViewModel;
import interface_adapter.sharedsong.SharedSongViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

public class SharedSongView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "shared song";
    private InGroupViewModel inGroupViewModel;
    private SharedSongViewModel sharedSongViewModel;
    private ViewManagerModel viewManagerModel;

    private final JButton backButton;
    private final JPanel dataPanel;


    public SharedSongView(InGroupViewModel inGroupViewModel,
                          ViewManagerModel viewManagerModel,
                          SharedSongViewModel sharedSongViewModel) {
        this.inGroupViewModel = inGroupViewModel;
        this.viewManagerModel = viewManagerModel;
        this.sharedSongViewModel = sharedSongViewModel;
        this.addPropertyChangeListener(this);

        JLabel title = new JLabel("Who shares your currently playing song?");
        title.setFont(new Font("Century Schoolbook", Font.PLAIN, 25));
        title.setAlignmentX(RIGHT_ALIGNMENT);

        dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));

        // defaulting to do only if state is not nothing
        if ( sharedSongViewModel != null &&  sharedSongViewModel.getState() != null) {
            setDataPanel(sharedSongViewModel.getState().getUsernameToShared());
        }

        backButton = new JButton("Back to Group");
        backButton.addActionListener(this);

        this.setLayout(new BorderLayout());
        this.add(title, BorderLayout.NORTH);
//        this.add(dataPanel, BorderLayout.CENTER);
        this.add(dataPanel, BorderLayout.CENTER);
        this.add(backButton, BorderLayout.SOUTH);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            viewManagerModel.firePropertyChange(inGroupViewModel.getViewName());
            sharedSongViewModel.firePropertyChange("back");
        }
    }
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("shared song")) {
            setDataPanel(sharedSongViewModel.getState().getUsernameToShared());

        } else if (evt.getPropertyName().equals("error")) {
            inGroupViewModel.firePropertyChange("shared song error");
        }
    }

    public void setDataPanel(Map<String, String> UsernameToShared) {
        dataPanel.removeAll();
        dataPanel.add(Box.createVerticalStrut(7));
        if (UsernameToShared != null) {
            dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
            for (String username : UsernameToShared.keySet()) {
                JLabel label = new JLabel(String.format("%s: %s", username, UsernameToShared.get(username)));
                label.setFont(new Font("Century Schoolbook", Font.PLAIN, 30));
                label.setAlignmentX(CENTER_ALIGNMENT);
                dataPanel.add(label);
                dataPanel.add(Box.createVerticalStrut(45));
            }
        }
    }

    public String getViewName() { return this.viewName; }
}
