package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        String clientId = System.getenv("SPOTIFY_CLIENT_ID");

        if (clientId == null) {
            System.out.println("Please set SPOTIFY_CLIENT_ID environment variable");
            System.out.println("You can get this from: https://developer.spotify.com/dashboard");
            System.out.println("Note: PKCE flow doesn't require a client secret!");
            System.out.println("Running without Spotify integration...");
        }

        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addViewModels()         // ✅ Create ALL view models FIRST
                .addSignupView()         // Now all views can reference any view model
                .addLoginView()
                .addLoggedInView()
                .addSpotifyAuthView()
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