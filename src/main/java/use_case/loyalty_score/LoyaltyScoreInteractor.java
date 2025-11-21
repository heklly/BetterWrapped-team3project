package use_case.loyalty_score;

import interface_adapter.loyalty_score.SpotifyGateway;

public class LoyaltyScoreInteractor implements LoyaltyScoreInputBoundary {

    private final LoyaltyScoringService scoringService;
    private final LoyaltyScoreDataAccessInterface loyaltyDataAccessObject;
    private final LoyaltyScoreOutputBoundary loyaltyPresenter;
    private final SpotifyGatewayInterface spotifyGateway;

    public LoyaltyScoreInteractor(LoyaltyScoringService scoringService,
                                  LoyaltyScoreDataAccessInterface loyaltyScoreDataAccessInterface,
                                  LoyaltyScoreOutputBoundary loyaltyScoreOutputBoundary,
                                  SpotifyGatewayInterface spotifyGateway) {
        this.scoringService = scoringService;
        this.loyaltyDataAccessObject = loyaltyScoreDataAccessInterface;
        this.loyaltyPresenter = loyaltyScoreOutputBoundary;
        this.spotifyGateway = spotifyGateway;
    }

    @Override
    public void execute(LoyaltyScoreInputData loyaltyScoreInputData) {

        //TODO: Finish

    }

}
