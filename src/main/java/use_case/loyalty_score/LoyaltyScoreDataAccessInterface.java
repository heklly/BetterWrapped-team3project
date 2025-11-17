package use_case.loyalty_score;

import entity.TopItem;

import java.util.List;
import java.util.Map;

/**
 * DAO interface for the LoyaltyScore Use Case
 */
public interface LoyaltyScoreDataAccessInterface {

    /**
     * @param item_type specifies whether looking for artist or track loyalty score.
     * @return A map where the key is the name of object, integer is
     * saved loyalty_score FOR that date
     */
    Map<String, Integer> getTopItemsDate(String userid, String date, ItemType item_type);

    /**
     * @param item_type specifies whether looking for artist or track loyalty score.
     * @return Whether the artist/track exists in that date's saved loyalty scores
     */

    boolean loyaltyScoreExists(String userid, String date, ItemType item_type, String object_name);

    /**
     * @param userid who to save to
     * @param date the date of saving
     * @param item_type specifies whether saving artist or track loyalty score.
     * @param object_name name of the object; track/artist name
     * @param score the score.
     * Saves loyalty score
     */
    void saveLoyalty(String userid, String date, ItemType item_type, String object_name, int score);
}
