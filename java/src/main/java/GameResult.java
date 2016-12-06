import enumerations.Action;
import enumerations.GameOutcome;
import enumerations.Rank;

import java.util.Set;

public class GameResult implements GameResultI {

    private final int blackJackWin = 21;

    public GameResult(GameOutcome gameOutcome, int dealerScore, int playerScore, String showDealerHand){
        gameOutcome.asString(dealerScore, playerScore, showDealerHand);
    }

    // TODO: Probably belongs in GameResult (more abstractions)
    @Override
    public GameOutcome determineWinner(int dealerScore, int playerScore){
        boolean playerBusted = playerScore > blackJackWin; // TODO: naming. Try to avoid abbreviations
        boolean dealerBusted = dealerScore > blackJackWin;
        boolean playerWon = playerScore > dealerScore || dealerBusted;
        boolean dealerWon = dealerScore > playerScore || playerBusted;

        if (playerWon && !playerBusted){
            return GameOutcome.Player;
        } else if (dealerWon && !dealerBusted){
            return GameOutcome.Dealer;
        }
        return null;
    }

    @Override
    public GameResult resultOfGame(Action humanAction, Action botAction, HandI dealerHand, HandI playerHand) {
        // TODO: refactor. extract method. Would be Easier to test. Complected decisions and string generation. Single-responsibility
        // Possible suggestion: Use enumerations with string values
        int dealerScore = dealerHand.scoreHand();
        int playerScore = playerHand.scoreHand();
        String showDealerHand = dealerHand.visibleHand(false);

        if (bothPlayersBust(botAction, humanAction)) {
            return new GameResult(GameOutcome.BothBusted, dealerScore, playerScore, showDealerHand);
        } else if (gameIsPush(dealerScore, playerScore)) {
            return new GameResult(GameOutcome.Push, dealerScore, playerScore, showDealerHand);
        }

        GameOutcome winner = determineWinner(dealerScore, playerScore);
        return new GameResult(winner, dealerScore, playerScore, showDealerHand);
    }

    @Override
    public boolean gameIsPush(int dealerScore, int playerScore) {
        return dealerScore == playerScore;
    }

    @Override
    public boolean bothPlayersBust(Action playerAction, Action otherAction){
        return playerAction.equals(Action.Busted) && otherAction.equals(Action.Busted);
    }
}
