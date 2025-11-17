package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        String clientId = System.getenv("SPOTIFY_CLIENT_ID");
        String clientSecret = System.getenv("SPOTIFY_CLIENT_SECRET");

        if (clientId == null || clientSecret == null) {
            System.out.println("Please set SPOTIFY_CLIENT_ID and SPOTIFY_CLIENT_SECRET environment variables");
            System.out.println("You can get these from: https://developer.spotify.com/dashboard");
            System.out.println("Running without Spotify integration...");
        }

        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addSignupView()
                .addLoginView()
                .addSpotifyAuthView()
                .addLoggedInView()
                .addSignupUseCase()
                .addLoginUseCase()
                .addChangePasswordUseCase()
                .addLogoutUseCase()
                .addSpotifyAuthUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}