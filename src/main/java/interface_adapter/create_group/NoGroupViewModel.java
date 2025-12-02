package interface_adapter.create_group;

import interface_adapter.ViewModel;

public class NoGroupViewModel extends ViewModel<UserGroupState> {

    public NoGroupViewModel() {
        super("in group");
        setState(new UserGroupState());
    }
}
