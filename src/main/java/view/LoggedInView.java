package view;

import data_access.SpotifyDataAccessObject;
import entity.ArtistLoyaltyScore;
import entity.SpotifyUser;
import interface_adapter.logged_in.ChangePasswordController;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.ViewManagerModel;
import interface_adapter.spotify_auth.SpotifyAuthViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class LoggedInView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "logged in";
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;
    private final SpotifyAuthViewModel spotifyAuthViewModel;
    private final JLabel passwordErrorField = new JLabel();
    private ChangePasswordController changePasswordController = null;
    private LogoutController logoutController;
    private SpotifyDataAccessObject spotifyDAO;  // NEW
    private SpotifyUser currentSpotifyUser;  // NEW

    private final JLabel username;
    private final JLabel spotifyStatusLabel = new JLabel();

    private final JButton logOut;
    private final JButton connectSpotifyButton;
    private final JButton showLoyaltyScoresButton;  // NEW

    private final JTextField passwordInputField = new JTextField(15);
    private final JButton changePassword;

    private final JButton groupAnalyticsButton = new JButton("Group analytics");

    public LoggedInView(LoggedInViewModel loggedInViewModel, ViewManagerModel viewManagerModel,
                        SpotifyAuthViewModel spotifyAuthViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;
        this.spotifyAuthViewModel = spotifyAuthViewModel;
        this.loggedInViewModel.addPropertyChangeListener(this);
        this.spotifyDAO = new SpotifyDataAccessObject();  // NEW

        final JLabel title = new JLabel("Better Wrapped - Dashboard");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel usernameInfo = new JLabel("Currently logged in: ");
        username = new JLabel();

        // Spotify status panel
        final JPanel spotifyPanel = new JPanel();
        spotifyPanel.add(new JLabel("Spotify Status: "));
        spotifyStatusLabel.setText("Not Connected");
        spotifyStatusLabel.setForeground(Color.RED);
        spotifyPanel.add(spotifyStatusLabel);

        // Password change section
        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel("New Password"), passwordInputField);

        // Buttons panel
        final JPanel buttons = new JPanel();
        logOut = new JButton("Log Out");
        buttons.add(logOut);

        changePassword = new JButton("Change Password");
        buttons.add(changePassword);

        connectSpotifyButton = new JButton("Connect Spotify");
        buttons.add(connectSpotifyButton);

        showLoyaltyScoresButton = new JButton("Show Artist Loyalty");  // NEW
        showLoyaltyScoresButton.setEnabled(false);  // Disabled until Spotify connected
        buttons.add(showLoyaltyScoresButton);  // NEW

        buttons.add(groupAnalyticsButton);

        // Log out button listener
        logOut.addActionListener(evt -> {
            if (evt.getSource().equals(logOut)) {
                if (logoutController != null) {
                    logoutController.execute();
                }
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Password field listener
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final LoggedInState currentState = loggedInViewModel.getState();
                currentState.setPassword(passwordInputField.getText());
                loggedInViewModel.setState(currentState);
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

        // Change password button listener
        changePassword.addActionListener(evt -> {
            if (evt.getSource().equals(changePassword)) {
                final LoggedInState currentState = loggedInViewModel.getState();
                if (changePasswordController != null) {
                    changePasswordController.execute(
                            currentState.getUsername(),
                            currentState.getPassword()
                    );
                }
            }
        });

        // Connect Spotify button listener
        connectSpotifyButton.addActionListener(evt -> {
            if (evt.getSource().equals(connectSpotifyButton)) {
                viewManagerModel.setState(spotifyAuthViewModel.getViewName());
                viewManagerModel.firePropertyChange();
            }
        });

        // NEW: Show Artist Loyalty button listener
        showLoyaltyScoresButton.addActionListener(evt -> {
            if (evt.getSource().equals(showLoyaltyScoresButton) && currentSpotifyUser != null) {
                showArtistLoyaltyScores();
            }
        });

        groupAnalyticsButton.addActionListener(e -> {
            viewManagerModel.setState("group analytics");
            viewManagerModel.firePropertyChange();
        });

        // Add all components to view
        this.add(title);
        this.add(usernameInfo);
        this.add(username);
        this.add(spotifyPanel);
        this.add(passwordInfo);
        this.add(passwordErrorField);
        this.add(buttons);
    }

    // REPLACE the showArtistLoyaltyScores() method with this corrected version
    private void showArtistLoyaltyScores() {
        // First check if we have a Spotify user
        if (currentSpotifyUser == null) {
            JOptionPane.showMessageDialog(this,
                    "No Spotify user found. Please reconnect to Spotify.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Show loading dialog
        JDialog loadingDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                "Loading...", true);
        JLabel loadingLabel = new JLabel("Fetching artist loyalty scores...");
        loadingLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        loadingDialog.add(loadingLabel);
        loadingDialog.pack();
        loadingDialog.setLocationRelativeTo(this);

        // Fetch data in background thread
        new Thread(() -> {
            try {
                System.out.println("Fetching loyalty scores for user: " + currentSpotifyUser.getUsername());
                List<ArtistLoyaltyScore> scores = spotifyDAO.getArtistLoyaltyScores(currentSpotifyUser);
                System.out.println("Got " + scores.size() + " artist scores");

                // Close loading dialog and show results
                SwingUtilities.invokeLater(() -> {
                    loadingDialog.dispose();
                    showResultsDialog(scores);
                });

            } catch (Exception e) {
                e.printStackTrace();  // Print error to console
                SwingUtilities.invokeLater(() -> {
                    loadingDialog.dispose();
                    JOptionPane.showMessageDialog(this,
                            "Error loading artist loyalty scores:\n" + e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                });
            }
        }).start();

        // Show loading dialog (blocks until closed by the background thread)
        loadingDialog.setVisible(true);
    }

    // NEW: Separate method to show results
    private void showResultsDialog(List<ArtistLoyaltyScore> scores) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                "Artist Loyalty Scores", true);
        dialog.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea(20, 60);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        StringBuilder sb = new StringBuilder();
        sb.append("ARTIST LOYALTY SCORES\n");
        sb.append("=".repeat(70)).append("\n\n");

        if (scores.isEmpty()) {
            sb.append("No artists found. Try saving some music on Spotify!");
        } else {
            sb.append(String.format("%-35s %10s %8s %8s %8s\n",
                    "Artist", "Score", "Tracks", "Albums", "Recent"));
            sb.append("-".repeat(70)).append("\n");

            for (int i = 0; i < Math.min(scores.size(), 20); i++) {
                ArtistLoyaltyScore score = scores.get(i);
                sb.append(String.format("%-35s %10.0f %8d %8d %8s\n",
                        truncate(score.getArtistName(), 35),
                        score.getLoyaltyScore(),
                        score.getSavedTracks(),
                        score.getSavedAlbums(),
                        score.isInRecentlyPlayed() ? "Yes" : "No"
                ));
            }

            if (scores.size() > 20) {
                sb.append("\n... and ").append(scores.size() - 20).append(" more artists");
            }
        }

        textArea.setText(sb.toString());
        textArea.setCaretPosition(0); // Scroll to top

        JScrollPane scrollPane = new JScrollPane(textArea);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private String truncate(String str, int maxLength) {
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }

    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final LoggedInState state = (LoggedInState) evt.getNewValue();
            username.setText(state.getUsername());

            // Update Spotify status display
            if (state.isSpotifyAuthenticated()) {
                spotifyStatusLabel.setText("Connected ✓");
                spotifyStatusLabel.setForeground(Color.GREEN);
                connectSpotifyButton.setEnabled(false);
                connectSpotifyButton.setText("Spotify Connected");
                showLoyaltyScoresButton.setEnabled(true);  // NEW: Enable loyalty button

            } else {
                spotifyStatusLabel.setText("Not Connected");
                spotifyStatusLabel.setForeground(Color.RED);
                connectSpotifyButton.setEnabled(true);
                connectSpotifyButton.setText("Connect Spotify");
                showLoyaltyScoresButton.setEnabled(false);  // NEW: Disable loyalty button
            }
        }
        else if (evt.getPropertyName().equals("password")) {
            final LoggedInState state = (LoggedInState) evt.getNewValue();
            if (state.getPasswordError() == null) {
                JOptionPane.showMessageDialog(this, "Password updated for " + state.getUsername());
                passwordInputField.setText("");
            }
            else {
                JOptionPane.showMessageDialog(this, state.getPasswordError());
            }
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setChangePasswordController(ChangePasswordController changePasswordController) {
        this.changePasswordController = changePasswordController;
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    public void setCurrentSpotifyUser(SpotifyUser user) {
        this.currentSpotifyUser = user;
    }
}
