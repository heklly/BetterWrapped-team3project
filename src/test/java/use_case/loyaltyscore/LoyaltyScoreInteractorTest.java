package use_case.loyaltyscore;

import data_access.InMemoryUserDataAccessObject;
import data_access.JSONParsers;
import data_access.LoyaltyScoreDataAccessObject;
import data_access.SpotifyDataAccessObject;
import entity.ArtistLoyaltyScore;
import entity.SpotifyUser;
import entity.User;
import entity.UserFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.logout.LogoutOutputData;
import use_case.loyalty_score.*;


import javax.print.DocFlavor;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class LoyaltyScoreInteractorTest {

    @Test
    void successTest() {

        String USERID = "1203";
        String DATE_1 = "2025-04-12";
        String DATE_2 = "2025-04-13";
        int SCORE_DATE_1 = 90;
        int SCORE_DATE_2 = 590;
        String ARTIST_1 = "Artist1";
        User USER = new User("kelly", USERID, "2023-10-12");
        SpotifyUser SPOTIFY_USER = new SpotifyUser("kellyh", "a",
                "t", "kh1203");
        LoyaltyScoreInputData INPUT_DATA = new LoyaltyScoreInputData(USER, SPOTIFY_USER, ARTIST_1);



        // Mock the SpotifyDataAccessObject
        SpotifyDataAccessObject mockSpotifyDAO = mock(SpotifyDataAccessObject.class);

        ArtistLoyaltyScore als1 = new ArtistLoyaltyScore("Artist1", "id1",
                2, 10, true);

        int ALS_current = als1.getLoyaltyScore();

        ArtistLoyaltyScore als2 = new ArtistLoyaltyScore("Artist2", "id2",
                3, 20, true);


        // Mock the return value of getArtistLoyaltyScores
        List<ArtistLoyaltyScore> mockLoyaltyScores = List.of(als1, als2);

        when(mockSpotifyDAO.getArtistLoyaltyScores(SPOTIFY_USER)).thenReturn(mockLoyaltyScores);

        // Mock the LoyaltyScoreDataAccessObject (this is for saving scores)
        LoyaltyScoreDataAccessObject dataAccessObject = new LoyaltyScoreDataAccessObject();



        // This creates a successPresenter that tests whether the test case is as we expect.
        LoyaltyScoreOutputBoundary successPresenter = new LoyaltyScoreOutputBoundary() {
            @Override
            public void prepareView(LoyaltyScoreOutputData outputData) {
                assertEquals(SCORE_DATE_1, outputData.getScores().get(DATE_1));
                assertEquals(SCORE_DATE_2, outputData.getScores().get(DATE_2));
                assertEquals(3, outputData.getScores().size());
                assertEquals(ALS_current, outputData.getScores().get(LocalDate.now().toString()));
            }
        };

        // Create the interactor with the mock Spotify DAO and the mock data access object
        LoyaltyScoreInputBoundary interactor = new LoyaltyScoreInteractor(dataAccessObject, successPresenter, mockSpotifyDAO);

        dataAccessObject.saveLoyalty(USERID, DATE_1, ARTIST_1, SCORE_DATE_1);
        dataAccessObject.saveLoyalty(USERID, DATE_2, ARTIST_1, SCORE_DATE_2);

        // Execute the interactor (this will trigger the mock SpotifyDAO)
        interactor.execute(INPUT_DATA);

        // Verify interactions with the mock objects
        verify(mockSpotifyDAO).getArtistLoyaltyScores(SPOTIFY_USER);
    }
}
