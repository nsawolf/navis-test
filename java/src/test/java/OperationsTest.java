import enumerations.Action;
import enumerations.Rank;
import enumerations.Suit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class OperationsTest {

    private Deck mockedDeck = mock(Deck.class);
    private Hand mockedHand = mock(Hand.class);
    private HumanPlayer mockedHumanPlayer = mock(HumanPlayer.class);
    private BotPlayer mockedBotPlayer = mock(BotPlayer.class);
    private GameResult mockedScore = mock(GameResult.class);
    private Long mockedTimeValue = 23L;
    private Operations gameOps = Dependencies.gameOps.make();

    @Before
    public void setup() {
        Dependencies.hand.override(() -> mockedHand);
        Dependencies.deck.override(() -> mockedDeck);
        Dependencies.humanPlayer.override((a) -> mockedHumanPlayer);
        Dependencies.botPlayer.override((a) -> mockedBotPlayer);
        Dependencies.gameResult.override(() -> mockedScore);
        Dependencies.now.override(() -> mockedTimeValue);
    }

    @After
    public void tearDown() {
        Dependencies.deck.close();
        Dependencies.humanPlayer.close();
        Dependencies.botPlayer.close();
        Dependencies.gameResult.close();
        Dependencies.now.close();
        Dependencies.gameOps.close();
    }

    @Test
    public void initialGameDeal_shuffles_and_then_deals_cards() throws OutOfCardsException {
        gameOps.initialGameDeal(mockedHand, mockedHand);

        verify(mockedHand, times(4)).addCard(any(Card.class));
    }

    @Test
    public void handlePlayerAction_stays_when_human_requests_stays() throws OutOfCardsException {
        when(mockedHumanPlayer.nextAction(any(Hand.class))).thenReturn(Action.Stay);

        Action result = gameOps.handleHumanPlayerAction(mockedHand, mockedHand);

        verify(mockedHumanPlayer, times(1)).nextAction(any(Hand.class));
        verify(mockedHand, times(0)).addCard(any(Card.class));
    }

    @Test
    public void handlePlayerAction_busts_when_human_busts() throws OutOfCardsException {
        when(mockedHumanPlayer.nextAction(any(Hand.class))).thenReturn(Action.Busted);

        Action result = gameOps.handleHumanPlayerAction(mockedHand, mockedHand);

        verify(mockedHumanPlayer, times(1)).nextAction(any(Hand.class));
        verify(mockedHand, times(0)).addCard(any(Card.class));
    }

    // really good. Says exactly what the behavior is
    @Test
    public void deals_cards_until_player_stays() throws OutOfCardsException {
        when(mockedHumanPlayer.nextAction(any(Hand.class))).thenReturn(Action.Hit).thenReturn(Action.Hit).thenReturn(Action.Stay);
        when(mockedHumanPlayer.getHand()).thenReturn(mockedHand);

        Action result = gameOps.handleHumanPlayerAction(mockedHand, mockedHand);

        assertEquals(result, Action.Stay);
        verify(mockedHumanPlayer, times(3)).nextAction(any(Hand.class));
        verify(mockedHand, times(2)).addCard(any(Card.class));
    }

    @Test
    public void handleDealerAction_busts_when_dealer_busts() throws OutOfCardsException {
        when(mockedBotPlayer.nextAction(any(Hand.class))).thenReturn(Action.Hit).thenReturn(Action.Busted);

        Action result = gameOps.handleDealerAction(mockedHand, mockedHand);

        assertEquals(result, Action.Busted);
        verify(mockedBotPlayer, times(2)).nextAction(any(Hand.class));
        verify(mockedHand, times(1)).addCard(any(Card.class));
    }

    @Test
    public void handleDealerAction_stays_when_dealer_stays() throws OutOfCardsException {
        when(mockedBotPlayer.nextAction(any(Hand.class))).thenReturn(Action.Stay);

        Action result = gameOps.handleDealerAction(mockedHand, mockedHand);

        assertEquals(result, Action.Stay);
        verify(mockedBotPlayer, times(1)).nextAction(any(Hand.class));
        verify(mockedHand, times(0)).addCard(any(Card.class));
    }
}
