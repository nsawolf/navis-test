import enumerations.Action;
import enumerations.Rank;
import enumerations.Suit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.beans.DefaultPersistenceDelegate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class BotPlayerTests {

    private HandI mockedHand = spy(Hand.class);
    private final int over17= 19;
    private final int under17 = 15;
    private final int busted = 22;
    private final int under21 = 17;


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
        PlayerI dealer = new BotPlayer(mockedHand);
        when(mockedHand.scoreHand()).thenReturn(over17);

        Action result = dealer.nextAction(mockedHand);

        assertEquals(Action.Stay, result);
    }

    @Test
    public void busts_if_score_is_over_21() {
        PlayerI dealer = new BotPlayer(mockedHand);
        when(mockedHand.scoreHand()).thenReturn(busted);

        Action result = dealer.nextAction(mockedHand);

        assertEquals(Action.Busted, result);
    }

    @Test
    public void stays_if_score_is_at_17() {
        PlayerI dealer = new BotPlayer(mockedHand);
        when(mockedHand.scoreHand()).thenReturn(under21).thenReturn(under17);

        Action result = dealer.nextAction(mockedHand);

        assertEquals(Action.Stay, result);
    }

    @Test
    public void stays_if_score_is_between_17_and_21() {
        final int twenty = 20;
        PlayerI dealer = new BotPlayer(mockedHand);
        when(mockedHand.scoreHand()).thenReturn(twenty).thenReturn(under21);

        Action result = dealer.nextAction(mockedHand);

        assertEquals(Action.Stay, result);
    }

    @Test
    public void showHand_reveals_all_cards_in_hand(){
        final String dealerHand = "eight, jack";
        PlayerI dealer = new BotPlayer(mockedHand);
        when(mockedHand.visibleHand(false)).thenReturn(dealerHand);

        String result = dealer.showHand();

        assertEquals(dealerHand, result);
        verify(mockedHand, times(1)).visibleHand(false);
    }

}
