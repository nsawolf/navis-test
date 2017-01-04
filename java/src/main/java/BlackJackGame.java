import enumerations.Action;

public class BlackJackGame {
    public GameResult play() throws OutOfCardsException {
        GameResult gameResult = Dependencies.gameResult.make();
        Operations gameOps = Dependencies.gameOps.make();
        Hand player = Dependencies.hand.make();
        Hand dealer = Dependencies.hand.make();
        Action botAction = Action.Stay;

        gameOps.initialGameDeal(dealer, player);
        Action humanAction = gameOps.handleHumanPlayerAction(player, dealer);
        if (humanAction != Action.Busted) {
            botAction = gameOps.handleDealerAction(dealer, player);
        }
        return gameResult.resultOfGame(humanAction, botAction, dealer, player);
    }

}
