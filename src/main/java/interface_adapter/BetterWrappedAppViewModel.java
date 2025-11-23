package interface_adapter;

import view.BetterWrappedAppView;



public class BetterWrappedAppViewModel<G, Y, P> extends ViewModel{

    private final String groupViewName;
    private final String youViewName;
    private final String profileViewName;

    public String getYouViewName() {
        return this.youViewName;
    }
    public String getGroupViewName() {
        return this.groupViewName;
    }
    public String getProfileViewName() {
        return this.profileViewName;
    }

    public T getState() {
        return this.state;
    }

    public void setState(T state) {
        this.state = state;
    }

    public BetterWrappedAppViewModel(String groupViewName, String youViewName, String profileViewName) {
        this.groupViewName = groupViewName;
        this.youViewName = youViewName;
        this.profileViewName = profileViewName;
    }

    public BetterWrappedAppViewModel() {}


}
