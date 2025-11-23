package use_case.loyaltyscore;
import org.junit.jupiter.api.Test;
import use_case.loyalty_score.LoyaltyScoringService;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class LoyaltyScoringServiceTest {

    @Test
    void testLoyaltyScoringService() {
        int expected_1 = 100;
        int expected_25 = 19;
        int expected_50 = 3;
        assertEquals(expected_1, LoyaltyScoringService.scoreFromRank(1));
        assertEquals(expected_25, LoyaltyScoringService.scoreFromRank(25));
        assertEquals(expected_50, LoyaltyScoringService.scoreFromRank(50));
    }
}
