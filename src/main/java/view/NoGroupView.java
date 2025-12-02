package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_group.CreateGroupController;
import interface_adapter.create_group.NoGroupViewModel;
import interface_adapter.create_group.UserGroupState;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoGroupView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "no group";
    private final NoGroupViewModel noGroupViewModel;
    private final ViewManagerModel viewManagerModel;

    private final JTextField inputGroupNameField = new JTextField();
    private final JLabel nameErrorField = new JLabel();
    private final JTextField inputGroupUsersField = new JTextField();

    private final JButton loggedIn;
    private final JButton createGroup;
    private final JButton joinGroup;
    private CreateGroupController createGroupController = null;
//    private JoinGroupController joinGroupController = null;

    public NoGroupView(NoGroupViewModel noGroupViewModel, ViewManagerModel viewManagerModel) {
        this.noGroupViewModel = noGroupViewModel;
        this.viewManagerModel = viewManagerModel;
        this.noGroupViewModel.addPropertyChangeListener(this);

        JPanel topRow = new JPanel();
        final JLabel title = new JLabel("You're not in a group");
        title.setAlignmentX(CENTER_ALIGNMENT);
        topRow.add(title);
        loggedIn = new JButton("Main Page");
        topRow.add(loggedIn);

        JPanel fields = new JPanel();
        fields.setLayout(new BoxLayout(fields, BoxLayout.Y_AXIS));
        final LabelTextPanel enterGroupName = new LabelTextPanel(
                new JLabel("Enter Group Name"), inputGroupNameField);
        fields.add(enterGroupName);
        final LabelTextPanel enterGroupUsers = new LabelTextPanel(
                new JLabel("Enter Group Users"), inputGroupUsersField);
        fields.add(enterGroupUsers);
        fields.add(nameErrorField);


        final JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
        createGroup = new JButton("Create Group");
        buttons.add(createGroup);
        joinGroup = new JButton("Join Group");
        buttons.add(joinGroup);

        loggedIn.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(loggedIn)) {
                            viewManagerModel.setState("logged in");
                            viewManagerModel.firePropertyChange();
                        }
                    }
                }
        );

        createGroup.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(createGroup)) {
                            final UserGroupState currentState = noGroupViewModel.getState();

                            createGroupController.execute(
                                    currentState.getGroupName(),
                                    currentState.getSpotifyUser(),
                                    currentState.getGroupUsers()
                            );
                        }
                    }
                }
        );

//        joinGroup.addActionListener(
//                new ActionListener() {
//                    public void actionPerformed(ActionEvent evt) {
//                        if (evt.getSource().equals(joinGroup)) {
//                            final UserGroupState = noGroupViewModel.getState();
//                            joinGroupController.execute();
//                            TODO
//                        }
//                    }
//                }
//        );

        inputGroupNameField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final UserGroupState currentState = noGroupViewModel.getState();
                currentState.setGroupName(inputGroupNameField.getText());
                noGroupViewModel.setState(currentState);
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        inputGroupUsersField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final UserGroupState currentState = noGroupViewModel.getState();
                List<String> groupUsernames = new ArrayList<>();
                Collections.addAll(groupUsernames, inputGroupUsersField.getText().split(","));
                currentState.setGroupUsernames(groupUsernames);
                noGroupViewModel.setState(currentState);
            }
            @Override
            public void insertUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override
            public void removeUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override
            public void changedUpdate(DocumentEvent e) { documentListenerHelper(); }
        });
        this.setLayout(new BorderLayout(5, 5));
        this.add(topRow, BorderLayout.NORTH);
        this.add(buttons, BorderLayout.EAST);
        this.add(fields, BorderLayout.CENTER);

    }
    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    public void propertyChange(PropertyChangeEvent evt) {
//        if (evt.getPropertyName().equals("creatError")) {}
        setNameError(noGroupViewModel.getState().getNameError());
    }

    public String getViewName() { return viewName; }

    public void setCreateGroupController(CreateGroupController createGroupController) {
        this.createGroupController = createGroupController;
    }
//    public void setJoinGroupController(JoinGorupController joinGroupController) {
//        this.joinGroupController = joinGroupController
//    }
    public void setNameError(String nameError) { this.nameErrorField.setText("nameError"); }
}
