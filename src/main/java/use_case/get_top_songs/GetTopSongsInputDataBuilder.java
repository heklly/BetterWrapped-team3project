package use_case.get_top_songs;

import entity.SpotifyUser;

public class GetTopSongsInputDataBuilder {
    private TopItem topItem;
    private TimeType timeType;
    private SpotifyUser spotifyUser;

    public GetTopSongsInputDataBuilder setTopItem(TopItem topItem) {
        this.topItem = topItem;
        return this;
    }

    public GetTopSongsInputDataBuilder setTimeType(TimeType timeType) {
        this.timeType = timeType;
        return this;
    }

    public GetTopSongsInputDataBuilder setSpotifyUser(SpotifyUser spotifyUser) {
        this.spotifyUser = spotifyUser;
        return this;
    }

    public GetTopSongsInputData createGetTopSongsInputData() {
        return new GetTopSongsInputData(topItem, timeType, spotifyUser);
    }
}