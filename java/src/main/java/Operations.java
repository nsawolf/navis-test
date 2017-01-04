import enumerations.Action;

import java.util.List;

public class Operations  {

    private Deck deck = Dependencies.deck.make();

    public void initialGameDeal(Hand dealerHand, Hand playerHand) throws OutOfCardsException {
        deck.shuffleDeck();
        for (int i = 0; i < 2; i++) {
            dealerHand.addCard(deck.dealCard());
            playerHand.addCard(deck.dealCard());
        }
    }

    public Action handleHumanPlayerAction(Hand playerHand, Hand otherHand) throws OutOfCardsException{
        PlayerI humanPlayer = Dependencies.humanPlayer.make(playerHand);
        Action action = humanPlayer.nextAction(otherHand);
        while (action.equals(Action.Hit)) {
            playerHand.addCard(deck.dealCard());
            action = humanPlayer.nextAction(otherHand);
        }
        return action;
    }

    public Action handleDealerAction(Hand dealerHand, Hand otherHand) throws OutOfCardsException{
        PlayerI dealer = Dependencies.botPlayer.make(dealerHand);
        Action action = dealer.nextAction(otherHand);
        while(action.equals(Action.Hit)){
            dealerHand.addCard(deck.dealCard());
            action = dealer.nextAction(otherHand);
        }
        return action;
    }

}
