package interface_adapter.get_top_songs;

public class GetTopSongsState {
    private String resultType = "";
    private Object payload = null;

    public void setResultType(String resultType) {this.resultType = resultType;}
    public void setPayload(Object payload) {this.payload = payload;}

    public String getResultType() {return resultType;}
    public Object getPayload() {return payload;}
}
