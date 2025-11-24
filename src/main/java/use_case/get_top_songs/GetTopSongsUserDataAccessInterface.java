package use_case.get_top_songs;

public interface GetTopSongsUserDataAccessInterface {
    GetTopSongsOutputData fetchSpotifyResult(GetTopSongsInputData inputData) throws Exception;
}
