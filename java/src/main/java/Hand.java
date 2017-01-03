import enumerations.Rank;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Hand implements HandI {

    private List<Card> cards = new ArrayList<Card>();
    public String obscurredCard = "****";

    @Override
    public String visibleHand(boolean hideBottom) {
        String cardstr = "";
        int cnt = 0;
        if (cards.size() > 0) {
            for (Card card : cards) {
                if (hideBottom && cards.get(0).equals(card)) {
                    cardstr += obscurredCard + ",";
                    continue;
                }
                cardstr += card.toString() + ",";
            }
            return cardstr.substring(0, cardstr.lastIndexOf(","));
        }
        return cardstr;
    }

    @Override
    public Set<Card> getCards() {
        return new HashSet<Card>(cards);
    }

    @Override
    public void addCard(Card card) {
        cards.add(card);
    }

    @Override
    public int size() {
        return cards.size();
    }

    @Override
    public int scoreHand(){
        final int blackjack = 21;
        int calculated = cards.parallelStream().map(card -> card.rank.getValue()).reduce(0, (x, y) -> x + y);
        if (cards.toString().contains(Rank.Ace.name())) {
            int adjValue = calculated + 10;
            if (adjValue <= 21){
                return adjValue;
            }
        }
        return calculated;
    }
}
