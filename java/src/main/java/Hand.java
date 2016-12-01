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

    // NOTE: desirable behavior (that you have): modifying the set returned from getCards is isolated from Hand
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
    public void resetHand() {cards = new ArrayList<Card>();}
}
