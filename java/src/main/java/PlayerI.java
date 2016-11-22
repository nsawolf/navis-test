import enumerations.Action;

public interface PlayerI {
    //Returns Hit, Stay, or Busted (based on known cards of the other player's hand)
    Action nextAction(Hand otherHand);

    Hand getHand();

}
