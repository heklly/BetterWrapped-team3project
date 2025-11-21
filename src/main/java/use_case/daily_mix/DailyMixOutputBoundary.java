package use_case.daily_mix;

/**
 * Output Boundary for the Daily Mix Use Case.
 *
 * The presenter receives a DailyMixOutputData object containing the generated
 * Playlist entity and an optional message for display. Any unexpected issues
 * (e.g., empty library) should trigger the fail view instead.
 */
public interface DailyMixOutputBoundary {

    /**
     * Prepares the success view for the Daily Mix Use Case.
     *
     * @param outputData the output data containing the generated Playlist
     */
    void prepareSuccessView(DailyMixOutputData outputData);

    /**
     * Prepares the failure view for the Daily Mix Use Case.
     *
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}

