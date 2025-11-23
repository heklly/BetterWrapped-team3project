package use_case.loyalty_score;

import org.json.JSONArray;

/**
 * The Input Data for the Login Use Case.
 * Userid is for the userid for which the loyalty score is calculated
 * Date is for the date of calculation;
 */
public class LoyaltyScoreInputData {

    private final String userid;
    private final String date;
    private JSONArray loyalty_scores;


    public LoyaltyScoreInputData(String userid , String date) {
        this.userid = userid;
        this.date = date;
    }

    String getUserid() { return this.userid;}
    String getDate() {
        return this.date;
    }

}