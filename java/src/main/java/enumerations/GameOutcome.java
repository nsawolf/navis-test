package enumerations;

public enum GameOutcome {
    BothBusted, Push, Dealer, Player;

    public String asString(int playerScore, int dealerScore, String showDealerHand) {
        switch (this) {
            case BothBusted:
                return String.format("Both players have busted. \n Dealer Hand: %s \n Human score: %d \n Dealer score: %d \n ", showDealerHand, playerScore, dealerScore);
            case Push:
                return String.format("Game is a push, both players have the score: %d \n Dealer Hand: %s", playerScore, showDealerHand);
            case Dealer:
                return String.format("Dealer has won. \n Dealer Hand: %s \n Dealer score: %d \n Human score: %d \n", showDealerHand, dealerScore, playerScore);
            case Player:
                return String.format("Human player has won. \n Human score: %d \n Dealer score: %d \n Dealer Hand: %s \n", playerScore, dealerScore, showDealerHand);
        }
        return null;
    }
}