import enumerations.Action;

public class BlackJackGame implements BlackJackGameI {

    // FIXME break out logic into operations helper method
    @Override
    public String play() throws OutOfCardsException {
        OperationsI gameOps = Dependencies.gameOps.make();
        PlayerI botPlayer = Dependencies.botPlayer.make();
        PlayerI humanPlayer = Dependencies.humanPlayer.make();

        Action humanAction = gameOps.handlePlayerAction(humanPlayer, botPlayer);
        Action botAction = gameOps.handlePlayerAction(botPlayer, humanPlayer);

        Integer botScore = gameOps.getScore(botPlayer);
        Integer humanScore = gameOps.getScore(humanPlayer);

        if (gameOps.bothPlayersBust(botAction, humanAction)) {
            return "Both players bust at (" + botScore + ") bot and (" + humanScore + ")";
        } else if (gameOps.gameIsPush(botScore, humanScore)) {
            return "Game is a push at " + humanScore;
        }
        return gameOps.determineWinner(botScore, humanScore);
    }
}
