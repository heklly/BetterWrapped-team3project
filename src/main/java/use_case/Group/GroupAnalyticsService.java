package use_case.group_analytics;

import spotify.GroupAnalyticsResult;
import spotify.GroupMemberProfile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Provides a lightweight, local-only analysis of how compatible up to five listeners are.
 * This deliberately works on fake/offline data so the team can demo the experience before
 * wiring in real Spotify profiles.
 */
public class GroupAnalyticsService {
    private static final int MAX_MEMBERS = 5;

    public GroupAnalyticsResult analyzeGroup(List<GroupMemberProfile> members) {
        Objects.requireNonNull(members, "members");
        if (members.size() < 2) {
            throw new IllegalArgumentException("At least two listeners are required for a group vibe check.");
        }
        if (members.size() > MAX_MEMBERS) {
            throw new IllegalArgumentException("Group size capped at " + MAX_MEMBERS + " for this demo.");
        }

        Set<String> sharedArtists = intersectAll(members, GroupMemberProfile::getTopArtists);
        Set<String> sharedTracks = intersectAll(members, GroupMemberProfile::getTopTracks);
        Set<String> sharedGenres = intersectAll(members, GroupMemberProfile::getTopGenres);

        double avgArtistCount = averageSize(members, GroupMemberProfile::getTopArtists);
        double avgTrackCount = averageSize(members, GroupMemberProfile::getTopTracks);
        double avgGenreCount = averageSize(members, GroupMemberProfile::getTopGenres);

        double artistOverlap = safeDivide(sharedArtists.size(), avgArtistCount);
        double trackOverlap = safeDivide(sharedTracks.size(), avgTrackCount);
        double genreOverlap = safeDivide(sharedGenres.size(), avgGenreCount);

        double overlapScore = Math.round(((artistOverlap + trackOverlap + genreOverlap) / 3.0) * 100.0) / 100.0;
        String vibeTagline = buildVibeTagline(overlapScore, sharedGenres);
        String narrative = buildNarrative(members, sharedArtists, sharedGenres, overlapScore);

        return new GroupAnalyticsResult(overlapScore, vibeTagline,
                new ArrayList<>(sharedArtists), new ArrayList<>(sharedGenres), narrative);
    }

    public List<GroupMemberProfile> buildDemoProfiles() {
        List<GroupMemberProfile> profiles = new ArrayList<>();
        profiles.add(new GroupMemberProfile("Alex",
                List.of("Taylor Swift", "Kendrick Lamar", "Phoebe Bridgers", "SZA"),
                List.of("Anti-Hero", "N95", "Motion Sickness", "Kill Bill"),
                List.of("pop", "hip hop", "indie", "sad girl")));
        profiles.add(new GroupMemberProfile("Priya",
                List.of("Taylor Swift", "Dua Lipa", "SZA", "Bad Bunny"),
                List.of("Levitating", "Kill Bill", "Cardigan", "Titi Me Pregunto"),
                List.of("pop", "reggaeton", "dance pop", "r&b")));
        profiles.add(new GroupMemberProfile("Sam",
                List.of("The Weeknd", "SZA", "Calvin Harris", "Kendrick Lamar"),
                List.of("Blinding Lights", "Slide", "Starboy", "Money Trees"),
                List.of("r&b", "pop", "edm", "hip hop")));
        profiles.add(new GroupMemberProfile("Jordan",
                List.of("Phoebe Bridgers", "boygenius", "Lucy Dacus", "The National"),
                List.of("Not Strong Enough", "Motion Sickness", "The System Only Dreams in Total Darkness", "Kill Bill"),
                List.of("indie", "sad girl", "alt-rock")));
        profiles.add(new GroupMemberProfile("Casey",
                List.of("Dua Lipa", "Charli XCX", "SZA", "Fred again.."),
                List.of("Dance The Night", "Boom Clap", "Kill Bill", "Danielle (smile on my face)"),
                List.of("dance pop", "house", "pop")));
        return profiles;
    }

    private Set<String> intersectAll(List<GroupMemberProfile> members,
                                     Function<GroupMemberProfile, List<String>> extractor) {
        Set<String> intersection = new HashSet<>(extractor.apply(members.get(0)));
        for (int i = 1; i < members.size(); i++) {
            intersection.retainAll(new HashSet<>(extractor.apply(members.get(i))));
        }
        return intersection;
    }

    private double averageSize(List<GroupMemberProfile> members,
                               Function<GroupMemberProfile, List<String>> extractor) {
        return members.stream().map(extractor).mapToInt(List::size).average().orElse(0.0);
    }

    private double safeDivide(double numerator, double denominator) {
        if (denominator == 0) {
            return 0;
        }
        return numerator / denominator;
    }

    private String buildVibeTagline(double overlapScore, Set<String> sharedGenres) {
        if (overlapScore >= 0.6) {
            return "Dance-floor besties";
        }
        if (overlapScore >= 0.35) {
            return "Indie movie montage crew";
        }
        if (overlapScore >= 0.2) {
            return "Late-night study session buddies";
        }
        if (sharedGenres.contains("sad girl")) {
            return "Group cry potential unlocked";
        }
        return "Chaos playlist incoming";
    }

    private String buildNarrative(List<GroupMemberProfile> members, Set<String> sharedArtists,
                                  Set<String> sharedGenres, double overlapScore) {
        String memberNames = members.stream().map(GroupMemberProfile::getDisplayName)
                .collect(Collectors.joining(", "));
        String artists = sharedArtists.isEmpty() ? "no artists in common" : String.join(", ", sharedArtists);
        String genres = sharedGenres.isEmpty() ? "no shared genres" : String.join(", ", sharedGenres);

        return "%s share %.0f%% of their core vibe. They overlap on %s and lean into %s."
                .formatted(memberNames, overlapScore * 100, artists, genres);
    }
}