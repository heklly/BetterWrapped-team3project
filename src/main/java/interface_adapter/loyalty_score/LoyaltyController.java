package interface_adapter.loyalty_score;

import entity.SpotifyUser;
import entity.User;
import use_case.logout.LogoutInputBoundary;
import use_case.loyalty_score.LoyaltyScoreInputBoundary;
import use_case.loyalty_score.LoyaltyScoreInputData;

public class LoyaltyController {

    private final LoyaltyScoreInputBoundary loyaltyScoreInteractor;

    public LoyaltyController(LoyaltyScoreInputBoundary loyaltyScoreInteractor) {
        this.loyaltyScoreInteractor = loyaltyScoreInteractor;
    }

    /**
     * Executes the Logout Use Case.
     */
    public void execute(User user, SpotifyUser spotifyUser, String artistName) {
        LoyaltyScoreInputData inputData = new LoyaltyScoreInputData(user, spotifyUser, artistName);
        loyaltyScoreInteractor.execute(inputData);
    }
}
