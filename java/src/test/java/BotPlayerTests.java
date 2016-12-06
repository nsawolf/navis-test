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
    private Card fiveHearts = new Card(Suit.Hearts, Rank.Five);
    private Card queenClubs = new Card(Suit.Clubs, Rank.Queen);
    private Card eightSpades = new Card(Suit.Spades, Rank.Eight);
    private Card sevenDiamonds = new Card(Suit.Diamonds, Rank.Seven);
    private final int over17= 19;
    private final int under17 = 15;
    private final int busted = 22;
    private final int blackJack = 21;
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
        when(mockedHand.scoreHand()).thenReturn(over17);
        PlayerI dealer = new BotPlayer();
        HandI dealerHand = dealer.getHand();
        dealerHand.addCard(eightSpades);
        dealerHand.addCard(queenClubs);

        Action result = dealer.nextAction(mockedHand);

        assertEquals(Action.Stay, result);
    }

    @Test
    public void busts_if_score_is_over_21() {
        PlayerI dealer = new BotPlayer();
        when(mockedHand.scoreHand()).thenReturn(busted);
        HandI dealerHand = dealer.getHand();
        dealerHand.addCard(fiveHearts);
        dealerHand.addCard(queenClubs);
        dealerHand.addCard(eightSpades);


        Action result = dealer.nextAction(mockedHand);

        assertEquals(Action.Busted, result);
    }

    @Test
    public void stays_if_score_is_at_17() {
        PlayerI dealer = new BotPlayer();
        HandI dealerHand = dealer.getHand();
        dealerHand.addCard(sevenDiamonds);
        dealerHand.addCard(queenClubs);
        when(mockedHand.scoreHand()).thenReturn(under21).thenReturn(under17);

        Action result = dealer.nextAction(mockedHand);
        assertEquals(Action.Stay, result);
    }

    @Test
    public void stays_if_score_is_between_17_and_21() {
        final int twenty = 20;
        PlayerI dealer = new BotPlayer();
        HandI dealerHand = dealer.getHand();
        dealerHand.addCard(eightSpades);
        dealerHand.addCard(queenClubs);
        when(mockedHand.scoreHand()).thenReturn(twenty).thenReturn(under21);

        Action result = dealer.nextAction(mockedHand);
        assertEquals(Action.Stay, result);
    }

    @Test
    public void showHand_reveals_all_cards_in_hand(){
        PlayerI dealer = new BotPlayer();
        HandI dealerHand = dealer.getHand();
        dealerHand.addCard(eightSpades);
        dealerHand.addCard(queenClubs);
        when(mockedHand.visibleHand(false)).thenReturn(dealerHand.toString());

        String result = dealer.showHand();

        assertEquals(dealerHand.visibleHand(false), result);
    }

}
