package app;

import interface_adapter.ViewManagerModel;
import view.CreateGroupView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;

public class GroupTabBuilder {

    private final JPanel cardPanel =  new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final ViewManagerModel groupViewManagerModel =  new ViewManagerModel();
    ViewManager groupViewManager = new ViewManager(cardPanel, cardLayout, groupViewManagerModel);


//    private NoGroupView noGroupView;
//    private NoGroupViewModel noGroupViewModel;
    private CreateGroupView createGroupView;
    private interface_adapter.create_group.CreateGroupViewModel createGroupViewModel;
//    private InGroupView inGroupView;
//    private InGroupViewModel inGroupViewModel;

    public GroupTabBuilder() {cardPanel.setLayout(cardLayout); }

    // add  view methods
    public GroupTabBuilder addNoGroupView() {
        // ...
        return this;
    }

    public GroupTabBuilder addCreateGroupView() {
        // ...
        cardPanel.add(createGroupView, "create group");
        return this;
    }

    public GroupTabBuilder addInGroupView() {
        // ...
        return this;
    }

    // add use cases
//    public GroupTabBuilder addJoinGroupUseCase() {
//        // ...
//        return this;
//    }

    public GroupTabBuilder addCreateGroupUseCase() {
        // ...
        return this;
    }

    public GroupTabBuilder addLeaveGroupUseCase() {
        // ...
        return this;
    }

    public GroupTabBuilder addSharedSongUseCase() {
        // ...
        return this;
    }

    public JPanel build() { return cardPanel; }

}
