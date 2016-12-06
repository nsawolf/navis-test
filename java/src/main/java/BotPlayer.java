import enumerations.Action;

import java.util.Set;

public class BotPlayer implements PlayerI {

    public HandI dealerHand = new Hand();

    @Override
    public Action nextAction(HandI otherHand) {
        final int stayScore = 16;
        final int blackJackScore = 21;

        int dealerScore = dealerHand.scoreHand();
//        int otherSum = otherHand.scoreHand();

        if (dealerScore > stayScore && dealerScore <= blackJackScore){
            return Action.Stay;
        } else if (dealerScore > blackJackScore){
            return Action.Busted;
        }
        return Action.Hit;
    }

    @Override
    public HandI getHand() {
        return dealerHand;
    }

    @Override
    public String showHand(){
        return dealerHand.visibleHand(false);
    }
}
