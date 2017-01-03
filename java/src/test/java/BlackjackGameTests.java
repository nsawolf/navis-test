import enumerations.Action;
import enumerations.GameOutcome;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class BlackjackGameTests {

    private OperationsI mockedOps = mock(Operations.class);
    private Deck mockedDeck = mock(Deck.class);
    private GameResultI mockedGameResult = mock(GameResult.class);
    private HandI mockedHand = mock(Hand.class);
    private BlackJackGameI game = Dependencies.game.make();
    private final int winningScore = 20;
    private final int losingScore = 17;
    private final String dealerHandInfo = "Dealer Hand";

    @Before
    public void setup() {
        Dependencies.gameOps.override(() -> mockedOps);
        Dependencies.deck.override(() -> mockedDeck);
        Dependencies.gameResult.override(() -> mockedGameResult);
        Dependencies.hand.override(() -> mockedHand);
        Dependencies.game.make();
    }

    @After
    public void teardown() {
        Dependencies.gameOps.close();
        Dependencies.deck.close();
        Dependencies.gameResult.close();
        Dependencies.hand.close();
        Dependencies.game.close();
    }

    @Test
    public void human_player_wins_when_bot_busts() throws OutOfCardsException {
        when(mockedOps.handleHumanPlayerAction(mockedHand, mockedHand)).thenReturn(Action.Stay).thenReturn(Action.Busted);
        when(mockedGameResult.resultOfGame(Action.Stay, Action.Busted, mockedHand, mockedHand)).thenReturn(new GameResult(GameOutcome.Player, losingScore, winningScore, dealerHandInfo));

        GameResultI result = game.play();

        verify(mockedOps, times(1)).initialGameDeal(any(Hand.class), any(Hand.class));
        verify(mockedOps, times(1)).handleHumanPlayerAction(any(Hand.class), any(Hand.class));
        verify(mockedOps, times(1)).handleDealerAction(any(Hand.class), any(Hand.class));
        verify(mockedGameResult, times(1)).resultOfGame(any(Action.class), any(Action.class), any(Hand.class), any(Hand.class));
    }

    @Test
    public void bot_player_wins_when_human_busts() throws OutOfCardsException {
        final int bustedScore = 22;
        when(mockedOps.handleHumanPlayerAction(mockedHand, mockedHand)).thenReturn(Action.Busted);
        when(mockedGameResult.resultOfGame(Action.Busted, Action.Stay, mockedHand, mockedHand)).thenReturn(new GameResult(GameOutcome.Dealer, winningScore, bustedScore, dealerHandInfo));

        GameResultI result = game.play();

        verify(mockedOps, times(1)).initialGameDeal(any(Hand.class), any(Hand.class));
        verify(mockedOps, times(1)).handleHumanPlayerAction(any(Hand.class), any(Hand.class));
        verify(mockedOps, times(0)).handleDealerAction(any(Hand.class), any(Hand.class));
        verify(mockedGameResult, times(1)).resultOfGame(any(Action.class), any(Action.class), any(Hand.class), any(Hand.class));
    }

    @Test
    public void player_with_greater_score_not_busted_wins() throws OutOfCardsException {
        when(mockedOps.handleHumanPlayerAction(mockedHand, mockedHand)).thenReturn(Action.Stay).thenReturn(Action.Stay);
        when(mockedGameResult.resultOfGame(Action.Stay, Action.Stay, mockedHand, mockedHand)).thenReturn(new GameResult(GameOutcome.Player, losingScore, winningScore, dealerHandInfo));

        GameResultI result = game.play();

        verify(mockedOps, times(1)).initialGameDeal(any(Hand.class), any(Hand.class));
        verify(mockedOps, times(1)).handleHumanPlayerAction(any(Hand.class), any(Hand.class));
        verify(mockedOps, times(1)).handleDealerAction(any(Hand.class), any(Hand.class));
        verify(mockedGameResult, times(1)).resultOfGame(any(Action.class), any(Action.class), any(Hand.class), any(Hand.class));
    }
}
