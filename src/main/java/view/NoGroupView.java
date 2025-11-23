package view;

import interface_adapter.NoGroupViewModel;

import javax.swing.*;
import java.beans.PropertyChangeListener;

public class NoGroupView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "no group";
    private final NoGroupViewModel noGroupViewModel;

    private final JButton createGroup;

    public NoGroupView(NoGroupViewModel noGroupViewModel) {
        this.noGroupViewModel = noGroupViewModel;
        this.noGroupViewModel.addPropertyChangeListener(this);



    }
}
