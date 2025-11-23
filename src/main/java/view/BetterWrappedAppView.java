package view;

import app.GroupTabBuilder;
import interface_adapter.BetterWrappedAppViewModel;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * The View for when the user is logged into our Better Wrapped App.
 */
public class BetterWrappedAppView {

//    private final String viewName;
//    private final String groupViewName;
//    private final String youViewName;
//    private final String profileViewName;

    private final BetterWrappedAppViewModel betterWrappedAppViewModel;

    private final JTabbedPane tabPane = new JTabbedPane();
    private final JPanel youTab;
    private final JPanel groupTab;
    private final JPanel profileTab;

    final ViewManagerModel viewManagerModel;
    ViewManager viewManager;
    final ViewManagerModel groupViewManagerModel;
    ViewManagerModel groupViewManager;

    //DAO here

    public BetterWrappedAppView(BetterWrappedAppViewModel betterWrappedAppViewModel,
                                ViewManagerModel viewManagerModel,
                                ViewManager viewManager) {

        this.betterWrappedAppViewModel = betterWrappedAppViewModel;
        this.viewManagerModel = viewManagerModel;
        this.viewManager = viewManager;

        this.youTab = new JPanel();
        JLabel youPlaceholder = new JLabel("You");
        youTab.add(youPlaceholder);
        this.groupTab = new JPanel();
        JLabel groupPlaceholder = new JLabel("Group");
        groupTab.add(groupPlaceholder);
        this.profileTab = new JPanel();
        JLabel profilePlaceholder = new JLabel("Profile");
        profileTab.add(profilePlaceholder);

//        this.groupTab =  new GroupTabBuilder()
//                .addNoGroupView()
//                .addCreateGroupView()
//                .addInGroupView()
//                .addCreateGroupUseCase()
//                .addLeaveGroupUseCase()
//                .addSharedSongUseCase()
//                .build();

        tabPane.addTab("You", youTab);
        tabPane.setMnemonicAt(0, KeyEvent.VK_1);
        tabPane.addTab("Group", groupTab);
        tabPane.setMnemonicAt(1, KeyEvent.VK_2);
        tabPane.addTab("Profile", profileTab);
        tabPane.setMnemonicAt(2, KeyEvent.VK_3);
    }
}
