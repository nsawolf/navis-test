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

    public void  dealCardToPlayer(DeckI deck, PlayerI player) throws OutOfCardsException{
        player.getHand().addCard(deck.dealCard());
    }

    @Override
    public Action handlePlayerAction(PlayerI player, PlayerI otherPlayer, DeckI deck) throws OutOfCardsException{
        Action action = player.nextAction(otherPlayer.getHand());
        while (action.equals(Action.Hit)) {
            dealCardToPlayer(deck, player);
            action = player.nextAction(otherPlayer.getHand());
        }
        return action;
    }

    @Override
    public Integer getScore(PlayerI player){
        ScoreI score = Dependencies.score.make();
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

    @Override
    public void showBotHand(PlayerI botPlayer){
        ConsoleIOI console = Dependencies.console.make();
        console.generateConsoleOutput("Bot's hand is: \n" + botPlayer.getHand().visibleHand(false) + "\n");
    }

    @Override
    public String determineWinner(Integer botScore, Integer humanScore){
        boolean hWithinBJ = humanScore <= blackJackWin;
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
