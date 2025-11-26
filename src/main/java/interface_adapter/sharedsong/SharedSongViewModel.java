package interface_adapter.sharedsong;

import interface_adapter.ViewModel;

public class SharedSongViewModel extends ViewModel<SharedSongState> {

    public SharedSongViewModel() {
        super("shared song");
        setState(new SharedSongState());
    }
}
