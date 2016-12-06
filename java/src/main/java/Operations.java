import enumerations.Action;

import java.util.List;

public class Operations implements OperationsI {

    private Integer blackJackWin = 21;

    @Override
    public DeckI initialGameDeal(HandI dealerHand, HandI playerHand) throws OutOfCardsException {
       // TODO: Remove resetting of game should happen when new play() happens in mainGuy
//        if (!handIsEmpty(dealerHand.getCards())){
//            resetHand(botPlayer);
//        }
//        if (!handIsEmpty(humanPlayer.getHand())){
//            resetHand(humanPlayer);
//        }
        DeckI deck = Dependencies.deck.make();
        deck.shuffleDeck();
        for (int i = 0; i < 2; i++) {
            dealerHand.addCard(deck.dealCard());
            playerHand.addCard(deck.dealCard());
        }
        return deck;
    }

    // TODO: law of demeter. Method signature: You don't need the whole other player, just their Hand
    @Override
    public Action handlePlayerAction(PlayerI player, HandI playerHand, HandI otherHand, DeckI deck) throws OutOfCardsException{
        Action action = player.nextAction(otherHand);
        while (action.equals(Action.Hit)) {
            playerHand.addCard(deck.dealCard());
            action = player.nextAction(otherHand);
        }
        return action;
    }

}
