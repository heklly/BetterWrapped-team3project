package use_case.daily_mix;

/**
 * Input Boundary for actions related to generating the Daily Mix.
 *
 * The controller should construct a {@link DailyMixInputData} containing
 * the userid of the currently logged-in user and pass it to this boundary.
 */
public interface DailyMixInputBoundary {

    /**
     * Executes the Daily Mix use case for the given user.
     *
     * @param inputData the input data (contains the userid)
     */
    void execute(DailyMixInputData inputData);
}


