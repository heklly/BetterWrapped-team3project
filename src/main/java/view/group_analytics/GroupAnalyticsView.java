package view.group_analytics;

import entity.Group;
import entity.SpotifyUser;
import interface_adapter.ViewManagerModel;
import interface_adapter.group_analytics.GroupAnalyticsController;
import interface_adapter.group_analytics.GroupAnalyticsState;
import interface_adapter.group_analytics.GroupAnalyticsViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * View for showing group analytics verdict and scores.
 * This view only works with REAL data (real Group / SpotifyUser list).
 */
public class GroupAnalyticsView extends JPanel implements PropertyChangeListener {

    private final GroupAnalyticsViewModel viewModel;
    private GroupAnalyticsController controller;

    private final JTextArea outputArea = new JTextArea(15, 40);

    public GroupAnalyticsView(GroupAnalyticsViewModel viewModel,
                              ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;

        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Group Analytics", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));

        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        JPanel top = new JPanel(new BorderLayout());
        top.add(title, BorderLayout.CENTER);

        add(top, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Called from AppBuilder
    public void setGroupAnalyticsController(GroupAnalyticsController controller) {
        this.controller = controller;
    }

    // Called from group UI / presenter when a real Group is ready
    public void analyzeRealGroup(Group group) {
        if (controller == null) {
            outputArea.setText("Controller not set yet.");
            return;
        }
        if (group == null) {
            outputArea.setText("No group provided.");
            return;
        }
        controller.analyzeGroup(group);
    }

    // Alternative: if you only have a list of SpotifyUsers
    public void analyzeRealUsers(List<SpotifyUser> users) {
        if (controller == null) {
            outputArea.setText("Controller not set yet.");
            return;
        }
        if (users == null || users.isEmpty()) {
            outputArea.setText("No users provided.");
            return;
        }
        controller.analyzeFromUsers(users);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!"state".equals(evt.getPropertyName())) return;
        GroupAnalyticsState state = (GroupAnalyticsState) evt.getNewValue();

        if (state.getErrorMessage() != null) {
            outputArea.setText("Error: " + state.getErrorMessage());
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Final verdict: ").append(state.getVerdict()).append("\n\n");
        sb.append(String.format("Average similarity: %.2f%n%n", state.getAverageSimilarity()));

        sb.append("Vibe scores:\n");
        if (state.getVibeScores() != null) {
            state.getVibeScores().forEach((k, v) ->
                    sb.append("  ").append(k).append(": ")
                            .append(String.format("%.2f", v))
                            .append("\n")
            );
        }

        sb.append("\nPairwise similarities:\n");
        if (state.getPairwise() != null) {
            state.getPairwise().forEach(p ->
                    sb.append(String.format("  %s ↔ %s : %.2f%n",
                            p.getUserA(), p.getUserB(), p.getSimilarity()))
            );
        }

        outputArea.setText(sb.toString());
    }

    // Used by AppBuilder to register the card name
    public String getViewName() {
        return viewModel.getViewName();
    }
}