package use_case.group_analytics;

import org.junit.jupiter.api.Test;
import spotify.GroupMemberProfile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupAnalyticsServiceTest {

    private final use_case.group_analytics.GroupAnalyticsService service = new use_case.group_analytics.GroupAnalyticsService();

    @Test
    void analyzeGroupReturnsNarrativeForDemoProfiles() {
        var profiles = service.buildDemoProfiles();

        var result = service.analyzeGroup(profiles);

        assertEquals(0.0, result.getOverlapScore());
        assertEquals("Chaos playlist incoming", result.getVibeTagline());
        assertTrue(result.getSharedArtists().isEmpty());
        assertTrue(result.getSharedGenres().isEmpty());
        assertTrue(result.getNarrative().toLowerCase().contains("share 0%"));
    }

    @Test
    void analyzeGroupRejectsGroupsLargerThanFive() {
        List<GroupMemberProfile> profiles = new ArrayList<>(service.buildDemoProfiles());
        profiles.add(new GroupMemberProfile(
                "Extra",
                List.of("Artist X"),
                List.of("Song X"),
                List.of("genre x")));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.analyzeGroup(profiles));

        assertTrue(exception.getMessage().contains("capped"));
    }

    @Test
    void analyzeGroupDetectsOverlapAcrossArtistsTracksAndGenres() {
        List<GroupMemberProfile> profiles = List.of(
                new GroupMemberProfile("One", List.of("A", "B"), List.of("T1", "T2"), List.of("pop", "dance")),
                new GroupMemberProfile("Two", List.of("A", "C"), List.of("T1", "T3"), List.of("pop", "indie")),
                new GroupMemberProfile("Three", List.of("A", "D"), List.of("T1", "T4"), List.of("pop", "dance"))
        );

        var result = service.analyzeGroup(profiles);

        assertEquals(0.5, result.getOverlapScore());
        assertEquals("Indie movie montage crew", result.getVibeTagline());
        assertEquals(List.of("a"), result.getSharedArtists());
        assertEquals(List.of("pop"), result.getSharedGenres());
    }
}