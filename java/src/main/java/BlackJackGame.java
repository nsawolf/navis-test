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
    public String play() throws OutOfCardsException {
        OperationsI gameOps = Dependencies.gameOps.make();
        PlayerI botPlayer = Dependencies.botPlayer.make();
        PlayerI humanPlayer = Dependencies.humanPlayer.make();

        DeckI deck = gameOps.initialGameDeal(botPlayer, humanPlayer);
        Action humanAction = gameOps.handlePlayerAction(humanPlayer, botPlayer, deck);
        Action botAction = gameOps.handlePlayerAction(botPlayer, humanPlayer, deck);

        // TODO: prefer primitive types of OO types
        Integer botScore = gameOps.getScore(botPlayer);
        Integer humanScore = gameOps.getScore(humanPlayer);

        // TODO: refactor. extract method. Would be Easier to test. Complected decisions and string generation. Single-responsibility
        // Possible suggestion: Use enumerations with string values
        if (gameOps.bothPlayersBust(botAction, humanAction)) {
            gameOps.showBotHand(botPlayer);
            return "Both players bust at (" + botScore + ") bot and (" + humanScore + ")\n"; //new GameResult(GameResultE.BothBusted, botPlayer, humanPlayer);
        } else if (gameOps.gameIsPush(botScore, humanScore)) {
            gameOps.showBotHand(botPlayer);
            return "Game is a push at " + humanScore + "\n";
        }
        gameOps.showBotHand(botPlayer);
        return gameOps.determineWinner(botScore, humanScore);
    }


}
