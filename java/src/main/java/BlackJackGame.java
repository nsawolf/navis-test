import enumerations.Action;

/*
class GameResult {

    public String toString() {

    }
}

enum GameResultE {
    BothBusted, Push, Dealer, Player;

    public String asString(int playerScore, int dealerScore) {
        switch (this) {
            case BothBusted:
                return "";
        }
    }
}

*/
public class BlackJackGame implements BlackJackGameI {

    // FIXME break out logic into operations helper method
    @Override
    public GameResult  play() throws OutOfCardsException {
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
