import enumerations.Action;
import enumerations.GameOutcome;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import java.util.Stack;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class BlackjackGameTests {

    private Operations mockedOps = mock(Operations.class);
    private Deck mockedDeck = mock(Deck.class);
    private GameResult mockedGameResult = mock(GameResult.class);
    private Hand playerHand = mock(Hand.class);
    private Hand dealerHand = mock(Hand.class);
    private BlackJackGame game = Dependencies.game.make();

    @Before
    public void setup() {
        final Stack<Hand> h = new Stack();
        h.push(dealerHand);
        h.push(playerHand);
        Dependencies.gameOps.override(() -> mockedOps);
        Dependencies.deck.override(() -> mockedDeck);
        Dependencies.gameResult.override(() -> mockedGameResult);
        Dependencies.hand.override(() -> h.pop());
    }

    @After
    public void teardown() {
        Dependencies.gameOps.close();
        Dependencies.deck.close();
        Dependencies.gameResult.close();
        Dependencies.hand.close();
    }

    // THESE ARE NOT THE TESTS YOU'RE LOOKING FOR
    // TODO: InOrder verification of play
    // Stay at level of abstraction
    @Test
    public void initializes_the_game_and_processes_human_player() throws OutOfCardsException {
        GameResult result = game.play();

        InOrder order = inOrder(mockedOps);

        order.verify(mockedOps, times(1)).initialGameDeal(same(dealerHand), same(playerHand));
        order.verify(mockedOps, times(1)).handleHumanPlayerAction(same(playerHand), eq(dealerHand));
    }

    @Test
    public void human_player_busts_dealer_wins() throws OutOfCardsException {
        when(mockedOps.handleHumanPlayerAction(playerHand, dealerHand)).thenReturn(Action.Busted);
        GameResult result = game.play();

        InOrder order = inOrder(mockedOps, mockedGameResult);


        order.verify(mockedOps, times(1)).handleHumanPlayerAction(same(playerHand), same(dealerHand));
        order.verify(mockedOps, times(0)).handleDealerAction(same(dealerHand), same(playerHand));
        order.verify(mockedGameResult, times(1)).resultOfGame(any(Action.class), any(Action.class), same(dealerHand), same(playerHand));
    }

    @Test
    public void processes_human_player_and_dealer() throws OutOfCardsException {
        when(mockedOps.handleHumanPlayerAction(playerHand, dealerHand)).thenReturn(Action.Stay);
        when(mockedOps.handleDealerAction(dealerHand, playerHand)).thenReturn(Action.Stay);

        GameResult result = game.play();

        InOrder order = inOrder(mockedOps);

        order.verify(mockedOps, times(1)).handleHumanPlayerAction(same(playerHand), same(dealerHand));
        order.verify(mockedOps, times(1)).handleDealerAction(same(dealerHand), same(playerHand));
    }
}
