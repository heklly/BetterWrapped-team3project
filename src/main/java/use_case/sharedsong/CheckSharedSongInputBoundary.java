package use_case.sharedsong;

/**
 * The Check Shared Song Use Case.
 */
public interface CheckSharedSongInputBoundary {
    /**
     * Execute the Check Shared Song Use Case.
     * @param checkSharedSongInputData the input data for this use case
     */
    void execute(CheckSharedSongInputData checkSharedSongInputData);
}
