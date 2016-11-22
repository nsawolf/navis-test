public interface DeckI {
    void shuffleDeck(Long seed);

    Card dealCard() throws OutOfCardsException;

    int size();
}
