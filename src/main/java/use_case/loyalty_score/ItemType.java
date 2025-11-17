package use_case.loyalty_score;


/**
 * A simple enum specifying the allowed ItemType (One of Track or Artist)
 */
public enum ItemType {

    TRACK("tracks"),
    ARTIST("artists");

    private final String displayName;

    ItemType(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }
}

