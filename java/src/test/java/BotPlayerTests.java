import enumerations.Action;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class BotPlayerTests {

    private Hand mockedHand = mock(Hand.class);
    PlayerI dealer = new BotPlayer(mockedHand);


    @Before
    public void setup() {
        Dependencies.hand.override(() -> mockedHand);
    }

    @After
    public void teardown() {
        Dependencies.hand.close();
    }

    @Test
    public void hits_if_score_is_withinin_range_of_0_and_less_than_stay_value_of_17() {
       score_range_helper(0, 16, Action.Hit);
    }

    @Test
    public void busts_if_score_is_greater_than_21(){
        score_range_helper(22, 27, Action.Busted);
    }

    @Test
    public void stays_if_score_is_within_range_17_to_21(){
        score_range_helper(17, 21, Action.Stay);
    }

    private void score_range_helper(int startRange, int endRange, Action expectedAction){
        PlayerI dealer = new BotPlayer(mockedHand);
        for(int i = startRange; i <= endRange; i++){
            when(mockedHand.scoreHand()).thenReturn(i);
            Action result = dealer.nextAction(mockedHand);
            assertEquals(expectedAction, result);

        }
    }

    @Test
    public void shows_visible_hand_with_no_cards_hidden() {
        final String dealerHand = "eight, jack";
        PlayerI dealer = new BotPlayer(mockedHand);
        when(mockedHand.visibleHand(false)).thenReturn(dealerHand);

        String result = dealer.showHand();

        assertEquals(dealerHand, result);
    }

    @Test
    public void getHand_gives_back_the_dealer_hand(){
        PlayerI dealer = new BotPlayer(mockedHand);
        Hand hand = dealer.getHand();
        assertSame(hand, mockedHand);
    }


}
