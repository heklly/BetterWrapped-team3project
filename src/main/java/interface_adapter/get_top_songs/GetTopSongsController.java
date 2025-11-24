package interface_adapter.get_top_songs;

import entity.SpotifyUser;
import use_case.get_top_songs.*;

public class GetTopSongsController {
    private final GetTopSongInputBoundary GetTopSongSongsUseCaseInteractor;

    public GetTopSongsController(GetTopSongInputBoundary GetTopSongSongsUseCaseInteractor){
        this.GetTopSongSongsUseCaseInteractor = GetTopSongSongsUseCaseInteractor;
    }

    public void execute(SpotifyUser spotifyUser, TopItem topItem, TimeType timeType){
        GetTopSongsInputData inputData = new GetTopSongsInputDataBuilder()
                .setTopItem(topItem)
                .setTimeType(timeType)
                .setSpotifyUser(spotifyUser)
                .createGetTopSongsInputData();
        GetTopSongSongsUseCaseInteractor.execute(inputData);
    }
}
