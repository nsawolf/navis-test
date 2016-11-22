import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class OperationsTest {

    private Operations gameOps;
    private Deck mockedDeck = mock(Deck.class);
    private HumanPlayer mockedHumanPlayer = mock(HumanPlayer.class);
    private BotPlayer mockedBotPlayer = mock(BotPlayer.class);
    private Score mockedScore = mock(Score.class);

    @Before
    public void setup(){
        Dependencies.gameOps.make();
        Dependencies.deck.override(() -> mockedDeck);
        Dependencies.humanPlayer.override (() -> mockedHumanPlayer);
        Dependencies.botPlayer.override(() -> mockedBotPlayer);
        Dependencies.score.override(() -> mockedScore);
    }

    @After
    public void tearDown(){
        Dependencies.gameOps.close();
        Dependencies.deck.close();
        Dependencies.humanPlayer.close();
        Dependencies.botPlayer.close();
        Dependencies.score.close();
    }

    @Test
    public void handles_initial_game_deal() throws OutOfCardsException {
        DeckI result = gameOps.initialGameDeal(mockedBotPlayer, mockedHumanPlayer);

        assertEquals(result.size(), 48);

        verify(mockedHumanPlayer, times(2)).getHand();
        verify(mockedBotPlayer, times(2)).getHand();
        verify(mockedDeck, times(4)).dealCard();
        verify(mockedDeck, times(1)).shuffleDeck(anyLong());
    }
}
