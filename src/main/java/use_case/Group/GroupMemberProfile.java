package spotify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a group member's listening profile that can be compared against others.
 */
public class GroupMemberProfile {
    private final String displayName;
    private final List<String> topArtists;
    private final List<String> topTracks;
    private final List<String> topGenres;

    public GroupMemberProfile(String displayName, List<String> topArtists, List<String> topTracks,
                              List<String> topGenres) {
        this.displayName = requireNonBlank(displayName, "displayName");
        this.topArtists = copyAndNormalize(topArtists);
        this.topTracks = copyAndNormalize(topTracks);
        this.topGenres = copyAndNormalize(topGenres);
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getTopArtists() {
        return Collections.unmodifiableList(topArtists);
    }

    public List<String> getTopTracks() {
        return Collections.unmodifiableList(topTracks);
    }

    public List<String> getTopGenres() {
        return Collections.unmodifiableList(topGenres);
    }

    private static List<String> copyAndNormalize(List<String> items) {
        Objects.requireNonNull(items, "items");
        List<String> copy = new ArrayList<>(items.size());
        for (String item : items) {
            if (item != null) {
                String trimmed = item.trim();
                if (!trimmed.isEmpty()) {
                    copy.add(trimmed.toLowerCase());
                }
            }
        }
        return copy;
    }

    private static String requireNonBlank(String value, String name) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return value;
    }
}