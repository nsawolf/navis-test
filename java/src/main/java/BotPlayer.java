import enumerations.Action;

import java.util.Set;

public class BotPlayer implements PlayerI {

    private Hand dealerHand = null;

    public BotPlayer(Hand dealerHand){
        this.dealerHand = dealerHand;
    }

    @Override
    public Action nextAction(Hand otherHand) {
        final int stayScore = 16;
        final int blackJackScore = 21;

        int dealerScore = dealerHand.scoreHand();

        if (dealerScore > stayScore && dealerScore <= blackJackScore){
            return Action.Stay;
        } else if (dealerScore > blackJackScore){
            return Action.Busted;
        }
        return Action.Hit;
    }

    @Override
    public Hand getHand() {
        return dealerHand;
    }

    @Override
    public String showHand(){
        return dealerHand.visibleHand(false);
    }
}
