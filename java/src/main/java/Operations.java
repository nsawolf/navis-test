import enumerations.Action;

import java.util.List;

public class Operations implements OperationsI {

    private Integer blackJackWin = 21;

    // TODO break out into deal card.. this is common among initial and hit no need to
    // make it separate.  Do some head stands tonight and think about these things. :D
    @Override
    public DeckI initialGameDeal(PlayerI botPlayer, PlayerI humanPlayer) throws OutOfCardsException {
        DeckI deck = Dependencies.deck.make();
        deck.shuffleDeck(Dependencies.now.make().getTime());
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
    public String determineWinner(Integer botScore, Integer humanScore){
        boolean hWithinBJ = humanScore <= blackJackWin;
        boolean bWithinBJ = botScore <= blackJackWin;
        boolean hWins = humanScore > botScore;

        if (hWins && hWithinBJ){
            return "Human wins the game with " + humanScore + " bot loses with " + botScore + "\n";
        } else if (!hWins && bWithinBJ){
            return "Bot wins the game with " + botScore + " human loses with " + humanScore + "\n";
        }
        return "";
    }
}
