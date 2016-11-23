import enumerations.Action;

public interface OperationsI {
    DeckI initialGameDeal(PlayerI botPlayer, PlayerI humanPlayer) throws OutOfCardsException;

    void dealCardToPlayer(DeckI deck, PlayerI player) throws OutOfCardsException;

    Action handlePlayerAction(PlayerI player, PlayerI otherPlayer, DeckI deck) throws OutOfCardsException;

    Integer getScore(PlayerI player);

    boolean gameIsPush(Integer botScore, Integer humanScore);

    boolean bothPlayersBust(Action playerAction, Action otherAction);

    String determineWinner(Integer botScore, Integer humanScore);
}
