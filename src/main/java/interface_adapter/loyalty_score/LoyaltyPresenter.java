package interface_adapter.loyalty_score;


import interface_adapter.ViewManagerModel;
import use_case.loyalty_score.LoyaltyScoreOutputBoundary;
import use_case.loyalty_score.LoyaltyScoreOutputData;

public class LoyaltyPresenter implements LoyaltyScoreOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final LoyaltyViewModel loyaltyViewModel;
    private final String previousViewName;

    public LoyaltyPresenter(ViewManagerModel viewManagerModel,
                           LoyaltyViewModel loyaltyViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loyaltyViewModel = loyaltyViewModel;
        this.previousViewName = viewManagerModel.getState();
    }

    @Override
    public void prepareView(LoyaltyScoreOutputData outputData) {
        // Switch to loyaltyView
        this.viewManagerModel.setState(loyaltyViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();
    }

    @Override
    public void returnPreviousView() {
        this.viewManagerModel.setState(previousViewName);
        this.viewManagerModel.firePropertyChange();
    }
}
