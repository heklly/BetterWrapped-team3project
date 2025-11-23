package app;

import interface_adapter.NoGroupViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.create_group.CreateGroupController;
import interface_adapter.create_group.CreateGroupPresenter;
import interface_adapter.in_group.InGroupViewModel;
import interface_adapter.create_group.CreateGroupViewModel;
import use_case.create_group.CreateGroupInputBoundary;
import use_case.create_group.CreateGroupInteractor;
import use_case.create_group.CreateGroupOutputBoundary;
import view.CreateGroupView;
import view.InGroupView;
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
    private CreateGroupViewModel createGroupViewModel;
    private InGroupView inGroupView;
    private InGroupViewModel inGroupViewModel;

    public GroupTabBuilder() {cardPanel.setLayout(cardLayout); }

    // add  view methods
//    public GroupTabBuilder addNoGroupView() {
//        // ...
//        return this;
//    }

    public GroupTabBuilder addCreateGroupView() {
        createGroupViewModel = new CreateGroupViewModel();
        createGroupView = new CreateGroupView(createGroupViewModel);
        cardPanel.add(createGroupView, createGroupView.getName());
        return this;
    }

    public GroupTabBuilder addInGroupView() {
        inGroupViewModel = new InGroupViewModel();
        inGroupView = new InGroupView(inGroupViewModel);
        cardPanel.add(inGroupView, inGroupViewModel.getViewName());
        return this;
    }

    // add use cases
//    public GroupTabBuilder addJoinGroupUseCase() {
//        // ...
//        return this;
//    }

    public GroupTabBuilder addCreateGroupUseCase() {
//        TODO: fix to implement this
//        final CreateGroupOutputBoundary createGroupOutputBoundary = new CreateGroupPresenter();
//        final CreateGroupInputBoundary createGroupInteractor = new CreateGroupInteractor();
//        CreateGroupController controller = new CreateGroupController(createGroupInteractor);
//        createGroupView.setCreateGroupController(controller);
        return this;
    }

//    TODO: make Leave group use case
    public GroupTabBuilder addLeaveGroupUseCase() {
        // ...
        return this;
    }

    public GroupTabBuilder addSharedSongUseCase() {
//        TODO: fix to implement
//    final SharedSongOutputBoundary sharedSongOutputBoundary = new SharedSongPresenter(groupViewManagerModel,
//                inGroupViewModel);
//        final SharedSongInputBoundary sharedSongInteractor =
//                new SharedSongInteractor(..., sharedSongOutputBoundary);
//        SharedSongController controller = new SharedSongController(sharedSongInteractor);
//        inGroupView.setSharedSongController(controller);
        return this;
    }

    public JPanel build() {
//        If user in group set view as in group
//        If not in group set view as no group
        groupViewManagerModel.setState(NoGroupViewModel.getState());
        groupViewManagerModel.firePropertyChange();
        return cardPanel; }

}
