import enumerations.Action;

import java.util.List;

public class Operations implements OperationsI {

    private Integer blackJackWin = 21;
    private DeckI deck = Dependencies.deck.make();

    @Override
    public DeckI initialGameDeal(HandI dealerHand, HandI playerHand) throws OutOfCardsException {
        deck.shuffleDeck();
        for (int i = 0; i < 2; i++) {
            dealerHand.addCard(deck.dealCard());
            playerHand.addCard(deck.dealCard());
        }
        return deck;
    }

    @Override
    public Action handleHumanPlayerAction(HandI playerHand, HandI otherHand) throws OutOfCardsException{
        PlayerI humanPlayer = Dependencies.humanPlayer.make();
        Action action = humanPlayer.nextAction(otherHand);
        while (action.equals(Action.Hit)) {
            playerHand.addCard(deck.dealCard());
            action = humanPlayer.nextAction(otherHand);
        }
        return action;
    }

    @Override
    public Action handleDealerAction(HandI dealerHand, HandI otherHand) throws OutOfCardsException{
        PlayerI dealer = Dependencies.botPlayer.make();
        Action action = dealer.nextAction(otherHand);
        while(action.equals(Action.Hit)){
            dealerHand.addCard(deck.dealCard());
            action = dealer.nextAction(otherHand);
        }
        return action;
    }

}
