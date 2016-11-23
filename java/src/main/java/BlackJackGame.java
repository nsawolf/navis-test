import enumerations.Action;

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

        Integer botScore = gameOps.getScore(botPlayer);
        Integer humanScore = gameOps.getScore(humanPlayer);

        if (gameOps.bothPlayersBust(botAction, humanAction)) {
            return "Both players bust at (" + botScore + ") bot and (" + humanScore + ")\n";
        } else if (gameOps.gameIsPush(botScore, humanScore)) {
            return "Game is a push at " + humanScore +"\n";
        }
        return gameOps.determineWinner(botScore, humanScore);
    }
}
