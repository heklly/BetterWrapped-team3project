package use_case.get_top_songs;

public interface GetTopSongsUserDataAccessInterface {
    GetTopSongsOutputData fetchSpotifyResult(ActionType actionType) throws Exception;
}
