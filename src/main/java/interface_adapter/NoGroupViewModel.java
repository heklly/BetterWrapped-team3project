package interface_adapter;

public class NoGroupViewModel {


    public NoGroupViewModel() {
        super("no group");
        setState(new UserGroupState());
    }
    public static String getState() {
    }
}
