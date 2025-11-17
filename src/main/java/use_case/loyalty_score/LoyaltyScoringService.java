package use_case.loyalty_score;

/**
 * A wrapper class for calculating loyalty score.
 */
public class LoyaltyScoringService {

    private static final double K = 0.07;
    /**
     * Calculates score, 1 to 100 from rank in top 50 items. 1st ~ 100 loyalty, 50th ~ 3.
     */

    public static double scoreFromRank(int rank) {
        double unrounded = 100 * Math.exp(-K * (rank - 1));
        return (int) Math.round(unrounded);
    }
}

