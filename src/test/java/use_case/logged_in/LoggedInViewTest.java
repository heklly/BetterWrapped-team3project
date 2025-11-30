package use_case.logged_in;

import view.LoggedInView;

import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.spotify_auth.SpotifyAuthViewModel;
import interface_adapter.daily_mix.DailyMixViewModel;
import interface_adapter.get_topItems.GetTopItemsViewModel;
import interface_adapter.ViewManagerModel;

import javax.swing.*;

public class LoggedInViewTest {

    public static void main(String[] args) {

        // --- Mock/dupe ViewModels so GUI can render ---
        LoggedInViewModel loggedInVM = new LoggedInViewModel();
        loggedInVM.setState(new LoggedInState());

        DailyMixViewModel dailyMixVM = new DailyMixViewModel();
        GetTopItemsViewModel topItemsVM = new GetTopItemsViewModel();

        SpotifyAuthViewModel spotifyAuthVM = new SpotifyAuthViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();

        // --- Build the view ---
        LoggedInView view = new LoggedInView(
                loggedInVM,
                viewManagerModel,
                spotifyAuthVM,
                dailyMixVM,
                topItemsVM
        );

        // --- Display it in a real window ---
        JFrame frame = new JFrame("LoggedInView Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setContentPane(view);
        frame.setVisible(true);
    }
}
