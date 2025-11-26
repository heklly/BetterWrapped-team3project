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
import java.beans.PropertyChangeListener;

public class NoGroupView extends JPanel implements ActionListener {

    private final String viewName = "no group";
    private final NoGroupViewModel noGroupViewModel;
    private final ViewManagerModel viewManagerModel;

    private final JTextField inputGroupNameField = new JTextField();
//    private final JLabel groupNameErrorField = new JLabel();
//    private final JTextField searchGroupName = new JTextField();
//    private final JLabel joinGroupErrorField = new JLabel();

    private final JButton createGroup;
    private final JButton joinGroup;
    private CreateGroupController createGroupController = null;
//    private JoinGroupController joinGroupController = null;

    public NoGroupView(NoGroupViewModel noGroupViewModel, ViewManagerModel viewManagerModel) {
        this.noGroupViewModel = noGroupViewModel;
        this.viewManagerModel = viewManagerModel;
//        this.noGroupViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("You're not in a group");
        title.setAlignmentX(CENTER_ALIGNMENT);

        final LabelTextPanel enterGroupName = new LabelTextPanel(
                new JLabel("Group Name"), inputGroupNameField);

        final JPanel buttons = new JPanel();
        createGroup = new JButton("Create Group");
        buttons.add(createGroup);
        joinGroup = new JButton("Join Group");
        buttons.add(joinGroup);

        createGroup.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(createGroup)) {
                            final UserGroupState currentState = noGroupViewModel.getState();

//                            createGroupController.execute(
//                                    currentState.getGroupName(),
//                                    currentState.getGroupUsernames()
//                            );
                        }
                    }
                }
        );

//        joinGroup.addActionListener(
//                new ActionListener() {
//                    public void actionPerformed(ActionEvent evt) {
//                        if (evt.getSource().equals(joinGroup)) {
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

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(enterGroupName);
        this.add(buttons);
    }
    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    private String getViewName() { return viewName; }

    public void setCreateGroupController(CreateGroupController createGroupController) {
        this.createGroupController = createGroupController;
    }
//    public void setJoinGroupController(JoinGorupController joinGroupController) {
//        this.joinGroupController = joinGroupController
//    }
//
}
