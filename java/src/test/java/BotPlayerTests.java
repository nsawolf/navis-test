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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class BotPlayerTests {

    private HandI mockedHand = spy(Hand.class);
    private GameResult mockedGameResult = mock(GameResult.class);
    private PlayerI bot = Dependencies.botPlayer.make();
    private Card fiveHearts = new Card(Suit.Hearts, Rank.Five);
    private Card queenClubs = new Card(Suit.Clubs, Rank.Queen);
    private Card eightSpades = new Card(Suit.Spades, Rank.Eight);
    private Card fiveDiamonds = new Card(Suit.Diamonds, Rank.Five);
    Set<Card> dealerHand = bot.getHand().getCards();
    Set<Card> otherHand = new HashSet<Card>();
    private final int over17= 19;
    private final int under17 = 13;
    private final int busted = 22;
    private final int blackJack = 21;
    private final int under21 = 17;


    @Before
    public void setup() {
        Dependencies.hand.override(() -> mockedHand);
        Dependencies.gameResult.override(() -> mockedGameResult);
        dealerHand.add(fiveHearts);
        dealerHand.add(queenClubs);
        otherHand.add(fiveDiamonds);
        otherHand.add(eightSpades);
    }

    @After
    public void teardown() {
        Dependencies.hand.close();
        Dependencies.gameResult.close();
        Dependencies.botPlayer.close();
    }

    //  Hits if not won score is under 17 (or something similar).
    @Test
    public void hits_if_score_is_under_17() {
        when(mockedHand.getCards()).thenReturn(otherHand);
        when(mockedHand.scoreHand()).thenReturn(under17).thenReturn(over17);

        Action result = bot.nextAction(mockedHand);
        assertEquals(Action.Hit, result);
    }

    @Test
    public void busts_if_score_is_over_21() {
        when(mockedHand.getCards()).thenReturn(otherHand);
        when(mockedHand.scoreHand()).thenReturn(busted).thenReturn(over17);

        Action result = bot.nextAction(mockedHand);

        assertEquals(Action.Busted, result);
    }

    @Test
    public void stays_if_score_is_at_17() {
        Set<Card> mHand = new HashSet<>();
        mHand.add(fiveHearts);
        mHand.add(queenClubs);
        when(mockedHand.getCards()).thenReturn(mHand);
        when(mockedHand.scoreHand()).thenReturn(under21).thenReturn(under17);

        Action result = bot.nextAction(mockedHand);
        assertEquals(Action.Stay, result);
    }

    @Test
    public void stays_if_score_is_between_17_and_21() {
        final int twenty = 20;
        Set<Card> mHand = new HashSet<>();
        mHand.add(fiveHearts);
        mHand.add(queenClubs);
        when(mockedHand.getCards()).thenReturn(mHand);
        when(mockedHand.scoreHand()).thenReturn(twenty).thenReturn(under21);

        Action result = bot.nextAction(mockedHand);
        assertEquals(Action.Stay, result);
    }

}
