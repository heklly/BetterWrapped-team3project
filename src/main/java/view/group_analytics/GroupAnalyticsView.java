package view.group_analytics;

import entity.UserTasteProfile;
import interface_adapter.group_analytics.GroupAnalyticsController;
import interface_adapter.group_analytics.GroupAnalyticsState;
import interface_adapter.group_analytics.GroupAnalyticsViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Set;

/**
 * Simple view showing the verdict and scores.
 * For now it uses a demo group; your teammate's group-forming use case
 * should call controller.analyzeGroup(...) with real UserTasteProfiles.
 */

// got rid of implements View interface. You need to actually specify a view interface.
public class GroupAnalyticsView extends JPanel implements PropertyChangeListener {

    private final GroupAnalyticsViewModel viewModel;
    private GroupAnalyticsController controller;

    private final JTextArea outputArea = new JTextArea(15, 40);
    private final JButton demoButton = new JButton("Run demo group");

    public void setGroupAnalyticsController(GroupAnalyticsController controller) {
        this.controller = controller;
    }

    public GroupAnalyticsView(GroupAnalyticsViewModel viewModel,
                              GroupAnalyticsController controller) {
        this.viewModel = viewModel;
        this.controller = controller;

        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Group Analytics", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));

        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        JPanel top = new JPanel(new BorderLayout());
        top.add(title, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.add(demoButton);

        add(top, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        wireActions();
    }

    private void wireActions() {
        demoButton.addActionListener(e -> runDemo());
    }

    private void runDemo() {
        // TEMP: demo data. Replace with real group from your teammate later.
        List<UserTasteProfile> demoGroup = List.of(
                new UserTasteProfile("Nisarg", "me", Set.of("dance pop", "edm", "k-pop")),
                new UserTasteProfile("Friend 1", "f1", Set.of("indie pop", "acoustic", "sad")),
                new UserTasteProfile("Friend 2", "f2", Set.of("rock", "classic rock", "country"))
        );

        controller.analyzeGroup(demoGroup);
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

    // commented out so code compiles. You need to specify an interface for @Override
    public String getViewName() {
        return viewModel.getViewName();
    }
}