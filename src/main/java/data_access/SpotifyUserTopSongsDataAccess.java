package data_access;

import use_case.get_top_songs.ActionType;
import use_case.get_top_songs.GetTopSongsOutputData;
import use_case.get_top_songs.GetTopSongsUserDataAccessInterface;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SpotifyUserTopSongsDataAccess implements GetTopSongsUserDataAccessInterface {
    private final String accessToken;
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public SpotifyUserTopSongsDataAccess(String accessToken) {
        this.accessToken = accessToken;
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    private String getEndpoint(ActionType type) {
        switch (type) {
            case IN_1_MONTH, IN_6_MONTH,  IN_1_YEAR:
                return "https://api.spotify.com/v1/me/top/tracks";
            default:
                throw new IllegalArgumentException("invalid action type" + type);
        }
    }

    private String getResultType(ActionType type) {
        switch (type) {
            case IN_1_MONTH,  IN_6_MONTH,  IN_1_YEAR: return "tracks";
            default: return "unknown";
        }
    }

    @Override
    public GetTopSongsOutputData fetchSpotifyResult(ActionType type) throws IOException {
        String url = getEndpoint(type);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                throw new IOException("Spotify API error:" + response.code() + "-" + response.message());
            }

            String responseBody = response.body().string();
            JsonNode payload = objectMapper.readTree(responseBody);
            String resultType = getResultType(type);

            return new GetTopSongsOutputData(resultType, payload);
        }
    }
}
