import enumerations.Action;

public interface PlayerI {

    Action nextAction(HandI otherHand);

    HandI getHand();

    String showHand();

}
