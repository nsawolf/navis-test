import enumerations.Action;

import java.util.Set;

public class BotPlayer implements PlayerI {

    public Hand botHand = new Hand();

    @Override
    public Action nextAction(Hand otherHand) {
        ScoreI score = Dependencies.score.make();
        Set<Card> ohand = otherHand.getCards();
        Set<Card> bhand = botHand.getCards();
        Integer bSum = score.scoreHand(bhand);
        Integer oSum = score.scoreHand(ohand);
        if (bSum > 16 && bSum <= 21){
            return Action.Stay;
        } else if (bSum > 21){
            return Action.Busted;
        }
        return Action.Hit;
    }

    @Override
    public Hand getHand() {
        return botHand;
    }


}
