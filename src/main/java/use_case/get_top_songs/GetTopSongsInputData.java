package use_case.get_top_songs;

import entity.SpotifyUser;

public class GetTopSongsInputData {
    private final TopItem topItem;
    private final TimeType timeType;
    private final SpotifyUser spotifyUser;

    public GetTopSongsInputData(TopItem topItem, TimeType timeType, SpotifyUser spotifyUser) {
        this.timeType = timeType;
        this.topItem = topItem;
        this.spotifyUser = spotifyUser;
    }

    public TopItem getTopItem() {
        return this.topItem;
    }
    public TimeType getTimeType() {
        return this.timeType;
    }
    public SpotifyUser getSpotifyUser() {
        return this.spotifyUser;
    }
}
