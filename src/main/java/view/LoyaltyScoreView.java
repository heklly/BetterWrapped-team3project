package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.loyalty_score.LoyaltyState;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * The View for looking at user's loyalty score
 */
public class LoyaltyScoreView extends JPanel implements ActionListener, PropertyChangeListener {

    private JTable loyaltyScoreTable;
    private JButton backButton;
    private LoyaltyState loyaltyState;
    private ViewManagerModel viewManagerModel;
    private String previousView;

    // Constructor to initialize the view
    public LoyaltyScoreView(LoyaltyState loyaltyState, ViewManagerModel viewManagerModel) {
        this.loyaltyState = loyaltyState;
        this.viewManagerModel = viewManagerModel;
        this.previousView = viewManagerModel.getViewName();

        setLayout(new BorderLayout());

        // Initialize components
        initializeComponents();
    }

    // Initialize the components (table, button, etc.)
    private void initializeComponents() {
        // Create a panel for the artist name at the top
        JPanel artistPanel = new JPanel();
        JLabel artistNameLabel = new JLabel("Artist: " + loyaltyState.getCurrentArtist(), JLabel.CENTER);
        artistNameLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Customize the font for emphasis
        artistPanel.add(artistNameLabel);
        add(artistPanel, BorderLayout.NORTH); // Add the artist name label to the top of the panel

        // Create table with loyalty score data
        loyaltyScoreTable = new JTable();
        updateTableData(); // Set initial table data

        // Add the table to a JScrollPane for better visibility
        JScrollPane scrollPane = new JScrollPane(loyaltyScoreTable);
        add(scrollPane, BorderLayout.CENTER);

        // Create back button
        backButton = new JButton("Back");
        backButton.addActionListener(this);

        // Add back button at the bottom of the panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH); // Move back button to the bottom
    }

    // Update the table data with loyalty scores
    private void updateTableData() {
        Map<String, Integer> scores = loyaltyState.getLoyaltyScores();
        ArrayList<String> dates = loyaltyState.getDates();

        dates.sort(Comparator.reverseOrder());
        String[] columnNames = {"Previous visit dates", "Loyalty Score"};

        Object[][] data = new Object[dates.size()][2];

        for (int i = 0; i < dates.size(); i++) {
            String date = dates.get(i);
            data[i][0] = date;

            if (scores.containsKey(date)) {
                data[i][1] = scores.get(date);
            } else {
                data[i][1] = ":( No loyalty score available for this date";
            }
        }
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        loyaltyScoreTable.setModel(model);
    }

    // Handle actions (e.g., back button click)
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            // Notify the ViewManagerModel to switch to the previous view
            viewManagerModel.setState(previousView);
        }
    }

    // PropertyChangeListener to update view when the state changes
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Update the table with the latest loyalty scores when the state changes
        if (evt.getPropertyName().equals("loyaltyScores")) {
            // Update the table data when loyalty scores are updated
            updateTableData();
            evt.getNewValue();
        }

        // If the current artist changes, update the artist label
        if (evt.getPropertyName().equals("currentArtist")) {
            JLabel artistNameLabel = new JLabel("Artist: " + evt.getNewValue(), JLabel.CENTER);
            artistNameLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Customize the font for emphasis
            // Assuming the artist name label is part of the top panel, update it
            JPanel artistPanel = (JPanel) getComponent(0);
            artistPanel.removeAll();
            artistPanel.add(artistNameLabel);
            artistPanel.revalidate();
            artistPanel.repaint();
        }
    }
}
