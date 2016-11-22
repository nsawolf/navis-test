import enumerations.Action;

import java.util.List;

public class Operations {

    public DeckI initialGameDeal(BotPlayer botPlayer, HumanPlayer humanPlayer) throws OutOfCardsException {
        DeckI deck = Dependencies.deck.make();
        deck.shuffleDeck(Dependencies.now.make().getTime());
        for (int i = 0; i < 2; i++) {
            botPlayer.getHand().addCard(deck.dealCard());
            humanPlayer.getHand().addCard(deck.dealCard());
        }
        return deck;
    }

    public Action handlePlayerAction(PlayerI player, PlayerI otherPlayer, DeckI deck) throws OutOfCardsException {
        Action action = player.nextAction(otherPlayer.getHand());
        while (action.equals(Action.Hit)) {
            player.getHand().addCard(deck.dealCard());
            action = player.nextAction(otherPlayer.getHand());
        }
        return action;
    }

    public String determineWinner(PlayerI botPlayer, PlayerI humanPlayer){
        ScoreI score = Dependencies.score.make();
        return null;
    }
}
