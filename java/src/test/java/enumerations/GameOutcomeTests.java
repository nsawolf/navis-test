package enumerations;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameOutcomeTests {

    @Test
    public void has_four_possible_results(){
        assertEquals(GameOutcome.values().length, 4);
    }

    @Test
    public void asString_informs_both_players_have_busted(){
        int dealerBusted = 23;
        int playerBusted = 22;
        String dealerBustedInfo = "Dealer hand";
        String expectedResult = String.format("Both players have busted. \n Dealer Hand: %s \n Human score: %d \n Dealer score: %d", dealerBustedInfo, playerBusted, dealerBusted);
        assertEquals(expectedResult, GameOutcome.BothBusted.asString(dealerBusted, playerBusted, dealerBustedInfo));
    }

    @Test
    public void asString_informs_both_players_have_tied(){
        int pushValue = 17;
        String dealerInfo = "Dealer hand";
        String expectedResult = String.format("Game is a push, both players have the score: %d \n Dealer Hand: %s", pushValue, dealerInfo);
        assertEquals(expectedResult, GameOutcome.Push.asString(pushValue, pushValue, dealerInfo));
    }

    @Test
    public void asString_informs_dealer_is_winner_of_game(){
        int dealerScore = 17;
        int playerScore = 16;
        String dealerInfo = "Dealer hand";
        String expectedResult = String.format("Dealer has won. \n Dealer Hand: %s \n Dealer score: %d \n Human score: %d", dealerInfo, dealerScore, playerScore);
        assertEquals(expectedResult, GameOutcome.Dealer.asString(dealerScore, playerScore, dealerInfo));
    }

    @Test
    public void asString_informs_player_is_winner_of_game(){
        int dealerScore = 16;
        int playerScore = 17;
        String dealerInfo = "Dealer hand";
        String expectedResult = String.format("Human player has won. \n Human score: %d \n Dealer score: %d \n Dealer Hand: %s", playerScore, dealerScore, dealerInfo);
        assertEquals(expectedResult, GameOutcome.Player.asString(dealerScore, playerScore, dealerInfo));
    }
}
