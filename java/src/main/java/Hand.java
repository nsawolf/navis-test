import enumerations.Rank;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Hand {

    private List<Card> cards = new ArrayList<Card>();
    public String obscurredCard = "****";

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

    public Set<Card> getCards() {
        return new HashSet<Card>(cards);
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public int size() {
        return cards.size();
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hand hand = (Hand) o;

        if (cards != null ? !cards.equals(hand.cards) : hand.cards != null) return false;
        return obscurredCard != null ? obscurredCard.equals(hand.obscurredCard) : hand.obscurredCard == null;

    }

    @Override
    public int hashCode() {
        int result = cards != null ? cards.hashCode() : 0;
        result = 31 * result + (obscurredCard != null ? obscurredCard.hashCode() : 0);
        return result;
    }
}
