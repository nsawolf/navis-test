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
    private Hand mockedHand = spy(Hand.class);
    private HumanPlayer mockedHumanPlayer = mock(HumanPlayer.class);
    private BotPlayer mockedBotPlayer = mock(BotPlayer.class);
    private Score mockedScore = mock(Score.class);
    private Long mockedTimeValue = 23L;
    private Card eightDiamonds = new Card(Suit.Diamonds, Rank.Eight);
    private Card tenHearts = new Card(Suit.Hearts, Rank.Ten);
    private Card threeSpades = new Card(Suit.Spades, Rank.Three);
    private Card aceClubs = new Card(Suit.Clubs, Rank.Ace);
    private Operations gameOps = new Operations();

    @Before
    public void setup(){
        Dependencies.hand.override(() -> mockedHand);
        Dependencies.deck.override(() -> mockedDeck);
        Dependencies.humanPlayer.override (() -> mockedHumanPlayer);
        Dependencies.botPlayer.override(() -> mockedBotPlayer);
        Dependencies.score.override(() -> mockedScore);
        Dependencies.now.override(() -> mockedTimeValue);
        mockedHand.addCard(threeSpades);
    }

    @After
    public void tearDown(){
        Dependencies.deck.close();
        Dependencies.humanPlayer.close();
        Dependencies.botPlayer.close();
        Dependencies.score.close();
        Dependencies.now.close();
        Dependencies.gameOps.close();
    }

    @Test
    public void player_gets_card_dealt_to_hand() throws OutOfCardsException{
        when(mockedBotPlayer.getHand()).thenReturn(mockedHand);

        gameOps.dealCardToPlayer(mockedDeck, mockedBotPlayer);

        verify(mockedBotPlayer, times(1)).getHand();
        verify(mockedDeck, times(1)).dealCard();
//        verify(mockedHand, times(1)).addCard(any(Card.class));
    }

    @Test
    public void initialGameDeal_shuffles_and_then_deals_cards() throws OutOfCardsException {
        when(mockedDeck.dealCard()).thenReturn(eightDiamonds).thenReturn(threeSpades).thenReturn(aceClubs).thenReturn(tenHearts);
        when(mockedBotPlayer.getHand()).thenReturn(mockedHand);
        when(mockedHumanPlayer.getHand()).thenReturn(mockedHand);

        InOrder inOrder = inOrder(mockedDeck);

        gameOps.initialGameDeal(mockedBotPlayer, mockedHumanPlayer);

        inOrder.verify(mockedDeck, times(1)).shuffleDeck();
        inOrder.verify(mockedDeck, times(4)).dealCard();
    }



    @Test
    public void does_not_deal_anymore_cards_when_player_stays() throws OutOfCardsException{
        when(mockedHumanPlayer.nextAction(any(Hand.class))).thenReturn(Action.Stay);

        Action result = gameOps.handlePlayerAction(mockedHumanPlayer, mockedBotPlayer, mockedDeck);

        assertEquals(result, Action.Stay);
        verify(mockedHumanPlayer, times(1)).nextAction(any(Hand.class));
    }

    @Test
    public void deals_cards_until_player_stays() throws OutOfCardsException{
        when(mockedHumanPlayer.nextAction(any(Hand.class))).thenReturn(Action.Hit).thenReturn(Action.Hit).thenReturn(Action.Stay);
        when(mockedHumanPlayer.getHand()).thenReturn(mockedHand);

        Action result = gameOps.handlePlayerAction(mockedHumanPlayer, mockedBotPlayer, mockedDeck);

        assertEquals(result, Action.Stay);
        verify(mockedHumanPlayer, times(3)).nextAction(any(Hand.class));
    }

    @Test
    public void score_of_player_hand_is_calculated_correctly(){

    }
}
