import enumerations.Action;
import enumerations.GameOutcome;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameResultTests {

    private final int winningScore = 20;
    private final int losingScore = 17;
    private final String dealerHandInfo = "Dealer Hand";
    private HandI mockedHand = mock(Hand.class);


    @Before
    public void setup(){
        Dependencies.hand.override(() -> mockedHand);
    }

    @After
    public void tearDown(){
        Dependencies.hand.close();
    }

    @Test
    public void determines_player_as_winner_of_game_when_player_score_is_higher(){
        GameResultI gameResult = new GameResult();

        GameOutcome result = gameResult.determineWinner(losingScore, winningScore);

        assertEquals(GameOutcome.Player, result);
    }

    @Test
    public void determines_dealer_as_winner_of_game_when_dealer_player_score_is_higher(){
        GameResultI gameResult = new GameResult();

        GameOutcome result = gameResult.determineWinner(winningScore, losingScore);

        assertEquals(GameOutcome.Dealer, result);
    }

    @Test
    public void is_push_when_dealer_and_player_have_same_score(){
        GameResultI gameResult = new GameResult();

        boolean result = gameResult.gameIsPush(losingScore, losingScore);

        assertTrue(result);
    }

    @Test
    public void both_players_bust_when_each_player_reports_action_busted(){
        GameResultI gameResult = new GameResult();

        boolean result = gameResult.bothPlayersBust(Action.Busted, Action.Busted);

        assertTrue(result);
    }

    @Test
    public void resultOfGame_player_with_highest_score_wins_the_game(){
        GameResultI gameResult = new GameResult();
        String expected = new GameResult(GameOutcome.Player, losingScore, winningScore, dealerHandInfo).toString();
        when(mockedHand.scoreHand()).thenReturn(losingScore).thenReturn(winningScore);
        when(mockedHand.visibleHand(false)).thenReturn(dealerHandInfo);

        GameResultI result = gameResult.resultOfGame(Action.Stay, Action.Stay, mockedHand, mockedHand);

        assertEquals(expected, result.toString());
    }

    @Test
    public void resultOfGame_dealer_with_highest_score_wins_the_game(){
        GameResultI gameResult = new GameResult();
        final String expected = new GameResult(GameOutcome.Dealer, winningScore, losingScore, dealerHandInfo).toString();
        when(mockedHand.scoreHand()).thenReturn(winningScore).thenReturn(losingScore);
        when(mockedHand.visibleHand(false)).thenReturn(dealerHandInfo);

        GameResultI result = gameResult.resultOfGame(Action.Stay, Action.Stay, mockedHand, mockedHand);

        assertEquals(expected, result.toString());
    }

    @Test
    public void resultOfGame_players_busted_game_is_bust(){
        final int dealerScore = 22;
        final int playerScore = 23;
        GameResult gameResult = new GameResult();
        String expected = new GameResult(GameOutcome.BothBusted, dealerScore, playerScore, dealerHandInfo).toString();
        when(mockedHand.scoreHand()).thenReturn(dealerScore).thenReturn(playerScore);
        when(mockedHand.visibleHand(false)).thenReturn(dealerHandInfo);

        GameResultI result = gameResult.resultOfGame(Action.Busted, Action.Busted, mockedHand, mockedHand);

        assertEquals(expected, result.toString());
    }

    @Test
    public void resultOfGame_players_have_a_pushed_game(){
        GameResult gameResult = new GameResult();
        final String expected = new GameResult(GameOutcome.Push, winningScore, winningScore, dealerHandInfo).toString();
        when(mockedHand.scoreHand()).thenReturn(20);
        when(mockedHand.visibleHand(false)).thenReturn(dealerHandInfo);

        GameResultI result = gameResult.resultOfGame(Action.Stay, Action.Stay, mockedHand, mockedHand);

        assertEquals(expected, result.toString());
    }

}
