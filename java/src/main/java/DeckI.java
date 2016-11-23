public interface DeckI {
    void shuffleDeck();

    Card dealCard() throws OutOfCardsException;

    int size();
}
