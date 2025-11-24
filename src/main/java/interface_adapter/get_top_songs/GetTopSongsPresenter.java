package interface_adapter.get_top_songs;

import interface_adapter.ViewManagerModel;
import use_case.get_top_songs.ActionType;
import use_case.get_top_songs.GetTopSongsOutputData;
import use_case.get_top_songs.GetTopSongsOutputBoundary;
import view.ViewManager;

public class GetTopSongsPresenter implements GetTopSongsOutputBoundary {

    private final GetTopSongsViewModel GetTopSongsViewModel;
    private final ViewManagerModel viewManagerModel;

    public GetTopSongsPresenter(ViewManagerModel viewManagerModel,
                                GetTopSongsViewModel GetTopSongsViewModel) {
        this.GetTopSongsViewModel = GetTopSongsViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(GetTopSongsOutputData response) {
        final GetTopSongsState getTopSongsState = GetTopSongsViewModel.getState();
        getTopSongsState.setResultType(response.getResultType());
        GetTopSongsViewModel.firePropertyChange();

        viewManagerModel.setState(GetTopSongsViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    public void prepareFailureView(String errorMessage) {
        final GetTopSongsState getTopSongsState = GetTopSongsViewModel.getState();
        getTopSongsState.setResultType(errorMessage);
        GetTopSongsViewModel.firePropertyChange();
    }
}
