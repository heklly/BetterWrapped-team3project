package interface_adapter.in_group;

import interface_adapter.ViewModel;

public class InGroupViewModel extends ViewModel<InGroupState> {

    public InGroupViewModel() {
        super("in group");
        setState(new InGroupState());
    }

}
