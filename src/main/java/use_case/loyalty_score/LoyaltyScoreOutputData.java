package use_case.loyalty_score;

import java.util.Map;

/**
 * Output Data for the LoyaltyScore
 */

public class LoyaltyScoreOutputData {
    private final String userid;
    private final String date;
    private final Map<String, Integer> loyalty_scores;


    public LoyaltyScoreOutputData(String userid, String date, Map<String, Integer> loyalty_scores)
    {
        this.date = date;
        this.userid = userid;
        this.loyalty_scores = loyalty_scores;
    }

    public String getUserid() {return userid; }
    public String getDate() {return this.date; }

    /**
     * Where name is the name of artist; as specified from spotify's api.
     */
    public int getScore(String name) {return loyalty_scores.get(name);}

}
