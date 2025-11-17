package use_case.loyalty_score;

public class LoyaltyScoreInteractor implements LoyaltyScoreInputBoundary {

    private final LoyaltyScoringService scoringService;
    private final LoyaltyScoreDataAccessInterface loyaltyDataAccessObject;
    private final LoyaltyScoreOutputBoundary loyaltyPresenter;

    public LoyaltyScoreInteractor(LoyaltyScoringService scoringService,
                                  LoyaltyScoreDataAccessInterface loyaltyScoreDataAccessInterface,
                                  LoyaltyScoreOutputBoundary loyaltyScoreOutputBoundary) {
        this.scoringService = scoringService;
        this.loyaltyDataAccessObject = loyaltyScoreDataAccessInterface;
        this.loyaltyPresenter = loyaltyScoreOutputBoundary;
    }

    @Override
    public void execute(LoyaltyScoreInputData loyaltyScoreInputData) {



    }

}
