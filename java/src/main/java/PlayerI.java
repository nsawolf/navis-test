import enumerations.Action;

public interface PlayerI {

    Action nextAction(Hand otherHand);

    Hand getHand();

    String showHand();

}
