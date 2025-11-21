package use_case.daily_mix;

/**
 * The Input Data for the Daily Mix Use Case.
 *
 * Daily Mix should identify a user uniquely, so we pass userid instead of username.
 */
public class DailyMixInputData {

    private final int userId;

    public DailyMixInputData(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}


