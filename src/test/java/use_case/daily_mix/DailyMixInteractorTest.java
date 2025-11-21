package use_case.daily_mix;

import entity.Playlist;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DailyMixInteractorTest {

    /**
     * Simple in-memory implementation of DailyMixUserDataAccessInterface used for tests.
     */
    private static class InMemoryDailyMixDataAccessObject
            implements DailyMixUserDataAccessInterface {

        Map<String, Integer> library = new HashMap<>();
        Playlist previousPlaylist = null;
        Playlist savedPlaylist = null;

        @Override
        public Map<String, Integer> getLibraryWithFrequencies(int userId) {
            return library;
        }

        @Override
        public Playlist saveDailyMixPlaylist(int userId, String playlistName, List<String> trackIds) {
            Playlist p = new Playlist(playlistName, "2025-01-01");
            for (String id : trackIds) {
                p.addSong(id);
            }
            this.savedPlaylist = p;
            this.previousPlaylist = p; // so next time cooldown works
            return p;
        }

        @Override
        public Playlist getPreviousDailyMix(int userId) {
            return previousPlaylist;
        }
    }

    @Test
    void successGeneratesTenSongs() {
        DailyMixInputData inputData = new DailyMixInputData(1);
        InMemoryDailyMixDataAccessObject userRepository =
                new InMemoryDailyMixDataAccessObject();

        // prepare library with >10 songs
        for (int i = 1; i <= 15; i++) {
            userRepository.library.put("track-" + i, i); // play count = i
        }

        DailyMixOutputBoundary successPresenter = new DailyMixOutputBoundary() {
            @Override
            public void prepareSuccessView(DailyMixOutputData outputData) {
                Playlist playlist = outputData.getPlaylist();

                assertEquals("Daily Mix", playlist.getName());
                assertEquals(10, playlist.getSongs().size());
                assertEquals(10, new HashSet<>(playlist.getSongs()).size());
                assertEquals("", outputData.getMessage());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Unexpected fail.");
            }
        };

        DailyMixInputBoundary interactor =
                new DailyMixInteractor(userRepository, successPresenter, new Random(42));

        interactor.execute(inputData);

        assertNotNull(userRepository.savedPlaylist);
        assertEquals(10, userRepository.savedPlaylist.getSongs().size());
    }

    @Test
    void successLibraryLessThanTenSongsUsesAll() {
        DailyMixInputData inputData = new DailyMixInputData(1);
        InMemoryDailyMixDataAccessObject userRepository =
                new InMemoryDailyMixDataAccessObject();

        // only 7 songs
        for (int i = 1; i <= 7; i++) {
            userRepository.library.put("track-" + i, 5);
        }

        DailyMixOutputBoundary successPresenter = new DailyMixOutputBoundary() {
            @Override
            public void prepareSuccessView(DailyMixOutputData outputData) {
                Playlist playlist = outputData.getPlaylist();

                assertEquals(7, playlist.getSongs().size());
                assertEquals(
                        "Your library has fewer than 10 songs; using all available songs.",
                        outputData.getMessage()
                );
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Unexpected fail.");
            }
        };

        DailyMixInputBoundary interactor =
                new DailyMixInteractor(userRepository, successPresenter, new Random(42));

        interactor.execute(inputData);

        assertEquals(7, userRepository.savedPlaylist.getSongs().size());
    }

    @Test
    void failureWhenLibraryIsEmpty() {
        DailyMixInputData inputData = new DailyMixInputData(1);
        InMemoryDailyMixDataAccessObject userRepository =
                new InMemoryDailyMixDataAccessObject();

        DailyMixOutputBoundary failurePresenter = new DailyMixOutputBoundary() {
            @Override
            public void prepareSuccessView(DailyMixOutputData outputData) {
                fail("Unexpected success.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("User with id 1 has no songs in their library.", errorMessage);
            }
        };

        DailyMixInputBoundary interactor =
                new DailyMixInteractor(userRepository, failurePresenter, new Random(42));

        interactor.execute(inputData);

        assertNull(userRepository.savedPlaylist);
    }
}



