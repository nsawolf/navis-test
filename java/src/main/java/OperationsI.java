import enumerations.Action;

public interface OperationsI {
    DeckI initialGameDeal(HandI dealerHand, HandI playerHand) throws OutOfCardsException;

    Action handlePlayerAction(PlayerI player, HandI playerHand, HandI otherHand, DeckI deck) throws OutOfCardsException;
}
