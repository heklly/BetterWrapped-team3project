package use_case.get_top_songs;

public interface GetTopSongsOutputBoundary {
    void prepareSuccessView(GetTopSongsOutputData  outputData);
    void prepareFailureView(String errorMessage);
}
