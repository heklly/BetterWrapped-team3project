package use_case.loyaltyscore;

import data_access.LoyaltyScoreDataAccessObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class LoyaltyScoreDataAccessTest {

    private LoyaltyScoreDataAccessObject dataAccess;
    private final String userId = "520";
    private final String testFilePath = "loyaltyscore_520.json";

    @BeforeEach
    public void setup() {
        // Set up the data access object before each test
        dataAccess = new LoyaltyScoreDataAccessObject();

        // Ensure the file is clean before each test
        File testFile = new File(testFilePath);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testSaveLoyaltyFirstTime() {
        // Arrange
        String date = "231123";
        String artist = "Artist1";
        int score = 85;

        // Act: Save the loyalty score for the first time
        dataAccess.saveLoyalty(userId, date, artist, score);

        // Assert: Check if the file now exists and the score is saved
        File testFile = new File(testFilePath);
        assertTrue(testFile.exists(), "Test file should be created.");

        // Check that the loyalty score is correct
        Map<String, Integer> loyaltyScores = dataAccess.getLoyaltyDate(userId, date);
        assertEquals(1, loyaltyScores.size(), "Should have 1 artist score.");
        assertEquals(score, loyaltyScores.get(artist), "The score for the artist should match.");
    }

    @Test
    public void testGetLoyaltyDate() {
        // Arrange: Save loyalty scores for a given date
        dataAccess.saveLoyalty(userId, "231123", "Artist1", 85);
        dataAccess.saveLoyalty(userId, "231123", "Artist2", 90);

        // Act: Retrieve the loyalty scores for that date
        Map<String, Integer> loyaltyScores = dataAccess.getLoyaltyDate(userId, "231123");

        // Assert: Check if the correct scores are returned
        assertEquals(2, loyaltyScores.size(), "Should have 2 artist scores.");
        assertEquals(85, loyaltyScores.get("Artist1"), "The score for Artist1 should be 85.");
        assertEquals(90, loyaltyScores.get("Artist2"), "The score for Artist2 should be 90.");
    }

    @Test
    public void testLoyaltyScoreExists() {
        // Arrange: Save a loyalty score for a specific artist
        dataAccess.saveLoyalty(userId, "231123", "Artist1", 85);

        // Act: Check if the loyalty score exists for that artist and date
        boolean exists = dataAccess.loyaltyScoreExists(userId, "231123", "Artist1");
        boolean notExists = dataAccess.loyaltyScoreExists(userId, "231123", "Artist2");

        // Assert: Verify that the score exists for Artist1, but not for Artist2
        assertTrue(exists, "Loyalty score should exist for Artist1.");
        assertFalse(notExists, "Loyalty score should not exist for Artist2.");
    }

    @Test
    public void testSaveLoyaltyUpdateScore() {
        // Arrange: Save an initial loyalty score for an artist
        dataAccess.saveLoyalty(userId, "231123", "Artist1", 85);

        // Act: Save a new score for the same artist
        dataAccess.saveLoyalty(userId, "231123", "Artist1", 90);

        // Assert: Check if the updated score is saved correctly
        Map<String, Integer> loyaltyScores = dataAccess.getLoyaltyDate(userId, "231123");
        assertEquals(1, loyaltyScores.size(), "Should have 1 artist score.");
        assertEquals(90, loyaltyScores.get("Artist1"), "The updated score for Artist1 should be 90.");
    }

    @Test
    public void testFileDoesNotExist() {
        // Arrange: Ensure the file doesn't exist
        File testFile = new File(testFilePath);
        if (testFile.exists()) {
            testFile.delete();
        }

        // Act: Try to get loyalty data when the file doesn't exist
        Map<String, Integer> loyaltyScores = dataAccess.getLoyaltyDate(userId, "231123");

        // Assert: The result should be an empty map
        assertTrue(loyaltyScores.isEmpty(), "There should be no loyalty scores when the file does not exist.");
    }

    @Test
    public void testEmptyFile() throws IOException {
        // Arrange: Manually create an empty JSON file
        File testFile = new File(testFilePath);
        testFile.createNewFile();

        // Act: Try to get loyalty data from an empty file
        Map<String, Integer> loyaltyScores = dataAccess.getLoyaltyDate(userId, "231123");

        // Assert: The result should be an empty map since the file is empty
        assertTrue(loyaltyScores.isEmpty(), "There should be no loyalty scores in the empty file.");
    }
}
