import java.util.Set;

public interface HandI {
    String visibleHand(boolean hideBottom);
    Set<Card> getCards();
    void addCard(Card card);
    int size();
    int scoreHand();
    void resetHand();
}
