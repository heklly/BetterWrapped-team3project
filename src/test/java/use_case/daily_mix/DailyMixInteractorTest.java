package use_case.daily_mix;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DailyMixInteractorTest {

    /**
     * Simple in-memory implementation of DailyMixUserDataAccessInterface
     * used only for tests.
     */
    private static class InMemoryDailyMixDataAccessObject
            implements DailyMixUserDataAccessInterface {

        Map<String, Integer> library = new HashMap<>();
        List<String> previousDailyMix = new ArrayList<>();

        String savedUsername;
        String savedPlaylistName;
        List<String> savedPlaylistTracks = new ArrayList<>();

        @Override
        public Map<String, Integer> getLibraryWithFrequencies(String username) {
            return library;
        }

        @Override
        public void saveDailyMixPlaylist(String username,
                                         String playlistName,
                                         List<String> trackIds) {
            this.savedUsername = username;
            this.savedPlaylistName = playlistName;
            this.savedPlaylistTracks = new ArrayList<>(trackIds);
        }

        @Override
        public List<String> getPreviousDailyMix(String username) {
            return previousDailyMix;
        }
    }

    @Test
    void successGeneratesTenSongs() {
        DailyMixInputData inputData = new DailyMixInputData("Paul");
        InMemoryDailyMixDataAccessObject userRepository =
                new InMemoryDailyMixDataAccessObject();

        // more than 10 songs
        for (int i = 1; i <= 15; i++) {
            userRepository.library.put("track-" + i, i); // play count = i
        }

        DailyMixOutputBoundary successPresenter = new DailyMixOutputBoundary() {
            @Override
            public void prepareSuccessView(DailyMixOutputData outputData) {
                assertEquals("Daily Mix", outputData.getPlaylistName());

                // generate 10 songs
                assertEquals(10, outputData.getTrackIds().size());

                // with no repeat
                assertEquals(10,
                        new HashSet<>(outputData.getTrackIds()).size());

                // output message
                assertEquals("", outputData.getMessage());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        // Set Seed
        DailyMixInputBoundary interactor =
                new DailyMixInteractor(userRepository, successPresenter, new Random(42));

        interactor.execute(inputData);

        // check playlist being saved
        assertEquals("Paul", userRepository.savedUsername);
        assertEquals("Daily Mix", userRepository.savedPlaylistName);
        assertEquals(10, userRepository.savedPlaylistTracks.size());
    }

    @Test
    void successLibraryLessThanTenSongsUsesAll() {
        DailyMixInputData inputData = new DailyMixInputData("Paul");
        InMemoryDailyMixDataAccessObject userRepository =
                new InMemoryDailyMixDataAccessObject();

        // 7 songs in total
        for (int i = 1; i <= 7; i++) {
            userRepository.library.put("track-" + i, 5);
        }

        DailyMixOutputBoundary successPresenter = new DailyMixOutputBoundary() {
            @Override
            public void prepareSuccessView(DailyMixOutputData outputData) {
                // take all
                assertEquals(7, outputData.getTrackIds().size());

                // output message
                assertEquals(
                        "Your library has fewer than 10 songs; using all available songs.",
                        outputData.getMessage()
                );
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        DailyMixInputBoundary interactor =
                new DailyMixInteractor(userRepository, successPresenter, new Random(42));

        interactor.execute(inputData);

        assertEquals(7, userRepository.savedPlaylistTracks.size());
    }

    @Test
    void failureWhenLibraryIsEmpty() {
        DailyMixInputData inputData = new DailyMixInputData("Paul");
        InMemoryDailyMixDataAccessObject userRepository =
                new InMemoryDailyMixDataAccessObject();
        // library set as empty

        DailyMixOutputBoundary failurePresenter = new DailyMixOutputBoundary() {
            @Override
            public void prepareSuccessView(DailyMixOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals(
                        "User \"Paul\" has no songs in their library.",
                        errorMessage
                );
            }
        };

        DailyMixInputBoundary interactor =
                new DailyMixInteractor(userRepository, failurePresenter, new Random(42));

        interactor.execute(inputData);

        // no playlist saved
        assertNull(userRepository.savedPlaylistName);
        assertTrue(userRepository.savedPlaylistTracks.isEmpty());
    }
}

