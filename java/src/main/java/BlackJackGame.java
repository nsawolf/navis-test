import enumerations.Action;

public class BlackJackGame implements BlackJackGameI {

    @Override
    public GameResultI play() throws OutOfCardsException {
        GameResultI gameResult = Dependencies.gameResult.make();
        OperationsI gameOps = Dependencies.gameOps.make();
        HandI dealer = Dependencies.hand.make();
        HandI player = Dependencies.hand.make();
        Action botAction = Action.Stay;

        gameOps.initialGameDeal(dealer, player);
        Action humanAction = gameOps.handleHumanPlayerAction(player, dealer);
        if (humanAction != Action.Busted) {
            botAction = gameOps.handleDealerAction(dealer, player);
        }
        return gameResult.resultOfGame(humanAction, botAction, dealer, player);
    }

}
