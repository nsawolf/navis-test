import enumerations.Action;

public interface OperationsI {
    DeckI initialGameDeal(HandI dealerHand, HandI playerHand) throws OutOfCardsException;

    Action handleHumanPlayerAction(HandI playerHand, HandI otherHand) throws OutOfCardsException;

    Action handleDealerAction(HandI dealerHand, HandI otherHand) throws OutOfCardsException;
}
