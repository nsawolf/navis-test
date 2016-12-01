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
    public DeckI initialGameDeal(PlayerI botPlayer, PlayerI humanPlayer) throws OutOfCardsException {
        if (!handIsEmpty(botPlayer.getHand())){
            resetHand(botPlayer);
        }
        if (!handIsEmpty(humanPlayer.getHand())){
            resetHand(humanPlayer);
        }
        DeckI deck = Dependencies.deck.make();
        deck.shuffleDeck();
        for (int i = 0; i < 2; i++) {
            dealCardToPlayer(deck, botPlayer);
            dealCardToPlayer(deck, humanPlayer);
        }
        return deck;
    }

    // TODO: code smell. Single-line method
    // This should be part of player, so it is simpler to test
    public void  dealCardToPlayer(DeckI deck, PlayerI player) throws OutOfCardsException{
        // TODO: law of Demeter. don't talk to indirect things
        // To fix: add a method to player for adding a card to their hand
        player.getHand().addCard(deck.dealCard());
    }

    // TODO: law of demeter. Method signature: You don't need the whole other player, just their Hand
    @Override
    public Action handlePlayerAction(PlayerI player, PlayerI otherPlayer, DeckI deck) throws OutOfCardsException{
        Action action = player.nextAction(otherPlayer.getHand());
        while (action.equals(Action.Hit)) {
            dealCardToPlayer(deck, player);
            action = player.nextAction(otherPlayer.getHand());
        }
        return action;
    }

    // TODO: similar to dealCardToPlayer. Factored too much. Probably belongs in Hand
    @Override
    public Integer getScore(PlayerI player){
        ScoreI score = Dependencies.score.make();
        // scoreHand probably takes a hand, which in turn can give access to the set of cards
        return score.scoreHand(player.getHand().getCards());
    }

    @Override
    public boolean gameIsPush(Integer botScore, Integer humanScore){
        return botScore == humanScore;
    }

    @Override
    public boolean bothPlayersBust(Action playerAction, Action otherAction){
        return playerAction.equals(Action.Busted) && otherAction.equals(Action.Busted);
    }

    // TODO: Probably belongs in Hand + PlayerI. Combination of law of demeter and single-responsibility. Operations should not be concerned with message formatting
    @Override
    public void showBotHand(PlayerI botPlayer){
        ConsoleIOI console = Dependencies.console.make();
        console.generateConsoleOutput("Bot's hand is: \n" + botPlayer.getHand().visibleHand(false) + "\n");
    }

    // TODO: Probably belongs in GameResult (more abstractions)
    @Override
    public String determineWinner(Integer botScore, Integer humanScore){
        boolean hWithinBJ = humanScore <= blackJackWin; // TODO: naming. Try to avoid abbreviations
        boolean bWithinBJ = botScore <= blackJackWin;
        boolean hWins = humanScore > botScore || !bWithinBJ;
        boolean bWins = botScore > humanScore || !hWithinBJ;

        if (hWins && hWithinBJ){
            return "Human wins the game with " + humanScore + " bot loses with " + botScore + "\n";
        } else if (bWins && bWithinBJ){
            return "Bot wins the game with " + botScore + " human loses with " + humanScore + "\n";
        }
        return "";
    }
}
