package use_case.Group;

import spotify.GroupAnalyticsResult;
import spotify.GroupMemberProfile;
import use_case.group_analytics.GroupAnalyticsService;

import java.util.List;

/**
 * Controller facade so the UI can trigger a quick group analytics demo.
 */
public class GroupAnalyticsController {
    private final GroupAnalyticsService service;

    public GroupAnalyticsController(GroupAnalyticsService service) {
        this.service = service;
    }

    /**
     * Runs the canned demo data through the analyzer.
     */
    public GroupAnalyticsResult runDemoAnalysis() {
        List<GroupMemberProfile> profiles = service.buildDemoProfiles();
        return service.analyzeGroup(profiles);
    }
}
