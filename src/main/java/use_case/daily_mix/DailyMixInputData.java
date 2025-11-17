package use_case.daily_mix;

/**
 * The Input Data for the Daily Mix Use Case.
 */
public class DailyMixInputData {

    private final String username;

    public DailyMixInputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

