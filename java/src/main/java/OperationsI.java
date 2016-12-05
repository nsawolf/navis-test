import enumerations.Action;

public interface OperationsI {
    DeckI initialGameDeal(HandI dealerHand, HandI playerHand) throws OutOfCardsException;

    Action handlePlayerAction(PlayerI player, HandI playerHand, HandI otherHand, DeckI deck) throws OutOfCardsException;

    Integer getScore(HandI hand);

    boolean gameIsPush(int dealerScore, int playerScore);

    boolean bothPlayersBust(Action playerAction, Action otherAction);

    boolean handIsEmpty(Hand hand);

    void resetHand(PlayerI player);

    void showBotHand(HandI hand);
}
