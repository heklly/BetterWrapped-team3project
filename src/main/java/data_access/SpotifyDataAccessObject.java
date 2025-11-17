package data_access;

import entity.ArtistLoyaltyScore;
import entity.SpotifyUser;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import util.ConfigManager;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

public class SpotifyDataAccessObject {
    private final SpotifyApi spotifyApi;
    private final String clientId;
    private final String redirectUri;

    public SpotifyDataAccessObject() {
        this.clientId = ConfigManager.getProperty("spotify.client.id");
        String clientSecret = ConfigManager.getProperty("spotify.client.secret");
        this.redirectUri = ConfigManager.getProperty("spotify.redirect.uri");

        if ("default_client_id".equals(this.clientId) || "default_client_secret".equals(clientSecret)) {
            System.err.println("ERROR: Please set SPOTIFY_CLIENT_ID and SPOTIFY_CLIENT_SECRET environment variables");
            System.err.println("Get them from: https://developer.spotify.com/dashboard");
        }

        this.spotifyApi = new SpotifyApi.Builder()
                .setClientId(this.clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(URI.create(this.redirectUri))
                .build();
    }

    public String getAuthorizationUrl() {
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                .scope("user-library-read,user-read-recently-played,user-top-read")
                .build();
        return authorizationCodeUriRequest.execute().toString();
    }

    public SpotifyUser exchangeCodeForTokens(String authorizationCode, String username) {
        try {
            AuthorizationCodeRequest request = spotifyApi.authorizationCode(authorizationCode).build();
            var credentials = request.execute();

            spotifyApi.setAccessToken(credentials.getAccessToken());
            spotifyApi.setRefreshToken(credentials.getRefreshToken());

            var userProfile = spotifyApi.getCurrentUsersProfile().build().execute();

            return new SpotifyUser(
                    username,
                    credentials.getAccessToken(),
                    credentials.getRefreshToken(),
                    userProfile.getId()
            );

        } catch (Exception e) {
            throw new RuntimeException("Failed to exchange authorization code", e);
        }
    }

    public SpotifyApi getSpotifyApiForUser(SpotifyUser user) {
        return new SpotifyApi.Builder()
                .setAccessToken(user.getAccessToken())
                .setRefreshToken(user.getRefreshToken())
                .setClientId(clientId)
                .build();
    }

    /**
     * Calculate artist loyalty scores for a user
     * @param user The Spotify user
     * @return List of artist loyalty scores, sorted by score descending
     */
    public List<ArtistLoyaltyScore> getArtistLoyaltyScores(SpotifyUser user) {
        try {
            SpotifyApi userApi = getSpotifyApiForUser(user);

            // Get saved tracks
            Map<String, Integer> artistTrackCount = new HashMap<>();
            Map<String, String> artistNames = new HashMap<>();

            Paging<SavedTrack> savedTracks = userApi.getUsersSavedTracks().limit(50).build().execute();
            for (SavedTrack savedTrack : savedTracks.getItems()) {
                Track track = savedTrack.getTrack();
                for (ArtistSimplified artist : track.getArtists()) {
                    String artistId = artist.getId();
                    artistTrackCount.put(artistId, artistTrackCount.getOrDefault(artistId, 0) + 1);
                    artistNames.put(artistId, artist.getName());
                }
            }

            // Get saved albums
            Map<String, Integer> artistAlbumCount = new HashMap<>();
            Paging<SavedAlbum> savedAlbums = userApi.getCurrentUsersSavedAlbums().limit(50).build().execute();
            for (SavedAlbum savedAlbum : savedAlbums.getItems()) {
                Album album = savedAlbum.getAlbum();
                for (ArtistSimplified artist : album.getArtists()) {
                    String artistId = artist.getId();
                    artistAlbumCount.put(artistId, artistAlbumCount.getOrDefault(artistId, 0) + 1);
                    artistNames.putIfAbsent(artistId, artist.getName());
                }
            }

            // Get recently played tracks
            Set<String> recentlyPlayedArtists = new HashSet<>();
            PagingCursorbased<PlayHistory> recentlyPlayed = userApi.getCurrentUsersRecentlyPlayedTracks()
                    .limit(50).build().execute();
            for (PlayHistory playHistory : recentlyPlayed.getItems()) {
                Track track = playHistory.getTrack();
                for (ArtistSimplified artist : track.getArtists()) {
                    recentlyPlayedArtists.add(artist.getId());
                    artistNames.putIfAbsent(artist.getId(), artist.getName());
                }
            }

            // Combine all artists
            Set<String> allArtists = new HashSet<>();
            allArtists.addAll(artistTrackCount.keySet());
            allArtists.addAll(artistAlbumCount.keySet());
            allArtists.addAll(recentlyPlayedArtists);

            // Create loyalty scores
            List<ArtistLoyaltyScore> loyaltyScores = new ArrayList<>();
            for (String artistId : allArtists) {
                String artistName = artistNames.get(artistId);
                int trackCount = artistTrackCount.getOrDefault(artistId, 0);
                int albumCount = artistAlbumCount.getOrDefault(artistId, 0);
                boolean inRecent = recentlyPlayedArtists.contains(artistId);

                ArtistLoyaltyScore score = new ArtistLoyaltyScore(
                        artistName, artistId, trackCount, albumCount, inRecent
                );
                loyaltyScores.add(score);
            }

            // Sort by loyalty score descending
            loyaltyScores.sort((a, b) -> Double.compare(b.getLoyaltyScore(), a.getLoyaltyScore()));

            return loyaltyScores;

        } catch (Exception e) {
            throw new RuntimeException("Failed to get artist loyalty scores", e);
        }
    }
}
