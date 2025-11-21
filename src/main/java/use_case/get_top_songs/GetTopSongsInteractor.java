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
            GetTopSongsOutputData output = dataAccess.fetchSpotifyResult(input.getActionType());
            presenter.present(output);
        } catch (Exception e){
            presenter.present(new GetTopSongsOutputData("error", e.getMessage()));
        }
    }
}
