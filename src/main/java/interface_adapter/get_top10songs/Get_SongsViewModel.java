package interface_adapter.get_top10songs;

import interface_adapter.ViewModel;

public class Get_SongsViewModel extends ViewModel<Get_SongsViewModel> {

    public Get_SongsViewModel() {
        super("get songs");
        setState(new Get_SongsViewModel());
    }
}
