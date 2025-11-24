package interface_adapter.get_top_songs;

import use_case.get_top_songs.ActionType;
import use_case.get_top_songs.GetTopSongInputBoundary;
import use_case.get_top_songs.GetTopSongsInputData;

public class GetTopSongsController {
    private final GetTopSongInputBoundary GetTopSongSongsUseCaseInteractor;

    public GetTopSongsController(GetTopSongInputBoundary GetTopSongSongsUseCaseInteractor){
        this.GetTopSongSongsUseCaseInteractor = GetTopSongSongsUseCaseInteractor;
    }

    public void onButtonClicked(String actionName){
        ActionType actionType = ActionType.valueOf(actionName);
        GetTopSongsInputData getTopSongsInputData = new GetTopSongsInputData(actionType);
        GetTopSongSongsUseCaseInteractor.execute(getTopSongsInputData);
    }
}
