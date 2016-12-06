import enumerations.Action;
import enumerations.GameOutcome;
import enumerations.Rank;

import java.util.Set;

public class GameResult implements GameResultI {

    private String gameResultString = null;
    public GameResult(){}

    public GameResult(GameOutcome gameOutcome, int dealerScore, int playerScore, String showDealerHand){
        this.gameResultString = gameOutcome.asString(dealerScore, playerScore, showDealerHand);
    }

    // TODO: Probably belongs in GameResult (more abstractions)
    @Override
    public GameOutcome determineWinner(int dealerScore, int playerScore){
        final int blackJackWin = 21;
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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return  gameResultString;
    }
}
