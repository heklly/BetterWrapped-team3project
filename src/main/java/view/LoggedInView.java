package view;

import data_access.SpotifyDataAccessObject;
import entity.ArtistLoyaltyScore;
import entity.SpotifyUser;

import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.ViewManagerModel;
import interface_adapter.spotify_auth.SpotifyAuthViewModel;
import interface_adapter.daily_mix.DailyMixViewModel;
import interface_adapter.daily_mix.DailyMixState;
import interface_adapter.daily_mix.DailyMixController;
import interface_adapter.get_topItems.GetTopItemsViewModel;
import interface_adapter.get_topItems.GetTopItemsController;
import interface_adapter.get_topItems.GetTopItemsState;
import use_case.get_topItems.TimeRange;
import use_case.get_topItems.TopItem;

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
    private final DailyMixViewModel dailyMixViewModel;
    private DailyMixController dailyMixController;   // NEW
    private final JButton generateDailyMixButton;    // NEW
    private final JTextArea dailyMixArea;            // NEW

    private final GetTopItemsViewModel getTopItemsViewModel;
    private GetTopItemsController getTopItemsController;
    private final JButton artistsButton;
    private final JButton tracksButton;
    private final JButton short_termButton;
    private final JButton medium_termButton;
    private final JButton long_termButton;
    private final JButton getTopItemButton;
    private final JTextArea TopItemsArea;


    public LoggedInView(LoggedInViewModel loggedInViewModel,
                        ViewManagerModel viewManagerModel,
                        SpotifyAuthViewModel spotifyAuthViewModel,
                        DailyMixViewModel dailyMixViewModel,
                        GetTopItemsViewModel getTopItemsViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;
        this.spotifyAuthViewModel = spotifyAuthViewModel;
        this.dailyMixViewModel = dailyMixViewModel;
        this.getTopItemsViewModel = getTopItemsViewModel;

        this.loggedInViewModel.addPropertyChangeListener(this);
        this.dailyMixViewModel.addPropertyChangeListener(this);  // 监听 DailyMix
        this.getTopItemsViewModel.addPropertyChangeListener(this); // listen at getTopItems
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
        // Daily Mix text
        dailyMixArea = new JTextArea(10, 40);
        dailyMixArea.setEditable(false);
        dailyMixArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane dailyMixScroll = new JScrollPane(dailyMixArea);

        // Daily Mix button
        generateDailyMixButton = new JButton("Generate Daily Mix");
        generateDailyMixButton.setEnabled(false);  // 先禁用，连上 Spotify 后再启用
        buttons.add(generateDailyMixButton);

        // TopItems text
        TopItemsArea = new JTextArea(10, 30);
        TopItemsArea.setEditable(false);
        TopItemsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane TopItemsScroll = new JScrollPane(TopItemsArea);

        // get TopItems button
        artistsButton =  new JButton("Top Artists");
        artistsButton.setEnabled(false); // disabled until Spotify connected
        tracksButton = new JButton("Top Tracks");
        tracksButton.setEnabled(false);
        short_termButton = new JButton("1 month");
        short_termButton.setEnabled(false);
        medium_termButton = new JButton("6 months");
        medium_termButton.setEnabled(false);
        long_termButton = new JButton("1 year");
        long_termButton.setEnabled(false);
        getTopItemButton =  new JButton("Get your Top Tracks/Artists");
        getTopItemButton.setEnabled(false); // disabled until time and item selected



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

        // NEW: Generate Daily Mix button listener
        generateDailyMixButton.addActionListener(evt -> {
            if (evt.getSource().equals(generateDailyMixButton)) {
                if (dailyMixController == null || currentSpotifyUser == null) {
                    JOptionPane.showMessageDialog(this,
                            "Please connect your Spotify account first.",
                            "No Spotify User",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    // generate 20 songs
                    dailyMixController.execute(currentSpotifyUser, 20);
                }
            }
        });

        // Generate time button listener
        short_termButton.addActionListener(evt -> {
            if (evt.getSource().equals(short_termButton)) {
                if (getTopItemsController == null || currentSpotifyUser == null) {
                    JOptionPane.showMessageDialog(this,
                            "Please connect your Spotify account first.",
                            "No Spotify User",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    set_time_shortTerm();
                    checkEnableGetTopItemButton();
                }
            }
        });

        medium_termButton.addActionListener(evt -> {
            if (evt.getSource().equals(medium_termButton)) {
                if (getTopItemsController == null || currentSpotifyUser == null) {
                    JOptionPane.showMessageDialog(this,
                            "Please connect your Spotify account first.",
                            "No Spotify User",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    set_time_mediumTerm();
                    checkEnableGetTopItemButton();
                }
            }
        });

        long_termButton.addActionListener(evt -> {
            if (evt.getSource().equals(long_termButton)) {
                if (getTopItemsController == null || currentSpotifyUser == null) {
                    JOptionPane.showMessageDialog(this,
                            "Please connect your Spotify account first.",
                            "No Spotify User",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    set_time_longTerm();
                    checkEnableGetTopItemButton();
                }
            }
        });
        // Generate item button listener
        artistsButton.addActionListener(evt -> {
            if (evt.getSource().equals(artistsButton)) {
                if (getTopItemsController == null || currentSpotifyUser == null) {
                    JOptionPane.showMessageDialog(this,
                            "Please connect your Spotify account first.",
                            "No Spotify User",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    set_item_artist();
                    checkEnableGetTopItemButton();
                }
            }
        });

        tracksButton.addActionListener(evt -> {
            if (evt.getSource().equals(tracksButton)) {
                if (getTopItemsController == null || currentSpotifyUser == null) {
                    JOptionPane.showMessageDialog(this,
                            "Please connect your Spotify account first.",
                            "No Spotify User",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    set_item_tracks();
                    checkEnableGetTopItemButton();
                }
            }
        });

        // Generate getTopItems button listener
        getTopItemButton.addActionListener(evt -> {
            if (evt.getSource().equals(getTopItemButton)) {
                if (getTopItemsController == null || currentSpotifyUser == null) {
                    JOptionPane.showMessageDialog(this,
                            "Please select time range and top item first.",
                            "unselected item or time",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                final GetTopItemsState getTopItemsState = getTopItemsViewModel.getState();
                TopItem topItem = getTopItemsState.getSelectedTopItem();
                TimeRange timeRange = getTopItemsState.getSelectedTime();
                getTopItemsController.execute(currentSpotifyUser, topItem, timeRange);
            }
        });



        // Add all components to view
        this.add(title);
        this.add(usernameInfo);
        this.add(username);
        this.add(spotifyPanel);
        this.add(passwordInfo);
        this.add(passwordErrorField);
        this.add(buttons);
        this.add(Box.createVerticalStrut(10));
        this.add(new JLabel("Your Daily Mix:"));
        this.add(dailyMixScroll);
        this.add(TopItemsScroll);
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
        // get newValue for 2 following branches.
        Object newValue = evt.getNewValue();

        // --------------------------
        // 1. LoggedInState update
        // --------------------------
        if (newValue instanceof LoggedInState) {
            LoggedInState state = (LoggedInState) newValue;

            if (evt.getPropertyName().equals("state")) {
                username.setText(state.getUsername());

                // Update Spotify status display
                if (state.isSpotifyAuthenticated()) {
                    spotifyStatusLabel.setText("Connected ✓");
                    spotifyStatusLabel.setForeground(Color.GREEN);
                    connectSpotifyButton.setEnabled(false);
                    connectSpotifyButton.setText("Spotify Connected");
                    showLoyaltyScoresButton.setEnabled(true);   // Enable loyalty button
                    generateDailyMixButton.setEnabled(true);    // Enable Daily Mix
                    tracksButton.setEnabled(true);
                    artistsButton.setEnabled(true);
                    short_termButton.setEnabled(true);
                    medium_termButton.setEnabled(true);
                    long_termButton.setEnabled(true);       // enable time and item button
                } else {
                    spotifyStatusLabel.setText("Not Connected");
                    spotifyStatusLabel.setForeground(Color.RED);
                    connectSpotifyButton.setEnabled(true);
                    connectSpotifyButton.setText("Connect Spotify");
                    showLoyaltyScoresButton.setEnabled(false);  // Disable loyalty button
                    generateDailyMixButton.setEnabled(false);   // Disable Daily Mix
                    tracksButton.setEnabled(false);
                    artistsButton.setEnabled(false);
                    short_termButton.setEnabled(false);
                    medium_termButton.setEnabled(false);
                    long_termButton.setEnabled(false);  // Disable time and item button
                    getTopItemButton.setEnabled(false); // Disable getTopItem button
                }
            }
            else if (evt.getPropertyName().equals("password")) {
                if (state.getPasswordError() == null) {
                    JOptionPane.showMessageDialog(this,
                            "Password updated for " + state.getUsername());
                    passwordInputField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, state.getPasswordError());
                }
            }
        }

        // --------------------------
        // 2. DailyMixState update
        // --------------------------
        else if (newValue instanceof DailyMixState) {
            DailyMixState mixState = (DailyMixState) newValue;

            if (mixState.getError() != null) {
                JOptionPane.showMessageDialog(this,
                        mixState.getError(),
                        "Daily Mix Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            StringBuilder sb = new StringBuilder();
            if (mixState.getTracks() == null || mixState.getTracks().isEmpty()) {
                sb.append("No tracks found. Try saving some songs or playing music on Spotify!");
            } else {
                int index = 1;
                for (String line : mixState.getTracks()) {
                    sb.append(index).append(". ").append(line).append("\n");
                    index++;
                }
            }

            dailyMixArea.setText(sb.toString());
            dailyMixArea.setCaretPosition(0);
        }

        // 3. GetTopItemState update
        else if (newValue instanceof GetTopItemsState) {
            GetTopItemsState getTopItemsState = (GetTopItemsState) newValue;

            if(!getTopItemsState.getSuccess()){
                JOptionPane.showMessageDialog(this,
                        getTopItemsState.getSuccess(),
                        "GetTop Items Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
        }
            StringBuilder sb = new StringBuilder();
            if(getTopItemsState.getTopItems() == null || getTopItemsState.getTopItems().isEmpty()) {
                sb.append("no items found. Try listening to some music on Spotify!");
            } else {
                int index = 1;
                for (String item : getTopItemsState.getTopItems()) {
                    sb.append(index).append(". ").append(item).append("\n");
                    index++;
                }
            }

            TopItemsArea.setText(sb.toString());
            TopItemsArea.setCaretPosition(0);
        }
    }



    // setting time
    // set the selected time to short_term
    private void set_time_shortTerm() {
        // check if we have a Spotify user
        if(currentSpotifyUser == null) {
            JOptionPane.showMessageDialog(this,
                    "No Spotify user found. Please reconnect to Spotify.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        final GetTopItemsState getTopItemsState = getTopItemsViewModel.getState();
        getTopItemsState.setSelectedTime(TimeRange.short_term);
        getTopItemsViewModel.setState(getTopItemsState);
        getTopItemsViewModel.firePropertyChange();
    }

    // set the selected time to medium_term
    private void set_time_mediumTerm(){
        // check if we have a Spotify user
        if(currentSpotifyUser == null) {
            JOptionPane.showMessageDialog(this,
                    "No Spotify user found. Please reconnect to Spotify.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        final GetTopItemsState getTopItemsState = getTopItemsViewModel.getState();
        getTopItemsState.setSelectedTime(TimeRange.medium_term);
        getTopItemsViewModel.setState(getTopItemsState);
        getTopItemsViewModel.firePropertyChange();
    }

    // set the selected time to long_term
    private void set_time_longTerm(){
        // check if we have a Spotify user
        if(currentSpotifyUser == null) {
            JOptionPane.showMessageDialog(this,
                    "No Spotify user found. Please reconnect to Spotify.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        final GetTopItemsState getTopItemsState = getTopItemsViewModel.getState();
        getTopItemsState.setSelectedTime(TimeRange.long_term);
        getTopItemsViewModel.setState(getTopItemsState);
        getTopItemsViewModel.firePropertyChange();
    }


    // setting item
    // set the item to tracks
    private void set_item_tracks() {
        // check if we have a Spotify user
        if (currentSpotifyUser == null) {
            JOptionPane.showMessageDialog(this,
                    "No Spotify user found. Please reconnect to Spotify.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        final GetTopItemsState getTopItemsState = getTopItemsViewModel.getState();
        getTopItemsState.setSelectedTopItem(TopItem.tracks);
        getTopItemsViewModel.setState(getTopItemsState);
        getTopItemsViewModel.firePropertyChange();
    }

    // set the item to artists
    private void set_item_artist() {
        // check if we have a Spotify user
        if (currentSpotifyUser == null) {
            JOptionPane.showMessageDialog(this,
                    "No Spotify user found. Please reconnect to Spotify.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        final GetTopItemsState getTopItemsState = getTopItemsViewModel.getState();
        getTopItemsState.setSelectedTopItem(TopItem.artists);
        getTopItemsViewModel.setState(getTopItemsState);
        getTopItemsViewModel.firePropertyChange();
    }


    // check whether GetTopItemButton is enabled
    private void checkEnableGetTopItemButton(){
        final GetTopItemsState getTopItemsState = getTopItemsViewModel.getState();
        boolean enabled = getTopItemsState.getSelectedTopItem() != null
                && getTopItemsState.getSelectedTime() != null;
        getTopItemButton.setEnabled(enabled);
    }



    public void setDailyMixController(DailyMixController dailyMixController) {
        this.dailyMixController = dailyMixController;
    }

    public void setGetTopItemsController(GetTopItemsController getTopItemsController) {
        this.getTopItemsController = getTopItemsController;
    }

    public String getViewName() {
        return viewName;
    }


    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    public void setCurrentSpotifyUser(SpotifyUser user) {
        this.currentSpotifyUser = user;
    }
}
