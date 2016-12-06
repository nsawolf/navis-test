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
    private HandI mockedHand = mock(Hand.class);
    private HumanPlayer mockedHumanPlayer = mock(HumanPlayer.class);
    private BotPlayer mockedBotPlayer = mock(BotPlayer.class);
    private GameResult mockedScore = mock(GameResult.class);
    private Long mockedTimeValue = 23L;
    private Card eightDiamonds = new Card(Suit.Diamonds, Rank.Eight);
    private Card tenHearts = new Card(Suit.Hearts, Rank.Ten);
    private Card threeSpades = new Card(Suit.Spades, Rank.Three);
    private Card aceClubs = new Card(Suit.Clubs, Rank.Ace);
    private Operations gameOps = new Operations();

    @Before
    public void setup() {
        Dependencies.hand.override(() -> mockedHand);
        Dependencies.deck.override(() -> mockedDeck);
        Dependencies.humanPlayer.override(() -> mockedHumanPlayer);
        Dependencies.botPlayer.override(() -> mockedBotPlayer);
        Dependencies.gameResult.override(() -> mockedScore);
        Dependencies.now.override(() -> mockedTimeValue);
//        mockedHand.addCard(threeSpades);
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
        when(mockedDeck.dealCard()).thenReturn(eightDiamonds).thenReturn(threeSpades).thenReturn(aceClubs).thenReturn(tenHearts);
        when(mockedBotPlayer.getHand()).thenReturn(mockedHand);
        when(mockedHumanPlayer.getHand()).thenReturn(mockedHand);

        InOrder inOrder = inOrder(mockedDeck);

        gameOps.initialGameDeal(mockedHand, mockedHand);

        inOrder.verify(mockedDeck, times(1)).shuffleDeck();
        inOrder.verify(mockedDeck, times(4)).dealCard();
    }

    @Test
    public void handlePlayerAction_stays_when_human_requests_stays() throws OutOfCardsException {
        when(mockedHumanPlayer.nextAction(any(Hand.class))).thenReturn(Action.Stay);

        Action result = gameOps.handlePlayerAction(mockedHumanPlayer, mockedHand, mockedHand, mockedDeck);

        verify(mockedHumanPlayer, times(1)).nextAction(any(Hand.class));
    }

    @Test
    public void handlePlayerAction_busts_when_human_busts() throws OutOfCardsException {
        when(mockedHumanPlayer.nextAction(any(Hand.class))).thenReturn(Action.Busted);

        Action result = gameOps.handlePlayerAction(mockedHumanPlayer, mockedHand, mockedHand, mockedDeck);

        verify(mockedHumanPlayer, times(1)).nextAction(any(Hand.class));
    }

    // really good. Says exactly what the behavior is
    @Test
    public void deals_cards_until_player_stays() throws OutOfCardsException {
        when(mockedHumanPlayer.nextAction(any(Hand.class))).thenReturn(Action.Hit).thenReturn(Action.Hit).thenReturn(Action.Stay);
        when(mockedHumanPlayer.getHand()).thenReturn(mockedHand);

        Action result = gameOps.handlePlayerAction(mockedHumanPlayer, mockedHand, mockedHand, mockedDeck);

        assertEquals(result, Action.Stay);
        verify(mockedHumanPlayer, times(3)).nextAction(any(Hand.class));
    }

}
