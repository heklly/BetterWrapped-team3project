package app;

import javax.swing.*;

public class ProfileTabBuilder {

    private final JPanel profileTab;

    public ProfileTabBuilder() {
        //for now
        this.profileTab = new JPanel();
        JLabel profilePlaceholder = new JLabel("Profile");
        profileTab.add(profilePlaceholder);
    }

    //DAOs

    //add views

    //add usecases

    public JPanel build() {
        //for now
        return profileTab;
    }
}
