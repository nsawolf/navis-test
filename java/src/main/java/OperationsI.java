import enumerations.Action;

public interface OperationsI {
    DeckI initialGameDeal(PlayerI botPlayer, PlayerI humanPlayer) throws OutOfCardsException;

    Action handlePlayerAction(PlayerI player, PlayerI otherPlayer) throws OutOfCardsException;

    Integer getScore(PlayerI player);

    boolean gameIsPush(Integer botScore, Integer humanScore);

    boolean bothPlayersBust(Action playerAction, Action otherAction);

    String determineWinner(Integer botScore, Integer humanScore);
}
