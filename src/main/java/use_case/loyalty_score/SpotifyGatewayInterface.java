package use_case.loyalty_score;

import entity.TopItem;

import java.util.List;

public interface SpotifyGatewayInterface {
    /**
     * @return the TopItems, in order
     */
    List<TopItem> getTopItems(String userId);
}
