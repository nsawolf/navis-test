import enumerations.Action;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class BotPlayerTests {

    private Hand mockedHand = mock(Hand.class);


    @Before
    public void setup() {
        Dependencies.hand.override(() -> mockedHand);
    }

    @After
    public void teardown() {
        Dependencies.hand.close();
    }

    @Test
    public void stays_if_score_is_not_winning_and_above_stay_value() {
        final int over17 = 19;
        PlayerI dealer = new BotPlayer(mockedHand);
        when(mockedHand.scoreHand()).thenReturn(over17);

        Action result = dealer.nextAction(mockedHand);

        assertEquals(Action.Stay, result);
    }

    @Test
    public void busts_if_score_is_over_21() {
        final int busted = 22;
        PlayerI dealer = new BotPlayer(mockedHand);
        when(mockedHand.scoreHand()).thenReturn(busted);

        Action result = dealer.nextAction(mockedHand);

        assertEquals(Action.Busted, result);
    }

    @Test
    public void stays_if_score_is_at_17() {
        final int under21 = 17;
        PlayerI dealer = new BotPlayer(mockedHand);
        when(mockedHand.scoreHand()).thenReturn(under21);

        Action result = dealer.nextAction(mockedHand);

        assertEquals(Action.Stay, result);
    }

    @Test
    public void stays_if_score_is_between_17_and_21() {
        final int twenty = 20;
        PlayerI dealer = new BotPlayer(mockedHand);
        when(mockedHand.scoreHand()).thenReturn(twenty);

        Action result = dealer.nextAction(mockedHand);

        assertEquals(Action.Stay, result);
    }

    @Test
    public void hits_if_score_is_under_17(){
        final int under17 = 15;
        PlayerI dealer = new BotPlayer(mockedHand);
        when(mockedHand.scoreHand()).thenReturn(under17);
        Action result  = dealer.nextAction(mockedHand);
        assertEquals(Action.Hit, result);
    }

    // TODO: shows_visible_hand_with_no_cards_hidden ... Game being over has nothing to do with behavior
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
