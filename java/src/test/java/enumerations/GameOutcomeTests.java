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
        String expectedResult = String.format("Both players have busted. \n Dealer Hand: %s \n Human score: %d \n Dealer score: %d \n ", dealerBustedInfo, playerBusted, dealerBusted);
        assertEquals(GameOutcome.BothBusted.asString(playerBusted, dealerBusted, dealerBustedInfo), expectedResult);
    }

    @Test
    public void asString_informs_both_players_have_tied(){
        int pushValue = 17;
        String dealerInfo = "Dealer hand";
        String expectedResult = String.format("Game is a push, both players have the score: %d \n Dealer Hand: %s", pushValue, dealerInfo);
        assertEquals(GameOutcome.Push.asString(pushValue, pushValue, dealerInfo), expectedResult);
    }

    @Test
    public void asString_informs_dealer_is_winner_of_game(){
        int dealerScore = 17;
        int playerScore = 16;
        String dealerInfo = "Dealer hand";
        String expectedResult = String.format("Dealer has won. \n Dealer Hand: %s \n Dealer score: %d \n Human score: %d \n", dealerInfo, dealerScore, playerScore);
        assertEquals(GameOutcome.Dealer.asString(playerScore, dealerScore, dealerInfo), expectedResult);
    }

    @Test
    public void asString_informs_player_is_winner_of_game(){
        int dealerScore = 16;
        int playerScore = 17;
        String dealerInfo = "Dealer hand";
        String expectedResult = String.format("Human player has won. \n Human score: %d \n Dealer score: %d \n Dealer Hand: %s \n", playerScore, dealerScore, dealerInfo);
        assertEquals(GameOutcome.Player.asString(playerScore, dealerScore, dealerInfo), expectedResult);
    }
}
