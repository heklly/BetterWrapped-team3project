package data_access;

import data_access.SpotifyDataAccessObject;
// import entity.TopItem;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;
import use_case.get_top_songs.TopItem;
import se.michaelthelin.spotify.SpotifyApi;
import use_case.get_top_songs.GetTopSongsInputData;
import use_case.get_top_songs.GetTopSongsOutputData;
import use_case.get_top_songs.GetTopSongsUserDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class TopItemDataAccessObject
        extends SpotifyDataAccessObject
        implements GetTopSongsUserDataAccessInterface{
    @Override
    public GetTopSongsOutputData fetchSpotifyResult(GetTopSongsInputData inputData) throws Exception {

        List<String> topItems;
        try {
            SpotifyApi userApi = getSpotifyApiForUser(inputData.getSpotifyUser());
            topItems = new ArrayList<>();

            // case for topItem = tracks
            if (inputData.getTopItem().equals(TopItem.tracks)) {
                GetUsersTopTracksRequest response = userApi.getUsersTopTracks()
                        .limit(10)
                        .time_range(inputData.getTimeType().toString())
                        .offset(0)
                        .build();

                Paging<Track> topTracks = response.execute();
                for (Track track : topTracks.getItems()) {
                    topItems.add(track.getName());
                }
            }

            // case for topItem = artists
            if (inputData.getTopItem().equals(TopItem.artists)) {
                GetUsersTopArtistsRequest response = userApi.getUsersTopArtists()
                        .limit(10)
                        .offset(0)
                        .time_range(inputData.getTimeType().toString())
                        .build();

                Paging<Artist> topArtists = response.execute();
                for (Artist artist : topArtists.getItems()) {
                    topItems.add(artist.getName());
                }
            }

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return new GetTopSongsOutputData(topItems);
    }
}
