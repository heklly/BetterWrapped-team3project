package use_case.get_top_songs;

public class GetTopSongsInputData {
    private final ActionType actionType;

    public GetTopSongsInputData(ActionType actionType) {
        this.actionType = actionType;
    }

    public ActionType getActionType() {
        return actionType;
    }
}
