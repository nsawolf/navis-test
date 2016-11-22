import enumerations.Action;
import enumerations.Rank;
import enumerations.Suit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anySet;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BotPlayerTests {

    private Hand mockedHand = mock(Hand.class);
    private Score mockedScore = mock(Score.class);
    private BotPlayer bot = new BotPlayer();
    private Card card1 = new Card(Suit.Hearts, Rank.Five);
    private Card card2 = new Card(Suit.Clubs, Rank.Queen);

    @Before
    public void setup() {
        Dependencies.hand.override(() -> mockedHand);
        Dependencies.score.override(() -> mockedScore);
    }

    @After
    public void teardown() {
        Dependencies.hand.close();
        Dependencies.score.close();
    }

    //  Hits if not won score is under 17 (or something similar).
    @Test
    public void hits_if_score_is_under_17() {
        Set<Card> mHand = new HashSet<>();
        mHand.add(card1);
        mHand.add(card2);
        when(mockedHand.getCards()).thenReturn(mHand);
        when(mockedScore.scoreHand(anySet())).thenReturn(15).thenReturn(19);

        Action result = bot.nextAction(mockedHand);
        assertEquals(result, Action.Hit);
    }

    @Test
    public void stays_if_score_is_21() {
        Set<Card> mHand = new HashSet<>();
        mHand.add(card1);
        mHand.add(card2);
        when(mockedHand.getCards()).thenReturn(mHand);
        when(mockedScore.scoreHand(anySet())).thenReturn(21).thenReturn(19);

        Action result = bot.nextAction(mockedHand);
        assertEquals(result, Action.Stay);
    }

    @Test
    public void busts_if_score_is_over_21() {
        Set<Card> mHand = new HashSet<>();
        mHand.add(card1);
        mHand.add(card2);
        when(mockedHand.getCards()).thenReturn(mHand);
        when(mockedScore.scoreHand(anySet())).thenReturn(23).thenReturn(20);

        Action result = bot.nextAction(mockedHand);
        assertEquals(result, Action.Busted);
    }

    @Test
    public void stays_if_score_is_at_17() {
        Set<Card> mHand = new HashSet<>();
        mHand.add(card1);
        mHand.add(card2);
        when(mockedHand.getCards()).thenReturn(mHand);
        when(mockedScore.scoreHand(anySet())).thenReturn(17).thenReturn(19);

        Action result = bot.nextAction(mockedHand);
        assertEquals(result, Action.Stay);
    }

    @Test
    public void stays_if_score_is_between_17_and_21() {
        Set<Card> mHand = new HashSet<>();
        mHand.add(card1);
        mHand.add(card2);
        when(mockedHand.getCards()).thenReturn(mHand);
        when(mockedScore.scoreHand(anySet())).thenReturn(20).thenReturn(19);

        Action result = bot.nextAction(mockedHand);
        assertEquals(result, Action.Stay);
    }

}
