import enumerations.Action;
import enumerations.Rank;

import java.util.Set;

public class GameResult implements GameResultI {

    private final int blackJackWin = 21;
    // TODO: Probably belongs in GameResult (more abstractions)
    @Override
    public String determineWinner(int dealerScore, int playerScore){
        boolean playerBusted = playerScore > blackJackWin; // TODO: naming. Try to avoid abbreviations
        boolean dealerBusted = dealerScore > blackJackWin;
        boolean playerWon = playerScore > dealerScore || dealerBusted;
        boolean dealerWon = dealerScore > playerScore || playerBusted;

        if (playerWon && !playerBusted){
            return "Human wins the game with " + playerScore + " bot loses with " + dealerScore + "\n";
        } else if (dealerWon && !dealerBusted){
            return "Bot wins the game with " + dealerScore + " human loses with " + playerScore + "\n";
        }
        return "";
    }

    @Override
    public String resultOfGame(OperationsI gameOps, PlayerI dealer, Action humanAction, Action botAction, int dealerScore, int playerScore) {
        // TODO: refactor. extract method. Would be Easier to test. Complected decisions and string generation. Single-responsibility
        // Possible suggestion: Use enumerations with string values
        if (gameOps.bothPlayersBust(botAction, humanAction)) {
            gameOps.showBotHand(dealer.getHand());
            return "Both players bust at (" + dealerScore + ") bot and (" + playerScore + ")\n"; //new GameResult(GameResultE.BothBusted, botPlayer, humanPlayer);
        } else if (gameOps.gameIsPush(dealerScore, playerScore)) {
            gameOps.showBotHand(dealer.getHand());
            return "Game is a push at " + playerScore + "\n";
        }
        gameOps.showBotHand(dealer.getHand());
        return determineWinner(dealerScore, playerScore);
    }

}
