package use_case.get_top_songs;

public class GetTopSongsInteractor implements GetTopSongInputBoundary{
    private final GetTopSongsOutputBoundary presenter;
    private final GetTopSongsUserDataAccessInterface dataAccess;

    public GetTopSongsInteractor(GetTopSongsOutputBoundary presenter,
                                 GetTopSongsUserDataAccessInterface dataAccess) {
        this.presenter = presenter;
        this.dataAccess = dataAccess;
    }

    @Override
    public void execute(GetTopSongsInputData input) {
        try{
            GetTopSongsOutputData outputData = dataAccess.fetchSpotifyResult(input);
            presenter.prepareSuccessView(outputData);
        } catch (Exception e){
            presenter.prepareFailureView("Failed to fetch spotify result"
                    + e.getMessage());
        }
    }
}
