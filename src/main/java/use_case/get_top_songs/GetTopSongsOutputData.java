package use_case.get_top_songs;

public class GetTopSongsOutputData {
    private final String resultType;
    private final Object payload;

    public GetTopSongsOutputData(String resultType, Object payload) {
        this.resultType = resultType;
        this.payload = payload;
    }

    public String getResultType() {return resultType;}
    public Object getPayload() {return payload;}
}
