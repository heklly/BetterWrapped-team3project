package use_case.Group;

import java.util.Collections;
import java.util.List;

/**
 * Holds the outcome of a group listening compatibility analysis.
 */
public class GroupAnalyticsResult {
    private final double overlapScore;
    private final String vibeTagline;
    private final List<String> sharedArtists;
    private final List<String> sharedGenres;
    private final String narrative;

    public GroupAnalyticsResult(double overlapScore, String vibeTagline, List<String> sharedArtists,
                                List<String> sharedGenres, String narrative) {
        this.overlapScore = overlapScore;
        this.vibeTagline = vibeTagline;
        this.sharedArtists = List.copyOf(sharedArtists);
        this.sharedGenres = List.copyOf(sharedGenres);
        this.narrative = narrative;
    }

    public double getOverlapScore() {
        return overlapScore;
    }

    public String getVibeTagline() {
        return vibeTagline;
    }

    public List<String> getSharedArtists() {
        return Collections.unmodifiableList(sharedArtists);
    }

    public List<String> getSharedGenres() {
        return Collections.unmodifiableList(sharedGenres);
    }

    public String getNarrative() {
        return narrative;
    }
}