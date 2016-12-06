import enumerations.Action;

public class BlackJackGame implements BlackJackGameI {

    @Override
    public GameResultI play() throws OutOfCardsException {
        GameResultI gameResult = Dependencies.gameResult.make();
        OperationsI gameOps = Dependencies.gameOps.make();
        PlayerI dealer = Dependencies.botPlayer.make();
        PlayerI player = Dependencies.humanPlayer.make();

        DeckI deck = gameOps.initialGameDeal(dealer.getHand(), player.getHand());
        Action humanAction = gameOps.handlePlayerAction(player, player.getHand(), dealer.getHand(), deck);
        Action botAction = gameOps.handlePlayerAction(dealer, dealer.getHand(), player.getHand(), deck);

        return gameResult.resultOfGame(humanAction, botAction, dealer.getHand(), player.getHand());
    }

}
