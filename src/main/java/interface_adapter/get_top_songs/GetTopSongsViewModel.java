package interface_adapter.get_top_songs;
import interface_adapter.ViewModel;

/**
 * The ViewModel for the GetTopSongs View.
 */

public class GetTopSongsViewModel extends ViewModel<GetTopSongsState> {

    public GetTopSongsViewModel() {
        super("topsongs");
        setState(new GetTopSongsState());
    }
}
