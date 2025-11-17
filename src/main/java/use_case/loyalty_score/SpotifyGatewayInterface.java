package use_case.loyalty_score;

import entity.TopItem;

import java.util.List;

public interface SpotifyGatewayInterface {
    /**
     * @param type Whether getting top tracks/artists
     * @return the TopItems, in order
     */
    List<TopItem> getTopItems(String userId, ItemType type);
}
