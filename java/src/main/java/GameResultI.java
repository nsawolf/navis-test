import enumerations.Action;
import enumerations.GameOutcome;

import java.util.Set;

public interface GameResultI {
    GameOutcome determineWinner(int dealerScore, int playerScore);

    GameResult resultOfGame(Action humanAction, Action botAction, HandI dealerHand, HandI playerHand);

    boolean gameIsPush(int dealerScore, int playerScore);

    boolean bothPlayersBust(Action playerAction, Action otherAction);
}
