package interface_adapter.get_top_songs;

import interface_adapter.ViewManagerModel;
import use_case.get_top_songs.GetTopSongsOutputData;
import use_case.get_top_songs.GetTopSongsOutputBoundary;

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
        getTopSongsState.setSuccess(true);
        getTopSongsState.setTopItems(response.getTopItems());

        GetTopSongsViewModel.setState(getTopSongsState);
        GetTopSongsViewModel.firePropertyChange();

    }

    public void prepareFailureView(String errorMessage) {
        final GetTopSongsState getTopSongsState = GetTopSongsViewModel.getState();
        getTopSongsState.setSuccess(false);
        getTopSongsState.setTopItems(null);

        GetTopSongsViewModel.setState(getTopSongsState);
        GetTopSongsViewModel.firePropertyChange();
    }
}
