import enumerations.Rank;

import java.util.Set;

public class Score implements ScoreI{
    @Override
    public Integer scoreHand(Set<Card> hand) {
        Integer hSum = 0;

        for(Card card : hand){
            hSum += card.rank.getValue();
        }
        if (hand.toString().contains(Rank.Ace.name())){
            Integer tempSum = hSum + 10;
        if (tempSum <= 21){
                hSum = tempSum;
            }
        }
        return hSum;
    }
}
