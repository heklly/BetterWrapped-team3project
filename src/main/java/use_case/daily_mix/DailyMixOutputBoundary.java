package use_case.daily_mix;

/**
 * The output boundary for the Daily Mix Use Case.
 */
public interface DailyMixOutputBoundary {

    /**
     * Prepares the success view for the Daily Mix Use Case.
     *
     * @param outputData the output data
     */
    void prepareSuccessView(DailyMixOutputData outputData);

    /**
     * Prepares the failure view for the Daily Mix Use Case.
     *
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
