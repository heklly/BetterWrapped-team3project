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
        final JLabel title = new JLabel("You're not in a group :( ");
        title.setFont(new Font("Calibri", Font.PLAIN, 18));

        title.setAlignmentX(CENTER_ALIGNMENT);
        loggedIn = new JButton("Main Page");
        topRow.add(Box.createVerticalStrut(10));
        topRow.add(loggedIn);

        JPanel fields = new JPanel();
        fields.setLayout(new BoxLayout(fields, BoxLayout.Y_AXIS));
        inputGroupUsersField.setColumns(40);
        inputGroupUsersField.setFont(new Font("Calibri", Font.PLAIN, 15));
        inputGroupNameField.setColumns(40);
        inputGroupNameField.setFont(new Font("Calibri", Font.PLAIN, 15));
        final LabelTextPanel enterGroupName = new LabelTextPanel(
                new JLabel("Enter Group Name"), inputGroupNameField);
        final LabelTextPanel enterGroupUsers = new LabelTextPanel(
                new JLabel("Enter Group Users"), inputGroupUsersField);
        fields.add(title);
        fields.add(Box.createVerticalStrut(10));
        fields.add(nameErrorField);
        fields.add(enterGroupName);
        fields.add(enterGroupUsers);

        final JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        createGroup = new JButton("Create Group");
        createGroup.setFont(new Font("Calibri", Font.PLAIN, 15));
        buttons.add(createGroup);
        buttons.add(Box.createHorizontalStrut(10));
        joinGroup = new JButton("Join Group");
        joinGroup.setFont(new Font("Calibri", Font.PLAIN, 15));
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
        this.setLayout(new BorderLayout());
        this.add(topRow, BorderLayout.NORTH);
        fields.add(buttons);
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
        if (evt.getPropertyName().equals("createError")) {
            setNameError(noGroupViewModel.getState().getNameError());
        }

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
