package app;

import javax.swing.*;

public class YouTabBuilder {

    private final JPanel youTab;

    public YouTabBuilder() {
        //for now
        this.youTab = new JPanel();
        JLabel profilePlaceholder = new JLabel("Profile");
        youTab.add(profilePlaceholder);
    }

    //DAOs

    //add views

    //add usecases

    public JPanel build() {
        //for now
        return youTab;
    }
}
