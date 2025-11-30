package view;

import data_access.SpotifyDataAccessObject;
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

    private LogoutController logoutController;
    private SpotifyDataAccessObject spotifyDAO;
    private SpotifyUser currentSpotifyUser;

    private final JLabel spotifyStatusLabel = new JLabel();

    private final JButton logOut;
    private final JButton connectSpotifyButton;

    private final DailyMixViewModel dailyMixViewModel;
    private DailyMixController dailyMixController;
    private final JButton generateDailyMixButton;

    private final GetTopItemsViewModel getTopItemsViewModel;
    private GetTopItemsController getTopItemsController;
    private final JButton artistsButton;
    private final JButton tracksButton;
    private final JButton short_termButton;
    private final JButton medium_termButton;
    private final JButton long_termButton;
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
        this.dailyMixViewModel.addPropertyChangeListener(this);
        this.getTopItemsViewModel.addPropertyChangeListener(this);
        this.spotifyDAO = new SpotifyDataAccessObject();

        final JLabel title = new JLabel("Better Wrapped - Dashboard");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);


        // Spotify status panel
        final JPanel spotifyPanel = new JPanel();
        spotifyPanel.add(new JLabel("Spotify Status: "));
        spotifyStatusLabel.setText("Not Connected");
        spotifyStatusLabel.setForeground(Color.RED);
        spotifyPanel.add(spotifyStatusLabel);

        // Buttons panel
        final JPanel buttons = new JPanel();
        logOut = new JButton("Log Out");
        buttons.add(logOut);

        connectSpotifyButton = new JButton("Connect Spotify");
        buttons.add(connectSpotifyButton);


        // Daily Mix button
        generateDailyMixButton = new JButton("Generate Daily Mix");
        generateDailyMixButton.setEnabled(false);
        buttons.add(generateDailyMixButton);

        // TopItems text
        TopItemsArea = new JTextArea(10, 30);
        TopItemsArea.setEditable(false);
        TopItemsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane TopItemsScroll = new JScrollPane(TopItemsArea);

        // get TopItems buttons
        artistsButton =  new JButton("Top Artists");
        artistsButton.setEnabled(false);
        tracksButton = new JButton("Top Tracks");
        tracksButton.setEnabled(false);
        short_termButton = new JButton("1 month");
        short_termButton.setEnabled(false);
        medium_termButton = new JButton("6 months");
        medium_termButton.setEnabled(false);
        long_termButton = new JButton("1 year");
        long_termButton.setEnabled(false);


        // Log out button listener
        logOut.addActionListener(evt -> {
            if (logoutController != null) {
                logoutController.execute();
            }
        });

        // Connect Spotify listener
        connectSpotifyButton.addActionListener(evt -> {
            viewManagerModel.setState(spotifyAuthViewModel.getViewName());
            viewManagerModel.firePropertyChange();
        });

        // Daily Mix listener
        generateDailyMixButton.addActionListener(evt -> {
            if (dailyMixController == null || currentSpotifyUser == null) {
                JOptionPane.showMessageDialog(this,
                        "Please connect your Spotify account first.",
                        "No Spotify User",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                dailyMixController.execute(currentSpotifyUser, 20);
            }
        });

        // Time range listeners
        short_termButton.addActionListener(evt -> { set_time_shortTerm();});
        medium_termButton.addActionListener(evt -> { set_time_mediumTerm();});
        long_termButton.addActionListener(evt -> { set_time_longTerm();});

        // TopItem listeners
        artistsButton.addActionListener(evt -> { set_item_artist();});
        tracksButton.addActionListener(evt -> { set_item_tracks();});


        // Main layout
        this.setLayout(new BorderLayout());

        // --- Left panel (WEST) ---
        // Use BoxLayout to allow vertical centering
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(Box.createVerticalGlue());   // pushes buttons to vertical center
        leftPanel.add(artistsButton);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(tracksButton);
        leftPanel.add(Box.createVerticalGlue());
        this.add(leftPanel, BorderLayout.WEST);

        // --- Center panel (CENTER) ---
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // Title and username
        centerPanel.add(title);

        // Spotify status
        spotifyPanel.setMaximumSize(new Dimension(400, 30));
        centerPanel.add(spotifyPanel);

        // Time range buttons (below the above info)
        JPanel timePanel = new JPanel();
        timePanel.add(short_termButton);
        timePanel.add(medium_termButton);
        timePanel.add(long_termButton);
        centerPanel.add(Box.createVerticalStrut(10));
        timePanel.setMaximumSize(new Dimension(400, 30));
        centerPanel.add(timePanel);

        // TopItems text area
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(TopItemsScroll);

        this.add(centerPanel, BorderLayout.CENTER);

        // --- Bottom panel (SOUTH) ---
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(logOut);
        bottomPanel.add(connectSpotifyButton);
        bottomPanel.add(generateDailyMixButton);
        this.add(bottomPanel, BorderLayout.SOUTH);

    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object newValue = evt.getNewValue();

        // --------------------------
        // LoggedInState update
        // --------------------------
        if (newValue instanceof LoggedInState) {
            LoggedInState state = (LoggedInState) newValue;

            if (evt.getPropertyName().equals("state")) {
                // Spotify status
                boolean connected = state.isSpotifyAuthenticated();
                spotifyStatusLabel.setText(connected ? "Connected ✓" : "Not Connected");
                spotifyStatusLabel.setForeground(connected ? Color.GREEN : Color.RED);
                connectSpotifyButton.setEnabled(!connected);

                generateDailyMixButton.setEnabled(connected);
                tracksButton.setEnabled(connected);
                artistsButton.setEnabled(connected);
                short_termButton.setEnabled(connected);
                medium_termButton.setEnabled(connected);
                long_termButton.setEnabled(connected);
            }
        }


        // --------------------------
        // GetTopItemsState update
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
            // Show in new window:
            showDailyMixInNewWindow(mixState.getTracks());
        }

    }

    // --- Time and Item helpers ---
    private void set_time_shortTerm() {
        final GetTopItemsState state = getTopItemsViewModel.getState();
        state.setSelectedTime(TimeRange.short_term);
        getTopItemsViewModel.setState(state);
        getTopItemsViewModel.firePropertyChange();
    }

    private void set_time_mediumTerm() {
        final GetTopItemsState state = getTopItemsViewModel.getState();
        state.setSelectedTime(TimeRange.medium_term);
        getTopItemsViewModel.setState(state);
        getTopItemsViewModel.firePropertyChange();
    }

    private void set_time_longTerm() {
        final GetTopItemsState state = getTopItemsViewModel.getState();
        state.setSelectedTime(TimeRange.long_term);
        getTopItemsViewModel.setState(state);
        getTopItemsViewModel.firePropertyChange();
    }

    private void set_item_tracks() {
        final GetTopItemsState state = getTopItemsViewModel.getState();
        state.setSelectedTopItem(TopItem.tracks);
        getTopItemsViewModel.setState(state);
        getTopItemsViewModel.firePropertyChange();
    }

    private void set_item_artist() {
        final GetTopItemsState state = getTopItemsViewModel.getState();
        state.setSelectedTopItem(TopItem.artists);
        getTopItemsViewModel.setState(state);
        getTopItemsViewModel.firePropertyChange();
    }

    // --- Controllers and user setter ---
    public void setDailyMixController(DailyMixController dailyMixController) {
        this.dailyMixController = dailyMixController;
    }

    public void setGetTopItemsController(GetTopItemsController getTopItemsController) {
        this.getTopItemsController = getTopItemsController;
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    public void setCurrentSpotifyUser(SpotifyUser user) {
        this.currentSpotifyUser = user;
    }

    public String getViewName() {
        return viewName;
    }

    // private method for showing daily mix instead of on same view

    private void showDailyMixInNewWindow(List<String> tracks) {
        JFrame frame = new JFrame("Your Daily Mix");
        JTextArea textArea = new JTextArea(15, 40);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        StringBuilder sb = new StringBuilder();
        if (tracks == null || tracks.isEmpty()) {
            sb.append("No tracks found. Try saving some songs or playing music on Spotify!");
        } else {
            int index = 1;
            for (String track : tracks) {
                sb.append(index).append(". ").append(track).append("\n");
                index++;
            }
        }
        textArea.setText(sb.toString());
        textArea.setCaretPosition(0);

        frame.add(new JScrollPane(textArea));
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }


}
