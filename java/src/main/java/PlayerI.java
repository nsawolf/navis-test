import enumerations.Action;

public interface PlayerI {
    //Returns Hit, Stay, or Busted (based on known cards of the other player's hand)
    Action nextAction(HandI otherHand);

    HandI getHand();

}
