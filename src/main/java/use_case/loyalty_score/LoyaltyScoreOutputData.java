package use_case.loyalty_score;

import java.util.Map;

/**
 * Output Data for the LoyaltyScore
 */

public class LoyaltyScoreOutputData {
    private final String userid;
    private final String date;
    private final String type;
    private final Map<String, Integer> loyalty_scores;


    public LoyaltyScoreOutputData(String userid, String date, String type, Map<String, Integer> loyalty_scores)
    {
        this.date = date;
        this.userid = userid;
        this.type = type;
        this.loyalty_scores = loyalty_scores;
    }

    public String getUserid() {return userid; }
    public String getDate() {return this.date; }
    public String getType() {return this.type; }

    /**
     * Where object is the name of artist/track; as specified from spotify's api.
     */
    public int getScore(String object) {return loyalty_scores.get(object);}

}
