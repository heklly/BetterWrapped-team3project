package interface_adapter.loyalty_score;

import entity.TopItem;
import use_case.loyalty_score.SpotifyGatewayInterface;

import java.util.List;

public class SpotifyGateway implements SpotifyGatewayInterface {
    @Override
    public List<TopItem> getTopItems(String userId) {
        return List.of();
    }
}
