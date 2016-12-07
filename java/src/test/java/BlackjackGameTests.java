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
    private PlayerI mockedBot = mock(BotPlayer.class);
    private PlayerI mockedHuman = mock(HumanPlayer.class);
    private GameResultI mockedGameResult = mock(GameResult.class);
    private HandI mockedHand = mock(Hand.class);
    private BlackJackGame game = new BlackJackGame();

    @Before
    public void setup() {
        Dependencies.gameOps.override(() -> mockedOps);
        Dependencies.deck.override(() -> mockedDeck);
        Dependencies.botPlayer.override(() -> mockedBot);
        Dependencies.humanPlayer.override(() -> mockedHuman);
        Dependencies.gameResult.override(() -> mockedGameResult);
        Dependencies.hand.override(() -> mockedHand);
        Dependencies.game.make();
    }

    @After
    public void teardown() {
        Dependencies.gameOps.close();
        Dependencies.deck.close();
        Dependencies.botPlayer.close();
        Dependencies.humanPlayer.close();
        Dependencies.gameResult.close();
        Dependencies.hand.close();
        Dependencies.game.close();
    }

    // TODO: perhaps re-work because this may be considered too much setup given session on 12/6/2016
    @Test
    public void human_player_wins_when_bot_busts() throws OutOfCardsException {
        final int winningScore = 20;
        final int losingScore = 19;
        final String dealerHandInfo = "Dealer Hand";
        mocked_deal_cards_get_actions();
        when(mockedOps.handlePlayerAction(mockedHuman, mockedHand, mockedHand, mockedDeck)).thenReturn(Action.Stay).thenReturn(Action.Stay);
        when(mockedGameResult.resultOfGame(Action.Stay, Action.Stay, mockedHand, mockedHand)).thenReturn(new GameResult(GameOutcome.Player, winningScore, losingScore, dealerHandInfo));

        GameResultI result = game.play();

        verify(mockedOps, times(1)).initialGameDeal(any(Hand.class), any(Hand.class));
        verify(mockedOps, times(2)).handlePlayerAction(any(PlayerI.class), any(Hand.class), any(Hand.class), any(Deck.class));
        verify(mockedGameResult, times(1)).resultOfGame(any(Action.class), any(Action.class), any(Hand.class), any(Hand.class));
    }

    private void mocked_deal_cards_get_actions() throws OutOfCardsException {
        when(mockedOps.initialGameDeal(any(Hand.class), any(Hand.class))).thenReturn(mockedDeck);
        when(mockedOps.handlePlayerAction(mockedBot, mockedHand, mockedHand, mockedDeck)).thenReturn(Action.Stay);
        when(mockedOps.handlePlayerAction(mockedHuman, mockedHand, mockedHand, mockedDeck)).thenReturn(Action.Stay);
    }

}
