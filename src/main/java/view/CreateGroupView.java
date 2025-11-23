package view;

import interface_adapter.create_group.CreateGroupViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

public class CreateGroupView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "create group";
    private final CreateGroupViewModel createGroupViewModel;


    private final JTextField nameInputField = new JTextField();
    private final JLabel nameErrorField = new JLabel();

    private final JTextField usersInputField = new JTextField();
    private final JLabel usersErrorField = new JLabel();

    private final JButton createGroup = new JButton("Create Group");

    private interface_adapter.create_group.CreateGroupController createGroupController;

    public CreateGroupView(CreateGroupViewModel createGroupViewModel) {

        this.createGroupViewModel = createGroupViewModel;
        this.createGroupViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Create a Group");
        title.setHorizontalAlignment(JLabel.CENTER);

        final LabelTextPanel groupName = new LabelTextPanel(new JLabel("Group Name"), nameInputField);
        final LabelTextPanel groupUsers = new LabelTextPanel(new JLabel("Group Members"), usersInputField);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        createGroup.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if  (evt.getSource().equals(createGroup)) {
                            final GroupState currentState = createGroupViewModel.getState();
                            createGroupController.execute(...)
                        }
                    }
                }
        );

        this.add(title);
        this.add(groupName);
        this.add(nameErrorField);
        this.add(groupUsers);
        this.add(usersErrorField);
        this.add(createGroup);
    }
}
