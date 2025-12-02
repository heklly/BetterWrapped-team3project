package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_group.InGroupViewModel;
import interface_adapter.sharedsong.SharedSongViewModel;

import javax.swing.*;
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

        JLabel title = new JLabel("Who shares your currently playing song?");

        dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));

        // defaulting to do only if state is not nothing
        if ( sharedSongViewModel != null &&  sharedSongViewModel.getState() != null) {
            setDataPanel(sharedSongViewModel.getState().getUsernameToShared());
        }

        backButton = new JButton("Back to Group");
        backButton.addActionListener(this);
        this.add(backButton);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title, dataPanel);
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
        if (UsernameToShared != null) {
            dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
            for (String username : UsernameToShared.keySet()) {
                dataPanel.add(new JLabel(String.format("%s: %s", username, UsernameToShared.get(username))));
            }
        }
    }

    public String getViewName() { return this.viewName; }
}
