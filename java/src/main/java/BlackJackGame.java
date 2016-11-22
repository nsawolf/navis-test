import enumerations.Action;

public class BlackJackGame implements BlackJackGameI {

    // FIXME break out logic into operations helper method
    @Override
    public String play() throws OutOfCardsException {
        DeckI deck = Dependencies.deck.make();
        deck.shuffleDeck(Dependencies.now.make().getTime());
        PlayerI botPlayer = Dependencies.botPlayer.make();
        PlayerI humanPlayer = Dependencies.humanPlayer.make();
        ScoreI score = Dependencies.score.make();
        for (int i = 0; i < 2; i++) {
            botPlayer.getHand().addCard(deck.dealCard());
            humanPlayer.getHand().addCard(deck.dealCard());
        }

        // human player actions
        Action humanAction = humanPlayer.nextAction(botPlayer.getHand());
        Action botAction = botPlayer.nextAction(humanPlayer.getHand());

        while (humanAction.equals(Action.Hit)) {
            humanPlayer.getHand().addCard(deck.dealCard());
            humanAction = humanPlayer.nextAction(botPlayer.getHand());
        }
        if (humanAction.equals(Action.Stay) || humanAction.equals(Action.Busted)) {
            while (botAction.equals(Action.Hit)) {
                botPlayer.getHand().addCard(deck.dealCard());
                botAction = botPlayer.nextAction(humanPlayer.getHand());
            }
        }
        Integer botScore = score.scoreHand(botPlayer.getHand().getCards());
        Integer humanScore = score.scoreHand(humanPlayer.getHand().getCards());
        if (humanAction.equals(Action.Busted) && botAction.equals(Action.Busted)) {
            return "No one wins this game, both players busted. Human score " + humanScore + " Bot score " + botScore;
        }
        if (humanScore <= 21 && (humanScore > botScore || botAction.equals(Action.Busted))) {
            return "Human player wins the game with " + humanScore + " Bot loses with " + botScore;
        }
        if (botScore == humanScore) {
            return "Game is a draw at " + botScore;
        }

         return "Bot player wins the game with " + botScore + " Human loses with " + humanScore;
    }


}
