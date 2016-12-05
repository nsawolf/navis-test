import enumerations.Action;

import java.util.Set;

public interface GameResultI {
    String determineWinner(int dealerScore, int playerScore);
    String resultOfGame(OperationsI gameOps, PlayerI dealer, Action humanAction, Action botAction, int dealerScore, int playerScore);
}
