import enumerations.Action;

import java.util.List;

public class Operations implements OperationsI {

    private Integer blackJackWin = 21;

    @Override
    public void resetHand(PlayerI player){
        player.getHand().resetHand();
    }

    @Override
    public boolean handIsEmpty(Hand hand){
        return hand.size() == 0;
    }

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

    // TODO: similar to dealCardToPlayer. Factored too much. Probably belongs in Hand
    @Override
    public Integer getScore(HandI hand){
        // scoreHand probably takes a hand, which in turn can give access to the set of cards
        return hand.scoreHand();
    }

    @Override
    public boolean gameIsPush(int dealerScore, int playerScore) {
        return dealerScore == playerScore;
    }

    @Override
    public boolean bothPlayersBust(Action playerAction, Action otherAction){
        return playerAction.equals(Action.Busted) && otherAction.equals(Action.Busted);
    }

    // TODO: Probably belongs in Hand + PlayerI. Combination of law of demeter and single-responsibility. Operations should not be concerned with message formatting
    @Override
    public void showBotHand(HandI hand){
        ConsoleIOI console = Dependencies.console.make();
        console.generateConsoleOutput("Bot's hand is: \n" + hand.visibleHand(false) + "\n");
    }

}
