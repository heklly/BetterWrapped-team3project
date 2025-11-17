package use_case.daily_mix;

/**
 * Input Boundary for actions which are related to generating the Daily Mix.
 */
public interface DailyMixInputBoundary {

    /**
     * Executes the Daily Mix use case.
     *
     * @param inputData the input data
     */
    void execute(DailyMixInputData inputData);
}

