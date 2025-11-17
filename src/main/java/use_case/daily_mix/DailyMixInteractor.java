package use_case.daily_mix;

import java.util.*;

/**
 * The Daily Mix Interactor.
 */
public class DailyMixInteractor implements DailyMixInputBoundary {

    private static final String PLAYLIST_NAME = "Daily Mix";
    private static final int TARGET_SIZE = 10;

    private final DailyMixUserDataAccessInterface userDataAccess;
    private final DailyMixOutputBoundary dailyMixPresenter;
    private final Random random;

    public DailyMixInteractor(DailyMixUserDataAccessInterface userDataAccess,
                              DailyMixOutputBoundary dailyMixPresenter) {
        this(userDataAccess, dailyMixPresenter, new Random());
    }

    // extra constructor for tests (can inject a fixed Random)
    public DailyMixInteractor(DailyMixUserDataAccessInterface userDataAccess,
                              DailyMixOutputBoundary dailyMixPresenter,
                              Random random) {
        this.userDataAccess = userDataAccess;
        this.dailyMixPresenter = dailyMixPresenter;
        this.random = random;
    }

    @Override
    public void execute(DailyMixInputData inputData) {
        String username = inputData.getUsername();

        Map<String, Integer> library = userDataAccess.getLibraryWithFrequencies(username);

        if (library == null || library.isEmpty()) {
            dailyMixPresenter.prepareFailView(
                    "User \"" + username + "\" has no songs in their library.");
            return;
        }

        List<String> allTracks = new ArrayList<>(library.keySet());

        // previous mix for simple "cooldown"
        List<String> previousMix = userDataAccess.getPreviousDailyMix(username);
        Set<String> previousSet = new HashSet<>(previousMix);

        // candidates that are not in the previous mix
        List<String> candidates = new ArrayList<>();
        for (String trackId : allTracks) {
            if (!previousSet.contains(trackId)) {
                candidates.add(trackId);
            }
        }

        // less than 10 songs if cooldown，use all
        if (candidates.size() < TARGET_SIZE) {
            candidates = allTracks;
        }

        int playlistSize = Math.min(TARGET_SIZE, candidates.size());
        List<String> chosen = weightedSampleWithoutReplacement(candidates, library, playlistSize);

        String message = "";
        if (allTracks.size() < TARGET_SIZE) {
            message = "Your library has fewer than 10 songs; using all available songs.";
        }

        // save / replace playlist
        userDataAccess.saveDailyMixPlaylist(username, PLAYLIST_NAME, chosen);

        DailyMixOutputData outputData =
                new DailyMixOutputData(PLAYLIST_NAME, chosen, message);

        dailyMixPresenter.prepareSuccessView(outputData);
    }

    /**
     * Weighted random sampling without replacement.
     * Weight = play count (minimum 1).
     */
    private List<String> weightedSampleWithoutReplacement(List<String> candidates,
                                                          Map<String, Integer> weights,
                                                          int k) {
        List<String> result = new ArrayList<>();
        Map<String, Double> remainingWeights = new HashMap<>();

        for (String id : candidates) {
            int w = Math.max(1, weights.getOrDefault(id, 1));
            remainingWeights.put(id, (double) w);
        }

        for (int i = 0; i < k && !remainingWeights.isEmpty(); i++) {
            double total = 0.0;
            for (double w : remainingWeights.values()) {
                total += w;
            }

            double r = random.nextDouble() * total;
            String chosen = null;

            for (String id : new ArrayList<>(remainingWeights.keySet())) {
                double w = remainingWeights.get(id);
                if (r <= w) {
                    chosen = id;
                    break;
                }
                r -= w;
            }

            if (chosen == null) {
                // fallback: just pick any
                chosen = remainingWeights.keySet().iterator().next();
            }

            result.add(chosen);
            remainingWeights.remove(chosen); // no duplicates
        }

        return result;
    }
}

