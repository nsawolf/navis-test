import enumerations.Rank;
import enumerations.Suit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class OperationsTest {

    private Deck mockedDeck = mock(Deck.class);
    private Hand mockedHand = mock(Hand.class);
    private HumanPlayer mockedHumanPlayer = mock(HumanPlayer.class);
    private BotPlayer mockedBotPlayer = mock(BotPlayer.class);
    private Score mockedScore = mock(Score.class);
    private Date mockedDateSeed = mock(Date.class);
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
        Dependencies.now.override(() -> mockedDateSeed);
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
    public void initialGameDeal_shuffles_and_deals_cards() throws OutOfCardsException {
        when(mockedDateSeed.getTime()).thenReturn(mockedTimeValue);
        when(mockedDeck.dealCard()).thenReturn(eightDiamonds).thenReturn(threeSpades).thenReturn(aceClubs).thenReturn(tenHearts);
        when(mockedBotPlayer.getHand()).thenReturn(mockedHand);
        when(mockedHumanPlayer.getHand()).thenReturn(mockedHand);
        gameOps.initialGameDeal(mockedBotPlayer, mockedHumanPlayer);

        verify(mockedDeck, times(4)).dealCard();
        verify(mockedDeck, times(1)).shuffleDeck(anyLong());
    }

    @Test
    public void player_gets_card_dealt_to_hand() throws OutOfCardsException{
        when(mockedDeck.dealCard()).thenReturn(eightDiamonds);
        when(mockedBotPlayer.getHand()).thenReturn(mockedHand);

        gameOps.dealCardToPlayer(mockedDeck, mockedBotPlayer);

        verify(mockedBotPlayer, times(1)).getHand().addCard(any(Card.class));
    }
}
