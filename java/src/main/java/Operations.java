import enumerations.Action;

import java.util.List;

public class Operations implements OperationsI {

    private Integer blackJackWin = 21;

    @Override
    public DeckI initialGameDeal(PlayerI botPlayer, PlayerI humanPlayer) throws OutOfCardsException {
        DeckI deck = Dependencies.deck.make();
        deck.shuffleDeck(Dependencies.now.make().getTime());
        for (int i = 0; i < 2; i++) {
            botPlayer.getHand().addCard(deck.dealCard());
            humanPlayer.getHand().addCard(deck.dealCard());
        }
        return deck;
    }

    @Override
    public Action handlePlayerAction(PlayerI player, PlayerI otherPlayer) throws OutOfCardsException{
        DeckI deck = initialGameDeal(player, otherPlayer);
        Action action = player.nextAction(otherPlayer.getHand());
        while (action.equals(Action.Hit)) {
            player.getHand().addCard(deck.dealCard());
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
    public String determineWinner(Integer botScore, Integer humanScore){
        boolean hWithinBJ = humanScore <= blackJackWin;
        boolean bWithinBJ = botScore <= blackJackWin;
        boolean hWins = humanScore > botScore;

        if (hWins && hWithinBJ){
            return "Human wins the game with " + humanScore + " bot loses with " + botScore;
        } else if (!hWins && bWithinBJ){
            return "Bot wins the game with " + botScore + " human loses with " + humanScore;
        }
        return "";
    }
}
